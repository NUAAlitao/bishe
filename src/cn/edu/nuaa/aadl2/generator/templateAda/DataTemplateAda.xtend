package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.DataType
import org.osate.aadl2.DataImplementation
import java.util.List
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import org.osate.aadl2.AadlPackage
import org.osate.aadl2.Classifier
import org.osate.aadl2.PublicPackageSection
import java.util.ArrayList
import java.util.LinkedList
import org.osate.aadl2.Subcomponent

class DataTemplateAda {
	/*
	 * 处理系统实现下的数据子组件
	 * @param folder 系统目录
	 * @param systemName 系统名称
	 * @param dataSubcomponents 数据子组件列表
	 */
	def static genSystemDataSubcomponent(String folder, String systemName, List<DataSubcomponent> dataSubcomponents){
		Tools.createFile(folder,systemName.convert+"_data.ads",systemDataSubcomponent(systemName,dataSubcomponents).toString)
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
	/*
	 * 处理publicSection中的所有数据实现类型
	 * @param folder 根目录路径
	 * @param aadlPackage aadl包
	 */
	def static genDataType(String folder, AadlPackage aadlPackage){
		var dataImpls = aadlPackage.publicSection.allDataImplementation
		if(dataImpls.size > 0){
			Tools.createFile(folder,"data_type.ads",dataImpls.dealDataComponent.toString())
		}
	}
	/*
	 * 处理data implementation对象
	 * @param datas publicSection下的所有数据实现组件
	 */
	def static dealDataComponent(List<DataImplementation> datas)'''
		package data_type is
		«FOR data : datas»
			type «data.name.convertPoint» is 
			record
			«FOR subData : data.ownedDataSubcomponents»
				«subData.name» : «subData.dealDataType.toString.clearspace»;
			«ENDFOR»
			end record;
			
		«ENDFOR»
		end data_type;
	'''
	
	def static dealDataType(DataSubcomponent dataSubcomponent){
		var data = dataSubcomponent.classifier
		switch data.name{
			case "Float":'''Float'''
			case "Integer":'''Integer'''
		}
	}
	/*
	 * 获得publicSection下的所有Data Implementation对象
	 * @param publicSection 
	 * @return List<Classifier>
	 */
	def static getAllDataImplementation(PublicPackageSection publicSection){
		var result = new ArrayList<DataImplementation>();
		for(classifier : publicSection.ownedClassifiers){
			if(classifier instanceof DataImplementation){
				result.add(classifier)
			}
		}
		return result
	}
	
	def static template(DataSubcomponent subcomponent){
		var data=subcomponent.classifier
		switch data {
			DataType :'''
			«subcomponent.name» : «data.name.replace(".","_")»;
			'''
			DataImplementation :'''
			«subcomponent.name» : «data.name.replace(".","_")»;
			'''
		}
	}
	
	def static systemDataSubcomponent(String systemName, List<DataSubcomponent> dataSubcomponents)'''
		packege «systemName.convert»_data is
			«FOR DataSubcomponent dataSubcomponent : dataSubcomponents»
				«dataSubcomponent.template»
			«ENDFOR»
		end «systemName.convert»_data;
	'''
}