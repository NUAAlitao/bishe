package cn.edu.nuaa.aadl2.generator.templateAda

import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.SubprogramCall
import org.osate.aadl2.SubprogramCallSequence
import org.osate.aadl2.ThreadImplementation
import org.osate.aadl2.ThreadSubcomponent
import org.osate.aadl2.ThreadType

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.AnnexSubclauseTemplateAda.*
import java.util.List
import org.osate.aadl2.AnnexSubclause
import org.osate.aadl2.DefaultAnnexSubclause

class ThreadTemplateAda {
	def static create(ThreadSubcomponent subcomponent)'''
		«subcomponent.head»
		«subcomponent.template»
	'''
	
	/*
	 * 处理进程实现下的线程子组件
	 * @param threadSubcomponents 线程子组件列表
	 */
	def static genProcessThreadSubcomponent(List<ThreadSubcomponent> threadSubcomponents)'''
		«FOR threadSubcomponent : threadSubcomponents»
			«threadSubcomponent.head»
			«threadSubcomponent.template»
		«ENDFOR»
	'''
	def static head(ThreadSubcomponent subcomponent){
		var thread=subcomponent.classifier
		switch thread{
			ThreadType : '''
			'''
			ThreadImplementation : '''
				task type «subcomponent.name.replace('.','_')»_task is
					«IF subcomponent.inModes.size >0»
					entry Start(«IF thread.allFeatures.size>0»«thread.allFeatures.genThreadFeature.toString.clearspace»«ENDIF»);
					«ENDIF»
				end «subcomponent.name.replace(',','_')»_task;
			'''
		}
	}
	
	def static template(ThreadSubcomponent subcomponent){
		var thread=subcomponent.classifier
		switch thread{
			ThreadType : '''

			'''
			ThreadImplementation : '''
				
				task body «subcomponent.name.convertPoint»_task is
					«IF thread.ownedAnnexSubclauses.size >0»
						«FOR AnnexSubclause annexSubclause : thread.ownedAnnexSubclauses»
							«genBehaviorAnnexVarible(annexSubclause as DefaultAnnexSubclause)»
							«genBehaviorAnnexState(annexSubclause as DefaultAnnexSubclause)»
							current_state : States;
						«ENDFOR»
					«ENDIF»
				begin
					«IF subcomponent.inModes.size>0»
					accept Start(«IF thread.allFeatures.size>0»«thread.allFeatures.genThreadFeature.toString.clearspace»«ENDIF») do
					end Start;
					«ENDIF»
					«FOR SubprogramCallSequence subprogramCallSequence : thread.ownedSubprogramCallSequences»
						«FOR SubprogramCall subprogramCall : subprogramCallSequence.ownedSubprogramCalls»
							«TemplateAda.packageName»_«Tools.getCalledSubprogramName(subprogramCall.calledSubprogram.toString()).convertPoint»;
						«ENDFOR»
					«ENDFOR»
					«IF thread.ownedAnnexSubclauses.size > 0»
						«FOR AnnexSubclause annexSubclause : thread.ownedAnnexSubclauses»
							«initBehaviorAnnexState(annexSubclause as DefaultAnnexSubclause).toString.clearspace»
							«genBehaviorAnnexTransition(annexSubclause as DefaultAnnexSubclause)»
						«ENDFOR»
					«ENDIF»
				end «subcomponent.name.replace('.','_')»_task;
				
			'''
		}
	}
}