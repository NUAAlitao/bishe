package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.DefaultAnnexSubclause
import org.osate.ba.aadlba.BehaviorAnnex
import org.osate.ba.aadlba.BehaviorVariable
import org.osate.ba.aadlba.BehaviorState
import org.osate.ba.aadlba.BehaviorTransition
import org.osate.ba.aadlba.ValueExpression
import org.osate.ba.aadlba.Relation
import org.osate.ba.aadlba.DataComponentReference
import org.osate.ba.aadlba.DataHolder
import org.osate.ba.aadlba.BehaviorIntegerLiteral
import org.osate.ba.aadlba.BehaviorRealLiteral
import org.osate.ba.aadlba.BehaviorBooleanLiteral
import org.osate.ba.aadlba.BehaviorVariableHolder

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.ba.utils.AadlBaVisitors
import org.osate.ba.aadlba.BehaviorActionSequence
import org.osate.ba.aadlba.PortSendAction
import org.osate.ba.aadlba.PortDequeueAction
import org.osate.ba.aadlba.AssignmentAction
import org.osate.ba.aadlba.Target
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl
import org.osate.ba.aadlba.DispatchCondition
import java.util.Map
import java.util.HashMap
import org.osate.ba.aadlba.DispatchTriggerLogicalExpression
import org.osate.ba.aadlba.EventDataPortHolder
import org.osate.ba.aadlba.EventPortHolder
import org.osate.ba.aadlba.DataPortHolder
import org.osate.ba.aadlba.BehaviorTime

class AnnexSubclauseTemplateAda {

	def static genBehaviorAnnexVarible(DefaultAnnexSubclause defaultAnnexSubclause){
		if((defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)==null){
			TemplateAda.printLogAnnexError
		}else{
			dealBehaviorAnnexVariable(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)
		}
	}
	
	def static genBehaviorAnnexState(DefaultAnnexSubclause defaultAnnexSubclause){
		if((defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)==null){
			TemplateAda.printLogAnnexError
		}else{
			dealBehaviorAnnexState(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex).toString.clearspace
		}
	}
	
	def static genBehaviorAnnexTransition(DefaultAnnexSubclause defaultAnnexSubclause){
		if((defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)==null){
			TemplateAda.printLogAnnexError
		}else{
			dealBehaviorAnnexTransition(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)
		}
	}
	
	def static initBehaviorAnnexState(DefaultAnnexSubclause defaultAnnexSubclause){
		if((defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)==null){
			TemplateAda.printLogAnnexError
			return ""
		}else{
			dealInitState(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex).toString.clearspace
		}
	}
	
	def static dealInitState(BehaviorAnnex behaviorAnnex)'''
		current_state 
		«FOR behaviorState : behaviorAnnex.states»
			«IF behaviorState.initial == true»
				:= «behaviorState.name»;
			«ENDIF»
		«ENDFOR»
	'''
	
	def static dealBehaviorAnnexVariable(BehaviorAnnex behaviorAnnex)'''
		«FOR BehaviorVariable behaviorVariable : behaviorAnnex.variables»
			«behaviorVariable.name» : «behaviorVariable.dataClassifier.name.convertPoint»;
		«ENDFOR»
	'''
	
	def static dealBehaviorAnnexState(BehaviorAnnex behaviorAnnex)'''
		type States is (
		«FOR BehaviorState behaviorState : behaviorAnnex.states.subList(0,behaviorAnnex.states.size-1)»
			«behaviorState.name», 
		«ENDFOR»
		«behaviorAnnex.states.get(behaviorAnnex.states.size-1).name»);
		
	'''
	def static dealBehaviorAnnexTransition(BehaviorAnnex behaviorAnnex)'''
		case current_state is
		«var Map<BehaviorTransition,BehaviorTransition> isUsed = new HashMap<BehaviorTransition,BehaviorTransition>()»
		«FOR BehaviorTransition behaviorTransition : behaviorAnnex.transitions»
			«IF isUsed.get(behaviorTransition) === null»
				when «behaviorTransition.sourceState.name» =>
					«FOR BehaviorTransition behaviorTransition1 : AadlBaVisitors.getTransitionWhereSrc(behaviorTransition.sourceState)»
						«IF isUsed.get(behaviorTransition1) === null»
							«initBehaviorAnnexTransitionConditon(behaviorTransition1)»
							if («dealBehaviorAnnexTransitionCondition(behaviorTransition1).toString.clearspace.dealMultipleSpace») then
								«dealBehaviorAnnexTransitionAction(behaviorTransition1)»
								current_state := «behaviorTransition1.destinationState.name»;
							end if;
							«isUsed.put(behaviorTransition1,behaviorTransition1)»
						«ENDIF»
					«ENDFOR»
			«ENDIF»
		«ENDFOR»
		when others => null;
		end case;
	'''
	
	def static dealBehaviorAnnexTransitionAction(BehaviorTransition behaviorTransition)'''
		«IF behaviorTransition.actionBlock !== null»
			«var actionBlock = behaviorTransition.actionBlock»
			«FOR actionElement : actionBlock.children»
				«switch actionElement{
					BehaviorActionSequence:'''
						«actionElement.dealActionElement»
					'''
					PortSendAction:'''
						«actionElement.dealActionElement»
					'''
					PortDequeueAction:'''
						«actionElement.dealActionElement»
					'''
					AssignmentAction:'''
						«actionElement.dealActionElement»
					'''
					default:null
				}»
			«ENDFOR»
			«IF actionBlock.timeout !== null»
				delay «dealActionTimeOut(actionBlock.timeout).toString.clearspace»;
			«ENDIF»
		«ENDIF»
	'''
	def static dealActionTimeOut(BehaviorTime behaviorTime){
		var float base
		switch behaviorTime.unit.name{
			case "ms":base=0.001f
			default :base=1
		}
		switch behaviorTime.integerValue{
			BehaviorIntegerLiteral:'''
				«(Integer.parseInt(dealBehaviorIntegerLiteral(behaviorTime.integerValue as BehaviorIntegerLiteral).toString.clearspace)*base).toString»
			'''
		}
	}
	def static dealActionElement(BehaviorActionSequence behaviorActionSequence)'''
		«FOR action : behaviorActionSequence.actions»
			«switch action{
				BehaviorActionSequence:'''
					«action.dealActionElement»
				'''
				PortSendAction:'''
					«action.dealActionElement»
				'''
				PortDequeueAction:'''
					«action.dealActionElement»
				'''
				AssignmentAction:'''
					«action.dealActionElement»
				'''
				default:null
			}»
		«ENDFOR»
	'''
	def static dealActionElement(PortSendAction portSendAction)'''
		«portSendAction.port.element.name»_temp.send(«IF portSendAction.valueExpression !== null»«portSendAction.valueExpression.dealValueExpression.clearspace»«ELSE»True«ENDIF»);
		«portSendAction.port.element.name»_temp.release;
	'''
	def static dealActionElement(PortDequeueAction portDequeueAction)'''
		«portDequeueAction.target.dealActionTarget.toString.clearspace» := «portDequeueAction.port.element.name»;
	'''
	def static dealActionElement(AssignmentAction assignmentAction)'''
		«assignmentAction.target.dealActionTarget.toString.clearspace» := «assignmentAction.valueExpression.dealValueExpression.clearspace»;
	'''
	def static dealActionTarget(Target target)'''
		«switch target{
			BehaviorVariableHolderImpl:'''
				«target.element.name»
			'''
		}»
	'''
	
	def static initBehaviorAnnexTransitionConditon(BehaviorTransition behaviorTransition)'''
		«IF behaviorTransition.condition !== null»
			«switch behaviorTransition.condition{
				DispatchCondition:'''
					«dealDispatchCondition(behaviorTransition.condition as DispatchCondition).toString.clearspace»_temp.secure;
					«dealDispatchCondition(behaviorTransition.condition as DispatchCondition).toString.clearspace»:=«dealDispatchCondition(behaviorTransition.condition as DispatchCondition).toString.clearspace»_temp.receive;
				'''
			}»
		«ENDIF»
	'''
	
	def static dealBehaviorAnnexTransitionCondition(BehaviorTransition behaviorTransition)'''
		«IF behaviorTransition.condition !== null»
			«switch behaviorTransition.condition{
				ValueExpression:
					dealValueExpression(behaviorTransition.condition as ValueExpression)
				DispatchCondition:
					dealDispatchCondition(behaviorTransition.condition as DispatchCondition)
			}»
		«ELSE»
			True
		«ENDIF»
	'''
	
	def static dealDispatchCondition(DispatchCondition dispatchCondition)'''
		«switch dispatchCondition.dispatchTriggerCondition{
			DispatchTriggerLogicalExpression:'''
				«dealDispatchTriggerLogicalExpression(dispatchCondition.dispatchTriggerCondition as DispatchTriggerLogicalExpression)»
			'''
		}»
	'''
	
	def static dealDispatchTriggerLogicalExpression(DispatchTriggerLogicalExpression logicalExpression)'''
		«FOR dispatchConjunction :logicalExpression.dispatchConjunctions»
			«FOR dispatchTrigger : dispatchConjunction.dispatchTriggers»
				«switch dispatchTrigger{
					EventDataPortHolder:'''
						«dispatchTrigger.element.name»
					'''
					EventPortHolder:'''
						«dispatchTrigger.element.name»
					'''
					DataPortHolder:'''
						«dispatchTrigger.element.name»
					'''
				}»
			«ENDFOR»
		«ENDFOR»
	'''
	def static String dealValueExpression(ValueExpression valueExpression)'''
		«var relations = valueExpression.relations»
		«var logicalOperators = valueExpression.logicalOperators»
		«var i = 0»
		«FOR Relation relation : relations»
			«var firstValue = relation.firstExpression.terms.get(0).factors.get(0).firstValue»
			«relation.firstExpression.terms.get(0).factors.get(0).unaryBooleanOperator»
			 («switch firstValue {
				DataComponentReference:'''
					«dealDataComponentReference(firstValue as DataComponentReference).toString.clearspace»
				'''
				BehaviorIntegerLiteral:'''
					«dealBehaviorIntegerLiteral(firstValue as BehaviorIntegerLiteral)»
				'''
				BehaviorRealLiteral:'''
					«dealBehaviorRealLiteral(firstValue as BehaviorRealLiteral)»
				'''
				BehaviorBooleanLiteral:'''
					«dealBehaviorBooleanLiteral(firstValue as BehaviorBooleanLiteral)»
				'''
				BehaviorVariableHolder:'''
					«dealBehaviorVariableHolder(firstValue as BehaviorVariableHolder)»
				'''
				EventDataPortHolder:'''
					«dealEventDataPortHolder(firstValue as EventDataPortHolder)»
				'''
				ValueExpression:'''
					«dealValueExpression(firstValue as ValueExpression)»
				'''
			}» «relation.relationalOperator» 
			«IF relation.secondExpression !== null»
				«var secondValue = relation.secondExpression.terms.get(0).factors.get(0).firstValue»
				«switch secondValue {
					DataComponentReference:'''
						«dealDataComponentReference(secondValue as DataComponentReference).toString.clearspace»
					'''
					BehaviorIntegerLiteral:'''
						«dealBehaviorIntegerLiteral(secondValue as BehaviorIntegerLiteral)»
					'''
					BehaviorRealLiteral:'''
						«dealBehaviorRealLiteral(secondValue as BehaviorRealLiteral)»
					'''
					BehaviorBooleanLiteral:'''
						«dealBehaviorBooleanLiteral(secondValue as BehaviorBooleanLiteral)»
					'''
					BehaviorVariableHolder:'''
						«dealBehaviorVariableHolder(secondValue as BehaviorVariableHolder)»
					'''
					EventDataPortHolder:'''
						«dealEventDataPortHolder(secondValue as EventDataPortHolder)»
					'''
					ValueExpression:'''
						«dealValueExpression(secondValue as ValueExpression)»
					'''
			}»«ENDIF») 
			«IF i < logicalOperators.size»
				«logicalOperators.get(i++)»
			«ENDIF»
		«ENDFOR»
	'''
	def static dealEventDataPortHolder(EventDataPortHolder eventDataPortHolder)'''
		«eventDataPortHolder.element.name»
	'''
	def static dealDataComponentReference(DataComponentReference dataComponentReference)'''
		«FOR DataHolder dataHolder : dataComponentReference.data.subList(0,dataComponentReference.data.size-1)»
			«dataHolder.element.name».
		«ENDFOR»
		«dataComponentReference.data.get(dataComponentReference.data.size-1).element.name»
	'''
	def static dealBehaviorIntegerLiteral(BehaviorIntegerLiteral behaviorIntegerLiteral)'''
		«behaviorIntegerLiteral.value»
	'''
	def static dealBehaviorRealLiteral(BehaviorRealLiteral behaviorRealLiteral)'''
		«behaviorRealLiteral.value»
	'''
	def static dealBehaviorBooleanLiteral(BehaviorBooleanLiteral behaviorBooleanLiteral)'''
		«behaviorBooleanLiteral.getValue()»
	'''
	def static dealBehaviorVariableHolder(BehaviorVariableHolder behaviorVariableHolder)'''
		«behaviorVariableHolder.element.name»
	'''
}