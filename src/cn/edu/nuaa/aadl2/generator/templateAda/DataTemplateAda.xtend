package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.DataType
import org.osate.aadl2.DataImplementation

class DataTemplateAda {
	def static create(DataSubcomponent subcomponent)'''
		«subcomponent.template»
	'''
	
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
}