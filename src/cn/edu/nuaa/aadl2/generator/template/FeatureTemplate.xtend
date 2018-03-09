package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.Feature
import org.osate.aadl2.DataPort
import org.osate.aadl2.DirectionType
import java.util.List

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.utils.PropertyParser.*
import static extension cn.edu.nuaa.aadl2.generator.template.DataTemplate.*
import org.osate.aadl2.Parameter
import org.osate.aadl2.EventPort
import org.osate.aadl2.EventDataPort
import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.Classifier
import org.osate.aadl2.DataClassifier

/*
 * Feature a
 * feature as method parameter while call method, eg: f(a);
 * feature as variable, eg: int a;
 * feature as variable in method defination,eg: int f(int a);
 * */
class FeatureTemplate {
	def static template(Feature feature){
		switch feature{
			DataPort : '''
				«IF feature.direction.equals(DirectionType.IN)»
					«feature.classifier.name.convert» «feature.name.convert»;
				«ENDIF»
				«IF feature.direction.equals(DirectionType.OUT)»«ENDIF»
				«IF feature.direction.equals(DirectionType.IN_OUT)»«ENDIF»
			'''
			EventPort : '''
			
			'''
			EventDataPort : '''
			
			'''
			Parameter : '''
			
			'''
		}		
	}
	  
	def static asDefinations(List<Feature> features)'''
		«FOR Feature feature : features»
			«feature.datadefination.toString.clear»
			«IF !feature.equals(features.get(features.size-1))»«(feature.asDefination+",").toString.clearspace»«ENDIF»
			«IF feature.equals(features.get(features.size-1))»«feature.asDefination»«ENDIF»
		«ENDFOR»
	'''
	
	def static asDefination(Feature feature){
		switch feature{
			Parameter : '''
				«IF feature.direction.equals(DirectionType.IN)»
					«feature.classifier.name.convert» «feature.name.convert»
				«ENDIF»
				«IF feature.direction.equals(DirectionType.OUT)»
					«feature.classifier.name.convert» *«feature.name.convert»
				«ENDIF»					
				«IF feature.direction.equals(DirectionType.IN_OUT)»
					
				«ENDIF»
			'''
			EventPort : ''''''
			EventDataPort : ''''''
			DataPort : '''
				«IF feature.direction.equals(DirectionType.IN)»
					«feature.classifier.name.convert» «feature.name.convert»
				«ENDIF»
				«IF feature.direction.equals(DirectionType.OUT)»
					«feature.classifier.name.convert» *«feature.name.convert»
				«ENDIF»					
				«IF feature.direction.equals(DirectionType.IN_OUT)»
					
				«ENDIF»
			'''
		}	
	}
	
	def static asParameters(List<Feature> features)'''
		«FOR Feature feature : features»
			«feature.datadefination.toString.clear»
			«IF !feature.equals(features.get(features.size-1))»«(feature.asParameter+",").toString.clearspace»«ENDIF»
			«IF feature.equals(features.get(features.size-1))»«feature.asParameter»«ENDIF»
		«ENDFOR»
	'''
	
	def static asParameter(Feature feature){
		switch feature{
			Parameter : '''
				«IF feature.direction.equals(DirectionType.IN)»
					«feature.name.convert»
				«ENDIF»
				«IF feature.direction.equals(DirectionType.OUT)»
					«feature.name.convert»
				«ENDIF»					
				«IF feature.direction.equals(DirectionType.IN_OUT)»
					«feature.name.convert»
				«ENDIF»
			'''
			EventPort : ''''''
			EventDataPort : ''''''
			DataPort : ''''''
		}
	}
	
	def static asVariables(List<Feature> features)'''
		«FOR Feature feature : features»
			«feature.datadefination.toString.clear»
			«(feature.asVariable+";").toString.clearspace»
		«ENDFOR»
	'''
	
	def static asVariable(Feature feature){
		switch feature{
			Parameter : '''
				«IF feature.direction.equals(DirectionType.IN)»
					«feature.classifier.name.convert» «feature.name.convert»
				«ENDIF»
				«IF feature.direction.equals(DirectionType.OUT)»
					«feature.classifier.name.convert» *«feature.name.convert»
				«ENDIF»					
				«IF feature.direction.equals(DirectionType.IN_OUT)»
					
				«ENDIF»
			'''			
			EventPort : ''''''
			EventDataPort : ''''''
			DataPort : '''
				«IF feature.direction.equals(DirectionType.IN)»
					«feature.classifier.name.convert» «feature.name.convert»
				«ENDIF»
				«IF feature.direction.equals(DirectionType.OUT)»
					«feature.classifier.name.convert» *«feature.name.convert»
				«ENDIF»					
				«IF feature.direction.equals(DirectionType.IN_OUT)»
					
				«ENDIF»
			'''
		}
	}
	
	def static  datadefination(Feature feature)'''
		«feature.classifier.featuredata»
	'''
	
	def static featuredata(Classifier classifier){
		switch classifier{
			DataClassifier : '''«classifier.template»'''
		}
	}
}