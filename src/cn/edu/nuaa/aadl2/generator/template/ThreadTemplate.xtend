package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.ThreadSubcomponent
import cn.edu.nuaa.aadl2.generator.utils.Tools

import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import static extension cn.edu.nuaa.aadl2.generator.utils.PropertyParser.*
import static extension cn.edu.nuaa.aadl2.generator.template.SubprogramTemplate.*
import static extension cn.edu.nuaa.aadl2.generator.template.FeatureTemplate.*
import org.osate.aadl2.ThreadType
import org.osate.aadl2.ThreadImplementation
import org.osate.aadl2.SubprogramCall
import org.osate.aadl2.SubprogramClassifier
import org.osate.aadl2.ThreadClassifier
import org.osate.aadl2.Subcomponent
import org.osate.aadl2.Feature

class ThreadTemplate {
	def static create(ThreadSubcomponent subcomponent)'''
		«Tools.createFile(subcomponent.classifier.name.convert+".h",subcomponent.head.toString)»
		«Tools.createFile(subcomponent.classifier.name.convert+".c",subcomponent.template.toString)»
	'''
	
	def static head(ThreadSubcomponent subcomponent){
		var thread=subcomponent.classifier
		switch thread{
			ThreadType : '''
				/*Thread Type head file*/
				«Template.head»
			'''
			ThreadImplementation : '''
				/*Thread head c file*/
				«Template.head»
				
			'''
		}
	}
	
	def static template(ThreadSubcomponent subcomponent){
		var thread=subcomponent.classifier
		switch thread{ThreadType : '''
				/*Thread Type c file*/
				«Template.head»
				#include "«Template.systemheadfile»"	
				
				void «thread.name.convert»()
				{
					/*features in thread*/
					«IF thread.getAllFeatures!=null»
						«thread.getAllFeatures.asVariables»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Periodic")»
						while(1)
						{
							
						}
					«ENDIF»
				}
			'''
			ThreadImplementation : '''
				/*Thread Implementation c file*/
				«Template.head»
				#include "«Template.systemheadfile»"	
							
				«IF thread.getSubprogramCalls!=null»
					«FOR SubprogramCall sc : thread.getSubprogramCalls»
						«IF sc.calledSubprogram!=null»
							«sc.calledSubprogram.template»
							«IF sc.calledSubprogram instanceof SubprogramClassifier»
								«sc.calledSubprogram.include»
							«ENDIF»
						«ENDIF»
					«ENDFOR»
				«ENDIF»				
				void «thread.name.convert»()
				{
					/*features in thread*/
					«IF thread.type.getAllFeatures!=null»
						«thread.type.getAllFeatures.asVariables»
					«ENDIF»
					/*subprogram in thread with different type*/
					«IF thread.dispatchProtocol.equalsIgnoreCase("Periodic")»
						while(1)
						{
							
							«IF thread.getSubprogramCalls!=null»
								«FOR SubprogramCall sc : thread.getSubprogramCalls»
									«IF sc.calledSubprogram!=null»
										«sc.calledSubprogram.inThread»
									«ENDIF»
								«ENDFOR»
							«ENDIF»	
							
						}
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Sporadic")»						
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»	
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Aperiodic")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Timed")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Hybrid")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Background")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
				}
			'''
		}
	}
	
		
	def static template(ThreadClassifier thread){
		switch thread{
			ThreadType : '''
				/*Thread Type c file*/
				«Template.head»
				#include "«Template.systemheadfile»"	
				void «thread.name.convert»()
				{
					/*features in thread*/
					«IF thread.getAllFeatures!=null»
						«thread.getAllFeatures.asVariables»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Periodic")»
						while(1)
						{
							
						}
					«ENDIF»
				}
			'''
			ThreadImplementation : '''
				/*Thread Implementation c file*/
				«Template.head»
				#include "«Template.systemheadfile»"				
				«IF thread.getSubprogramCalls!=null»
					«FOR SubprogramCall sc : thread.getSubprogramCalls»
						«IF sc.calledSubprogram!=null»
							«sc.calledSubprogram.template»
							«IF sc.calledSubprogram instanceof SubprogramClassifier»
								«sc.calledSubprogram.include»
							«ENDIF»
						«ENDIF»
					«ENDFOR»
				«ENDIF»				
				void «thread.name.convert»()
				{
					/*features in thread*/
					«IF thread.type.getAllFeatures!=null»
						«thread.type.getAllFeatures.asVariables»
					«ENDIF»
					/*subprogram in thread with different type*/
					«IF thread.dispatchProtocol.equalsIgnoreCase("Periodic")»
						while(1)
						{
							
							«IF thread.getSubprogramCalls!=null»
								«FOR SubprogramCall sc : thread.getSubprogramCalls»
									«IF sc.calledSubprogram!=null»
										«sc.calledSubprogram.inThread»
									«ENDIF»
								«ENDFOR»
							«ENDIF»	
							
						}
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Sporadic")»						
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»	
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Aperiodic")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Timed")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Hybrid")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
					«IF thread.dispatchProtocol.equalsIgnoreCase("Background")»
						«IF thread.getSubprogramCalls!=null»
							«FOR SubprogramCall sc : thread.getSubprogramCalls»
								«IF sc.calledSubprogram!=null»
									«sc.calledSubprogram.inThread»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDIF»
				}
			'''
		}
	}
}