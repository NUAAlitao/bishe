package cn.edu.nuaa.aadl2.generator.templateAda

import java.util.Map
import java.util.LinkedHashMap
import org.eclipse.ui.console.MessageConsole
import org.eclipse.ui.console.MessageConsoleStream
import org.eclipse.ui.console.IConsoleManager
import org.eclipse.ui.console.ConsolePlugin
import org.eclipse.ui.console.IConsole
import cn.edu.nuaa.aadl2.generator.utils.ShowMessage

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
		var message = '''
		---------------------------------------------------
		系统：«log.get("系统")»
		进程：«log.get("进程")»
		线程：«log.get("线程")»
		端口：«log.get("端口")»
		错误信息：端口没有连接
		---------------------------------------------------'''
		ShowMessage.printMessage(message)
	}
	def static void printLogAnnexError(){
		var message = '''
		---------------------------------------------------
		系统：«log.get("系统")»
		进程：«log.get("进程")»
		线程：«log.get("线程")»
		错误信息：行为附件语法错误
		---------------------------------------------------'''
		ShowMessage.printMessage(message)
	}
}