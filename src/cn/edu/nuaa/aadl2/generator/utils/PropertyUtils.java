package cn.edu.nuaa.aadl2.generator.utils;

import java.util.ArrayList ;
import java.util.List ;

import org.eclipse.emf.common.util.BasicEList ;
import org.eclipse.emf.common.util.EList ;
import org.osate.aadl2.Aadl2Factory ;
import org.osate.aadl2.AadlInteger ;
import org.osate.aadl2.BooleanLiteral ;
import org.osate.aadl2.Classifier ;
import org.osate.aadl2.ClassifierValue ;
import org.osate.aadl2.ComponentImplementation ;
import org.osate.aadl2.ComponentType ;
import org.osate.aadl2.ContainedNamedElement ;
import org.osate.aadl2.ContainmentPathElement ;
import org.osate.aadl2.EnumerationLiteral ;
import org.osate.aadl2.IntegerLiteral ;
import org.osate.aadl2.ListValue ;
import org.osate.aadl2.ModalPropertyValue ;
import org.osate.aadl2.NamedElement ;
import org.osate.aadl2.NamedValue ;
import org.osate.aadl2.NumberValue ;
import org.osate.aadl2.ProcessorSubcomponent ;
import org.osate.aadl2.Property ;
import org.osate.aadl2.PropertyAssociation ;
import org.osate.aadl2.PropertyExpression ;
import org.osate.aadl2.RangeValue ;
import org.osate.aadl2.RealLiteral ;
import org.osate.aadl2.RecordValue ;
import org.osate.aadl2.ReferenceValue ;
import org.osate.aadl2.RefinableElement ;
import org.osate.aadl2.StringLiteral ;
import org.osate.aadl2.Subcomponent ;
import org.osate.aadl2.UnitLiteral ;
import org.osate.aadl2.instance.ComponentInstance ;
import org.osate.aadl2.instance.InstanceReferenceValue ;
import org.osate.utils.UnitConversion;
import org.osate.xtext.aadl2.properties.util.AadlProject;
import org.osate.xtext.aadl2.properties.util.GetProperties;

/**
 * Toolbox
 */
public class PropertyUtils {
  
  /**
   * @param propertyName
   * @param owner
   * @return
   */
  public static PropertyAssociation findProperty(String propertyName, NamedElement owner) {
		for (PropertyAssociation pa : owner.getOwnedPropertyAssociations()) {
			//System.out.println("____________PropertyUtils.java findProperty ______1____________");
			if (pa.getProperty().getName() == null) {
				
				continue;
				
			}
			System.out.println(pa.getProperty().getName());
			if (pa.getProperty().getName().equalsIgnoreCase(propertyName)) {
			//	System.out.println("____________PropertyUtils.java findProperty ______2____________");
				return pa;
			}
		}
		if(owner.eContainer()!=null && owner.eContainer() instanceof NamedElement)
		{
			//System.out.println("____________PropertyUtils.java findProperty _________3_________");
			for (PropertyAssociation pa : ((NamedElement)owner.eContainer()).getOwnedPropertyAssociations()) {

				if (pa.getProperty().getName() == null) {
					continue;	
				}
				if (pa.getProperty().getName().equalsIgnoreCase(propertyName) && 
						isInAppliesTo(pa, owner)) {
					return pa;
					///////////////////
				}
			}
		}
		if (owner instanceof ComponentInstance) {
			//System.out.println("____________PropertyUtils.java findProperty ________4__________");
			ComponentInstance inst = (ComponentInstance) owner;
			ComponentImplementation impl = inst.getContainingComponentImpl();
			if(impl != null)
			{
			  return findProperty(propertyName, impl);
			}
			else
			{
			  return null ;
			}
		} else if (owner instanceof ComponentImplementation) {
			//System.out.println("____________PropertyUtils.java findProperty ________5__________");
			ComponentImplementation impl = (ComponentImplementation) owner;
			if(impl.getOwnedExtension() != null)
			{
			  ComponentImplementation extendedImpl = impl.getOwnedExtension().getExtended();
			  PropertyAssociation extendedPA = findProperty(propertyName, extendedImpl);
			  if(extendedPA!=null)
			    return extendedPA;
			}
			ComponentType type = impl.getType();
			return findProperty(propertyName, type);
		}else if(owner instanceof ComponentType){
			//System.out.println("____________PropertyUtils.java findProperty _______6___________");
		  ComponentType type = (ComponentType) owner;
		  if(type.getOwnedExtension() != null)
		  {
		    ComponentType extendedType = type.getOwnedExtension().getExtended();
		    return findProperty(propertyName, extendedType);
		  }
		}
		//System.out.println("____________PropertyUtils.java findProperty _______null___________");
		return null;
	}
	
	private static boolean isInAppliesTo(PropertyAssociation pa, NamedElement owner)
	{
		for (ContainedNamedElement cne: pa.getAppliesTos())
		{
			for(ContainmentPathElement cpe: cne.getContainmentPathElements())
			{
				if(cpe.getNamedElement().equals(owner))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * @param i
	 * @param propertyName
	 *            property name.
	 * @return property value.
	 */
	public static Long getIntValue(NamedElement i, String propertyName) {
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof IntegerLiteral) {
						IntegerLiteral il = (IntegerLiteral) expr;
						final UnitLiteral unit = il.getUnit();
						if (unit != null) {
							if (pa.getProperty().getPropertyType() instanceof AadlInteger) {
								// XXX: Ms and Bytes are chosen for base units;
								// this is specific for POK
								AadlInteger ai = (AadlInteger) pa.getProperty().getPropertyType();
								if (ai.getUnitsType().getName().equalsIgnoreCase(AadlProject.SIZE_UNITS))
									unit.setBaseUnit(GetProperties.findUnitLiteral(p,
											                                  AadlProject.B_LITERAL));
								if (ai.getUnitsType().getName().equalsIgnoreCase(AadlProject.TIME_UNITS))
									unit.setBaseUnit(GetProperties.findUnitLiteral(p,
											AadlProject.MS_LITERAL));
							}
							// Warning: the cast from double to long is licit
							// only if the result of the conversion is an
							// integer
							return (long) il.getScaledValue(unit.getBaseUnit());
						}

						return ((IntegerLiteral) expr).getValue();
					}
				}
			}
		}

		return null ;
	}

	/**
	 * @param i
	 * @param propertyName
	 * @return
	 */
	public static RecordValue getRecordValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof RecordValue) {
						return ((RecordValue) expr);
					}
				}
			}
		}

		return null ;
	}

	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static Float getFloatValue(NamedElement i, String propertyName, String unit)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof NumberValue) {
						NumberValue val = (NumberValue) expr;
						float fVal = 0f;

						if (val instanceof IntegerLiteral) {
							fVal = ((IntegerLiteral) val).getValue();
						} else if (val instanceof RealLiteral) {
							fVal = (float) ((RealLiteral) val).getValue();
						}

						return UnitConversion.convertInMs(fVal, val.getUnit().getName());
					}
				}
			}
		}
		if(i instanceof RefinableElement)
		{
		  RefinableElement re = (RefinableElement) i;
		  if(re.getRefinedElement()!=null)
			return getFloatValue(re.getRefinedElement(), propertyName, unit);
		}
		
		return null ;
	}

	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static Float getFloatValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof IntegerLiteral) {
						return (float) ((IntegerLiteral) expr).getValue();
					} else if (expr instanceof RealLiteral) {
						return (float) ((RealLiteral) expr).getValue();
					}
				}
			}
		}
		// try on a refined NamedElement
		if(i instanceof RefinableElement)
		{
		  RefinableElement re = (RefinableElement) i;
		  if(re.getRefinedElement()!=null)
			return getFloatValue(re.getRefinedElement(), propertyName);
		}
		
		return null ;
	}

	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static Boolean getBooleanValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof BooleanLiteral) {
						return (boolean) ((BooleanLiteral) expr).getValue();
					}
				}
			}
		}

		return null ;
	}

	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static String getStringValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof StringLiteral) {
						return ((StringLiteral) expr).getValue();
					}
				}
			}
		}

		return null ;
	}

	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static List<String> getStringListValue(NamedElement i, String propertyName)
	{
		List<String> res = new ArrayList<String>();
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof ListValue) {
						ListValue lv = (ListValue) expr;

						for (PropertyExpression pe : lv.getOwnedListElements()) {
							if (pe instanceof StringLiteral) {
								StringLiteral sl = (StringLiteral) pe;
								res.add(sl.getValue());
							} else if (pe instanceof NamedValue) {
								NamedValue nv = (NamedValue) pe;

								if (nv.getNamedValue() instanceof EnumerationLiteral) {
									EnumerationLiteral el = (EnumerationLiteral) nv.getNamedValue();
									res.add(el.getName());
								}
							}
						}

						if (!res.isEmpty()) {
							return res;
						}
					}
				}
			}
		}

		return null ;
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static String getEnumValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);
		System.out.println("____________PropertyUtils.java getEnumValue______1127最后问题在这_123____");
	//	System.out.println("____________PropertyUtils.java getEnumValue______1___________");
		if (pa != null) {
	//		System.out.println("____________PropertyUtils.java getEnumValue____2_____________");
			Property p = pa.getProperty();
			
			if (p.getName().equalsIgnoreCase(propertyName)) {
				
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();
					if (expr instanceof EnumerationLiteral) {
						return ((EnumerationLiteral) expr).getName();
					} else if (expr instanceof NamedValue) {
						NamedValue nv = (NamedValue) expr;
						if (nv.getNamedValue() instanceof EnumerationLiteral) {
							EnumerationLiteral el = (EnumerationLiteral) nv.getNamedValue();
							return el.getName();
						}
					}
				}
			}
		}
		// try on a refined NamedElement
		if(i instanceof RefinableElement)
		{
			//System.out.println("____________PropertyUtils.java getEnumValue______3___________");
		  RefinableElement re = (RefinableElement) i;
		  if(re.getRefinedElement()!=null)
			return getEnumValue(re.getRefinedElement(), propertyName);
		}
		//System.out.println("____________PropertyUtils.java getEnumValue_________4________");
		return null ;
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static RangeValue getRangeValue(NamedElement i, String propertyName) {
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof RangeValue) {
						return (RangeValue) expr;
					}
				}
			}
		}

		return null;
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static NumberValue getMinRangeValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof RangeValue) {
						NumberValue n = ((RangeValue) expr).getMinimumValue();
						return n;
					} else if (expr instanceof NumberValue) {
						return (NumberValue) expr;
					}
				}
			}
		}

		return null ;
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static NumberValue getMaxRangeValue(NamedElement i, String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof RangeValue) {
						NumberValue n = ((RangeValue) expr).getMaximumValue();
						return n;
					} else if (expr instanceof NumberValue) {
						return (NumberValue) expr;
					}
				}
			}
		}

		return null ;
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static double getMaxRangeValue(NamedElement i, String propertyName,
	                                      float defaultValue)
  {
	  RealLiteral rl = (RealLiteral) getMaxRangeValue(i, propertyName) ;
    if(rl != null)
    {
      return rl.getValue() ;
    }
    else
    {
      return defaultValue ;
    }
  }
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static ComponentInstance getPropertyComponentRef(String propertyName,
	                                                        NamedElement owner)
	{
		PropertyExpression val = getPropertyValue(propertyName, owner);
		if(val != null)
		{
		  return (ComponentInstance) ((InstanceReferenceValue) val).
                                                  getReferencedInstanceObject();
		}
		else
		{
		  return null ;
		}
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static PropertyExpression getPropertyValue(String propertyName,
	                                                  NamedElement owner)
	{
		PropertyAssociation assign = findProperty(propertyName, owner);

		if (assign != null) {
			return assign.getOwnedValues().get(0).getOwnedValue();
		}

		return null ;
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static Classifier getClassifierValue(NamedElement owner,
	                                            String propertyName)
	{
		PropertyAssociation pa = findProperty(propertyName, owner);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof ClassifierValue) {
						return ((ClassifierValue) expr).getClassifier();
					}
				}
			}
		}

		return null ;
	}
	
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
	public static List<Classifier> getClassifierListValue(NamedElement i, String propertyName)
	{
		List<Classifier> res = new ArrayList<Classifier>();
		PropertyAssociation pa = findProperty(propertyName, i);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof ListValue) {
						ListValue lv = (ListValue) expr;

						for (PropertyExpression pe : lv.getOwnedListElements()) {
							if (pe instanceof ClassifierValue) {
								ClassifierValue sl = (ClassifierValue) pe;
								res.add(sl.getClassifier());
							}
						}

						if (!res.isEmpty()) {
							return res;
						}
					}
				}
			}
		}

		return null ;
	}

	public static PropertyAssociation createIntegerAssignment(String propertyName,
	                                                          long value)
	{
		Property property = Aadl2Factory.eINSTANCE.createProperty();
		PropertyAssociation assignment = Aadl2Factory.eINSTANCE.createPropertyAssociation();
		ModalPropertyValue modalPropertyValue = Aadl2Factory.eINSTANCE.createModalPropertyValue();
		IntegerLiteral propertyValue = Aadl2Factory.eINSTANCE.createIntegerLiteral();
		property.setName(propertyName);
		propertyValue.setValue(value);
		modalPropertyValue.setOwnedValue(propertyValue);
		assignment.setProperty(property);
		assignment.getOwnedValues().add(modalPropertyValue);
		return assignment;
	}

	public static boolean setFloatValue(NamedElement e, String propertyName,
	                                    float value)
	{
		PropertyAssociation pa = findProperty(propertyName, e);

		if (pa != null)
		{
			RealLiteral r = Aadl2Factory.eINSTANCE.createRealLiteral();
			r.setValue(value);
			r.setUnit(getUnit(pa));
			pa.getOwnedValues().get(0).setOwnedValue(r);
			return true;
		}

		return false;
	}
	
	// .
	private static UnitLiteral getUnit(PropertyAssociation pa)
	{
    PropertyExpression e = pa.getOwnedValues().get(0).getOwnedValue() ;
    if(e instanceof NumberValue)
    {
      return ((NumberValue) e).getUnit() ;
    }
    else if(e instanceof RangeValue)
    {
      return ((RangeValue) e).getMaximumValue().getUnit() ;
    }
    else
    {
      return null ;
    }
	}
	/**
	 * @param i
	 * @param propertyName
	 * @return property value.
	 */
  public static List<ComponentInstance> getComponentInstanceList(NamedElement object,
                                                            String propertyName)
  {
    List<ComponentInstance> res = null ;
    PropertyAssociation pa = findProperty(propertyName, object) ;

    if(pa != null)
    {
      res = new ArrayList<ComponentInstance>() ;
      Property p = pa.getProperty() ;

      if(p.getName().equals(propertyName))
      {
        List<ModalPropertyValue> values = pa.getOwnedValues() ;

        if(values.size() == 1)
        {
          ModalPropertyValue v = values.get(0) ;
          PropertyExpression expr = v.getOwnedValue() ;

          if(expr instanceof ListValue)
          {
            ListValue lv = (ListValue) expr ;

            for(PropertyExpression pe : lv.getOwnedListElements())
            {
              if(pe instanceof InstanceReferenceValue)
              {
                InstanceReferenceValue c = ((InstanceReferenceValue) pe) ;
                res.add((ComponentInstance)c.getReferencedInstanceObject()) ;
              }
            }
          }
        }
      }
    }

    return res ;
  }
  /**
	* @param i
	* @param propertyName
	* @return property value.
	*/
  public static List<Subcomponent> getSubcomponentList(NamedElement object,
                                                       String propertyName)
  {
    List<Subcomponent> res = new ArrayList<Subcomponent>() ;
    PropertyAssociation pa = findProperty(propertyName, object) ;
    if (pa == null)
      return null ;
    else {
      Property p = pa.getProperty();

      if (p.getName().equalsIgnoreCase(propertyName)) 
      {
        List<ModalPropertyValue> values = pa.getOwnedValues();

        if (values.size() == 1) {
          ModalPropertyValue v = values.get(0);
          PropertyExpression expr = v.getOwnedValue();

          if (expr instanceof ListValue) {
            ListValue lv = (ListValue) expr;
            
            for(PropertyExpression pe : lv.getOwnedListElements())
            {
              if(pe instanceof ReferenceValue)
              {
                ReferenceValue c = ((ReferenceValue) pe) ;
                for(ContainmentPathElement cpe :
                  c.getContainmentPathElements())
                {
                  res.add((Subcomponent) cpe.getNamedElement()) ;
                }
              }
            }
          }
        }
      }
      return res;
    }
  }  
  /**
   * @param object
   * @param propertyName
   * @return
   */
	public static List<Long> getIntListValue(NamedElement object,
	                                         String propertyName)
	{
		List<Long> res = new ArrayList<Long>();
		PropertyAssociation pa = findProperty(propertyName, object);

		if (pa == null)
			return null ;
		else {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof ListValue) {
						ListValue lv = (ListValue) expr;

						for (PropertyExpression pe : lv.getOwnedListElements()) {
							if (pe instanceof IntegerLiteral) {
								Long c = ((IntegerLiteral) pe).getValue();
								res.add(c);
							}
						}
					}
				}
			}
		}
		// try on a refined NamedElement
		if(object instanceof RefinableElement)
		{
		  RefinableElement re = (RefinableElement) object;
		  if(re.getRefinedElement()!=null)
		  {
		    List<Long> l = getIntListValue((ProcessorSubcomponent)re.getRefinedElement(),
		                               propertyName) ;
		    if(l != null)
		    {
		      res.addAll(l);
		    }
		  }
		}
		return res;
	}

	/**
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static List<Subcomponent> getSubcomponentList(ProcessorSubcomponent object,
	                                                     String propertyName)
	{
		List<Subcomponent> res = new ArrayList<Subcomponent>();
		PropertyAssociation pa = findProperty(propertyName, object);

		if (pa != null) {
			Property p = pa.getProperty();

			if (p.getName().equalsIgnoreCase(propertyName)) {
				List<ModalPropertyValue> values = pa.getOwnedValues();

				if (values.size() == 1) {
					ModalPropertyValue v = values.get(0);
					PropertyExpression expr = v.getOwnedValue();

					if (expr instanceof ListValue) {
						ListValue lv = (ListValue) expr;

						for (PropertyExpression pe : lv.getOwnedListElements()) {
							if (pe instanceof ReferenceValue) {
								ReferenceValue c = ((ReferenceValue) pe);
								ContainmentPathElement cpe = c.getContainmentPathElements().
								                   get(c.getContainmentPathElements().size()-1);
								res.add((Subcomponent) cpe.getNamedElement());
							}
						}
					}
				}
			}
		}
		// try on a refined NamedElement
		if(object instanceof RefinableElement)
		{
		  RefinableElement re = (RefinableElement) object;
		  if(re.getRefinedElement()!=null)
			{
		    List<Subcomponent> l = getSubcomponentList((ProcessorSubcomponent)re.getRefinedElement(),
		                                               propertyName) ;
		    if(! l.isEmpty())
		    {
		      res.addAll(l);
		    }
			}
		}
		return res;
	}
	
	 /**
   * @param cc the given component
   * @param propertyName the given property
   * @return the list of property expressions. The list may be empty.
   */
  public static EList<PropertyExpression> getPropertyExpression(Classifier cc,
                                                            String propertyName)
    {
      EList<PropertyExpression> result = new BasicEList<PropertyExpression>();
 
      EList<PropertyAssociation> lpa = getPropertyAssociations(cc,
                                                         propertyName) ;
    
      for (PropertyAssociation pa : lpa)
      {
        result.addAll(getPropertyExpression(pa)) ;
      }

      // If no property value is found, search within parent container if only
      // the given component is a component implementation. Recursive call.
      if(result.isEmpty() && cc instanceof ComponentImplementation )
      {
        cc = ((ComponentImplementation) cc).getType() ;
        result = getPropertyExpression(cc, propertyName) ;
      }

      return result ;
    }
  
  /**
   * @param cc a given component classifier
   * @param propertyName a given property name
   * @return the property association or null
   */
  public static PropertyAssociation getPropertyAssociation(Classifier cc,
                                                           String propertyName)
  {
    Property p = null ;

    // FIXME : TODO : improve function, get properties from component type
    // too
    if(!cc.isNoProperties())
    {
      for(PropertyAssociation pa : cc.getAllPropertyAssociations())
      {
        p = pa.getProperty() ;
        if(p.getName().equalsIgnoreCase(propertyName))
        {
          return pa ;
        }
      }
    }
    return null ;
  }
  
  /**
   * @param c
   * @param propertyName
   * @return
   */
  public static PropertyAssociation getPropertyAssociation(NamedElement c,
		  												                             String propertyName)
  {
	  Property p = null ;
	  
    for(PropertyAssociation pa : c.getOwnedPropertyAssociations())
    {
      p = pa.getProperty();
      if(p.getName().equalsIgnoreCase(propertyName))
      {
        return pa ;
      }
    }
    return null;
  }

  /**
   * @param cc a given component classifier
   * @param propertyName a given property name
   * @return the list of property association which match
   * the given propertyName. The list may be empty.
   */
  public static EList<PropertyAssociation> getPropertyAssociations(
                                                                  Classifier cc,
                                                            String propertyName)
  {
    Property p = null ;
    EList<PropertyAssociation> lpa = null ;

    EList<PropertyAssociation> lparesult = 
                                   new BasicEList<PropertyAssociation>() ;
    
    // FIXME : TODO : improve function, get properties from component type
    // too
    if(cc != null && !cc.isNoProperties())
    {
      lpa = cc.getAllPropertyAssociations() ;

      for(PropertyAssociation pa : lpa)
      {
        p = pa.getProperty() ;
        // Sometime, properties don't have name.
        if(p.getName() != null && p.getName().
                                     equalsIgnoreCase(propertyName))
        {
          lparesult.add(pa) ;
        }
      }
      if(lparesult.isEmpty() && cc.getExtended() != null)
      	lparesult.addAll(getPropertyAssociations(cc.getExtended(), propertyName));
    }
    return lparesult ;
  }

  /**
   * @param pa the given property association
   * @return the list of property expressions. The list may be empty
   */

  public static EList<PropertyExpression> getPropertyExpression(
                                                   PropertyAssociation pa)
  {
    EList<ModalPropertyValue> lmpv = null ;
    EList<PropertyExpression> result = new BasicEList<PropertyExpression>();

    lmpv = pa.getOwnedValues() ;

    for(ModalPropertyValue mpv : lmpv)  
    {
      result.add(mpv.getOwnedValue());
    }
    return result ;
  }
}