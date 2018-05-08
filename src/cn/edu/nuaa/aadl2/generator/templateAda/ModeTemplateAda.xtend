package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.Mode
import java.util.List
import org.osate.aadl2.ModeTransition
import java.util.Map
import java.util.HashMap
import cn.edu.nuaa.aadl2.generator.utils.AadlModeVisitors
import org.osate.aadl2.ModeTransitionTrigger

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.aadl2.Connection

class ModeTemplateAda {
	
	def static genMode(List<Mode> modes)'''
		type Modes is(
		«FOR Mode mode : modes.subList(0,modes.size-1)»
			«mode.name»,
		«ENDFOR»
		«modes.get(modes.size-1).name»);
	'''
	
	def static initMode(List<Mode> modes)'''
		current_mode 
		«FOR mode : modes»
			«IF mode.initial == true»
				:= «mode.name»;
			«ENDIF»
		«ENDFOR»
	'''
	
	def static genModeTransition(List<ModeTransition> modeTransitions)'''
		case current_mode is
		«var Map<ModeTransition,ModeTransition> isUsed = new HashMap<ModeTransition,ModeTransition>()»
		«FOR ModeTransition modeTransition : modeTransitions»
			«IF isUsed.get(modeTransition) == null»
				when «modeTransition.source.name» =>
					«FOR ModeTransition modeTransition1 : AadlModeVisitors.getTransitionsWhereSrc(modeTransition.source, modeTransitions)»
						«IF isUsed.get(modeTransition1) == null»
							if («modeTransition1.ownedTriggers.dealModeTransitionTrigger.toString.clearspace») then
								current_mode := «modeTransition1.destination.name»;
							end if;
							«isUsed.put(modeTransition1,modeTransition1)»
						«ENDIF»
					«ENDFOR»
			«ENDIF»
		«ENDFOR»
		end case;
	'''
	
	
	def static dealModeTransitionTrigger(List<ModeTransitionTrigger> modeTransitionTriggers)'''
		«FOR ModeTransitionTrigger modeTransitionTrigger : modeTransitionTriggers»
			«modeTransitionTrigger.triggerPort.name»
		«ENDFOR»
	'''
	
	def static genProcessModeTransition(List<ModeTransition> modeTransitions, List<Connection> connections)'''
		case current_mode is
		«var Map<ModeTransition,ModeTransition> isUsed = new HashMap<ModeTransition,ModeTransition>()»
		«FOR ModeTransition modeTransition : modeTransitions»
			«IF isUsed.get(modeTransition) == null»
				when «modeTransition.source.name» =>
					«FOR ModeTransition modeTransition1 : AadlModeVisitors.getTransitionsWhereSrc(modeTransition.source, modeTransitions)»
						«IF isUsed.get(modeTransition1) == null»
							if («modeTransition1.ownedTriggers.dealModeTransitionTrigger.toString.clearspace») then
								current_mode := «modeTransition1.destination.name»;
«««								«dealModeTriggerSend(modeTransition1.ownedTriggers,connections)»
							end if;
							«isUsed.put(modeTransition1,modeTransition1)»
						«ENDIF»
					«ENDFOR»
			«ENDIF»
		«ENDFOR»
		when others => null;
		end case;
	'''
	/*
	 * 将触发转换到当前模式的信号发送到对应的保护对象
	 * @param modeTransitions 模式转换列表
	 * @param connections 连接列表
	 * @param modeName 模式名称
	 */
	def static genModeTriggerSend(List<ModeTransition> modeTransitions, List<Connection> connections, String modeName)'''
		«FOR modeTransition : modeTransitions»
			«IF modeTransition.destination.name.equals(modeName)»
				«dealModeTriggerSend(modeTransition.ownedTriggers,connections)»
			«ENDIF»
		«ENDFOR»
	'''
	/*
	 * 当进程进行模式转换时，将对应的端口事件发送到对应的保护对象
	 */
	def static dealModeTriggerSend(List<ModeTransitionTrigger> modeTransitionTriggers, List<Connection> connections)'''
		«FOR ModeTransitionTrigger modeTransitionTrigger : modeTransitionTriggers»
			«var connection = getConnection(connections,modeTransitionTrigger.triggerPort.name)»
			«IF connection !== null»
				«connection.name»_object.send(«modeTransitionTrigger.triggerPort.name»);
				«connection.name»_object.release;
			«ENDIF»
		«ENDFOR»
	'''
	/*
	 * 返回连接的source的connectionEnd的端口名与指定端口名称一致的连接
	 * @param connections 进程中的连接列表
	 * @param portName 端口名称
	 */
	def static Connection getConnection(List<Connection> connections, String portName){
		for(connection : connections){
			if(connection.source.connectionEnd.name.equals(portName)){
				return connection;
			}
		}
		return null;
	}
}