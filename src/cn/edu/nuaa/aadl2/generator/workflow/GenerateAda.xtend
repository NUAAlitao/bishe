package cn.edu.nuaa.aadl2.generator.workflow

import org.osate.aadl2.SystemImplementation
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.SubcomponentTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ClassifierTemplateAda.*

import org.osate.aadl2.Subcomponent
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda
import org.osate.aadl2.impl.PublicPackageSectionImpl
import org.osate.aadl2.instance.impl.SystemInstanceImpl
import java.util.List
import org.osate.aadl2.Classifier

class GenerateAda {
	val static adaFolder = "Ada_codes"
	def static generate(SystemImplementation system){
		if (system.name!=null){
			//在工程目录下生成ada_codes 文件夹
			Tools.folder(adaFolder)
			//在工程下创建名为 system_name 的文件夹
			Tools.folder(adaFolder + "/" + system.name.convert)
			//设置生成代码系统的文件夹名
			TemplateAda.systemfolder=adaFolder +"/"+system.name.convert
			TemplateAda.packageName=Tools.getPackageName(system.eContainer.eContainer.toString)
			TemplateAda.subprogramsFileName = TemplateAda.packageName+"_Subprograms"
		}
		
		
		if (system.allSubcomponents!=null){
			//如果systemImplementation对象拥有Subcomponent
			for( Subcomponent subcomponent : system.allSubcomponents){
				//遍历所有Subcomponent，通过Template生成对应代码
				subcomponent.template
				System.out.println(subcomponent.name)
			}
		}
		
		var packageSectionImpl = system.eContainer() as PublicPackageSectionImpl;
		var classifiers = packageSectionImpl.getOwnedClassifiers();
		
		for(Classifier classifier : classifiers){
			classifier.template
		}
		
		Tools.dealSubprogramFile(TemplateAda.subprogramsFileName.toLowerCase+".ads")
		Tools.dealSubprogramFile(TemplateAda.subprogramsFileName.toLowerCase+".adb")
	}
}