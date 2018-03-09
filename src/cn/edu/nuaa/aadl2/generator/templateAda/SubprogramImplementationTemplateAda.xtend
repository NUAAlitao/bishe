package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.SubprogramCallSequence
import org.osate.aadl2.SubprogramCall
import org.osate.aadl2.Classifier

import cn.edu.nuaa.aadl2.generator.utils.Tools
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda.*

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.aadl2.SubprogramImplementation
import org.osate.aadl2.SubprogramAccess

class SubprogramImplementationTemplateAda {
	static String fileName = TemplateAda.subprogramsFileName;
	static String initlineAds = "package "+fileName+ " is\n";
	static String initlineAdb = '''
		with Ada.Text_IO; use Ada.Text_IO;
		
		package body «fileName» is
		
	'''
	
	def static create(SubprogramImplementation subprogramImplementation)'''
		«Tools.creatSubprogramFile(fileName.toLowerCase+".ads",initlineAds,subprogramImplementation.templateAds.toString)»
		«Tools.creatSubprogramFile(fileName.toLowerCase+".adb",initlineAdb,subprogramImplementation.templateAdb.toString)»
	'''
	
	def static templateAds(SubprogramImplementation subprogramImplementation)'''
	
		procedure «TemplateAda.packageName»_«subprogramImplementation.name.convertPoint»;
	'''
	
	def static templateAdb(SubprogramImplementation subprogramImplementation)'''
	
		procedure «TemplateAda.packageName»_«subprogramImplementation.name.convertPoint» is
		begin
			put_line("This is «subprogramImplementation.name.convertPoint»");
			«FOR SubprogramCall subprogramCall : subprogramImplementation.getSubprogramCalls»
				«TemplateAda.packageName»_«Tools.getCalledSubprogramName(subprogramCall.calledSubprogram.toString.convertPoint)»;
			«ENDFOR»
		end «TemplateAda.packageName»_«subprogramImplementation.name.convertPoint»;
		
	'''
}