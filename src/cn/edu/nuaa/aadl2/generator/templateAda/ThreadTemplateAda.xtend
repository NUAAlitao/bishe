package cn.edu.nuaa.aadl2.generator.templateAda

import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.SubprogramCall
import org.osate.aadl2.SubprogramCallSequence
import org.osate.aadl2.ThreadImplementation
import org.osate.aadl2.ThreadSubcomponent
import org.osate.aadl2.ThreadType

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import java.util.List

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
				task «subcomponent.name.replace('.','_')»_task is
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
				begin
					«FOR SubprogramCallSequence subprogramCallSequence : thread.ownedSubprogramCallSequences»
						«FOR SubprogramCall subprogramCall : subprogramCallSequence.ownedSubprogramCalls»
							«TemplateAda.packageName»_«Tools.getCalledSubprogramName(subprogramCall.calledSubprogram.toString()).convertPoint»;
						«ENDFOR»
					«ENDFOR»
				end «subcomponent.name.replace('.','_')»_task;
				
			'''
		}
	}
}