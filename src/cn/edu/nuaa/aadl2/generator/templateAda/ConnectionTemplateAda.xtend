package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.Connection
import java.util.List
import org.osate.aadl2.PortConnection
import org.osate.aadl2.ConnectionEnd

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda.*
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
	 * 生成保护类型（当连接是进程内的两个线程之间的端口或者是进程和线程之间的端口交互时）
	 * @param connections 进程组件下的所有连接
	 */
	def static genProtectType(List<Connection> connections)'''
		with DataType; use DataType;
		with base_protect;
		
		package protected_types is
			«FOR connection : connections»
			«switch connection{
				PortConnection:'''
					«connection.dealPortConType»
				'''
			}»
			«ENDFOR»
		end protected_types;
	'''
	/*
	 * 处理进程中的端口连接，生成对应的保护类型
	 * @param connection 端口连接
	 */
	def static dealPortConType(PortConnection portConnection)'''
		package «portConnection.name» is new base_protect(«dealConnectionEndType(portConnection.source.connectionEnd).toString.clearspace»);
	'''
	/*
	 * 生成保护类型对象（当连接是进程内的两个线程之间的端口或者是进程和线程之间的端口交互时）
	 * @param connections 进程组件下的所有连接
	 */
	def static genProtectObject(List<Connection> connections)'''
		«FOR connection : connections»
			«IF connection instanceof PortConnection»
				«connection.name»_object : aliased «connection.name».base;
			«ENDIF»
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
	/*
	 * 根据进程中的连接关系生成线程端口和保护对象的传递关系
	 * @param connections 进程中的connections列表
	 * @param subcomponent 进程中的线程子组件
	 */
	def static genThreadConParam(List<Connection> connections, ThreadSubcomponent subcomponent)'''
		«FOR feature : subcomponent.classifier.allFeatures»
			«var connection = getConnection(connections,subcomponent.name,feature.name)»
			«IF connection === null»
				«TemplateAda.addLogMessage("线程",subcomponent.name)»
				«TemplateAda.addLogMessage("端口",feature.name)»
				«TemplateAda.printLogNoConnection()»
			«ELSE»
			«switch feature{
				DataPort,
				EventPort,
				EventDataPort:'''
					«feature.name»_temp=>«connection.name»_object'Access, 
				'''
				DataAccess:'''
					«feature.name»_temp : access «feature.dealClassisfy», 
				'''
			}»
			«ENDIF»
		«ENDFOR»
		
	'''
	
}