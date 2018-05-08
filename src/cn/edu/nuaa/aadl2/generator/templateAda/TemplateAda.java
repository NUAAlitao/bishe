package cn.edu.nuaa.aadl2.generator.templateAda;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class TemplateAda {
  public static String systemfolder = null;
  
  public static String processfolder = null;
  
  public static String packageName = null;
  
  public static String subprogramsFileName = null;
  
  public static String typesFileName = null;
  
  public static String meta_systemheadfile = null;
  
  public static String meta_systemfolder = null;
  
  public static boolean isMeta = true;
  
  public static String base_protect_ads = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("generic");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("type Element_Type is private;");
      _builder.newLine();
      _builder.append("package base_protect is");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("protected type base(count:Integer:=0) is");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("entry secure;");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("procedure release;");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("procedure send(message:Element_Type);");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("function receive return Element_Type;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("private");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("current_count: Integer := count;");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("values: Element_Type;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("end base;");
      _builder.newLine();
      _builder.append("end base_protect;");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static String base_protect_adb = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package body base_protect is");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("protected body base is");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("entry secure when current_count>0 is");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("begin  ");
      _builder.newLine();
      _builder.append("         ");
      _builder.append("current_count := current_count-1;");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("end secure;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("      ");
      _builder.append("procedure release is");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("begin");
      _builder.newLine();
      _builder.append("         ");
      _builder.append("current_count:= current_count+1;");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("end release;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("      ");
      _builder.append("procedure send (message:Element_Type) is");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("begin");
      _builder.newLine();
      _builder.append("         ");
      _builder.append("values := message;");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("end send;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("      ");
      _builder.append("function receive return Element_Type is");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("begin");
      _builder.newLine();
      _builder.append("         ");
      _builder.append("return values;");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("end receive;");
      _builder.newLine();
      _builder.append("   ");
      _builder.append("end base;");
      _builder.newLine();
      _builder.append("end base_protect;");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
}
