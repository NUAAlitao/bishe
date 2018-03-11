package cn.edu.nuaa.aadl2.generator.templateAda

import java.util.List
import org.osate.aadl2.Feature
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*

class FeatureTemplateAda {
	def static genSystemFeature(String folderPath, String systemName, List<Feature> features){
		Tools.createFile(folderPath,systemName+"_feature.ads",systemFeature(systemName,features).toString())
	}
	
	def static systemFeature(String systemName, List<Feature> features)'''
		package «systemName»_feature is
			«FOR Feature feature : features»
				«feature.name» : «feature.classifier.name.convertPoint»;
			«ENDFOR»
		end «systemName»_feature;
	'''
}