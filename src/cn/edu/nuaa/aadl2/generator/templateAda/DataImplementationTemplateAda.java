package cn.edu.nuaa.aadl2.generator.templateAda;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.osate.aadl2.AbstractNamedValue;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.IntegerLiteral;
import org.osate.aadl2.NamedValue;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.PropertyExpression;

@SuppressWarnings("all")
public class DataImplementationTemplateAda {
  public static CharSequence create(final DataImplementation dataImplementation) {
    StringConcatenation _builder = new StringConcatenation();
    DataImplementation _println = InputOutput.<DataImplementation>println(dataImplementation);
    _builder.append(_println);
    _builder.newLineIfNotEmpty();
    {
      EList<PropertyAssociation> _ownedPropertyAssociations = dataImplementation.getOwnedPropertyAssociations();
      for(final PropertyAssociation propertyAssociation : _ownedPropertyAssociations) {
        PropertyAssociation _println_1 = InputOutput.<PropertyAssociation>println(propertyAssociation);
        _builder.append(_println_1);
        _builder.newLineIfNotEmpty();
        String _println_2 = InputOutput.<String>println(propertyAssociation.getProperty().getName());
        _builder.append(_println_2);
        _builder.newLineIfNotEmpty();
        {
          PropertyExpression _ownedValue = propertyAssociation.getOwnedValues().get(0).getOwnedValue();
          if ((_ownedValue instanceof IntegerLiteral)) {
            PropertyExpression _ownedValue_1 = propertyAssociation.getOwnedValues().get(0).getOwnedValue();
            Long _println_3 = InputOutput.<Long>println(Long.valueOf(((IntegerLiteral) _ownedValue_1).getValue()));
            _builder.append(_println_3);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          PropertyExpression _ownedValue_2 = propertyAssociation.getOwnedValues().get(0).getOwnedValue();
          if ((_ownedValue_2 instanceof NamedValue)) {
            PropertyExpression _ownedValue_3 = propertyAssociation.getOwnedValues().get(0).getOwnedValue();
            AbstractNamedValue _println_4 = InputOutput.<AbstractNamedValue>println(((NamedValue) _ownedValue_3).getNamedValue());
            _builder.append(_println_4);
            _builder.newLineIfNotEmpty();
            PropertyExpression _ownedValue_4 = propertyAssociation.getOwnedValues().get(0).getOwnedValue();
            EClass _println_5 = InputOutput.<EClass>println(((NamedValue) _ownedValue_4).getNamedValue().eClass());
            _builder.append(_println_5);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
}
