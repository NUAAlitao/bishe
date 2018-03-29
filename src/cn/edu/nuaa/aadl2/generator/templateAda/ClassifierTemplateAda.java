package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.SubprogramImplementationTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.SubprogramTypeTemplateAda;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.SubprogramImplementation;
import org.osate.aadl2.SubprogramType;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.ThreadType;

@SuppressWarnings("all")
public class ClassifierTemplateAda {
  public static CharSequence template(final Classifier classifier) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (classifier instanceof SubprogramType) {
      _matched=true;
      _switchResult = SubprogramTypeTemplateAda.create(((SubprogramType) classifier));
    }
    if (!_matched) {
      if (classifier instanceof SubprogramImplementation) {
        _matched=true;
        _switchResult = SubprogramImplementationTemplateAda.create(((SubprogramImplementation) classifier));
      }
    }
    if (!_matched) {
      if (classifier instanceof ThreadType) {
        _matched=true;
        _switchResult = InputOutput.<String>println("threadtype");
      }
    }
    if (!_matched) {
      if (classifier instanceof ThreadImplementation) {
        _matched=true;
        _switchResult = InputOutput.<String>println("threadImplementation");
      }
    }
    if (!_matched) {
      _switchResult = null;
    }
    return _switchResult;
  }
}
