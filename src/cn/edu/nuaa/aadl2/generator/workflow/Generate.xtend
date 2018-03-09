package cn.edu.nuaa.aadl2.generator.workflow

import org.osate.aadl2.SystemImplementation
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.template.SubcomponentTemplate.*

import cn.edu.nuaa.aadl2.generator.template.Template
import org.osate.aadl2.Subcomponent

class Generate {
	val static CFolder = "C_codes"
	def static generate(SystemImplementation system)'''

		«IF system.name!=null»
			«Tools.folder(CFolder)»
			
			«Tools.folder(CFolder+"/"+system.name.convert)»
			//在工程下创建名为 system_name 的文件夹
			«Template.systemfolder=CFolder+"/"+system.name.convert»
			//设置生成代码系统的文件夹名
			«Tools.createFile(system.name.convert+".h",Template.dhead)»
			//在 system_name 文件夹中生成名为system_name.h的文件，内容来自Template中的dhead
			«IF Template.isMeta»
					«Template.meta_systemfolder=CFolder+"/"+system.name.convert»
					«Template.meta_systemheadfile=system.name.convert+".h"»
					«Template.isMeta=false»
			«ENDIF»
			«Template.systemheadfile=system.name.convert+".h"»
			//设置系统（C实现）的头文件（主？）system_name.h
		«ENDIF»
		«IF system.allSubcomponents!=null»
		//如果systemImplementation对象拥有Subcomponent
			«FOR Subcomponent subcomponent : system.allSubcomponents»
			//遍历所有Subcomponent，通过Template生成对应代码
				«subcomponent.template»
				«System.out.println(subcomponent.name)»
			«ENDFOR»
		«ENDIF»
		
	'''
}