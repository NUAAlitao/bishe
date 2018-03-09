package cn.edu.nuaa.aadl2.generator.templateAda

import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.SubprogramCall
import org.osate.aadl2.SubprogramCallSequence
import org.osate.aadl2.ThreadImplementation
import org.osate.aadl2.ThreadSubcomponent
import org.osate.aadl2.ThreadType

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*

class ThreadTemplateAda {
	def static create(ThreadSubcomponent subcomponent)'''
		«subcomponent.head»
		«subcomponent.template»
	'''
	
	def static head(ThreadSubcomponent subcomponent){
		var thread=subcomponent.classifier
		switch thread{
			ThreadType : '''
				/*Thread Type head file*/
				«TemplateAda.head»
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