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
  
  public static String head = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"taskLib.h\"");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static String bakhead = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"taskLib.h\"");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static String dhead = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("typedef char* string;");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static String bakdhead = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"taskLib.h\"");
      _builder.newLine();
      _builder.append("typedef char* string;");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
}
