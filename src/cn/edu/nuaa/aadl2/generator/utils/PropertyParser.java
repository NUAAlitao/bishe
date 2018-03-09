package cn.edu.nuaa.aadl2.generator.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osate.aadl2.Classifier;
import org.osate.aadl2.ContainedNamedElement;
import org.osate.aadl2.ContainmentPathElement;
import org.osate.aadl2.DataClassifier;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataType;
import org.osate.aadl2.ListValue;
import org.osate.aadl2.ModalPropertyValue;
import org.osate.aadl2.ProcessClassifier;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessorClassifier;
import org.osate.aadl2.ProcessorSubcomponent;
import org.osate.aadl2.Property;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.PropertyExpression;
import org.osate.aadl2.ReferenceValue;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.ThreadClassifier;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.ThreadType;

import cn.edu.nuaa.aadl2.generator.utils.PropertyUtils;


public class PropertyParser {
	
	public static Map<ProcessorClassifier,ProcessClassifier> getActualProcessorBinding(SystemImplementation si){
		Map<ProcessorClassifier,ProcessClassifier> maps=new HashMap<ProcessorClassifier,ProcessClassifier>();
		ProcessClassifier process = null;
		ProcessorClassifier processor = null;
		ProcessSubcomponent processSubcomponent=null;
		ProcessorSubcomponent processorSubcomponent=null;
		for(PropertyAssociation pa : si.getAllPropertyAssociations()){
			Property p = pa.getProperty();
		      if (p.getName().equalsIgnoreCase("Actual_Processor_Binding")) 
		      {
		        for (ContainedNamedElement cne: pa.getAppliesTos())
				{
		        	
					for(ContainmentPathElement cpe: cne.getContainmentPathElements())
					{
						processSubcomponent=(ProcessSubcomponent) cpe.getNamedElement();
					}
				}
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
		                	processorSubcomponent=(ProcessorSubcomponent) cpe.getNamedElement() ;
		                }
		                
		              }
		            }
		          }
		        }
		      }
		      
		}
		if(processSubcomponent!=null&&processorSubcomponent!=null){
			process=(ProcessClassifier) processSubcomponent.getClassifier();
			processor=(ProcessorClassifier) processorSubcomponent.getClassifier();
			maps.put(processor,process);
		}
		return maps;
		
	}
	
	public static ProcessorSubcomponent getProcessor(ProcessSubcomponent pc){
		
		SystemImplementation si=(SystemImplementation) pc.getContainingClassifier();
		ProcessorSubcomponent processorSubcomponent=null;
		for(PropertyAssociation pa : si.getAllPropertyAssociations()){
			Property p = pa.getProperty();
		      if (p.getName().equalsIgnoreCase("Actual_Processor_Binding")) 
		      {
//		        for (ContainedNamedElement cne: pa.getAppliesTos())
//				{
//		        	
//					for(ContainmentPathElement cpe: cne.getContainmentPathElements())
//					{
//						processSubcomponent=(ProcessSubcomponent) cpe.getNamedElement();
//					}
//				}
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
		                	processorSubcomponent=(ProcessorSubcomponent) cpe.getNamedElement() ;
		                }
		                
		              }
		            }
		          }
		        }
		      }
		      
		}
		if(processorSubcomponent!=null)
			return processorSubcomponent;
		return null;
		
	}
	
	public static String getSchedule(ProcessorSubcomponent processor){
		List<String> values=PropertyUtils.getStringListValue(processor.getClassifier(), "Scheduling_Protocol");
		if(values.size()==1){
			return values.get(0);
		}
		return PropertyUtils.getStringListValue(processor.getClassifier(), "Scheduling_Protocol").get(0);
	}
	
	public static String getDataRepresentation(DataClassifier data){
		//Data_Model::Data_Representation
		System.out.println("看看运行了几次");
		String value=null;
		if(data instanceof DataType){
			value=PropertyUtils.getEnumValue(data, "Data_Representation");
		}else if(data instanceof DataImplementation){
			value=PropertyUtils.getEnumValue(data, "Data_Representation");
			if(value==null){
				DataType dt=((DataImplementation) data).getType();
				value=PropertyUtils.getEnumValue(dt, "Data_Representation");
			}
		}
		
		value=PropertyUtils.getEnumValue(data, "Data_Representation");
		if(value!=null){
			return value;
		}
		System.out.println("这里？");
		return "";
		
	}
	
	public static List<DataClassifier> getBaseTypes(DataClassifier data){
		//Data_Model::Base_Type
		List<DataClassifier> ds=new ArrayList<DataClassifier>();
		List<Classifier> cs=new ArrayList<Classifier>();
		cs=PropertyUtils.getClassifierListValue(data, "Base_Type");
		if(!cs.isEmpty()){
			for(Classifier c: cs){
				ds.add((DataClassifier)c);
			}
		}
		if(!ds.isEmpty())
			return ds;
		return null;
		
	}
	
	public static List<String> getElementNames(DataClassifier data){
		//Data_Model::Element_Names
		List<String> values=PropertyUtils.getStringListValue(data, "Element_Names");
		return values;
		
	}
	
	public static int getPeriod(ThreadClassifier thread){
		int value = 0;
		if(Integer.valueOf(new Long(PropertyUtils.getIntValue(thread, "Period")).intValue())!=null)
			value=new Long(PropertyUtils.getIntValue(thread, "Period")).intValue();
		return value;
	}
	
	public static Long getPeriod(ProcessClassifier process){
		Long value;
		value=PropertyUtils.getIntValue(process, "Period");
		return value;
	}
	
	public static int getPriority(ThreadClassifier thread){
		int value = 0;
		if(Integer.valueOf(new Long(PropertyUtils.getIntValue(thread, "Priority")).intValue())!=null)
			value=new Long(PropertyUtils.getIntValue(thread, "Priority")).intValue();
		
		return value;		
	}
	
	public static int getPeriod(ThreadSubcomponent subcomponent){		
//		if(subcomponent.getClassifier()==null)
//			return 0;
//		ThreadClassifier thread=(ThreadClassifier) subcomponent.getClassifier();
//		int value=0;
//		if(thread!=null)
//			value=getPeriod(thread);
//		return value;		
		return 0;
	}
	
	public static String getDispatchProtocol(ThreadClassifier thread){
		String value=null;

		if(thread instanceof ThreadType){
			
			value=PropertyUtils.getEnumValue(thread, "Dispatch_Protocol");
		}else if(thread instanceof ThreadImplementation){
			value=PropertyUtils.getEnumValue(thread, "Dispatch_Protocol");
			if(value==null){
				value=PropertyUtils.getEnumValue(((ThreadImplementation) thread).getType(), "Dispatch_Protocol");	
			}
		
		}
		if(value!=null)
			return value;
// 该处本来为null,造成调用程序报错！！！！
		return "null";
	}
	
	public static Long getPriority(ThreadSubcomponent subcomponent){
		ThreadClassifier thread=(ThreadClassifier) subcomponent.getClassifier();
		Long value;
		value=PropertyUtils.getIntValue(thread, "Priority");
		return value;
		
	}
	
	public static List<Long> getTime_Window(ThreadSubcomponent subcomponent){
		ThreadClassifier thread=(ThreadClassifier) subcomponent.getClassifier();
		//System.out.println("+~~111~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		List<Long> values=new ArrayList<Long>();
		List<Long> getvalues=PropertyUtils.getIntListValue(thread, "Time_Window");
	//	System.out.println("+~~111~~~~~~~~后~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		if(!(getvalues==null)) {
		
		for(Long value:getvalues){
			
			values.add(value);
		
		}
		if(!(values==null))
			{
			return values;
			}
		}
	
	
		return null;
	}
	

	
	
	
}
