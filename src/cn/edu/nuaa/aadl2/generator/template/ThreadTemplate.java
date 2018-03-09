package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.FeatureTemplate;
import cn.edu.nuaa.aadl2.generator.template.SubprogramTemplate;
import cn.edu.nuaa.aadl2.generator.template.Template;
import cn.edu.nuaa.aadl2.generator.utils.PropertyParser;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.CalledSubprogram;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.Feature;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SubprogramClassifier;
import org.osate.aadl2.ThreadClassifier;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.ThreadType;

@SuppressWarnings("all")
public class ThreadTemplate {
  public static CharSequence create(final ThreadSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    String _convert = StringUtils.convert(subcomponent.getClassifier().getName());
    String _plus = (_convert + ".h");
    Tools.createFile(_plus, ThreadTemplate.head(subcomponent).toString());
    _builder.newLineIfNotEmpty();
    String _convert_1 = StringUtils.convert(subcomponent.getClassifier().getName());
    String _plus_1 = (_convert_1 + ".c");
    Tools.createFile(_plus_1, ThreadTemplate.template(subcomponent).toString());
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence head(final ThreadSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier thread = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (thread instanceof ThreadType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/*Thread Type head file*/");
        _builder.newLine();
        _builder.append(Template.head);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (thread instanceof ThreadImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("/*Thread head c file*/");
          _builder.newLine();
          _builder.append(Template.head);
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence template(final ThreadSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier thread = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (thread instanceof ThreadType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/*Thread Type c file*/");
        _builder.newLine();
        _builder.append(Template.head);
        _builder.newLineIfNotEmpty();
        _builder.append("#include \"");
        _builder.append(Template.systemheadfile);
        _builder.append("\"\t");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("void ");
        String _convert = StringUtils.convert(((ThreadType)thread).getName());
        _builder.append(_convert);
        _builder.append("()");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("/*features in thread*/");
        _builder.newLine();
        {
          EList<Feature> _allFeatures = ((ThreadType)thread).getAllFeatures();
          boolean _notEquals = (!Objects.equal(_allFeatures, null));
          if (_notEquals) {
            _builder.append("\t");
            CharSequence _asVariables = FeatureTemplate.asVariables(((ThreadType)thread).getAllFeatures());
            _builder.append(_asVariables, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _equalsIgnoreCase = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Periodic");
          if (_equalsIgnoreCase) {
            _builder.append("\t");
            _builder.append("while(1)");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("{");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.append("}");
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (thread instanceof ThreadImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("/*Thread Implementation c file*/");
          _builder.newLine();
          _builder.append(Template.head);
          _builder.newLineIfNotEmpty();
          _builder.append("#include \"");
          _builder.append(Template.systemheadfile);
          _builder.append("\"\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t\t");
          _builder.newLine();
          {
            EList<SubprogramCall> _subprogramCalls = ((ThreadImplementation)thread).getSubprogramCalls();
            boolean _notEquals = (!Objects.equal(_subprogramCalls, null));
            if (_notEquals) {
              {
                EList<SubprogramCall> _subprogramCalls_1 = ((ThreadImplementation)thread).getSubprogramCalls();
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
          _builder.append("void ");
          String _convert = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert);
          _builder.append("()");
          _builder.newLineIfNotEmpty();
          _builder.append("{");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("/*features in thread*/");
          _builder.newLine();
          {
            EList<Feature> _allFeatures = ((ThreadImplementation)thread).getType().getAllFeatures();
            boolean _notEquals_2 = (!Objects.equal(_allFeatures, null));
            if (_notEquals_2) {
              _builder.append("\t");
              CharSequence _asVariables = FeatureTemplate.asVariables(((ThreadImplementation)thread).getType().getAllFeatures());
              _builder.append(_asVariables, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("\t");
          _builder.append("/*subprogram in thread with different type*/");
          _builder.newLine();
          {
            boolean _equalsIgnoreCase = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Periodic");
            if (_equalsIgnoreCase) {
              _builder.append("\t");
              _builder.append("while(1)");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("{");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("\t");
              _builder.newLine();
              {
                EList<SubprogramCall> _subprogramCalls_2 = ((ThreadImplementation)thread).getSubprogramCalls();
                boolean _notEquals_3 = (!Objects.equal(_subprogramCalls_2, null));
                if (_notEquals_3) {
                  {
                    EList<SubprogramCall> _subprogramCalls_3 = ((ThreadImplementation)thread).getSubprogramCalls();
                    for(final SubprogramCall sc_1 : _subprogramCalls_3) {
                      {
                        CalledSubprogram _calledSubprogram_2 = sc_1.getCalledSubprogram();
                        boolean _notEquals_4 = (!Objects.equal(_calledSubprogram_2, null));
                        if (_notEquals_4) {
                          _builder.append("\t");
                          _builder.append("\t");
                          CharSequence _inThread = SubprogramTemplate.inThread(sc_1.getCalledSubprogram());
                          _builder.append(_inThread, "\t\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    }
                  }
                }
              }
              _builder.append("\t");
              _builder.append("\t");
              _builder.newLine();
              _builder.append("\t");
              _builder.append("}");
              _builder.newLine();
            }
          }
          {
            boolean _equalsIgnoreCase_1 = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Sporadic");
            if (_equalsIgnoreCase_1) {
              {
                EList<SubprogramCall> _subprogramCalls_4 = ((ThreadImplementation)thread).getSubprogramCalls();
                boolean _notEquals_5 = (!Objects.equal(_subprogramCalls_4, null));
                if (_notEquals_5) {
                  {
                    EList<SubprogramCall> _subprogramCalls_5 = ((ThreadImplementation)thread).getSubprogramCalls();
                    for(final SubprogramCall sc_2 : _subprogramCalls_5) {
                      {
                        CalledSubprogram _calledSubprogram_3 = sc_2.getCalledSubprogram();
                        boolean _notEquals_6 = (!Objects.equal(_calledSubprogram_3, null));
                        if (_notEquals_6) {
                          _builder.append("\t");
                          CharSequence _inThread_1 = SubprogramTemplate.inThread(sc_2.getCalledSubprogram());
                          _builder.append(_inThread_1, "\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          {
            boolean _equalsIgnoreCase_2 = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Aperiodic");
            if (_equalsIgnoreCase_2) {
              {
                EList<SubprogramCall> _subprogramCalls_6 = ((ThreadImplementation)thread).getSubprogramCalls();
                boolean _notEquals_7 = (!Objects.equal(_subprogramCalls_6, null));
                if (_notEquals_7) {
                  {
                    EList<SubprogramCall> _subprogramCalls_7 = ((ThreadImplementation)thread).getSubprogramCalls();
                    for(final SubprogramCall sc_3 : _subprogramCalls_7) {
                      {
                        CalledSubprogram _calledSubprogram_4 = sc_3.getCalledSubprogram();
                        boolean _notEquals_8 = (!Objects.equal(_calledSubprogram_4, null));
                        if (_notEquals_8) {
                          _builder.append("\t");
                          CharSequence _inThread_2 = SubprogramTemplate.inThread(sc_3.getCalledSubprogram());
                          _builder.append(_inThread_2, "\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          {
            boolean _equalsIgnoreCase_3 = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Timed");
            if (_equalsIgnoreCase_3) {
              {
                EList<SubprogramCall> _subprogramCalls_8 = ((ThreadImplementation)thread).getSubprogramCalls();
                boolean _notEquals_9 = (!Objects.equal(_subprogramCalls_8, null));
                if (_notEquals_9) {
                  {
                    EList<SubprogramCall> _subprogramCalls_9 = ((ThreadImplementation)thread).getSubprogramCalls();
                    for(final SubprogramCall sc_4 : _subprogramCalls_9) {
                      {
                        CalledSubprogram _calledSubprogram_5 = sc_4.getCalledSubprogram();
                        boolean _notEquals_10 = (!Objects.equal(_calledSubprogram_5, null));
                        if (_notEquals_10) {
                          _builder.append("\t");
                          CharSequence _inThread_3 = SubprogramTemplate.inThread(sc_4.getCalledSubprogram());
                          _builder.append(_inThread_3, "\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          {
            boolean _equalsIgnoreCase_4 = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Hybrid");
            if (_equalsIgnoreCase_4) {
              {
                EList<SubprogramCall> _subprogramCalls_10 = ((ThreadImplementation)thread).getSubprogramCalls();
                boolean _notEquals_11 = (!Objects.equal(_subprogramCalls_10, null));
                if (_notEquals_11) {
                  {
                    EList<SubprogramCall> _subprogramCalls_11 = ((ThreadImplementation)thread).getSubprogramCalls();
                    for(final SubprogramCall sc_5 : _subprogramCalls_11) {
                      {
                        CalledSubprogram _calledSubprogram_6 = sc_5.getCalledSubprogram();
                        boolean _notEquals_12 = (!Objects.equal(_calledSubprogram_6, null));
                        if (_notEquals_12) {
                          _builder.append("\t");
                          CharSequence _inThread_4 = SubprogramTemplate.inThread(sc_5.getCalledSubprogram());
                          _builder.append(_inThread_4, "\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          {
            boolean _equalsIgnoreCase_5 = PropertyParser.getDispatchProtocol(((ThreadClassifier)thread)).equalsIgnoreCase("Background");
            if (_equalsIgnoreCase_5) {
              {
                EList<SubprogramCall> _subprogramCalls_12 = ((ThreadImplementation)thread).getSubprogramCalls();
                boolean _notEquals_13 = (!Objects.equal(_subprogramCalls_12, null));
                if (_notEquals_13) {
                  {
                    EList<SubprogramCall> _subprogramCalls_13 = ((ThreadImplementation)thread).getSubprogramCalls();
                    for(final SubprogramCall sc_6 : _subprogramCalls_13) {
                      {
                        CalledSubprogram _calledSubprogram_7 = sc_6.getCalledSubprogram();
                        boolean _notEquals_14 = (!Objects.equal(_calledSubprogram_7, null));
                        if (_notEquals_14) {
                          _builder.append("\t");
                          CharSequence _inThread_5 = SubprogramTemplate.inThread(sc_6.getCalledSubprogram());
                          _builder.append(_inThread_5, "\t");
                          _builder.newLineIfNotEmpty();
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          _builder.append("}");
          _builder.newLine();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence template(final ThreadClassifier thread) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (thread instanceof ThreadType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/*Thread Type c file*/");
      _builder.newLine();
      _builder.append(Template.head);
      _builder.newLineIfNotEmpty();
      _builder.append("#include \"");
      _builder.append(Template.systemheadfile);
      _builder.append("\"\t");
      _builder.newLineIfNotEmpty();
      _builder.append("void ");
      String _convert = StringUtils.convert(((ThreadType)thread).getName());
      _builder.append(_convert);
      _builder.append("()");
      _builder.newLineIfNotEmpty();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*features in thread*/");
      _builder.newLine();
      {
        EList<Feature> _allFeatures = ((ThreadType)thread).getAllFeatures();
        boolean _notEquals = (!Objects.equal(_allFeatures, null));
        if (_notEquals) {
          _builder.append("\t");
          CharSequence _asVariables = FeatureTemplate.asVariables(((ThreadType)thread).getAllFeatures());
          _builder.append(_asVariables, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equalsIgnoreCase = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Periodic");
        if (_equalsIgnoreCase) {
          _builder.append("\t");
          _builder.append("while(1)");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("{");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("}");
          _builder.newLine();
        }
      }
      _builder.append("}");
      _builder.newLine();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (thread instanceof ThreadImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/*Thread Implementation c file*/");
        _builder.newLine();
        _builder.append(Template.head);
        _builder.newLineIfNotEmpty();
        _builder.append("#include \"");
        _builder.append(Template.systemheadfile);
        _builder.append("\"\t\t\t\t");
        _builder.newLineIfNotEmpty();
        {
          EList<SubprogramCall> _subprogramCalls = ((ThreadImplementation)thread).getSubprogramCalls();
          boolean _notEquals = (!Objects.equal(_subprogramCalls, null));
          if (_notEquals) {
            {
              EList<SubprogramCall> _subprogramCalls_1 = ((ThreadImplementation)thread).getSubprogramCalls();
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
        _builder.append("void ");
        String _convert = StringUtils.convert(((ThreadImplementation)thread).getName());
        _builder.append(_convert);
        _builder.append("()");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("/*features in thread*/");
        _builder.newLine();
        {
          EList<Feature> _allFeatures = ((ThreadImplementation)thread).getType().getAllFeatures();
          boolean _notEquals_2 = (!Objects.equal(_allFeatures, null));
          if (_notEquals_2) {
            _builder.append("\t");
            CharSequence _asVariables = FeatureTemplate.asVariables(((ThreadImplementation)thread).getType().getAllFeatures());
            _builder.append(_asVariables, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("/*subprogram in thread with different type*/");
        _builder.newLine();
        {
          boolean _equalsIgnoreCase = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Periodic");
          if (_equalsIgnoreCase) {
            _builder.append("\t");
            _builder.append("while(1)");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("{");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.newLine();
            {
              EList<SubprogramCall> _subprogramCalls_2 = ((ThreadImplementation)thread).getSubprogramCalls();
              boolean _notEquals_3 = (!Objects.equal(_subprogramCalls_2, null));
              if (_notEquals_3) {
                {
                  EList<SubprogramCall> _subprogramCalls_3 = ((ThreadImplementation)thread).getSubprogramCalls();
                  for(final SubprogramCall sc_1 : _subprogramCalls_3) {
                    {
                      CalledSubprogram _calledSubprogram_2 = sc_1.getCalledSubprogram();
                      boolean _notEquals_4 = (!Objects.equal(_calledSubprogram_2, null));
                      if (_notEquals_4) {
                        _builder.append("\t");
                        _builder.append("\t");
                        CharSequence _inThread = SubprogramTemplate.inThread(sc_1.getCalledSubprogram());
                        _builder.append(_inThread, "\t\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
            _builder.append("\t");
            _builder.append("\t");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        {
          boolean _equalsIgnoreCase_1 = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Sporadic");
          if (_equalsIgnoreCase_1) {
            {
              EList<SubprogramCall> _subprogramCalls_4 = ((ThreadImplementation)thread).getSubprogramCalls();
              boolean _notEquals_5 = (!Objects.equal(_subprogramCalls_4, null));
              if (_notEquals_5) {
                {
                  EList<SubprogramCall> _subprogramCalls_5 = ((ThreadImplementation)thread).getSubprogramCalls();
                  for(final SubprogramCall sc_2 : _subprogramCalls_5) {
                    {
                      CalledSubprogram _calledSubprogram_3 = sc_2.getCalledSubprogram();
                      boolean _notEquals_6 = (!Objects.equal(_calledSubprogram_3, null));
                      if (_notEquals_6) {
                        _builder.append("\t");
                        CharSequence _inThread_1 = SubprogramTemplate.inThread(sc_2.getCalledSubprogram());
                        _builder.append(_inThread_1, "\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        {
          boolean _equalsIgnoreCase_2 = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Aperiodic");
          if (_equalsIgnoreCase_2) {
            {
              EList<SubprogramCall> _subprogramCalls_6 = ((ThreadImplementation)thread).getSubprogramCalls();
              boolean _notEquals_7 = (!Objects.equal(_subprogramCalls_6, null));
              if (_notEquals_7) {
                {
                  EList<SubprogramCall> _subprogramCalls_7 = ((ThreadImplementation)thread).getSubprogramCalls();
                  for(final SubprogramCall sc_3 : _subprogramCalls_7) {
                    {
                      CalledSubprogram _calledSubprogram_4 = sc_3.getCalledSubprogram();
                      boolean _notEquals_8 = (!Objects.equal(_calledSubprogram_4, null));
                      if (_notEquals_8) {
                        _builder.append("\t");
                        CharSequence _inThread_2 = SubprogramTemplate.inThread(sc_3.getCalledSubprogram());
                        _builder.append(_inThread_2, "\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        {
          boolean _equalsIgnoreCase_3 = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Timed");
          if (_equalsIgnoreCase_3) {
            {
              EList<SubprogramCall> _subprogramCalls_8 = ((ThreadImplementation)thread).getSubprogramCalls();
              boolean _notEquals_9 = (!Objects.equal(_subprogramCalls_8, null));
              if (_notEquals_9) {
                {
                  EList<SubprogramCall> _subprogramCalls_9 = ((ThreadImplementation)thread).getSubprogramCalls();
                  for(final SubprogramCall sc_4 : _subprogramCalls_9) {
                    {
                      CalledSubprogram _calledSubprogram_5 = sc_4.getCalledSubprogram();
                      boolean _notEquals_10 = (!Objects.equal(_calledSubprogram_5, null));
                      if (_notEquals_10) {
                        _builder.append("\t");
                        CharSequence _inThread_3 = SubprogramTemplate.inThread(sc_4.getCalledSubprogram());
                        _builder.append(_inThread_3, "\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        {
          boolean _equalsIgnoreCase_4 = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Hybrid");
          if (_equalsIgnoreCase_4) {
            {
              EList<SubprogramCall> _subprogramCalls_10 = ((ThreadImplementation)thread).getSubprogramCalls();
              boolean _notEquals_11 = (!Objects.equal(_subprogramCalls_10, null));
              if (_notEquals_11) {
                {
                  EList<SubprogramCall> _subprogramCalls_11 = ((ThreadImplementation)thread).getSubprogramCalls();
                  for(final SubprogramCall sc_5 : _subprogramCalls_11) {
                    {
                      CalledSubprogram _calledSubprogram_6 = sc_5.getCalledSubprogram();
                      boolean _notEquals_12 = (!Objects.equal(_calledSubprogram_6, null));
                      if (_notEquals_12) {
                        _builder.append("\t");
                        CharSequence _inThread_4 = SubprogramTemplate.inThread(sc_5.getCalledSubprogram());
                        _builder.append(_inThread_4, "\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        {
          boolean _equalsIgnoreCase_5 = PropertyParser.getDispatchProtocol(thread).equalsIgnoreCase("Background");
          if (_equalsIgnoreCase_5) {
            {
              EList<SubprogramCall> _subprogramCalls_12 = ((ThreadImplementation)thread).getSubprogramCalls();
              boolean _notEquals_13 = (!Objects.equal(_subprogramCalls_12, null));
              if (_notEquals_13) {
                {
                  EList<SubprogramCall> _subprogramCalls_13 = ((ThreadImplementation)thread).getSubprogramCalls();
                  for(final SubprogramCall sc_6 : _subprogramCalls_13) {
                    {
                      CalledSubprogram _calledSubprogram_7 = sc_6.getCalledSubprogram();
                      boolean _notEquals_14 = (!Objects.equal(_calledSubprogram_7, null));
                      if (_notEquals_14) {
                        _builder.append("\t");
                        CharSequence _inThread_5 = SubprogramTemplate.inThread(sc_6.getCalledSubprogram());
                        _builder.append(_inThread_5, "\t");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  }
                }
              }
            }
          }
        }
        _builder.append("}");
        _builder.newLine();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
}
