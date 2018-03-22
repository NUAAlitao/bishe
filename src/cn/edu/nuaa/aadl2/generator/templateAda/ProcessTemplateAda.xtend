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
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ModeTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.AnnexSubclause
import org.osate.aadl2.DefaultAnnexSubclause
import java.util.List
import org.osate.aadl2.Mode

class ProcessTemplateAda {
		/*
		 * 处理系统实现下的进程子组件
		 * @param parentFolder 系统实现目录路径
		 * @param processSubcomponent 进程子组件
		 */
		def static genSystemProcessSubcomponent(String parentFolder, ProcessSubcomponent processSubcomponent){
				Tools.folder(parentFolder+"/process_"+processSubcomponent.name.toLowerCase.replace(".","_"))
				Tools.createFile(parentFolder+"/process_"+processSubcomponent.name.toLowerCase.replace(".","_"),processSubcomponent.name.replace(".","_")+".adb",processSubcomponent.template.toString)
		}
		
		def static template(ProcessSubcomponent processSubcomponent){
			var process=processSubcomponent.classifier
			switch process{
				ProcessType : '''
				
				'''
				ProcessImplementation : '''
				with «TemplateAda.packageName»_Subprograms; use «TemplateAda.packageName»_Subprograms;
				with «TemplateAda.packageName»_types; use «TemplateAda.packageName»_types;
				
				procedure «processSubcomponent.name.replace('.','_')» («IF process.allFeatures.size > 0»«process.allFeatures.genProcessFeature.toString.clearspace»«ENDIF») is
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
							current_state : States;
						«ENDFOR»
					«ENDIF»
					«IF process.ownedModes.size > 0»
						«process.ownedModes.genMode.toString.clearspace»
						current_mode : Modes;
					«ENDIF»
				begin
					«IF process.ownedAnnexSubclauses.size > 0»
						«FOR AnnexSubclause annexSubclause : process.ownedAnnexSubclauses»
							«initBehaviorAnnexState(annexSubclause as DefaultAnnexSubclause).toString.clearspace»
							«genBehaviorAnnexTransition(annexSubclause as DefaultAnnexSubclause)»
						«ENDFOR»
					«ENDIF»
					«IF process.ownedModes.size > 0»
						«process.ownedModes.initMode.toString.clearspace»
					«ENDIF»
					«IF process.ownedModeTransitions.size > 0»
						«process.ownedModeTransitions.genModeTransition»
					«ENDIF»
					«IF process.ownedModes.size >0»
						«dealProcessMode(process.ownedModes,process.allSubcomponents)»
					«ENDIF»
				end «processSubcomponent.name.replace('.','_')»;
				'''
				}
		}
		
		/*
		 * 根据进程所处的不同模式调用该模式下的子组件
		 * @param modes 进程拥有的模式列表
		 * @param subcomponents 进程的子组件列表
		 */
		def static dealProcessMode(List<Mode> modes,List<Subcomponent> subcomponents)'''
			case surrent_mode is
			«FOR mode : modes»
			when «mode.name» =>
				«FOR subcomponent : subcomponents»
					«switch subcomponent{
						ThreadSubcomponent:'''
							«IF subcomponent.inModes.contains(mode)»
								«subcomponent.name»_task.Start();
							«ENDIF»
						'''
					}»
				«ENDFOR»
			«ENDFOR»
			end case;
		'''
		
		
}