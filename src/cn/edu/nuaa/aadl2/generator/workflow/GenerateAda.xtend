package cn.edu.nuaa.aadl2.generator.workflow

import org.osate.aadl2.SystemImplementation
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ProcessTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda.*

import org.osate.aadl2.Subcomponent
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda
import org.osate.aadl2.impl.PublicPackageSectionImpl
import org.osate.aadl2.instance.impl.SystemInstanceImpl
import java.util.List
import org.osate.aadl2.Classifier
import org.osate.aadl2.SystemType
import org.osate.aadl2.ProcessSubcomponent
import org.osate.aadl2.SystemSubcomponent
import org.osate.aadl2.DataSubcomponent

class GenerateAda {
	var static adaFolder = "Ada_codes"
	def static generate(SystemImplementation system){
		if (system.name!=null){
			Tools.folder(adaFolder)
			TemplateAda.packageName=Tools.getPackageName(system.eContainer.eContainer.toString)
			TemplateAda.subprogramsFileName = TemplateAda.packageName+"_Subprograms"
			generateSystem(adaFolder,system,null)
		}
	}
	/*
	 * 处理系统实现组件
	 * @param parentFolderPath 此系统目录的父目录路径
	 * @param system 此系统实现实例
	 * @param systemFolder 此系统目录名
	 */
	def static generateSystem(String parentFolderPath, SystemImplementation system, String systemFolder){
		var String currentFolder = "system_"
		if(systemFolder == null){
			currentFolder += system.name.convert 
		}else{
			currentFolder += systemFolder.convert
		}
		var currentFolderPath = parentFolderPath + "/" + currentFolder
		Tools.folder(currentFolderPath)

		if(system.ownedSystemSubcomponents.size > 0){
			for(SystemSubcomponent systemsubcomponent : system.ownedSystemSubcomponents){
				var SystemImplementation systemImplementation = systemsubcomponent.systemSubcomponentType as SystemImplementation
				generateSystem(currentFolderPath,systemImplementation, systemsubcomponent.name)
			}
		}
		if(system.allFeatures.size > 0){
			genSystemFeature(currentFolderPath,currentFolder,system.allFeatures)
		}
		if(system.ownedDataSubcomponents.size > 0){
			genSystemDataSubcomponent(currentFolderPath,currentFolder,system.ownedDataSubcomponents)
		}
		if(system.ownedProcessSubcomponents.size > 0){
			for(ProcessSubcomponent processsubcomponent : system.ownedProcessSubcomponents){
				genSystemProcessSubcomponent(currentFolderPath,processsubcomponent)
			}
		}

		
//		var packageSectionImpl = system.eContainer() as PublicPackageSectionImpl;
//		var classifiers = packageSectionImpl.getOwnedClassifiers();
//		
//		for(Classifier classifier : classifiers){
//			classifier.template
//		}
//		
//		Tools.dealSubprogramFile(TemplateAda.subprogramsFileName.toLowerCase+".ads")
//		Tools.dealSubprogramFile(TemplateAda.subprogramsFileName.toLowerCase+".adb")
	}
}