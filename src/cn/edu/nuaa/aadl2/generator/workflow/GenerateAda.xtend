package cn.edu.nuaa.aadl2.generator.workflow

import org.osate.aadl2.SystemImplementation
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ProcessTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ModeTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ConnectionTemplateAda.*

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
import org.osate.aadl2.Mode
import org.osate.aadl2.PublicPackageSection
import org.osate.aadl2.ModelUnit
import org.osate.aadl2.AadlPackage
import org.osate.aadl2.Connection
import org.osate.aadl2.ProcessorSubcomponent

class GenerateAda {
	var static adaFolder = "Ada_codes"
	def static generate(SystemImplementation system){
		if (system.name!=null){
			Tools.folder(adaFolder)
			TemplateAda.packageName=Tools.getPackageName(system.eContainer.eContainer.toString)
			TemplateAda.subprogramsFileName = TemplateAda.packageName+"_Subprograms"
			generateSystem(adaFolder,system,system.name)
			
			var PublicPackageSection publicSection = system.eContainer as PublicPackageSection
			for(ModelUnit modelUnit : publicSection.importedUnits){
				switch modelUnit{
					AadlPackage case modelUnit.name.equals("DataType"):
						genDataType(adaFolder+"/system_"+system.name.convert, modelUnit)
				}
			}
		}
	}
	/*
	 * 处理系统实现组件
	 * @param parentFolderPath 此系统目录的父目录路径
	 * @param system 此系统实现实例
	 * @param systemName 此系统名称
	 */
	def static generateSystem(String parentFolderPath, SystemImplementation system, String systemName){
		var String currentFolder = "system_"
		if(systemName === null){
			currentFolder += system.name.convert 
		}else{
			currentFolder += systemName.convert
		}
		var currentFolderPath = parentFolderPath + "/" + currentFolder
		Tools.folder(currentFolderPath)

		if(system.ownedSystemSubcomponents.size > 0){
			for(SystemSubcomponent systemsubcomponent : system.ownedSystemSubcomponents){
				var SystemImplementation systemImplementation = systemsubcomponent.systemSubcomponentType as SystemImplementation
				generateSystem(currentFolderPath,systemImplementation, systemsubcomponent.name)
			}
		}
		Tools.createFile(currentFolderPath, systemName.convert+".adb", genSystemProcedure(system,systemName).toString)
		
		if(system.allFeatures.size > 0){
			genSystemFeature(currentFolderPath,systemName,system.allFeatures)
		}
		if(system.ownedDataSubcomponents.size > 0){
			genSystemDataSubcomponent(currentFolderPath,systemName,system.ownedDataSubcomponents)
		}
		if(system.ownedProcessSubcomponents.size > 0){
			for(ProcessSubcomponent processsubcomponent : system.ownedProcessSubcomponents){
				genSystemProcessSubcomponent(currentFolderPath,processsubcomponent,systemName)
			}
		}
	}
	/*
	 * 生成系统主过程
	 * @param system 系统实现实例
	 * @param systemName 系统名称
	 */
	def static genSystemProcedure(SystemImplementation system, String systemName)'''
		procedure «systemName.convert» is
			«FOR processSubcomponent : system.ownedProcessSubcomponents»
				procedure «processSubcomponent.name.convert» is separate;
			«ENDFOR»
			«IF system.ownedConnections.size > 0»
				«system.ownedConnections.genConnectionVar»
			«ENDIF»
			«IF system.ownedModes.size > 0»
				«system.ownedModes.genMode.toString.clearspace»
				current_mode : Modes;
			«ENDIF»
		begin
			«IF system.ownedModes.size > 0»
				«system.ownedModes.initMode.toString.clearspace»
			«ENDIF»
			«IF system.ownedModeTransitions.size >0»
				«system.ownedModeTransitions.genModeTransition»
			«ENDIF»
			«IF system.ownedModes.size > 0»
				«dealSystemMode(system.ownedModes, system.allSubcomponents, system.ownedConnections)»
			«ELSE»
				«dealProcedureCall(system.allSubcomponents, system.ownedConnections)»
			«ENDIF»
		end «systemName.convert»;
	'''
	/*
	 * 根据系统所处的不同模式调用该模式下的进程子组件
	 * @param modes 系统拥有的模式列表
	 * @param subcomponents 系统的子组件列表
	 * @param connections 系统的连接列表
	 */
	def static dealSystemMode(List<Mode> modes,List<Subcomponent> subcomponents,List<Connection> connections)'''
		case surrent_mode is
		«FOR mode : modes»
		when «mode.name» =>
			«FOR subcomponent : subcomponents»
				«switch subcomponent{
					ProcessSubcomponent:'''
						«IF subcomponent.inModes.contains(mode) || subcomponent.inModes.size === 0»
							«subcomponent.name.convert»(«genConParam(connections,subcomponent.name).toString.clearspace.formatParam»);
						«ENDIF»
					'''
				}»
			«ENDFOR»
		«ENDFOR»
		end case;
	'''
	/*
	 * 当系统没有模式变换时调用进程子组件
	 * @param subcomponents 系统的子组件列表
	 * @param connections 系统的连接列表
	 */
	def static dealProcedureCall(List<Subcomponent> subcomponents, List<Connection> connections)'''
		«FOR subcomponent : subcomponents»
			«switch subcomponent{
				ProcessSubcomponent:'''
					«subcomponent.name.convert»(«genConParam(connections,subcomponent.name).toString.clearspace.formatParam»);
				'''
			}»
		«ENDFOR»
	'''
}