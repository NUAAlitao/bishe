package cn.edu.nuaa.aadl2.generator.templateAda

import java.util.List
import org.osate.aadl2.Feature
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*

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
		«FOR Feature feature : features»
			«feature.name» : «feature.classifier.name.convertPoint»;
		«ENDFOR»
	'''
	
	def static systemFeature(String systemName, List<Feature> features)'''
		package «systemName»_feature is
			«FOR Feature feature : features»
				«feature.name» : «feature.classifier.name.convertPoint»;
			«ENDFOR»
		end «systemName»_feature;
	'''
}