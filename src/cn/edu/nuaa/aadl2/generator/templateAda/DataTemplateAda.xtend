package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.DataType
import org.osate.aadl2.DataImplementation
import java.util.List
import cn.edu.nuaa.aadl2.generator.utils.Tools

class DataTemplateAda {
	def static create(DataSubcomponent subcomponent)'''
		«subcomponent.template»
	'''
	
	def static genSystemDataSubcomponent(String folder, String systemName, List<DataSubcomponent> dataSubcomponents){
		Tools.createFile(folder,systemName+"_data.ads",systemDataSubcomponent(systemName,dataSubcomponents).toString)
	}
	
	def static template(DataSubcomponent subcomponent){
		var data=subcomponent.classifier
		switch data {
			DataType :'''
			
			'''
			DataImplementation :'''
			«subcomponent.name» : «data.name.replace(".","_")» ;
			'''
		}
	}
	
	def static systemDataSubcomponent(String systemName, List<DataSubcomponent> dataSubcomponents)'''
		packege «systemName»_data is
			«FOR DataSubcomponent dataSubcomponent : dataSubcomponents»
				«dataSubcomponent.template»
			«ENDFOR»
		end «systemName»_data;
	'''
}