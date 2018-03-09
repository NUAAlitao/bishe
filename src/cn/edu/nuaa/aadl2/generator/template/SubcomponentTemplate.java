package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.DataTemplate;
import cn.edu.nuaa.aadl2.generator.template.ProcessTemplate;
import cn.edu.nuaa.aadl2.generator.template.SubprogramTemplate;
import cn.edu.nuaa.aadl2.generator.template.SystemTemplate;
import cn.edu.nuaa.aadl2.generator.template.ThreadTemplate;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DeviceSubcomponent;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SubprogramSubcomponent;
import org.osate.aadl2.SystemSubcomponent;
import org.osate.aadl2.ThreadSubcomponent;

@SuppressWarnings("all")
public class SubcomponentTemplate {
  public static CharSequence template(final Subcomponent subcomponent) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subcomponent instanceof ProcessSubcomponent) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _create = ProcessTemplate.create(((ProcessSubcomponent)subcomponent));
      _builder.append(_create);
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subcomponent instanceof ThreadSubcomponent) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = ThreadTemplate.create(((ThreadSubcomponent)subcomponent));
        _builder.append(_create);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (subcomponent instanceof SubprogramSubcomponent) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = SubprogramTemplate.create(((SubprogramSubcomponent)subcomponent));
        _builder.append(_create);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (subcomponent instanceof DataSubcomponent) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = DataTemplate.create(((DataSubcomponent)subcomponent));
        _builder.append(_create);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (subcomponent instanceof DeviceSubcomponent) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((DeviceSubcomponent)subcomponent).getClassifier().getName();
        String _plus = (_name + "没有");
        System.out.println(_plus);
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (subcomponent instanceof SystemSubcomponent) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = SystemTemplate.create(((SystemSubcomponent)subcomponent));
        _builder.append(_create);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      String _name = subcomponent.getClassifier().getName();
      String _plus = (_name + "没考虑");
      System.out.println(_plus);
      _switchResult = _builder;
    }
    return _switchResult;
  }
}
