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

class FeatureTemplateAda {
	/*
	 * 处理系统类型声明中的features
	 * @param folderPath 此系统目录路径
	 * @param systemName 此系统名
	 * @param features 系统类型声明中的features列表
	 */
	def static genSystemFeature(String folderPath, String systemName, List<Feature> features){
		Tools.createFile(folderPath,systemName+"_feature.ads",systemFeature(systemName,features).toString())
	}
	
	/*
	 * 处理进程类型声明中的features
	 * @param features 进程类型声明中的features列表
	 */
	def static genProcessFeature(List<Feature> features)'''
		«FOR Feature feature : features.subList(0,features.size-1)»
			«feature.dealFeature»; 
		«ENDFOR»
		«features.get(features.size-1).dealFeature»
	'''
	/*
	 * 处理线程类型声明中的features
	 * @param features 线程类型声明中的features列表
	 */
	def static genThreadFeature(List<Feature> features)'''
		«FOR Feature feature : features.subList(0,features.size-1)»
			«feature.dealFeature»; 
		«ENDFOR»
		«features.get(features.size-1).dealFeature»
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
		boolen
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
		package «systemName»_feature is
			«FOR Feature feature : features»
				«feature.name» : «IF feature.classifier != null»«feature.classifier.name.convertPoint»«ENDIF»;
			«ENDFOR»
		end «systemName»_feature;
	'''
}