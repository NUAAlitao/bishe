package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.Classifier
import org.osate.aadl2.SubprogramType
import org.osate.aadl2.SubprogramImplementation
import org.osate.aadl2.ThreadType
import org.osate.aadl2.ThreadImplementation
import org.osate.aadl2.DataType
import org.osate.aadl2.DataImplementation

import static  cn.edu.nuaa.aadl2.generator.templateAda.SubprogramImplementationTemplateAda.*
import static  cn.edu.nuaa.aadl2.generator.templateAda.SubprogramTypeTemplateAda.*


class ClassifierTemplateAda {
	def static template(Classifier classifier){
		switch classifier{
			SubprogramType :
				create(classifier as SubprogramType)
			SubprogramImplementation:
				create(classifier as SubprogramImplementation)
			ThreadType:
				println("threadtype")
			ThreadImplementation:
				println("threadImplementation")
			
			default:
				null
		}
	}
}