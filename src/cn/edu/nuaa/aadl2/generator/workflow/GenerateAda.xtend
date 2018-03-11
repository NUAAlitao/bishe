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
			//在工程下创建名为 system_name 的文件夹
//			Tools.folder(currentFolder)
//			var systemType = system.ownedRealization.implemented as SystemType;
//			systemType.ownedFeatures;
			//设置生成代码系统的文件夹名
//			TemplateAda.systemfolder=adaFolder +"/"+system.name.convert
			TemplateAda.packageName=Tools.getPackageName(system.eContainer.eContainer.toString)
			TemplateAda.subprogramsFileName = TemplateAda.packageName+"_Subprograms"
			generateSystem(adaFolder,system,null)
		}
	}
	
	def static generateSystem(String parentFolderPath, SystemImplementation system, String systemFolder){
		var String currentFolder
		if(systemFolder == null){
			currentFolder = system.name.convert 
		}else{
			currentFolder = systemFolder.convert
		}
		var currentFolderPath = parentFolderPath + "/" + currentFolder
		Tools.folder(currentFolderPath)
//		if (system.allSubcomponents!=null){
//			//如果systemImplementation对象拥有Subcomponent
//			for( Subcomponent subcomponent : system.allSubcomponents){
//				//遍历所有Subcomponent，通过Template生成对应代码
//				subcomponent.template
//				System.out.println(subcomponent.name)
//			}
//		}
		var systemType = system.ownedRealization.implemented as SystemType;
		if(systemType.ownedFeatures.size > 0){
			genSystemFeature(currentFolderPath,currentFolder,systemType.ownedFeatures)
		}
		
		
		if(system.ownedDataSubcomponents.size > 0){
			genSystemDataSubcomponent(currentFolderPath,currentFolder,system.ownedDataSubcomponents)
		}
		if(system.ownedProcessSubcomponents.size > 0){
			for(ProcessSubcomponent processsubcomponent : system.ownedProcessSubcomponents){
				genSystemProcessSubcomponent(currentFolderPath,processsubcomponent)
			}
		}
		if(system.ownedSystemSubcomponents.size > 0){
			for(SystemSubcomponent systemsubcomponent : system.ownedSystemSubcomponents){
				var SystemImplementation systemImplementation = systemsubcomponent.systemSubcomponentType as SystemImplementation
				generateSystem(currentFolderPath,systemImplementation, systemsubcomponent.name)
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