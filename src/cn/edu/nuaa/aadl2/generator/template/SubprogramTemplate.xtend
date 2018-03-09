package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.SubprogramType
import org.osate.aadl2.CalledSubprogram
import org.osate.aadl2.SubprogramImplementation
import org.osate.aadl2.SubprogramClassifier
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.utils.PropertyParser.*
import static extension cn.edu.nuaa.aadl2.generator.template.FeatureTemplate.*
import org.osate.aadl2.PropertyAssociation
import org.osate.aadl2.SubprogramCall
import org.osate.aadl2.SubprogramSubcomponent

/*
 * Subprogram template
 * subprogram as subcomponent applies to(process implementation ,thread implementation subprogram implementation)
 * subprogram as subprogram calls
 * */
class SubprogramTemplate {
	def static include(CalledSubprogram subprogram){
		switch subprogram{
			SubprogramType : '''
				#include "«subprogram.name.convert».h"
			'''
			SubprogramImplementation : '''
				#include "«subprogram.name.convert».h"
			'''
		}
	}
	
	def static init(CalledSubprogram subprogram){
		switch subprogram{
			SubprogramType : '''
				
			'''
			SubprogramImplementation : '''
				
			'''
		}
	}
	
	
	def static create(SubprogramSubcomponent subcomponent)'''
		«subcomponent.template»
	'''
	
	def static template(SubprogramSubcomponent subcomponent){
		var subprogram=subcomponent.classifier
		switch subprogram{
			SubprogramClassifier : '''«subprogram.create»'''
		}
	}
	
	def static template(CalledSubprogram cs){
		switch cs{
			SubprogramClassifier : '''«cs.create»'''
		}
	}
	
	def static create(SubprogramClassifier subprogram){
		switch subprogram{
			SubprogramType : '''
				«Tools.createFile(subprogram.name.convert+".h",subprogram.head.toString)»
				«Tools.createFile(subprogram.name.convert+".c",subprogram.template.toString)»
			'''
			SubprogramImplementation : '''
				«Tools.createFile(subprogram.name.convert+".h",subprogram.head.toString)»
				«Tools.createFile(subprogram.name.convert+".c",subprogram.template.toString)»
			'''
		}
	}
			
	def static head(SubprogramClassifier subprogram){
		switch subprogram{
			SubprogramType : '''
				/*Subprogram Type head file*/
				int «subprogram.name.convert»(«IF subprogram.getAllFeatures!=null»«subprogram.getAllFeatures.asDefinations»«ENDIF»);
			'''
			SubprogramImplementation : '''
				/*Subprogram Implementation head file*/
				int «subprogram.name.convert»(«IF subprogram.type.getAllFeatures!=null»«subprogram.type.getAllFeatures.asDefinations»«ENDIF»);
			'''
		}
	}
	
	def static template(SubprogramClassifier subprogram){
		switch subprogram{
			SubprogramType : '''
				/*Subprogram Type c file*/
				«Template.head»
				#include "«Template.systemheadfile»"	
						
				int «subprogram.name.convert»(«IF subprogram.getAllFeatures!=null»«subprogram.getAllFeatures.asDefinations»«ENDIF»)
				{
					«IF subprogram.allPropertyAssociations!=null»
						«FOR PropertyAssociation pa : subprogram.allPropertyAssociations»
							printf("«pa»");
						«ENDFOR»	
					«ENDIF»
					return 1;
				}
			'''
			SubprogramImplementation : '''
				/*Subprogram Implementation c file*/
				«Template.head»
				#include "«Template.systemheadfile»"
								
				«IF subprogram.getSubprogramCalls!=null»
					«FOR SubprogramCall sc : subprogram.getSubprogramCalls»
						«IF sc.calledSubprogram!=null»
							«sc.calledSubprogram.template»
							«IF sc.calledSubprogram instanceof SubprogramClassifier»
								«sc.calledSubprogram.include»
							«ENDIF»
						«ENDIF»
					«ENDFOR»
				«ENDIF»	
				int «subprogram.name.convert»(«IF subprogram.type.getAllFeatures!=null»«subprogram.type.getAllFeatures.asDefinations»«ENDIF»)
				{
					«IF subprogram.getSubprogramCalls!=null»
						«FOR SubprogramCall sc : subprogram.getSubprogramCalls»
							«IF sc.calledSubprogram!=null»
								«»
							«ENDIF»
						«ENDFOR»
					«ENDIF»	
					«IF subprogram.allPropertyAssociations!=null»
						«FOR PropertyAssociation pa : subprogram.allPropertyAssociations»
							printf("«pa»");
						«ENDFOR»	
					«ENDIF»
					«IF subprogram.getSubprogramCalls!=null»
						«FOR SubprogramCall sc : subprogram.getSubprogramCalls»
							«IF sc.calledSubprogram!=null»
								«sc.calledSubprogram.call»
							«ENDIF»
						«ENDFOR»
					«ENDIF»	
					return 1;
				}
			'''
		}
	}
	
	def static callFeature(CalledSubprogram subprogram){
		switch subprogram{
			SubprogramType : '''«subprogram.getAllFeatures.asVariables»'''
			SubprogramImplementation : '''«subprogram.type.getAllFeatures.asVariables»'''
		}
	}	
	def static call(CalledSubprogram subprogram){
		switch subprogram{
			SubprogramType : '''«subprogram.name.convert»(«IF subprogram.getAllFeatures!=null»«subprogram.getAllFeatures.asParameters»«ENDIF»);'''
			SubprogramImplementation : '''«subprogram.name.convert»(«IF subprogram.type.getAllFeatures!=null»«subprogram.type.getAllFeatures.asParameters»«ENDIF»);'''
		}
	}
	
	def static inThread(CalledSubprogram subprogram){
		switch subprogram{
			SubprogramType : '''«subprogram.name.convert»(«IF subprogram.getAllFeatures!=null»«subprogram.getAllFeatures.asParameters»«ENDIF»);'''
			SubprogramImplementation : '''«subprogram.name.convert»(«IF subprogram.type.getAllFeatures!=null»«subprogram.type.getAllFeatures.asParameters»«ENDIF»);'''
		}
	}
}