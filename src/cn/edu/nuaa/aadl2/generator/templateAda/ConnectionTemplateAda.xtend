package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.Connection
import java.util.List
import org.osate.aadl2.PortConnection
import org.osate.aadl2.ConnectionEnd

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.aadl2.AccessConnection
import org.osate.aadl2.DataPort
import org.osate.aadl2.DataAccess
import org.osate.aadl2.EventPort
import org.osate.aadl2.EventDataPort
import org.osate.aadl2.Subcomponent
import org.osate.aadl2.ThreadSubcomponent

class ConnectionTemplateAda {
	/*
	 * 生成连接变量（当连接是组件内的两个子组件之间的连接时）
	 * @param connections 当前组件下的所有连接
	 */
	def static genConnectionVar(List<Connection> connections)'''
		«FOR connection : connections»
			«switch connection{
				PortConnection:'''
					«connection.dealPortConVar»
				'''
				AccessConnection:'''
					«connection.dealAccessConVar»
				'''
			}»
		«ENDFOR»
	'''
	/*
	 * 根据组件之间的连接关系生成过程调用时的实参和形参的传递关系
	 * @param connections 当前组件下的所有连接
	 * @param componentName 当前组件的名称
	 */
	def static genConParam(List<Connection> connections, Subcomponent component)'''
		«FOR connection : connections»
			«IF connection.source.context !== null && connection.destination.context !== null»
				«dealConParamTwoContext(connection,component).toString.clearspace»
			«ELSE»
				«dealConParamOneContext(connection,component).toString.clearspace»
			«ENDIF»
		«ENDFOR»
	'''
	/*
	 * 处理当前组件端口和子组件端口之间的连接对应的参数传递关系
	 * @param connection 连接对象
	 * @param componentName 当前组件名称
	 */
	def static dealConParamOneContext(Connection connection, Subcomponent component){
		if(component instanceof ThreadSubcomponent){
			if(connection.destination.context!==null && connection.destination.context.name.equals(component.name)){
				return '''«connection.destination.connectionEnd.name»=>«connection.source.connectionEnd.name»,'''
			}
		}else{
			if(connection.source.context !== null){
				if(connection.source.context.name.equals(component.name)){
					return '''«connection.source.connectionEnd.name»=>«connection.destination.connectionEnd.name»,'''
				}
			}else{
				if(connection.destination.context.name.equals(component.name)){
					return '''«connection.destination.connectionEnd.name»=>«connection.source.connectionEnd.name»,'''
				}
			}
		}
		return ""
	}
	/*
	 * 处理两个子组件之间的连接对应的参数传递关系
	 * @param connection 连接对象
	 * @param componentName 当前组件名称
	 */
	def static dealConParamTwoContext(Connection connection, Subcomponent component){
		if(component instanceof ThreadSubcomponent){
			if(connection.destination.context.name.equals(component.name)){
				return '''«connection.destination.connectionEnd.name»=>«connection.name»,'''
			}
		}else{
			if(connection.source.context.name.equals(component.name)){
				return '''«connection.source.connectionEnd.name»=>«connection.name»,'''
			}else if(connection.destination.context.name.equals(component.name)){
				return '''«connection.destination.connectionEnd.name»=>«connection.name»,'''
			}
		}
		return ""
	}
	/*
	 * 处理端口连接生成变量
	 * @param portConnection 端口连接对象
	 */
	def static dealPortConVar(PortConnection portConnection)'''
		«IF portConnection.source.context !== null && portConnection.destination.context !== null»
			«portConnection.name» : «portConnection.source.connectionEnd.dealConnectionEndType.toString.clearspace»;
		«ENDIF»
	'''
	/*
	 * 处理访问连接生成变量
	 * @param accessConnection 访问连接对象
	 */
	def static dealAccessConVar(AccessConnection accessConnection)'''
		«switch accessConnection.accessCategory.getName{
			case "data":'''
				«accessConnection.dealDataAccessVar»
			'''
		}»
	'''
	/*
	 * 处理数据访问连接生成变量
	 * @param accessConnection
	 */
	def static dealDataAccessVar(AccessConnection accessConnection)'''
		«IF accessConnection.source.context !== null && accessConnection.destination.context !== null»
			type «accessConnection.source.connectionEnd.dealConnectionEndType.toString.clearspace»_access is access «accessConnection.source.connectionEnd.dealConnectionEndType.toString.clearspace»;
			«accessConnection.name» : «accessConnection.source.connectionEnd.dealConnectionEndType.toString.clearspace»_access;
		«ENDIF»
	'''
	/*
	 * 处理连接中端口或者数据访问的类型（如果未指定变量类型，默认为Boolean）
	 * @param connectionEnd
	 */
	def static dealConnectionEndType(ConnectionEnd connectionEnd)'''
		«switch connectionEnd{
			DataPort:'''
				«IF connectionEnd.classifier !== null »«connectionEnd.classifier.name.convertPoint»«ELSE»Boolean«ENDIF»
			'''
			EventPort:'''
				«IF connectionEnd.classifier !== null »«connectionEnd.classifier.name.convertPoint»«ELSE»Boolean«ENDIF»
			'''
			EventDataPort:'''
				«IF connectionEnd.classifier !== null »«connectionEnd.classifier.name.convertPoint»«ELSE»Boolean«ENDIF»
			'''
			DataAccess:'''
				«IF connectionEnd.classifier !== null »«connectionEnd.classifier.name.convertPoint»«ELSE»Boolean«ENDIF»
			'''
		}»
	'''
}