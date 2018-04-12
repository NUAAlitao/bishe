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
	 * 处理线程类型声明中的输入端口，生成形参
	 * @param features 线程类型声明中的features列表
	 */
	def static genThreadInFeature(List<Feature> features)'''
		«FOR Feature feature : features»
			«feature.dealThreadInFeature»; 
		«ENDFOR»
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
	 * 处理线程类型声明中的输入端口，生成线程内的局部变量
	 * @param features 线程类型声明中的features列表
	 */
	def static genThreadInPortVar(List<Feature> features)'''
		«FOR feature : features»
			«switch feature{
				DataPort:'''
					«IF feature.in == true && feature.out == false»
						«feature.name» : «feature.dealClassisfy.toString.clearspace»;
					«ENDIF»
				'''
				EventPort:'''
					«IF feature.in == true && feature.out == false»
						«feature.name» : «feature.dealClassisfy.toString.clearspace»;
					«ENDIF»
				'''
				EventDataPort:'''
					«IF feature.in == true && feature.out == false»
						«feature.name» : «feature.dealClassisfy.toString.clearspace»;
					«ENDIF»
				'''
				DataAccess:'''
					«feature.name» : access «feature.dealClassisfy.toString.clearspace»;
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

	def static dealThreadInFeature(Feature feature)'''
		«switch feature{
			DataPort:'''
				«IF feature.in == true && feature.out == false»
					«feature.name»_temp : in «feature.dealClassisfy»
				«ENDIF»
			'''
			EventPort:'''
				«IF feature.in == true && feature.out == false»
					«feature.name»_temp : in «feature.dealClassisfy»
				«ENDIF»
			'''
			EventDataPort:'''
				«IF feature.in == true && feature.out == false»
					«feature.name»_temp : in «feature.dealClassisfy»
				«ENDIF»
			'''
			DataAccess:'''
				«feature.name»_temp : access «feature.dealClassisfy»
			'''
		}»
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