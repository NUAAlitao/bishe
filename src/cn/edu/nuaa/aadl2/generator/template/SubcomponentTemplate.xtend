package cn.edu.nuaa.aadl2.generator.template

import org.osate.aadl2.Subcomponent
import org.osate.aadl2.ProcessSubcomponent
import org.osate.aadl2.ThreadSubcomponent
import org.osate.aadl2.SubprogramSubcomponent
import org.osate.aadl2.DataSubcomponent
import org.osate.aadl2.DeviceSubcomponent



import static extension cn.edu.nuaa.aadl2.generator.template.ProcessTemplate.*
import static extension cn.edu.nuaa.aadl2.generator.template.ThreadTemplate.*
import static extension cn.edu.nuaa.aadl2.generator.template.SubprogramTemplate.*
import static extension cn.edu.nuaa.aadl2.generator.template.DataTemplate.*
import static extension cn.edu.nuaa.aadl2.generator.template.SystemTemplate.*
import static extension cn.edu.nuaa.aadl2.generator.utils.StringUtils.*
import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.SystemSubcomponent

class SubcomponentTemplate {
	
	def static template(Subcomponent subcomponent){
		switch subcomponent{
			ProcessSubcomponent : '''
				«subcomponent.create»
			'''
			ThreadSubcomponent : '''
				«subcomponent.create»
			'''
			SubprogramSubcomponent : '''
				«subcomponent.create»
			'''
			DataSubcomponent : '''
				«subcomponent.create»
			'''
			DeviceSubcomponent : '''«System.out.println(subcomponent.classifier.name+"没有")»'''
			
			SystemSubcomponent:''' 
				«subcomponent.create»
			'''
			
			default:'''«System.out.println(subcomponent.classifier.name+"没考虑")»'''
		}
	}
	
}
