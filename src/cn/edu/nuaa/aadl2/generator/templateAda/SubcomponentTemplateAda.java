package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.ProcessTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ThreadTemplateAda;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.DeviceSubcomponent;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.ThreadSubcomponent;

@SuppressWarnings("all")
public class SubcomponentTemplateAda {
  public static CharSequence template(final Subcomponent subcomponent) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subcomponent instanceof ProcessSubcomponent) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _create = ProcessTemplateAda.create(((ProcessSubcomponent)subcomponent));
      _builder.append(_create);
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subcomponent instanceof ThreadSubcomponent) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = ThreadTemplateAda.create(((ThreadSubcomponent)subcomponent));
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
      StringConcatenation _builder = new StringConcatenation();
      String _name = subcomponent.getClassifier().getName();
      String _plus = (_name + "没考虑");
      System.out.println(_plus);
      _switchResult = _builder;
    }
    return _switchResult;
  }
}
