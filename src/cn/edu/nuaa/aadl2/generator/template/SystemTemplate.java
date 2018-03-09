package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.Template;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import cn.edu.nuaa.aadl2.generator.workflow.Generate;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.SystemClassifier;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.SystemSubcomponent;
import org.osate.aadl2.SystemType;

@SuppressWarnings("all")
public class SystemTemplate {
  public static CharSequence create(final SystemSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _template = SystemTemplate.template(subcomponent);
    _builder.append(_template);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence template(final SystemSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier subprogram = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (subprogram instanceof SystemClassifier) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = SystemTemplate.create(((SystemClassifier)subprogram));
        _builder.append(_create);
        _switchResult = _builder;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence create(final SystemClassifier subsystem) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subsystem instanceof SystemType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      System.out.println("SystemTemplate.xtend create()还未添加应对SystemType方法");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subsystem instanceof SystemImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        Object _generate = Generate.generate(((SystemImplementation)subsystem));
        _builder.append(_generate);
        _builder.newLineIfNotEmpty();
        System.out.println((Template.meta_systemheadfile + "11111111111111111111111111111111111111"));
        _builder.newLineIfNotEmpty();
        String _convert = StringUtils.convert(((SystemImplementation)subsystem).getName());
        String _plus = (_convert + ".h");
        Tools.sub_addContent(Template.meta_systemfolder, Template.meta_systemheadfile, _plus);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
}
