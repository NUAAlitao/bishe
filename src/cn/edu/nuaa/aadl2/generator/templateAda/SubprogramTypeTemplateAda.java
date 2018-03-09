package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.osate.aadl2.SubprogramType;

@SuppressWarnings("all")
public class SubprogramTypeTemplateAda {
  private static String fileName = TemplateAda.subprogramsFileName;
  
  private static String initlineAds = (("package " + SubprogramTypeTemplateAda.fileName) + " is\n");
  
  private static String initlineAdb = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("with Ada.Text_IO; use Ada.Text_IO;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("package body ");
      _builder.append(SubprogramTypeTemplateAda.fileName);
      _builder.append(" is");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static CharSequence create(final SubprogramType subprogramType) {
    StringConcatenation _builder = new StringConcatenation();
    String _lowerCase = SubprogramTypeTemplateAda.fileName.toLowerCase();
    String _plus = (_lowerCase + ".ads");
    Tools.creatSubprogramFile(_plus, SubprogramTypeTemplateAda.initlineAds, SubprogramTypeTemplateAda.templateAds(subprogramType).toString());
    _builder.newLineIfNotEmpty();
    String _lowerCase_1 = SubprogramTypeTemplateAda.fileName.toLowerCase();
    String _plus_1 = (_lowerCase_1 + ".adb");
    Tools.creatSubprogramFile(_plus_1, SubprogramTypeTemplateAda.initlineAdb, SubprogramTypeTemplateAda.templateAdb(subprogramType).toString());
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence templateAds(final SubprogramType subprogramType) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("procedure ");
    _builder.append(TemplateAda.packageName, "\t");
    _builder.append("_");
    String _convertPoint = StringUtils.convertPoint(subprogramType.getName());
    _builder.append(_convertPoint, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence templateAdb(final SubprogramType subprogramType) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("procedure ");
    _builder.append(TemplateAda.packageName, "\t");
    _builder.append("_");
    String _convertPoint = StringUtils.convertPoint(subprogramType.getName());
    _builder.append(_convertPoint, "\t");
    _builder.append(" is");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("put_line(\"This is ");
    String _convertPoint_1 = StringUtils.convertPoint(subprogramType.getName());
    _builder.append(_convertPoint_1, "\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("end ");
    _builder.append(TemplateAda.packageName, "\t");
    _builder.append("_");
    String _convertPoint_2 = StringUtils.convertPoint(subprogramType.getName());
    _builder.append(_convertPoint_2, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
}
