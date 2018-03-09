package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ThreadTemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.Element;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessType;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.ThreadSubcomponent;

@SuppressWarnings("all")
public class ProcessTemplateAda {
  public static CharSequence create(final ProcessSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    String _replace = subcomponent.getName().toLowerCase().replace(".", "_");
    String _plus = ((TemplateAda.systemfolder + "/") + _replace);
    Tools.folder(_plus);
    _builder.newLineIfNotEmpty();
    String _replace_1 = subcomponent.getName().toLowerCase().replace(".", "_");
    String _plus_1 = ((TemplateAda.systemfolder + "/") + _replace_1);
    String _processfolder = TemplateAda.processfolder = _plus_1;
    _builder.append(_processfolder);
    _builder.newLineIfNotEmpty();
    String _replace_2 = subcomponent.getName().toLowerCase().replace(".", "_");
    String _plus_2 = (_replace_2 + ".adb");
    Tools.createFile(_plus_2, ProcessTemplateAda.template(subcomponent).toString());
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence template(final ProcessSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier process = subcomponent.getClassifier();
      EList<Element> children = process.getChildren();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (process instanceof ProcessType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (process instanceof ProcessImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("with ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_Subprograms; use ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_Subprograms;");
          _builder.newLineIfNotEmpty();
          _builder.append("with ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_types; use ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_types;");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("procedure ");
          String _replace = subcomponent.getName().replace(".", "_");
          _builder.append(_replace);
          _builder.append(" is");
          _builder.newLineIfNotEmpty();
          {
            EList<Subcomponent> _allSubcomponents = ((ProcessImplementation)process).getAllSubcomponents();
            boolean _tripleNotEquals = (_allSubcomponents != null);
            if (_tripleNotEquals) {
              {
                EList<Subcomponent> _allSubcomponents_1 = ((ProcessImplementation)process).getAllSubcomponents();
                for(final Subcomponent sub : _allSubcomponents_1) {
                  _builder.append("\t");
                  CharSequence _switchResult_1 = null;
                  boolean _matched_1 = false;
                  if (sub instanceof DataSubcomponent) {
                    _matched_1=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    CharSequence _create = DataTemplateAda.create(((DataSubcomponent)sub));
                    _builder_1.append(_create);
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.newLine();
                    _switchResult_1 = _builder_1;
                  }
                  if (!_matched_1) {
                    if (sub instanceof ThreadSubcomponent) {
                      _matched_1=true;
                      StringConcatenation _builder_1 = new StringConcatenation();
                      CharSequence _create = ThreadTemplateAda.create(((ThreadSubcomponent)sub));
                      _builder_1.append(_create);
                      _builder_1.newLineIfNotEmpty();
                      _switchResult_1 = _builder_1;
                    }
                  }
                  _builder.append(_switchResult_1, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.append("\t");
          _builder.newLine();
          _builder.append("begin");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("null;");
          _builder.newLine();
          _builder.append("end ");
          String _replace_1 = subcomponent.getName().replace(".", "_");
          _builder.append(_replace_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
}
