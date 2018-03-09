package cn.edu.nuaa.aadl2.generator.templateAda

import org.osate.aadl2.DataType
import org.osate.aadl2.PropertyAssociation
import org.osate.aadl2.impl.PropertyAssociationImpl
import org.osate.aadl2.NamedValue
import org.osate.aadl2.IntegerLiteral

class DataTypeTemplateAda {
	
	def static create(DataType dataType)'''
		«println(dataType.name)»
		«FOR PropertyAssociation propertyAssociation : dataType.ownedPropertyAssociations»
			«println(propertyAssociation)»
			«println(propertyAssociation.property.name)»
			«IF propertyAssociation.ownedValues.get(0).ownedValue instanceof IntegerLiteral»
				«println((propertyAssociation.ownedValues.get(0).ownedValue as IntegerLiteral).value)»
			«ENDIF»
			«IF propertyAssociation.ownedValues.get(0).ownedValue instanceof NamedValue»
				«println((propertyAssociation.ownedValues.get(0).ownedValue as NamedValue).namedValue)»
				«println((propertyAssociation.ownedValues.get(0).ownedValue as NamedValue).namedValue.eClass)»
			«ENDIF»
		«ENDFOR»
	'''
	
	
}