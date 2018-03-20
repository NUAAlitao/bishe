package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.ProcessSubcomponent
import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.ProcessType
import org.osate.aadl2.ProcessImplementation
import org.osate.aadl2.Subcomponent
import org.osate.aadl2.ThreadSubcomponent

import static extension cn.edu.nuaa.aadl2.generator.templateAda.ThreadTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.AnnexSubclauseTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda.*

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.AnnexSubclause
import org.osate.aadl2.DefaultAnnexSubclause

class ProcessTemplateAda {
		/*
		 * 处理系统实现下的进程子组件
		 * @param parentFolder 系统实现目录路径
		 * @param processSubcomponent 进程子组件
		 */
		def static genSystemProcessSubcomponent(String parentFolder, ProcessSubcomponent processSubcomponent){
				Tools.folder(parentFolder+"/"+processSubcomponent.name.toLowerCase.replace(".","_"))
				Tools.createFile(parentFolder+"/"+processSubcomponent.name.toLowerCase.replace(".","_"),processSubcomponent.name.replace(".","_")+".adb",processSubcomponent.template.toString)
		}
		
		def static template(ProcessSubcomponent processSubcomponent){
			var process=processSubcomponent.classifier
			switch process{
				ProcessType : '''
				
				'''
				ProcessImplementation : '''
				with «TemplateAda.packageName»_Subprograms; use «TemplateAda.packageName»_Subprograms;
				with «TemplateAda.packageName»_types; use «TemplateAda.packageName»_types;
				
				procedure «processSubcomponent.name.replace('.','_')» is
					«IF process.allFeatures.size > 0»
						«process.allFeatures.genProcessFeature»
					«ENDIF»
					«IF process.ownedDataSubcomponents.size > 0»
						«process.ownedDataSubcomponents.genProcessDataSubcomponent»
					«ENDIF»
					«IF process.ownedThreadSubcomponents.size > 0»
						«process.ownedThreadSubcomponents.genProcessThreadSubcomponent»
					«ENDIF»

					«IF process.ownedAnnexSubclauses.size > 0»
						«FOR AnnexSubclause annexSubclause : process.ownedAnnexSubclauses»
							«genBehaviorAnnexVarible(annexSubclause as DefaultAnnexSubclause)»
							«genBehaviorAnnexState(annexSubclause as DefaultAnnexSubclause)»
						«ENDFOR»
					«ENDIF»
				begin
					«IF process.ownedAnnexSubclauses.size > 0»
						«FOR AnnexSubclause annexSubclause : process.ownedAnnexSubclauses»
							«genBehaviorAnnexTransition(annexSubclause as DefaultAnnexSubclause)»
						«ENDFOR»
					«ENDIF»
				end «processSubcomponent.name.replace('.','_')»;
				'''
				}
		}
		
		
}