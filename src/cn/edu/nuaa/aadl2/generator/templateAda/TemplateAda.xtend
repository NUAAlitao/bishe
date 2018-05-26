package cn.edu.nuaa.aadl2.generator.templateAda

import java.util.Map
import java.util.HashMap
import java.util.LinkedHashMap

class TemplateAda {
	public static String systemfolder=null;
	public static String processfolder=null;
	
	public static String packageName=null;
	public static String subprogramsFileName=null;
	public static String typesFileName=null;
	
	public static String meta_systemheadfile=null;
	public static String meta_systemfolder=null;
	
	
	public static String base_protect_ads='''
		generic
		    type Element_Type is private;
		package base_protect is
		    protected type base(count:Integer:=0) is
		        entry secure;
		        procedure release;
		        procedure send(message:Element_Type);
		        function receive return Element_Type;
		    private
		        current_count: Integer := count;
		        values: Element_Type;
		    end base;
		end base_protect;
	'''
	
	public static String base_protect_adb='''
		package body base_protect is
		   protected body base is
		      entry secure when current_count>0 is
		      begin  
		         current_count := current_count-1;
		      end secure;
		
		      procedure release is
		      begin
		         current_count:= current_count+1;
		      end release;
		
		      procedure send (message:Element_Type) is
		      begin
		         values := message;
		      end send;
		
		      function receive return Element_Type is
		      begin
		         return values;
		      end receive;
		   end base;
		end base_protect;
	'''
	
	private static Map<String,String> log = new LinkedHashMap<String,String>();
	def static void addLogMessage(String name, String message){
		log.put(name,message);
	}
	def static clearLogMessage(){
		log.clear();
	}
	def static void printLogNoConnection(){
		println("---------------------------------------------------");
		println("系统："+log.get("系统"))
		println("进程："+log.get("进程"))
		println("线程："+log.get("线程"))
		println("端口："+log.get("端口"))
		println("错误信息：端口没有连接")
		println("---------------------------------------------------");
	}
	def static void printLogAnnexError(){
		println("---------------------------------------------------");
		println("系统："+log.get("系统"))
		println("进程："+log.get("进程"))
		println("线程："+log.get("线程"))
		println("错误信息：行为附件语法错误")
		println("---------------------------------------------------");
	}
}