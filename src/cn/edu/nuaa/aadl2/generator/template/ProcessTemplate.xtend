package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.ProcessSubcomponent
import org.osate.aadl2.ProcessType
import org.osate.aadl2.ProcessImplementation

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.utils.PropertyParser.*
import static extension cn.edu.nuaa.aadl2.generator.template.ThreadTemplate.*

import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.Subcomponent
import org.osate.aadl2.ThreadSubcomponent
import org.osate.aadl2.Element
import org.osate.aadl2.impl.DefaultAnnexSubclauseImpl
import org.osate.aadl2.impl.AnnexSubclauseImpl
import org.osate.ba.aadlba.impl.BehaviorAnnexImpl
import org.osate.ba.aadlba.BehaviorActionBlock
import org.osate.ba.aadlba.BehaviorVariable
import org.osate.ba.aadlba.BehaviorTransition
import org.osate.ba.aadlba.BehaviorState
import org.osate.ba.aadlba.impl.ValueExpressionImpl
import org.osate.aadl2.impl.DataImplementationImpl
import org.osate.aadl2.impl.DataTypeImpl
import org.osate.ba.aadlba.impl.RelationImpl
import org.osate.ba.utils.AadlBaVisitors
import org.osate.ba.aadlba.impl.DataComponentReferenceImpl
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl
import org.osate.aadl2.DataClassifier
import org.osate.ba.aadlba.impl.DataSubcomponentHolderImpl

class ProcessTemplate {
	
	public static String init='''
		int time_cycle;//
		int time_current;//
		int ticks_pass;//
	'''
	public static String time_current="time_current"
	public static String time_cycle="time_cycle"
	
	def static create(ProcessSubcomponent subcomponent)'''
		«Tools.createFile(subcomponent.classifier.name.toLowerCase.replace(".","_")+"_main.c",subcomponent.template.toString)»
		«Tools.createFile(subcomponent.classifier.name.toLowerCase.replace(".","_")+".h",subcomponent.head.toString)»		
	'''
	
	def static head(ProcessSubcomponent subcomponent){
		var process=subcomponent.classifier
		
		switch process{
			ProcessType : '''
			33
				/*process type*/
				«Template.head»								
				int «process.name.convert»_init();
				int timer_schedule();
				int «process.name.convert»_init_task();
				
			'''
			ProcessImplementation : '''
			41
				/*process implementation*/
				«Template.head»								
				void «process.name.convert»_init();
				void timer_schedule();
				void «process.name.convert»_init_task();
				«IF process.allSubcomponents!=null»
					«FOR Subcomponent sub : process.allSubcomponents»
							«switch sub{
							ThreadSubcomponent : '''
								void «sub.classifier.name.convert»();
							'''
						}»
					«ENDFOR»
				«ENDIF»	
			'''
		}
	}
	
	def static template(ProcessSubcomponent subcomponent){
		var process=subcomponent.classifier
		var children=process.children
	
		switch process{
			
			ProcessType : '''
				«process.allFeatures.toString»
				/*process type*/
				«Template.head»
			'''
			ProcessImplementation : '''
				«children»
					«IF children!==null»
						«FOR Element sub :children»
						«switch sub{
								DefaultAnnexSubclauseImpl : '''
									«Tools.addContent(Template.systemheadfile,"有个行为附件")»
									有个行为附件
									«FOR Element sub2 :sub.children»
											«switch sub2{
														BehaviorAnnexImpl : '''
														
															«FOR Element sub3 :sub2.children»
																	«switch sub3{
																			BehaviorVariable : 
																				sub3.template
																			BehaviorTransition:
																				sub3.template
																			BehaviorState:
																				sub3.template
																	}»
															«ENDFOR»						                
															'''
											}»
									«ENDFOR»
								'''
						}»
						«ENDFOR»
					«ENDIF»					

				/*process implementation*/
				«Template.head»
				#include "«Template.systemheadfile»"
				#include "«process.name.convert+".h"»"
				
				int main()
				{
					«IF process.allSubcomponents!==null»
						«FOR Subcomponent sub : process.allSubcomponents»
							«switch sub{
								ThreadSubcomponent : '''
									«Tools.addContent(Template.systemheadfile,"int "+sub.classifier.name.convert+"_time;")»
									«sub.create»
								'''
							}»
						«ENDFOR»
					«ENDIF»	
					«process.name.convert»_init();
					«process.name.convert»_init_task();					
					«IF process.type.getAllFeatures!=null»						
					«ENDIF»
					«IF subcomponent.processor!=null»
						«IF subcomponent.processor.schedule!=null»
							«IF subcomponent.processor.schedule.equalsIgnoreCase("FixedTimeLine")»
								/*FixedTimeLine*/
								
								timer_schedule();															
							«ENDIF»
							«IF subcomponent.processor.schedule.equalsIgnoreCase("Round_Robin_Protocol")»
								/*Round_Robin_Protocol*/
								
							«ENDIF»
							«IF subcomponent.processor.schedule.equalsIgnoreCase("RMS")»
								/*RMS*/
								
							«ENDIF»
							«IF subcomponent.processor.schedule.equalsIgnoreCase("Round_Robin_Protocol")»
								/*EDF*/
								
							«ENDIF»
						«ENDIF»
					«ENDIF»
				}
				/*init task*/
				int «process.name.convert»_init_task()
				{
					«IF process.allSubcomponents!=null»
						«process.allFeatures.toString»
						«FOR Subcomponent sub : process.allSubcomponents»
						«sub.name»
						
							«switch sub{
								ThreadSubcomponent : '''
									if ( tsk_create("«sub.classifier.name.convert»",«sub.priority», 0,"", 0,(FUNCPTR)«sub.classifier.name.convert», 0, 0, 0) == ERROR)
									{
										printf("ERR:Memory Not Enough!\n");	
										tsk_suspend(0);	
									}
								'''
							}»
						«ENDFOR»
					«ENDIF»
					/*任务创建完之后挂起任务*/
					tsk_suspend(0);	
				}
				
				/*init process*/
				int «process.name.convert»_init()
				{
					«Tools.addContent(Template.systemheadfile,init)»
					time_current=-1;
					ticks_pass=0;
					time_cycle=«process.period»;
					«IF process.allSubcomponents!=null»
						«FOR Subcomponent sub : process.allSubcomponents»
							«switch sub{
								ThreadSubcomponent : '''
									«Tools.addContent(Template.systemheadfile,"int "+sub.classifier.name.convert+"_time;")»
									«Tools.addContent(Template.systemheadfile,"short "+sub.classifier.name.convert+"_finish;")»
									«Tools.addContent(Template.systemheadfile,"int "+sub.classifier.name.convert+"_failure_count;")»
									«Tools.addContent(Template.systemheadfile,"int "+sub.classifier.name.convert+"_begin;")»
									«Tools.addContent(Template.systemheadfile,"int "+sub.classifier.name.convert+"_end;")»
									«sub.classifier.name.convert+"_time"»=«sub.period»;
									«sub.classifier.name.convert+"_finish"»=1;
									«sub.classifier.name.convert+"_failure_count"»=1;
									«IF sub.time_Window!=null»
										«sub.classifier.name.convert+"_begin"»=«sub.time_Window.get(0)»;
										«sub.classifier.name.convert+"_end"»=«sub.time_Window.get(1)»;
									«ENDIF»
								'''
							}»
						«ENDFOR»
					«ENDIF»
					
				}
				/*for fixedtimeline*/
				int timer_schedule()
				{
					time_current++;
					time_current=(time_current+time_cycle)%time_cycle;
				}
				
				
			'''
		}
	}
	
	
	def static template(BehaviorVariable bv){
	'''
	«switch bv.dataClassifier{	
					DataImplementationImpl:
							'''«bv.name» is a «bv.dataClassifier.name»'''
					DataTypeImpl:
							'''«bv.name» is a «bv.dataClassifier.name»'''
				}»
	BehaviorVariable end!
	
	'''
	}											
	
	
	def static template(BehaviorTransition bt){
	'''
	
	 BehaviorTransition: «bt.name»
		«IF bt.condition!=null»
		«bt.condition.children»
		«FOR Element condition :bt.condition.children»
			«switch condition{	
				RelationImpl:
					{		
					var k=condition.firstExpression.terms.get(0).getFactors.get(0).getFirstValue
					switch k{
						DataComponentReferenceImpl:
						{
							
						var kk=	k.data.get(1)
							switch kk{
								BehaviorVariableHolderImpl:
									kk.getBehaviorVariable.name
								DataSubcomponentHolderImpl:
									kk.getSubcomponent
								
							}
						}
						default:
							k
					}
					
					}
			}»
		«ENDFOR»	
		«ENDIF»		
		«IF bt.condition==null»
		NO condition NO condition NO condition 
		«ENDIF»			
		this is actionBlock «bt.actionBlock»
		«IF bt.actionBlock!=null»
			«bt.actionBlock.children»		
		«ENDIF»	
		from «bt.sourceState.name» to «bt.destinationState.name»
		END of one Transition.
		
		'''
	}
	
	
	def static template(BehaviorState bs){
	'''
	AadlBaVisitors:	«AadlBaVisitors.getTransitionWhereSrc(bs)»
	«bs.name»
	is initial? «bs.isInitial»
	is final? «bs.isFinal»
	END of one state.
	'''	
	}
	
	
	
	
	
	
	def static init(ProcessImplementation process)'''
		«Tools.addContent(process.name.convert+".h","int "+process.name.convert+"_cycle"+";")»
		«Tools.addContent(process.name.convert+".h","int "+process.name.convert+"_current"+";")»
	'''
	
	
}