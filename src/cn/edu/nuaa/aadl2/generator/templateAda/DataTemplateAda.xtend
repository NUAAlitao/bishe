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
	
	/*
	 * 处理系统实现下的数据子组件
	 * @param folder 系统目录
	 * @param systemName 系统名称
	 * @param dataSubcomponents 数据子组件列表
	 */
	def static genSystemDataSubcomponent(String folder, String systemName, List<DataSubcomponent> dataSubcomponents){
		Tools.createFile(folder,systemName+"_data.ads",systemDataSubcomponent(systemName,dataSubcomponents).toString)
	}
	
	/*
	 * 处理进程实现下的数据子组件
	 * @param dataSubcomponents 数据子组件列表
	 */
	def static genProcessDataSubcomponent(List<DataSubcomponent> dataSubcomponents)'''
		«FOR dataSubcomponent : dataSubcomponents»
			«dataSubcomponent.template»
		«ENDFOR»
	'''
	
	def static template(DataSubcomponent subcomponent){
		var data=subcomponent.classifier
		switch data {
			DataType :'''
			
			'''
			DataImplementation :'''
			«subcomponent.name» : «data.name.replace(".","_")»;
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