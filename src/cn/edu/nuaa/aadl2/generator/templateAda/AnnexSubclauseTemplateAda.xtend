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
import org.osate.ba.aadlba.impl.BehaviorTransitionImpl
import org.osate.ba.utils.AadlBaVisitors
import org.osate.ba.aadlba.BehaviorAction
import org.osate.ba.aadlba.BehaviorActionSequence
import org.osate.ba.aadlba.PortSendAction
import org.osate.ba.aadlba.PortDequeueAction
import org.osate.ba.aadlba.AssignmentAction
import org.osate.ba.aadlba.Target
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl

class AnnexSubclauseTemplateAda {

	def static genBehaviorAnnexVarible(DefaultAnnexSubclause defaultAnnexSubclause){
		dealBehaviorAnnexVariable(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)
	}
	
	def static genBehaviorAnnexState(DefaultAnnexSubclause defaultAnnexSubclause){
		dealBehaviorAnnexState(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex).toString.clearspace
	}
	
	def static genBehaviorAnnexTransition(DefaultAnnexSubclause defaultAnnexSubclause){
		dealBehaviorAnnexTransition(defaultAnnexSubclause.parsedAnnexSubclause as BehaviorAnnex)
	}
		
	def static dealBehaviorAnnexVariable(BehaviorAnnex behaviorAnnex)'''
		«FOR BehaviorVariable behaviorVariable : behaviorAnnex.variables»
			«behaviorVariable.name» : «behaviorVariable.dataClassifier.name.convertPoint»;
		«ENDFOR»
	'''
	
	def static dealBehaviorAnnexState(BehaviorAnnex behaviorAnnex)'''
		type State is (
		«FOR BehaviorState behaviorState : behaviorAnnex.states.subList(0,behaviorAnnex.states.size-1)»
			«behaviorState.name», 
		«ENDFOR»
		«behaviorAnnex.states.get(behaviorAnnex.states.size-1).name»);
		
	'''
	def static dealBehaviorAnnexTransition(BehaviorAnnex behaviorAnnex)'''
		case current_state is
			«FOR BehaviorState behaviorState : behaviorAnnex.states»
				when «behaviorState.name» =>
					«FOR BehaviorTransition behaviorTransition : AadlBaVisitors.getTransitionWhereSrc(behaviorState)»
					if («dealBehaviorAnnexTransitionCondition(behaviorTransition).toString.clearspace.dealMultipleSpace») then
						«dealBehaviorAnnexTransitionAction(behaviorTransition)»
					«ENDFOR»
			«ENDFOR»
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
		«ENDIF»
	'''
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
		«portSendAction.port.element.name» = «portSendAction.valueExpression.dealValueExpression.clearspace»;
	'''
	def static dealActionElement(PortDequeueAction portDequeueAction)'''
		«portDequeueAction.target.dealActionTarget.toString.clearspace» = «portDequeueAction.port.element.name»;
	'''
	def static dealActionElement(AssignmentAction assignmentAction)'''
		«assignmentAction.target.dealActionTarget.toString.clearspace» = «assignmentAction.valueExpression.dealValueExpression.clearspace»;
	'''
	def static dealActionTarget(Target target)'''
		«switch target{
			BehaviorVariableHolderImpl:'''
				«target.element.name»
			'''
		}»
	'''
	
	def static dealBehaviorAnnexTransitionCondition(BehaviorTransition behaviorTransition)'''
		«IF behaviorTransition.condition !== null»
			«dealValueExpression(behaviorTransition.condition as ValueExpression)»
		«ELSE»
			True
		«ENDIF»
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
					«dealBehaivorIntegerLiteral(firstValue as BehaviorIntegerLiteral)»
				'''
				BehaviorRealLiteral:'''
					«dealBehaivorRealLiteral(firstValue as BehaviorRealLiteral)»
				'''
				BehaviorBooleanLiteral:'''
					«dealBehaviorBooleanLiteral(firstValue as BehaviorBooleanLiteral)»
				'''
				BehaviorVariableHolder:'''
					«dealBehaviorVariableHolder(firstValue as BehaviorVariableHolder)»
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
					«dealBehaivorIntegerLiteral(secondValue as BehaviorIntegerLiteral)»
				'''
				BehaviorRealLiteral:'''
					«dealBehaivorRealLiteral(secondValue as BehaviorRealLiteral)»
				'''
				BehaviorBooleanLiteral:'''
					«dealBehaviorBooleanLiteral(secondValue as BehaviorBooleanLiteral)»
				'''
				BehaviorVariableHolder:'''
					«dealBehaviorVariableHolder(secondValue as BehaviorVariableHolder)»
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
	
	def static dealDataComponentReference(DataComponentReference dataComponentReference)'''
		«FOR DataHolder dataHolder : dataComponentReference.data.subList(0,dataComponentReference.data.size-1)»
			«dataHolder.element.name».
		«ENDFOR»
		«dataComponentReference.data.get(dataComponentReference.data.size-1).element.name»
	'''
	def static dealBehaivorIntegerLiteral(BehaviorIntegerLiteral behaviorIntegerLiteral)'''
		«behaviorIntegerLiteral.value»
	'''
	def static dealBehaivorRealLiteral(BehaviorRealLiteral behaviorRealLiteral)'''
		«behaviorRealLiteral.value»
	'''
	def static dealBehaviorBooleanLiteral(BehaviorBooleanLiteral behaviorBooleanLiteral)'''
		«behaviorBooleanLiteral.getValue()»
	'''
	def static dealBehaviorVariableHolder(BehaviorVariableHolder behaviorVariableHolder)'''
		«behaviorVariableHolder.element.name»
	'''
}