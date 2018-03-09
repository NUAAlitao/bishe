package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.DeviceSubcomponent
import org.osate.aadl2.ProcessSubcomponent
import org.osate.aadl2.Subcomponent
import org.osate.aadl2.SubprogramSubcomponent
import org.osate.aadl2.SystemSubcomponent
import org.osate.aadl2.ThreadSubcomponent

import static extension cn.edu.nuaa.aadl2.generator.templateAda.ProcessTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.ThreadTemplateAda.*

class SubcomponentTemplateAda {
	
	def static template(Subcomponent subcomponent){
		switch subcomponent{
			ProcessSubcomponent : '''
				«subcomponent.create»
			'''
			ThreadSubcomponent : '''
				«subcomponent.create»
			'''
//			SubprogramSubcomponent : '''
//				«subcomponent.create»
//			'''
//			DataSubcomponent : '''
//				«subcomponent.create»
//			'''
 			DeviceSubcomponent : '''«System.out.println(subcomponent.classifier.name+"没有")»'''
//			
//			SystemSubcomponent:''' 
//				«subcomponent.create»
//			'''
			
			default:'''«System.out.println(subcomponent.classifier.name+"没考虑")»'''
		}
	}
	
}
