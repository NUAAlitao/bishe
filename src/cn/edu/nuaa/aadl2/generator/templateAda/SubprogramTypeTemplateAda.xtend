package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.SubprogramImplementation
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.aadl2.SubprogramType

class SubprogramTypeTemplateAda {
	static String fileName = TemplateAda.subprogramsFileName;
	static String initlineAds = "package "+fileName+ " is\n";
	static String initlineAdb = '''
		with Ada.Text_IO; use Ada.Text_IO;
		
		package body «fileName» is
		
	'''
	
	def static create(SubprogramType subprogramType)'''
		«Tools.creatSubprogramFile(fileName.toLowerCase+".ads",initlineAds,subprogramType.templateAds.toString)»
		«Tools.creatSubprogramFile(fileName.toLowerCase+".adb",initlineAdb,subprogramType.templateAdb.toString)»
	'''
	
	def static templateAds(SubprogramType subprogramType)'''
	
		procedure «TemplateAda.packageName»_«subprogramType.name.convertPoint»;
	'''
	
	def static templateAdb(SubprogramType subprogramType)'''
	
		procedure «TemplateAda.packageName»_«subprogramType.name.convertPoint» is
		begin
			put_line("This is «subprogramType.name.convertPoint»");
		end «TemplateAda.packageName»_«subprogramType.name.convertPoint»;
		
	'''
}