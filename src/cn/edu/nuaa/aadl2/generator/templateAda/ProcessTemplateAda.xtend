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
import static extension cn.edu.nuaa.aadl2.generator.templateAda.AnnexSubclauseTemplateAda.*

import org.osate.aadl2.DataSubcomponent
import java.util.List
import org.osate.aadl2.ProcessorSubcomponent
import java.util.Iterator
import org.osate.aadl2.AnnexSubclause
import org.osate.ba.aadlba.BehaviorAnnex
import org.osate.aadl2.DefaultAnnexSubclause

class ProcessTemplateAda {
	
		def static genSystemProcessSubcomponent(String parentFolder, ProcessSubcomponent processSubcomponent){
				Tools.folder(parentFolder+"/"+processSubcomponent.name.toLowerCase.replace(".","_"))
//				TemplateAda.processfolder = TemplateAda.systemfolder+"/"+subcomponent.name.toLowerCase.replace(".","_")
				Tools.createFile(parentFolder+"/"+processSubcomponent.name.toLowerCase.replace(".","_"),processSubcomponent.name.replace(".","_")+".adb",processSubcomponent.template.toString)
		}
		
		def static template(ProcessSubcomponent processSubcomponent){
			var process=processSubcomponent.classifier
			switch process{
				ProcessType : '''
				
				'''
				ProcessImplementation : '''
				with «TemplateAda.packageName»_Subprograms; use «TemplateAda.packageName»_Subprograms;
				with «TemplateAda.packageName»_types; use «TemplateAda.packageName»_types;
				
				procedure «processSubcomponent.name.replace('.','_')» is
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
					«IF process.ownedAnnexSubclauses.size > 0»
						«FOR AnnexSubclause annexSubclause : process.ownedAnnexSubclauses»
							«genBehaviorAnnexVarible(annexSubclause as DefaultAnnexSubclause)»
							«genBehaviorAnnexState(annexSubclause as DefaultAnnexSubclause)»
						«ENDFOR»
					«ENDIF»
				begin
					«IF process.ownedAnnexSubclauses.size > 0»
						«FOR AnnexSubclause annexSubclause : process.ownedAnnexSubclauses»
							«genBehaviorAnnexTransition(annexSubclause as DefaultAnnexSubclause)»
						«ENDFOR»
					«ENDIF»
				end «processSubcomponent.name.replace('.','_')»;
				'''
				}
		}
		
		
}