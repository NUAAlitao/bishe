package cn.edu.nuaa.aadl2.generator.templateAda

import java.util.List
import org.osate.aadl2.Feature
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.aadl2.FeatureClassifier
import org.osate.aadl2.EventPort
import org.osate.aadl2.DataPort
import org.osate.aadl2.EventDataPort
import org.osate.aadl2.DataAccess
import org.osate.aadl2.Connection

class FeatureTemplateAda {
	/*
	 * 处理系统类型声明中的features
	 * @param folderPath 此系统目录路径
	 * @param systemName 此系统名
	 * @param features 系统类型声明中的features列表
	 */
	def static genSystemFeature(String folderPath, String systemName, List<Feature> features){
		Tools.createFile(folderPath,systemName.convert+"_feature.ads",systemFeature(systemName,features).toString())
	}
	
	/*
	 * 处理进程类型声明中的features
	 * @param features 进程类型声明中的features列表
	 */
	def static genProcessFeature(List<Feature> features)'''
		«FOR Feature feature : features»
			«feature.dealFeature»; 
		«ENDFOR»
	'''
	/*
	 * 处理线程类型声明中的输入输出端口，生成形参
	 * @param features 线程类型声明中的features列表
	 * @param connections 线程所在进程中的connections列表
	 * @param threadName 线程子组件名称
	 */
	def static genThreadFeature(List<Feature> features, List<Connection> connections, String threadName)'''
		«FOR Feature feature : features»
			«TemplateAda.addLogMessage("端口",feature.name)»
			«feature.dealThreadFeature(getConnection(connections,threadName,feature.name))»; 
		«ENDFOR»
	'''
	/*
	 * 得到和子线程对应端口连接的连接对象
	 * @param connections 连接列表
	 * @param threadName 线程名称
	 * @param featureName 线程的feature端口名称
	 * @return 符合条件的connection对象或者null
	 */
	def static Connection getConnection(List<Connection> connections, String threadName, String featureName){
		for(connection : connections){
			if(connection.source.context !== null && connection.source.context.name.equals(threadName) && connection.source.connectionEnd.name.equals(featureName)){
				return connection;
			}
			if(connection.destination.context !== null && connection.destination.context.name.equals(threadName) && connection.destination.connectionEnd.name.equals(featureName)){
				return connection;
			}
		}
		return null;
	}
	
	def static dealThreadFeature(Feature feature,Connection connection)'''
		«IF connection === null»
			«TemplateAda.printLogNoConnection»
		«ELSE»
		«switch feature{
			DataPort,
			EventPort,
			EventDataPort:'''
				«feature.name»_temp : access «connection.name».base
			'''
			DataAccess:'''
				«feature.name»_temp : access «feature.dealClassisfy»
			'''
		}»
		«ENDIF»
	'''
	/*
	 * 将线程的out和in out端口生成为进程中的变量
	 * @param features 线程类型声明中的features列表
	 * @param threadName 线程名称
	 */
	def static genThreadFeatureVarInProc(List<Feature> features, String threadName)'''
		«FOR feature : features»
			«switch feature{
				DataPort:'''
					«IF feature.out == true»
						«feature.name»_«threadName» : «feature.dealClassisfy.toString.clearspace»;
					«ENDIF»
				'''
				EventPort:'''
					«IF feature.out == true»
						«feature.name»_«threadName» : «feature.dealClassisfy.toString.clearspace»;
					«ENDIF»
				'''
				EventDataPort:'''
					«IF feature.out == true»
						«feature.name»_«threadName» : «feature.dealClassisfy.toString.clearspace»;
					«ENDIF»
				'''
			}»
		«ENDFOR»
	'''
	/*
	 * 处理线程类型声明中的输入输出端口，生成线程内的局部变量
	 * @param features 线程类型声明中的features列表
	 */
	def static genThreadInPortVar(List<Feature> features)'''
		«FOR feature : features»
			«switch feature{
				DataPort,
				EventPort,
				EventDataPort:'''
					«feature.name» : «feature.dealClassisfy.toString.clearspace»;
				'''
			}»
		«ENDFOR»
	'''
	/*
	 * 在线程的entry函数中初始化输入端口生成的局部变量
	 * @param features 线程类型声明中的features列表
	 */
	def static initThreadInPortVar(List<Feature> features)'''
		«FOR feature : features»
			«switch feature{
				DataPort:'''
					«IF feature.in == true && feature.out == false»
						«feature.name» := «feature.name»_temp;
					«ENDIF»
				'''
				EventPort:'''
					«IF feature.in == true && feature.out == false»
						«feature.name» := «feature.name»_temp;
					«ENDIF»
				'''
				EventDataPort:'''
					«IF feature.in == true && feature.out == false»
						«feature.name» := «feature.name»_temp;
					«ENDIF»
				'''
				DataAccess:'''
					«feature.name» := «feature.name»_temp;
				'''
			}»
		«ENDFOR»
	'''

	def static dealClassisfy(Feature feature)'''
		«IF feature.classifier !== null»
			«feature.classifier.name.convertPoint»
		«ELSE»
		Boolean
		«ENDIF»
	'''
	
	def static dealFeature(Feature feature)'''
		«feature.name» : 
		«switch feature{
			DataPort:'''
				«dealFeatureDirection(feature)» 
			'''
			EventPort:'''
				«dealFeatureDirection(feature)» 
			'''
			EventDataPort:'''
				«dealFeatureDirection(feature)» 
			'''
			DataAccess:'''
				access 
			'''
		}»
		«IF feature.classifier !== null»
			«feature.classifier.name.convertPoint»
		«ELSE»
		Boolean
		«ENDIF»
	'''
	
	def static dealFeatureDirection(DataPort portDirection)'''
		«IF portDirection.in == true && portDirection.out == true»
			in out 
		«ELSEIF portDirection.in == true»
			in 
		«ELSEIF portDirection.out == true»
			out 
		«ENDIF»
	'''
	def static dealFeatureDirection(EventPort portDirection)'''
		«IF portDirection.in == true && portDirection.out == true»
			in out 
		«ELSEIF portDirection.in == true»
			in 
		«ELSEIF portDirection.out == true»
			out 
		«ENDIF»
	'''
	def static dealFeatureDirection(EventDataPort portDirection)'''
		«IF portDirection.in == true && portDirection.out == true»
			in out 
		«ELSEIF portDirection.in == true»
			in 
		«ELSEIF portDirection.out == true»
			out 
		«ENDIF»
	'''

	
	def static systemFeature(String systemName, List<Feature> features)'''
		package «systemName.convert»_feature is
			«FOR Feature feature : features»
				«feature.name» : «IF feature.classifier !== null»«feature.classifier.name.convertPoint»«ELSE»Boolean«ENDIF»;
			«ENDFOR»
		end «systemName.convert»_feature;
	'''
}