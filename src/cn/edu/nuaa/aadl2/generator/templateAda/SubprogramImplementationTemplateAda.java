package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SubprogramImplementation;

@SuppressWarnings("all")
public class SubprogramImplementationTemplateAda {
  private static String fileName = TemplateAda.subprogramsFileName;
  
  private static String initlineAds = (("package " + SubprogramImplementationTemplateAda.fileName) + " is\n");
  
  private static String initlineAdb = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("with Ada.Text_IO; use Ada.Text_IO;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("package body ");
      _builder.append(SubprogramImplementationTemplateAda.fileName);
      _builder.append(" is");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static CharSequence create(final SubprogramImplementation subprogramImplementation) {
    StringConcatenation _builder = new StringConcatenation();
    String _lowerCase = SubprogramImplementationTemplateAda.fileName.toLowerCase();
    String _plus = (_lowerCase + ".ads");
    Tools.creatSubprogramFile(_plus, SubprogramImplementationTemplateAda.initlineAds, SubprogramImplementationTemplateAda.templateAds(subprogramImplementation).toString());
    _builder.newLineIfNotEmpty();
    String _lowerCase_1 = SubprogramImplementationTemplateAda.fileName.toLowerCase();
    String _plus_1 = (_lowerCase_1 + ".adb");
    Tools.creatSubprogramFile(_plus_1, SubprogramImplementationTemplateAda.initlineAdb, SubprogramImplementationTemplateAda.templateAdb(subprogramImplementation).toString());
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence templateAds(final SubprogramImplementation subprogramImplementation) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("procedure ");
    _builder.append(TemplateAda.packageName, "\t");
    _builder.append("_");
    String _convertPoint = StringUtils.convertPoint(subprogramImplementation.getName());
    _builder.append(_convertPoint, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence templateAdb(final SubprogramImplementation subprogramImplementation) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("procedure ");
    _builder.append(TemplateAda.packageName, "\t");
    _builder.append("_");
    String _convertPoint = StringUtils.convertPoint(subprogramImplementation.getName());
    _builder.append(_convertPoint, "\t");
    _builder.append(" is");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("begin");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("put_line(\"This is ");
    String _convertPoint_1 = StringUtils.convertPoint(subprogramImplementation.getName());
    _builder.append(_convertPoint_1, "\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    {
      EList<SubprogramCall> _subprogramCalls = subprogramImplementation.getSubprogramCalls();
      for(final SubprogramCall subprogramCall : _subprogramCalls) {
        _builder.append("\t\t");
        _builder.append(TemplateAda.packageName, "\t\t");
        _builder.append("_");
        String _calledSubprogramName = Tools.getCalledSubprogramName(StringUtils.convertPoint(subprogramCall.getCalledSubprogram().toString()));
        _builder.append(_calledSubprogramName, "\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("end ");
    _builder.append(TemplateAda.packageName, "\t");
    _builder.append("_");
    String _convertPoint_2 = StringUtils.convertPoint(subprogramImplementation.getName());
    _builder.append(_convertPoint_2, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
}
