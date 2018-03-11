package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.ProcessSubcomponent
import org.osate.aadl2.ProcessType
import org.osate.aadl2.ProcessImplementation
import static extension cn.edu.nuaa.aadl2.generator.utils.Tools.*
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
import org.osate.aadl2.impl.DataImplementationImpl
import org.osate.aadl2.impl.DataTypeImpl
import org.osate.ba.aadlba.impl.RelationImpl
import org.osate.ba.utils.AadlBaVisitors
import org.osate.ba.aadlba.impl.DataComponentReferenceImpl
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl
import org.osate.aadl2.DataClassifier
import org.osate.ba.aadlba.impl.DataSubcomponentHolderImpl
import org.osate.ba.aadlba.Value
import org.osate.ba.aadlba.impl.BehaviorIntegerLiteralImpl
import org.osate.ba.aadlba.impl.BehaviorBooleanLiteralImpl
import org.osate.ba.aadlba.impl.BehaviorRealLiteralImpl
import org.osate.ba.aadlba.impl.BehaviorActionSequenceImpl
import org.osate.ba.aadlba.impl.BehaviorTimeImpl
import org.osate.ba.aadlba.impl.PortSendActionImpl
import org.osate.ba.aadlba.impl.AssignmentActionImpl
import org.osate.ba.aadlba.Relation
import org.osate.ba.aadlba.Target
import org.osate.ba.aadlba.BehaviorAction
import org.osate.ba.aadlba.impl.PortDequeueActionImpl
import org.osate.ba.aadlba.BehaviorCondition
import org.osate.ba.aadlba.ExecuteCondition
import org.osate.ba.aadlba.ValueExpression
import org.osate.ba.aadlba.impl.ValueExpressionImpl
import org.osate.ba.aadlba.impl.DispatchConditionImpl
import org.osate.ba.aadlba.impl.OtherwiseImpl
import org.osate.ba.aadlba.LogicalOperator

class ProcessTemplate1 {
	
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
				/*process type*/
				«Template.head»								
				int «process.name.convert»_init();
				int timer_schedule();
				int «process.name.convert»_init_task();
				
			'''
			ProcessImplementation : '''
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
				«IF children!==null»
						«FOR Element sub :children»
							«switch sub{
									DefaultAnnexSubclauseImpl : '''
										«Tools.addContent(Template.systemheadfile,"有个行为附件")»
										«FOR Element sub2 :sub.children»
												«switch sub2{
														BehaviorAnnexImpl : '''
														«sub2.frame_template4BV»
														
														void BehaviorAnnex(){
														«sub2.frame_template4BS»
														}					                
																			'''
															}»
													«ENDFOR»
												'''
										}»
										«ENDFOR»
									«ENDIF»				
				
				
			'''
		}
	}
	
	def static frame_template4BV(BehaviorAnnexImpl ba)'''
	«FOR Element sub :ba.children»
	«switch sub{
			BehaviorVariable : '''
			«sub.template»
			'''
	}»
	«ENDFOR»	
	
	enum State{«ba.enum_template.toString.clearspace»}
	'''
	
	def static enum_template(BehaviorAnnexImpl ba)'''
	«FOR Element sub :ba.children»
	«switch sub{
		BehaviorState:'''
		«IF sub.isInitial»«sub.name»«ELSE» , «sub.name»«ENDIF»
		'''}»
	«ENDFOR»
	'''
	def static frame_template4BS(BehaviorAnnexImpl ba)'''
«««	«ba.getInitialStateEx.string» 
«««	«ba.getFinalState.string»
	State current_state = initialState;
	while(0){
		switch(current_state){
		«FOR Element st :ba.children»
		«switch st{
			BehaviorState:'''
		case «st.getName» : 
			«st.template»
			«IF st.isFinal»return "finish";«ENDIF»
			break;
			'''
		}»
		«ENDFOR»
		default:
			break;	
		}
	}

	'''
	def static template(BehaviorVariable bv){
	'''
	«var BV= bv.dataClassifier»
	«switch BV{	
					DataImplementationImpl:
							'''«BV.getQualifiedName()»  «bv.name»;'''
					DataTypeImpl:
							'''«BV.getQualifiedName()»  «bv.name»;'''
				}»
	'''
	}											
	
	def static template(BehaviorState bs){
	'''
	«FOR transition:AadlBaVisitors.getTransitionWhereSrc(bs)»
	«IF bs==transition.getSourceState»
		«transition.template»
	«ENDIF»
	«ENDFOR»
	'''	
	}
	def static getInitialStateEx(BehaviorAnnexImpl ba)'''
	State initialState = «ba.getInitialState.name»;
	'''	
	def static getFinalState(BehaviorAnnexImpl ba)'''
	«FOR Element bs :ba.children»
			«switch bs{
				BehaviorState:'''
				«IF bs.isFinal»
				State finalState = «bs.name»;
				«ENDIF»
				'''
			}»
	«ENDFOR»
	'''	
	def static getFinalStateName(BehaviorAnnexImpl ba)'''
	«FOR Element bs :ba.children»
			«switch bs{
				BehaviorState:'''
				«IF bs.isFinal»
				«bs.name»
				«ENDIF»
				'''
			}»
	«ENDFOR»
	'''	
		
	def static template(BehaviorTransition bt){
	'''
	if( «bt.condition.condition_template.toString.clearspace» ){
		«bt.actionBlock.actionblock_template»
		current_state = «bt.destinationState.name»;
	}
	'''
	}

	def static condition_template(BehaviorCondition  bc)
		'''
		«IF bc!==null»
		«var BC=bc»
		«var tag=false»
		«switch BC{
			ValueExpressionImpl:'''
			«IF BC.isSetLogicalOperators»
			«var logic=BC.logicalOperators»
			«var logic_index=logic.length»
			«var index=0»
			«FOR Relation condition :BC.getRelations()»
				«IF condition.firstExpression.terms.get(0).getFactors.get(0).getUnaryBooleanOperator.toString=="!"»
					!(«condition.conditionChildren_template»)
				«ELSE»
					(«condition.conditionChildren_template»)«IF BC.isSetLogicalOperators&&index<logic_index»«logic.get(index++).translate_logOperator»«ENDIF»
				«ENDIF»
			«ENDFOR»
			«ELSE»
			«FOR Relation condition :BC.getRelations()»
				«IF condition.firstExpression.terms.get(0).getFactors.get(0).getUnaryBooleanOperator.toString=="!"»
					!(«condition.conditionChildren_template»)
				«ELSE»
					(«condition.conditionChildren_template»)
				«ENDIF»
			«ENDFOR»
			«ENDIF»					
			'''
			default:'''
			default «BC.class» at processtemplate.xtend line 351
			'''
		}»
		«ELSEIF bc===null»
		true
		«ENDIF»	
	'''

	def static conditionChildren_template(Relation  relation)'''
«««		«relation.firstExpression.terms.get(0).getFactors.get(0).getFirstValue.translate_value1.string»
		«IF relation.secondExpression!==null»
«««			«relation.relationalOperator» «relation.secondExpression.terms.get(0).getFactors.get(0).getFirstValue.translate_value1.string»
		«ENDIF»		
	'''
	
	
	def static actionblock_template(BehaviorActionBlock bb)'''
		«IF bb!=null»
			«FOR bb_child:bb.children»
			«switch bb_child{
				BehaviorActionSequenceImpl:'''
				«FOR action :bb_child.getActions»
				«action.baAction_template»
				«ENDFOR»
				'''
				BehaviorTimeImpl:'''
				time = «bb_child.getIntegerValue» «bb_child.getUnit.name» ;
				'''
				PortSendActionImpl:'''
				«bb_child.baAction_template»
				'''
				AssignmentActionImpl:'''
				«bb_child.baAction_template»
				'''
			}»«»«»«»«»«»
			«ENDFOR»
		«ENDIF»	
		«IF bb==null»
		printf("Without actionBlock");
		«ENDIF»			
	'''
	
	def static target_template(Target target){
	switch target{
		BehaviorVariableHolderImpl:'''
		«target.getBehaviorVariable.name»
		'''	
		DataSubcomponentHolderImpl:'''
		«target.getDataSubcomponent.name»
		'''
		default:'''
			target_template has no strategy for «target.class»
		'''
	}
	}
	def static baAction_template(BehaviorAction baAction)'''
	«switch baAction{
			AssignmentActionImpl:'''
				«FOR Relation relation :baAction.getValueExpression.getRelations»
				«baAction.getTarget.target_template.toString.clearspace» = «relation.relation_template.toString.clearspace»;   //:=
				«ENDFOR»	
			'''
			PortDequeueActionImpl:'''
				«baAction.getPort.getPort().name» = «baAction.getTarget.target_template.toString.clearspace»;   //?=
			'''
			PortSendActionImpl:'''
				«FOR relation:baAction.getValueExpression.getRelations»
				«baAction.getPort.getPort().name» = «relation.relation_template.toString.clearspace»;   //!=
				«relation.relation_template2»
				«ENDFOR»
			'''
			default:'''
				baAction_template has no strategy for «baAction.class»
				you can find information in BehaviorAction
				'''
				}»
	'''
	def static relation_template(Relation relation)'''
	«switch relation{	
		RelationImpl:'''
		«relation.firstExpression.terms.get(0).getFactors.get(0).getFirstValue.translate_value1»
		'''		
		default:'''
		relation_template has no strategy for «relation.class»
		'''
	}»
	'''
	
	def static relation_template2(Relation relation)'''
	«switch relation{	
		RelationImpl:'''
		«IF relation.secondExpression!=null»
		«relation.secondExpression.terms.get(0).getFactors.get(0).getFirstValue.translate_value1»
		«ENDIF»
		'''		
		default:'''
		relation_template2 has no strategy for «relation.class»
		'''
	}»
	'''
	
	def static translate_value1(Value value)'''
		«switch value{
			DataComponentReferenceImpl:'''
			«var cc1=value.data.get(0)»
			«switch cc1{
					BehaviorVariableHolderImpl:'''
					«cc1.getBehaviorVariable.name»'''
					DataSubcomponentHolderImpl:'''
					«cc1.getDataSubcomponent.name»'''
					default:'''
					line 470«cc1»'''
			}»
			.
			«var cc2=value.data.get(1)»
			«switch cc2{
					BehaviorVariableHolderImpl:'''
					«cc2.getBehaviorVariable.name»'''						
					DataSubcomponentHolderImpl:'''
					«cc2.getDataSubcomponent.name»'''					
					default:'''
					line 479 «cc1»'''
			}»
			'''
			BehaviorVariableHolderImpl:'''
			«value.getBehaviorVariable.name»
			'''
			BehaviorIntegerLiteralImpl:'''
			«value.getValue»
			'''
			BehaviorBooleanLiteralImpl:'''
			«value.getValue()»
			'''	
			BehaviorRealLiteralImpl:'''
			«value.getValue()»
			'''
			ValueExpressionImpl:'''
			«value.condition_template»
			'''
			default:'''
			«value»~~~~~~
			'''
		}»
	'''
		def static translate_value2(Value value)'''
		«switch value{
			DataComponentReferenceImpl:
			{
				var cc2=value.data.get(1)
					switch cc2{
						BehaviorVariableHolderImpl:
							cc2.getBehaviorVariable.name
						DataSubcomponentHolderImpl:
							cc2.getDataSubcomponent.name
					}
			}
		}»
	'''
	
	def static translate_logOperator(LogicalOperator logOperator)'''
	«switch logOperator.getValue{
		case 0:'''
		~
		'''
		case 1:'''
		&&
		'''
		case 2:'''
		||
		'''
		case 3:'''
		XOR
		'''
	}»
	'''
	def static init(ProcessImplementation process)'''
		«Tools.addContent(process.name.convert+".h","int "+process.name.convert+"_cycle"+";")»
		«Tools.addContent(process.name.convert+".h","int "+process.name.convert+"_current"+";")»
	'''
	
	
}