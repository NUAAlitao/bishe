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
import org.osate.aadl2.Connection

class ThreadTemplateAda {
	/*
	 * 处理进程实现下的线程子组件
	 * @param threadSubcomponents 线程子组件列表
	 * @param connections 进程中的connection列表
	 */
	def static genProcessThreadSubcomponent(List<ThreadSubcomponent> threadSubcomponents, List<Connection> connections)'''
		«FOR threadSubcomponent : threadSubcomponents»
			«threadSubcomponent.head(connections)»
			«threadSubcomponent.template»
		«ENDFOR»
	'''
	
	def static head(ThreadSubcomponent subcomponent, List<Connection> connections){
		var thread=subcomponent.classifier
		switch thread{
			ThreadType : '''
			'''
			ThreadImplementation : '''
				task type «thread.name.convert»(«thread.allFeatures.genThreadFeature(connections,subcomponent.name).toString.clearspace.formatParam») is
				end «thread.name.convert»;
				
				type access_«thread.name.convert» is access «thread.name.convert»;
			'''
		}
	}
	
	def static template(ThreadSubcomponent subcomponent){
		var thread=subcomponent.classifier
		switch thread{
			ThreadType : '''

			'''
			ThreadImplementation : '''
				
				task body «thread.name.convert» is
					«IF thread.allFeatures.size>0»
						«thread.allFeatures.genThreadInPortVar»
					«ENDIF»
					«IF thread.ownedAnnexSubclauses.size >0»
						«FOR AnnexSubclause annexSubclause : thread.ownedAnnexSubclauses»
							«genBehaviorAnnexVarible(annexSubclause as DefaultAnnexSubclause)»
							«genBehaviorAnnexState(annexSubclause as DefaultAnnexSubclause)»
							current_state : States;
						«ENDFOR»
					«ENDIF»
				begin
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
				end «thread.name.convert»;
				
			'''
		}
	}
	
	/*
	 * 将线程类型声明中的out和 in out端口生成为进程中的变量
	 * @param threadSubcomponents 进程中所有线程子组件
	 */
	 def static genThreadPortVar(List<ThreadSubcomponent> threadSubcomponents)'''
	 	«FOR threadSubcomponent : threadSubcomponents»
	 		«var thread = threadSubcomponent.classifier»
	 		«genThreadFeatureVarInProc(thread.allFeatures,threadSubcomponent.name)»
	 	«ENDFOR»
	 '''
	 /*
	  * 生成线程类型的访问类型的变量（进程中的所有子线程的访问类型）
	  * @param threadSubcomponents 进程中的线程子组件列表
	  */
	 def static genThreadAccessVar(List<ThreadSubcomponent> threadSubcomponents)'''
	 	«FOR threadSubcomponent : threadSubcomponents»
	 		«var thread = threadSubcomponent.classifier»
	 		«threadSubcomponent.name»_task : access_«thread.name.convert»;
	 	«ENDFOR»
	 '''
}