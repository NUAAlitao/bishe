package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.DataClassifier
import org.osate.aadl2.DataType
import org.osate.aadl2.DataImplementation
import java.util.HashSet
import java.util.Arrays
import cn.edu.nuaa.aadl2.generator.utils.Tools
import java.util.List

import static extension cn.edu.nuaa.aadl2.generator.utils.PropertyParser.*
import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*

class DataTemplate {
	
	/*
	 * base types in both c and AADL ,stored by hash set
	 * */
	public static List<String> basetype=
		Arrays.asList("string", "Integer" , "float" ,"double" , "character")
	
	public static HashSet<String> baseTypes = new HashSet<String>(basetype);
	
	def static create(DataSubcomponent subcomponent)'''
		«Tools.addContent(Template.systemheadfile,subcomponent.template.toString)»
	'''
		
	def static template(DataSubcomponent subcomponent){
		var data=subcomponent.classifier
		switch data{
			DataClassifier : '''
				«data.template»
			'''
		}
	}
	
	def static template(DataClassifier dc){
			System.out.println(dc.name);
		switch dc{
			DataType : '''
				«IF dc.name.equalsIgnoreCase("Integer")»
				«System.out.println("int")»
					int
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("float")»
					float
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("long")»
					long
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("double")»
					double
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("character")»
					char
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("string")»
					string
				«ENDIF»
				«IF baseTypes.add(dc.name.toLowerCase)»
					«IF dc.dataRepresentation!==null»
						«IF dc.dataRepresentation.equalsIgnoreCase("struct")»
							«System.out.println("addContent")»
							«Tools.addContent(Template.systemheadfile,dc.struct.toString)»
						«ENDIF»	
						«IF dc.dataRepresentation.equalsIgnoreCase("union")»
							«System.out.println("addContent")»
							«Tools.addContent(Template.systemheadfile,dc.union.toString)»
						«ENDIF»
					«ENDIF»
				«ENDIF»
			'''
			DataImplementation : '''
				«IF dc.name.equalsIgnoreCase("integer")»
					«System.out.println("int")»
					int
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("float")»
					float
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("double")»
					double
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("character")»
					char
				«ENDIF»
				«IF dc.name.equalsIgnoreCase("string")»
					string
				«ENDIF»
				«IF baseTypes.add(dc.name.toLowerCase)»
					
					«IF dc.dataRepresentation.equalsIgnoreCase("struct")»
					«System.out.println("addContent")»
						«Tools.addContent(Template.systemheadfile,dc.struct.toString)»
						«System.out.println(dc.struct.toString+"s")»
					«ENDIF»	
					«IF dc.dataRepresentation.equalsIgnoreCase("union")»
					«System.out.println("addContent")»
						«Tools.addContent(Template.systemheadfile,dc.union.toString)»
						«System.out.println(dc.union.toString+"s")»
					«ENDIF»	
				«ENDIF»
			'''
		}
	}
	
	def static struct(DataClassifier dc){
		switch dc{
			DataType : '''				
				typedef struct
				{	
					«data(dc.baseTypes,dc.elementNames)»		
				}«dc.name.convert»;
			'''
			DataImplementation : '''
				typedef struct
				{
					«data(dc.baseTypes,dc.elementNames)»		
				}«dc.name.convert»;
			'''
		}
	}
	
	def static union(DataClassifier dc){
		switch dc{
			DataType: '''
				typedef union
				{
					«data(dc.baseTypes,dc.elementNames)»		
				}«dc.name.convert»;
			'''
			DataImplementation : '''
				typedef union
				{
					«data(dc.baseTypes,dc.elementNames)»		
				}«dc.name.convert»;
			'''
		}
	}
	
	def static data(List<DataClassifier> datas,List<String> vars){
		var result=""
		var i=0
		var size=datas.size
		for(i=0;i<size;i++){
			result=result+datas.get(i).template.toString.replaceAll("\r|\n", "")+ " "+vars.get(i)+";\n"
		}
		return result
	}
}