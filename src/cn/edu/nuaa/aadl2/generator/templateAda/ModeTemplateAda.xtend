package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.Mode
import java.util.List
import org.osate.aadl2.ModeTransition
import java.util.Map
import java.util.HashMap
import cn.edu.nuaa.aadl2.generator.utils.AadlModeVisitors
import org.osate.aadl2.ModeTransitionTrigger

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*

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
}