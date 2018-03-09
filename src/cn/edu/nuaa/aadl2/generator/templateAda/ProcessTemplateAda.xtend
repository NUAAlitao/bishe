package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.ProcessSubcomponent
import cn.edu.nuaa.aadl2.generator.utils.Tools
import org.osate.aadl2.ProcessType
import org.osate.aadl2.ProcessImplementation
import org.osate.aadl2.Element
import org.osate.aadl2.Connection
import org.osate.aadl2.Subcomponent
import org.osate.aadl2.ThreadSubcomponent

import static extension cn.edu.nuaa.aadl2.generator.templateAda.ThreadTemplateAda.*
import static extension cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda.*
import org.osate.aadl2.DataSubcomponent

class ProcessTemplateAda {
	
		def static create(ProcessSubcomponent subcomponent)'''
			«Tools.folder(TemplateAda.systemfolder+"/"+subcomponent.name.toLowerCase.replace(".","_"))»
			«TemplateAda.processfolder = TemplateAda.systemfolder+"/"+subcomponent.name.toLowerCase.replace(".","_")»
			«Tools.createFile(subcomponent.name.toLowerCase.replace(".","_")+".adb",subcomponent.template.toString)»
		'''
		
		def static template(ProcessSubcomponent subcomponent){
			var process=subcomponent.classifier
			var children=process.children
			switch process{
				ProcessType : '''
				
				'''
				ProcessImplementation : '''
				with «TemplateAda.packageName»_Subprograms; use «TemplateAda.packageName»_Subprograms;
				with «TemplateAda.packageName»_types; use «TemplateAda.packageName»_types;
				
				procedure «subcomponent.name.replace('.','_')» is
					«IF process.allSubcomponents!==null»
						«FOR Subcomponent sub : process.allSubcomponents»
							«switch sub{
								DataSubcomponent:'''
									«sub.create»
									
								'''
								ThreadSubcomponent : '''
									«sub.create»
								'''
							}»
						«ENDFOR»
					«ENDIF»	
					
				begin
					null;
				end «subcomponent.name.replace('.','_')»;
				'''
				}
		}
}