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
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ConnectionTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda

import org.osate.aadl2.AnnexSubclause
import org.osate.aadl2.DefaultAnnexSubclause
import java.util.List
import org.osate.aadl2.Mode
import org.osate.aadl2.Connection
import org.osate.aadl2.ModeTransition

class ProcessTemplateAda {
	private static String currentFolder=""
	/*
	 * 处理系统实现下的进程子组件
	 * @param parentFolder 系统实现目录路径
	 * @param processSubcomponent 进程子组件
	 * @param systemName 系统名称
	 */
	def static genSystemProcessSubcomponent(String parentFolder, ProcessSubcomponent processSubcomponent, String systemName){
			currentFolder = parentFolder+"/process_"+processSubcomponent.name.convert
			Tools.folder(currentFolder)
			Tools.createFile(currentFolder, 
				systemName.convert+"-"+processSubcomponent.name.convert+".adb", 
				processSubcomponent.template(systemName).toString)
			
			Tools.createFile(currentFolder,"base_protect.ads",TemplateAda.base_protect_ads)
			Tools.createFile(currentFolder,"base_protect.adb",TemplateAda.base_protect_adb)
	}
	
	def static template(ProcessSubcomponent processSubcomponent, String systemName){
		var process=processSubcomponent.classifier
		switch process{
			ProcessType : '''
			
			'''
			ProcessImplementation : '''
			with Ada.Text_IO; use Ada.Text_IO;
			with DataType; use DataType;
			with protected_types; use protected_types;
			
			separate(«systemName.convert»)
			
			procedure «processSubcomponent.name.replace('.','_')» («IF process.allFeatures.size > 0»«process.allFeatures.genProcessFeature.toString.clearspace.formatParam»«ENDIF») is
				«IF process.ownedDataSubcomponents.size > 0»
					«process.ownedDataSubcomponents.genProcessDataSubcomponent»
				«ENDIF»
				«IF process.ownedThreadSubcomponents.size > 0»
					«process.ownedThreadSubcomponents.genProcessThreadSubcomponent(process.ownedConnections)»
				«ENDIF»
				«IF process.ownedConnections.size > 0»
					«Tools.createFile(currentFolder,"protected_types.ads",process.ownedConnections.genProtectType.toString)»
					«process.ownedConnections.genProtectObject»
				«ENDIF»
				«IF process.ownedThreadSubcomponents.size>0»
					«process.ownedThreadSubcomponents.genThreadAccessVar»
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
					«process.ownedModeTransitions.genProcessModeTransition(process.ownedConnections)»
				«ENDIF»
				«IF process.ownedModes.size >0»
					«dealProcessMode(process.ownedModes,process.allSubcomponents,process.ownedConnections,process.ownedModeTransitions)»
				«ELSE»
					«dealTaskCall(process.allSubcomponents,process.ownedConnections)»
				«ENDIF»
			end «processSubcomponent.name.convert»;
			'''
			}
	}
	
	/*
	 * 根据进程所处的不同模式调用该模式下的子组件
	 * @param modes 进程拥有的模式列表
	 * @param subcomponents 进程的子组件列表
	 * @param connections 进程的连接列表
	 */
	def static dealProcessMode(List<Mode> modes,List<Subcomponent> subcomponents, List<Connection> connections, List<ModeTransition> modeTransitions)'''
		case current_mode is
		«FOR mode : modes»
		when «mode.name» =>
			«genModeTriggerSend(modeTransitions,connections,mode.name)»
			«FOR subcomponent : subcomponents»
				«switch subcomponent{
					ThreadSubcomponent:'''
						«IF subcomponent.inModes.contains(mode) || (subcomponent.inModes.size ===0 && subcomponent.allFeatures.size > 0)»
							«subcomponent.name.convert»_task := new «subcomponent.classifier.name.convert»(«genThreadConParam(connections,subcomponent).toString.clearspace.formatParam»);
						«ENDIF»
					'''
				}»
			«ENDFOR»
		«ENDFOR»
		end case;
	'''
	/*
	 * 当进程没有模式变换时调用进程下的子组件
	 * @param subcomponents 进程的子组件列表
	 * @param connections 进程的连接列表
	 */
	def static dealTaskCall(List<Subcomponent> subcomponents, List<Connection> connections)'''
		«FOR subcomponent : subcomponents»
			«switch subcomponent{
				ThreadSubcomponent:'''
					«IF subcomponent.allFeatures.size>0»
						«subcomponent.name.convert»_task := new «subcomponent.classifier.name.convert»(«genThreadConParam(connections,subcomponent).toString.clearspace.formatParam»);
					«ENDIF»
				'''
			}»
		«ENDFOR»
	'''
}