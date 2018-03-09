package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.SystemSubcomponent
import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.SystemClassifier
import org.osate.aadl2.SystemType
import org.osate.aadl2.SystemImplementation
import static extension cn.edu.nuaa.aadl2.generator.template.Template.*
import static extension cn.edu.nuaa.aadl2.generator.workflow.Generate.*
import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
class SystemTemplate {
	
	
	
	def static create(SystemSubcomponent subcomponent)'''
		«subcomponent.template»
«««		«System.out.println(subcomponent.classifier.name+"-systemsubcomponent.c")»
«««		«System.out.println(subcomponent.classifier.name+"-systemsubcomponent.h")»	
	'''
		
	def static template(SystemSubcomponent subcomponent){
		var subprogram=subcomponent.classifier
		switch subprogram{
			SystemClassifier : '''«subprogram.create»'''
		}
	}
	def static create(SystemClassifier subsystem){
		switch subsystem{
			SystemType : '''
				«System.out.println("SystemTemplate.xtend create()还未添加应对SystemType方法")»

			'''
			SystemImplementation : '''
«««				«Tools.createFile(subsystem.name+"11111111111111111111.h","")»
«««				«Tools.createFile(subsystem.name+"11111111111111111111.c","")»
				«subsystem.generate»
				«System.out.println(Template.meta_systemheadfile+"11111111111111111111111111111111111111")»
				«Tools.sub_addContent(Template.meta_systemfolder,Template.meta_systemheadfile,subsystem.name.convert+".h")»
			'''
		}
	}
}
	
