package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.FeatureTemplate;
import cn.edu.nuaa.aadl2.generator.template.Template;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.CalledSubprogram;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.Feature;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SubprogramClassifier;
import org.osate.aadl2.SubprogramImplementation;
import org.osate.aadl2.SubprogramSubcomponent;
import org.osate.aadl2.SubprogramType;

/**
 * Subprogram template
 * subprogram as subcomponent applies to(process implementation ,thread implementation subprogram implementation)
 * subprogram as subprogram calls
 */
@SuppressWarnings("all")
public class SubprogramTemplate {
  public static CharSequence include(final CalledSubprogram subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"");
      String _convert = StringUtils.convert(((SubprogramType)subprogram).getName());
      _builder.append(_convert);
      _builder.append(".h\"");
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#include \"");
        String _convert = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        _builder.append(_convert);
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence init(final CalledSubprogram subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence create(final SubprogramSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _template = SubprogramTemplate.template(subcomponent);
    _builder.append(_template);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence template(final SubprogramSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier subprogram = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (subprogram instanceof SubprogramClassifier) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _create = SubprogramTemplate.create(((SubprogramClassifier)subprogram));
        _builder.append(_create);
        _switchResult = _builder;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence template(final CalledSubprogram cs) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (cs instanceof SubprogramClassifier) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      Object _create = SubprogramTemplate.create(((SubprogramClassifier)cs));
      _builder.append(_create);
      _switchResult = _builder;
    }
    return _switchResult;
  }
  
  public static CharSequence create(final SubprogramClassifier subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _convert = StringUtils.convert(((SubprogramType)subprogram).getName());
      String _plus = (_convert + ".h");
      Tools.createFile(_plus, SubprogramTemplate.head(subprogram).toString());
      _builder.newLineIfNotEmpty();
      String _convert_1 = StringUtils.convert(((SubprogramType)subprogram).getName());
      String _plus_1 = (_convert_1 + ".c");
      Tools.createFile(_plus_1, SubprogramTemplate.template(subprogram).toString());
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _convert = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        String _plus = (_convert + ".h");
        Tools.createFile(_plus, SubprogramTemplate.head(subprogram).toString());
        _builder.newLineIfNotEmpty();
        String _convert_1 = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        String _plus_1 = (_convert_1 + ".c");
        Tools.createFile(_plus_1, SubprogramTemplate.template(subprogram).toString());
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence head(final SubprogramClassifier subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/*Subprogram Type head file*/");
      _builder.newLine();
      _builder.append("int ");
      String _convert = StringUtils.convert(((SubprogramType)subprogram).getName());
      _builder.append(_convert);
      _builder.append("(");
      {
        EList<Feature> _allFeatures = ((SubprogramType)subprogram).getAllFeatures();
        boolean _notEquals = (!Objects.equal(_allFeatures, null));
        if (_notEquals) {
          CharSequence _asDefinations = FeatureTemplate.asDefinations(((SubprogramType)subprogram).getAllFeatures());
          _builder.append(_asDefinations);
        }
      }
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/*Subprogram Implementation head file*/");
        _builder.newLine();
        _builder.append("int ");
        String _convert = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        _builder.append(_convert);
        _builder.append("(");
        {
          EList<Feature> _allFeatures = ((SubprogramImplementation)subprogram).getType().getAllFeatures();
          boolean _notEquals = (!Objects.equal(_allFeatures, null));
          if (_notEquals) {
            CharSequence _asDefinations = FeatureTemplate.asDefinations(((SubprogramImplementation)subprogram).getType().getAllFeatures());
            _builder.append(_asDefinations);
          }
        }
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence template(final SubprogramClassifier subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/*Subprogram Type c file*/");
      _builder.newLine();
      _builder.append(Template.head);
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"");
      _builder.append(Template.systemheadfile);
      _builder.append("\"\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("int ");
      String _convert = StringUtils.convert(((SubprogramType)subprogram).getName());
      _builder.append(_convert);
      _builder.append("(");
      {
        EList<Feature> _allFeatures = ((SubprogramType)subprogram).getAllFeatures();
        boolean _notEquals = (!Objects.equal(_allFeatures, null));
        if (_notEquals) {
          CharSequence _asDefinations = FeatureTemplate.asDefinations(((SubprogramType)subprogram).getAllFeatures());
          _builder.append(_asDefinations);
        }
      }
      _builder.append(")");
      _builder.newLineIfNotEmpty();
      _builder.append("{");
      _builder.newLine();
      {
        EList<PropertyAssociation> _allPropertyAssociations = ((SubprogramType)subprogram).getAllPropertyAssociations();
        boolean _notEquals_1 = (!Objects.equal(_allPropertyAssociations, null));
        if (_notEquals_1) {
          {
            EList<PropertyAssociation> _allPropertyAssociations_1 = ((SubprogramType)subprogram).getAllPropertyAssociations();
            for(final PropertyAssociation pa : _allPropertyAssociations_1) {
              _builder.append("\t");
              _builder.append("printf(\"");
              _builder.append(pa, "\t");
              _builder.append("\");");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _builder.append("\t");
      _builder.append("return 1;");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/*Subprogram Implementation c file*/");
        _builder.newLine();
        _builder.append(Template.head);
        _builder.newLineIfNotEmpty();
        _builder.append("#include \"");
        _builder.append(Template.systemheadfile);
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t");
        _builder.newLine();
        {
          EList<SubprogramCall> _subprogramCalls = ((SubprogramImplementation)subprogram).getSubprogramCalls();
          boolean _notEquals = (!Objects.equal(_subprogramCalls, null));
          if (_notEquals) {
            {
              EList<SubprogramCall> _subprogramCalls_1 = ((SubprogramImplementation)subprogram).getSubprogramCalls();
              for(final SubprogramCall sc : _subprogramCalls_1) {
                {
                  CalledSubprogram _calledSubprogram = sc.getCalledSubprogram();
                  boolean _notEquals_1 = (!Objects.equal(_calledSubprogram, null));
                  if (_notEquals_1) {
                    CharSequence _template = SubprogramTemplate.template(sc.getCalledSubprogram());
                    _builder.append(_template);
                    _builder.newLineIfNotEmpty();
                    {
                      CalledSubprogram _calledSubprogram_1 = sc.getCalledSubprogram();
                      if ((_calledSubprogram_1 instanceof SubprogramClassifier)) {
                        CharSequence _include = SubprogramTemplate.include(sc.getCalledSubprogram());
                        _builder.append(_include);
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        _builder.append("int ");
        String _convert = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        _builder.append(_convert);
        _builder.append("(");
        {
          EList<Feature> _allFeatures = ((SubprogramImplementation)subprogram).getType().getAllFeatures();
          boolean _notEquals_2 = (!Objects.equal(_allFeatures, null));
          if (_notEquals_2) {
            CharSequence _asDefinations = FeatureTemplate.asDefinations(((SubprogramImplementation)subprogram).getType().getAllFeatures());
            _builder.append(_asDefinations);
          }
        }
        _builder.append(")");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        {
          EList<SubprogramCall> _subprogramCalls_2 = ((SubprogramImplementation)subprogram).getSubprogramCalls();
          boolean _notEquals_3 = (!Objects.equal(_subprogramCalls_2, null));
          if (_notEquals_3) {
            {
              EList<SubprogramCall> _subprogramCalls_3 = ((SubprogramImplementation)subprogram).getSubprogramCalls();
              for(final SubprogramCall sc_1 : _subprogramCalls_3) {
                {
                  CalledSubprogram _calledSubprogram_2 = sc_1.getCalledSubprogram();
                  boolean _notEquals_4 = (!Objects.equal(_calledSubprogram_2, null));
                  if (_notEquals_4) {
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.newLine();
                  }
                }
              }
            }
          }
        }
        {
          EList<PropertyAssociation> _allPropertyAssociations = ((SubprogramImplementation)subprogram).getAllPropertyAssociations();
          boolean _notEquals_5 = (!Objects.equal(_allPropertyAssociations, null));
          if (_notEquals_5) {
            {
              EList<PropertyAssociation> _allPropertyAssociations_1 = ((SubprogramImplementation)subprogram).getAllPropertyAssociations();
              for(final PropertyAssociation pa : _allPropertyAssociations_1) {
                _builder.append("\t");
                _builder.append("printf(\"");
                _builder.append(pa, "\t");
                _builder.append("\");");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
        {
          EList<SubprogramCall> _subprogramCalls_4 = ((SubprogramImplementation)subprogram).getSubprogramCalls();
          boolean _notEquals_6 = (!Objects.equal(_subprogramCalls_4, null));
          if (_notEquals_6) {
            {
              EList<SubprogramCall> _subprogramCalls_5 = ((SubprogramImplementation)subprogram).getSubprogramCalls();
              for(final SubprogramCall sc_2 : _subprogramCalls_5) {
                {
                  CalledSubprogram _calledSubprogram_3 = sc_2.getCalledSubprogram();
                  boolean _notEquals_7 = (!Objects.equal(_calledSubprogram_3, null));
                  if (_notEquals_7) {
                    _builder.append("\t");
                    CharSequence _call = SubprogramTemplate.call(sc_2.getCalledSubprogram());
                    _builder.append(_call, "\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
        _builder.append("\t");
        _builder.append("return 1;");
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence callFeature(final CalledSubprogram subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _asVariables = FeatureTemplate.asVariables(((SubprogramType)subprogram).getAllFeatures());
      _builder.append(_asVariables);
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _asVariables = FeatureTemplate.asVariables(((SubprogramImplementation)subprogram).getType().getAllFeatures());
        _builder.append(_asVariables);
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence call(final CalledSubprogram subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _convert = StringUtils.convert(((SubprogramType)subprogram).getName());
      _builder.append(_convert);
      _builder.append("(");
      {
        EList<Feature> _allFeatures = ((SubprogramType)subprogram).getAllFeatures();
        boolean _notEquals = (!Objects.equal(_allFeatures, null));
        if (_notEquals) {
          CharSequence _asParameters = FeatureTemplate.asParameters(((SubprogramType)subprogram).getAllFeatures());
          _builder.append(_asParameters);
        }
      }
      _builder.append(");");
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _convert = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        _builder.append(_convert);
        _builder.append("(");
        {
          EList<Feature> _allFeatures = ((SubprogramImplementation)subprogram).getType().getAllFeatures();
          boolean _notEquals = (!Objects.equal(_allFeatures, null));
          if (_notEquals) {
            CharSequence _asParameters = FeatureTemplate.asParameters(((SubprogramImplementation)subprogram).getType().getAllFeatures());
            _builder.append(_asParameters);
          }
        }
        _builder.append(");");
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence inThread(final CalledSubprogram subprogram) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (subprogram instanceof SubprogramType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _convert = StringUtils.convert(((SubprogramType)subprogram).getName());
      _builder.append(_convert);
      _builder.append("(");
      {
        EList<Feature> _allFeatures = ((SubprogramType)subprogram).getAllFeatures();
        boolean _notEquals = (!Objects.equal(_allFeatures, null));
        if (_notEquals) {
          CharSequence _asParameters = FeatureTemplate.asParameters(((SubprogramType)subprogram).getAllFeatures());
          _builder.append(_asParameters);
        }
      }
      _builder.append(");");
      _switchResult = _builder;
    }
    if (!_matched) {
      if (subprogram instanceof SubprogramImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _convert = StringUtils.convert(((SubprogramImplementation)subprogram).getName());
        _builder.append(_convert);
        _builder.append("(");
        {
          EList<Feature> _allFeatures = ((SubprogramImplementation)subprogram).getType().getAllFeatures();
          boolean _notEquals = (!Objects.equal(_allFeatures, null));
          if (_notEquals) {
            CharSequence _asParameters = FeatureTemplate.asParameters(((SubprogramImplementation)subprogram).getType().getAllFeatures());
            _builder.append(_asParameters);
          }
        }
        _builder.append(");");
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
}
