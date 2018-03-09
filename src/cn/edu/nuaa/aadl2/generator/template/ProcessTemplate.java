package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.Template;
import cn.edu.nuaa.aadl2.generator.template.ThreadTemplate;
import cn.edu.nuaa.aadl2.generator.utils.PropertyParser;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DataClassifier;
import org.osate.aadl2.Element;
import org.osate.aadl2.Feature;
import org.osate.aadl2.ProcessClassifier;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessType;
import org.osate.aadl2.ProcessorSubcomponent;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.impl.DataImplementationImpl;
import org.osate.aadl2.impl.DataTypeImpl;
import org.osate.aadl2.impl.DefaultAnnexSubclauseImpl;
import org.osate.ba.aadlba.BehaviorActionBlock;
import org.osate.ba.aadlba.BehaviorCondition;
import org.osate.ba.aadlba.BehaviorState;
import org.osate.ba.aadlba.BehaviorTransition;
import org.osate.ba.aadlba.BehaviorVariable;
import org.osate.ba.aadlba.DataHolder;
import org.osate.ba.aadlba.Value;
import org.osate.ba.aadlba.impl.BehaviorAnnexImpl;
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl;
import org.osate.ba.aadlba.impl.DataComponentReferenceImpl;
import org.osate.ba.aadlba.impl.DataSubcomponentHolderImpl;
import org.osate.ba.aadlba.impl.RelationImpl;
import org.osate.ba.utils.AadlBaVisitors;

@SuppressWarnings("all")
public class ProcessTemplate {
  public static String init = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("int time_cycle;//");
      _builder.newLine();
      _builder.append("int time_current;//");
      _builder.newLine();
      _builder.append("int ticks_pass;//");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  public static String time_current = "time_current";
  
  public static String time_cycle = "time_cycle";
  
  public static CharSequence create(final ProcessSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    String _replace = subcomponent.getClassifier().getName().toLowerCase().replace(".", "_");
    String _plus = (_replace + "_main.c");
    Tools.createFile(_plus, ProcessTemplate.template(subcomponent).toString());
    _builder.newLineIfNotEmpty();
    String _replace_1 = subcomponent.getClassifier().getName().toLowerCase().replace(".", "_");
    String _plus_1 = (_replace_1 + ".h");
    Tools.createFile(_plus_1, ProcessTemplate.head(subcomponent).toString());
    _builder.append("\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence head(final ProcessSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier process = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (process instanceof ProcessType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("33");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("/*process type*/");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(Template.head, "\t");
        _builder.append("\t\t\t\t\t\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int ");
        String _convert = StringUtils.convert(((ProcessType)process).getName());
        _builder.append(_convert, "\t");
        _builder.append("_init();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("int timer_schedule();");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("int ");
        String _convert_1 = StringUtils.convert(((ProcessType)process).getName());
        _builder.append(_convert_1, "\t");
        _builder.append("_init_task();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (process instanceof ProcessImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("41");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("/*process implementation*/");
          _builder.newLine();
          _builder.append("\t");
          _builder.append(Template.head, "\t");
          _builder.append("\t\t\t\t\t\t\t\t");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("void ");
          String _convert = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert, "\t");
          _builder.append("_init();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("void timer_schedule();");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("void ");
          String _convert_1 = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert_1, "\t");
          _builder.append("_init_task();");
          _builder.newLineIfNotEmpty();
          {
            EList<Subcomponent> _allSubcomponents = ((ProcessImplementation)process).getAllSubcomponents();
            boolean _notEquals = (!Objects.equal(_allSubcomponents, null));
            if (_notEquals) {
              {
                EList<Subcomponent> _allSubcomponents_1 = ((ProcessImplementation)process).getAllSubcomponents();
                for(final Subcomponent sub : _allSubcomponents_1) {
                  _builder.append("\t");
                  CharSequence _switchResult_1 = null;
                  boolean _matched_1 = false;
                  if (sub instanceof ThreadSubcomponent) {
                    _matched_1=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    _builder_1.append("void ");
                    String _convert_2 = StringUtils.convert(((ThreadSubcomponent)sub).getClassifier().getName());
                    _builder_1.append(_convert_2);
                    _builder_1.append("();");
                    _builder_1.newLineIfNotEmpty();
                    _switchResult_1 = _builder_1;
                  }
                  _builder.append(_switchResult_1, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
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
        String _string = ((ProcessType)process).getAllFeatures().toString();
        _builder.append(_string);
        _builder.newLineIfNotEmpty();
        _builder.append("/*process type*/");
        _builder.newLine();
        _builder.append(Template.head);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (process instanceof ProcessImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(children);
          _builder.newLineIfNotEmpty();
          {
            if ((children != null)) {
              {
                for(final Element sub : children) {
                  _builder.append("\t");
                  CharSequence _switchResult_1 = null;
                  boolean _matched_1 = false;
                  if (sub instanceof DefaultAnnexSubclauseImpl) {
                    _matched_1=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    Tools.addContent(Template.systemheadfile, "有个行为附件");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("有个行为附件");
                    _builder_1.newLine();
                    {
                      EList<Element> _children = ((DefaultAnnexSubclauseImpl)sub).getChildren();
                      for(final Element sub2 : _children) {
                        CharSequence _switchResult_2 = null;
                        boolean _matched_2 = false;
                        if (sub2 instanceof BehaviorAnnexImpl) {
                          _matched_2=true;
                          StringConcatenation _builder_2 = new StringConcatenation();
                          _builder_2.newLine();
                          {
                            EList<Element> _children_1 = ((BehaviorAnnexImpl)sub2).getChildren();
                            for(final Element sub3 : _children_1) {
                              _builder_2.append("\t");
                              CharSequence _switchResult_3 = null;
                              boolean _matched_3 = false;
                              if (sub3 instanceof BehaviorVariable) {
                                _matched_3=true;
                                _switchResult_3 = ProcessTemplate.template(((BehaviorVariable)sub3));
                              }
                              if (!_matched_3) {
                                if (sub3 instanceof BehaviorTransition) {
                                  _matched_3=true;
                                  _switchResult_3 = ProcessTemplate.template(((BehaviorTransition)sub3));
                                }
                              }
                              if (!_matched_3) {
                                if (sub3 instanceof BehaviorState) {
                                  _matched_3=true;
                                  _switchResult_3 = ProcessTemplate.template(((BehaviorState)sub3));
                                }
                              }
                              _builder_2.append(_switchResult_3, "\t");
                              _builder_2.newLineIfNotEmpty();
                            }
                          }
                          _switchResult_2 = _builder_2;
                        }
                        _builder_1.append(_switchResult_2);
                        _builder_1.newLineIfNotEmpty();
                      }
                    }
                    _switchResult_1 = _builder_1;
                  }
                  _builder.append(_switchResult_1, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.newLine();
          _builder.append("/*process implementation*/");
          _builder.newLine();
          _builder.append(Template.head);
          _builder.newLineIfNotEmpty();
          _builder.append("#include \"");
          _builder.append(Template.systemheadfile);
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.append("#include \"");
          String _convert = StringUtils.convert(((ProcessImplementation)process).getName());
          String _plus = (_convert + ".h");
          _builder.append(_plus);
          _builder.append("\"");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("int main()");
          _builder.newLine();
          _builder.append("{");
          _builder.newLine();
          {
            EList<Subcomponent> _allSubcomponents = ((ProcessImplementation)process).getAllSubcomponents();
            boolean _tripleNotEquals = (_allSubcomponents != null);
            if (_tripleNotEquals) {
              {
                EList<Subcomponent> _allSubcomponents_1 = ((ProcessImplementation)process).getAllSubcomponents();
                for(final Subcomponent sub_1 : _allSubcomponents_1) {
                  _builder.append("\t");
                  CharSequence _switchResult_2 = null;
                  boolean _matched_2 = false;
                  if (sub_1 instanceof ThreadSubcomponent) {
                    _matched_2=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    String _convert_1 = StringUtils.convert(((ThreadSubcomponent)sub_1).getClassifier().getName());
                    String _plus_1 = ("int " + _convert_1);
                    String _plus_2 = (_plus_1 + "_time;");
                    Tools.addContent(Template.systemheadfile, _plus_2);
                    _builder_1.newLineIfNotEmpty();
                    CharSequence _create = ThreadTemplate.create(((ThreadSubcomponent)sub_1));
                    _builder_1.append(_create);
                    _builder_1.newLineIfNotEmpty();
                    _switchResult_2 = _builder_1;
                  }
                  _builder.append(_switchResult_2, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.append("\t");
          String _convert_1 = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert_1, "\t");
          _builder.append("_init();");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          String _convert_2 = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert_2, "\t");
          _builder.append("_init_task();\t\t\t\t\t");
          _builder.newLineIfNotEmpty();
          {
            EList<Feature> _allFeatures = ((ProcessImplementation)process).getType().getAllFeatures();
            boolean _notEquals = (!Objects.equal(_allFeatures, null));
            if (_notEquals) {
            }
          }
          {
            ProcessorSubcomponent _processor = PropertyParser.getProcessor(subcomponent);
            boolean _notEquals_1 = (!Objects.equal(_processor, null));
            if (_notEquals_1) {
              {
                String _schedule = PropertyParser.getSchedule(PropertyParser.getProcessor(subcomponent));
                boolean _notEquals_2 = (!Objects.equal(_schedule, null));
                if (_notEquals_2) {
                  {
                    boolean _equalsIgnoreCase = PropertyParser.getSchedule(PropertyParser.getProcessor(subcomponent)).equalsIgnoreCase("FixedTimeLine");
                    if (_equalsIgnoreCase) {
                      _builder.append("\t");
                      _builder.append("/*FixedTimeLine*/");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.append("timer_schedule();\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
                      _builder.newLine();
                    }
                  }
                  {
                    boolean _equalsIgnoreCase_1 = PropertyParser.getSchedule(PropertyParser.getProcessor(subcomponent)).equalsIgnoreCase("Round_Robin_Protocol");
                    if (_equalsIgnoreCase_1) {
                      _builder.append("\t");
                      _builder.append("/*Round_Robin_Protocol*/");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                    }
                  }
                  {
                    boolean _equalsIgnoreCase_2 = PropertyParser.getSchedule(PropertyParser.getProcessor(subcomponent)).equalsIgnoreCase("RMS");
                    if (_equalsIgnoreCase_2) {
                      _builder.append("\t");
                      _builder.append("/*RMS*/");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                    }
                  }
                  {
                    boolean _equalsIgnoreCase_3 = PropertyParser.getSchedule(PropertyParser.getProcessor(subcomponent)).equalsIgnoreCase("Round_Robin_Protocol");
                    if (_equalsIgnoreCase_3) {
                      _builder.append("\t");
                      _builder.append("/*EDF*/");
                      _builder.newLine();
                      _builder.append("\t");
                      _builder.newLine();
                    }
                  }
                }
              }
            }
          }
          _builder.append("}");
          _builder.newLine();
          _builder.append("/*init task*/");
          _builder.newLine();
          _builder.append("int ");
          String _convert_3 = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert_3);
          _builder.append("_init_task()");
          _builder.newLineIfNotEmpty();
          _builder.append("{");
          _builder.newLine();
          {
            EList<Subcomponent> _allSubcomponents_2 = ((ProcessImplementation)process).getAllSubcomponents();
            boolean _notEquals_3 = (!Objects.equal(_allSubcomponents_2, null));
            if (_notEquals_3) {
              _builder.append("\t");
              String _string = ((ProcessImplementation)process).getAllFeatures().toString();
              _builder.append(_string, "\t");
              _builder.newLineIfNotEmpty();
              {
                EList<Subcomponent> _allSubcomponents_3 = ((ProcessImplementation)process).getAllSubcomponents();
                for(final Subcomponent sub_2 : _allSubcomponents_3) {
                  _builder.append("\t");
                  String _name = sub_2.getName();
                  _builder.append(_name, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.newLine();
                  _builder.append("\t");
                  _builder.append("\t");
                  CharSequence _switchResult_3 = null;
                  boolean _matched_3 = false;
                  if (sub_2 instanceof ThreadSubcomponent) {
                    _matched_3=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    _builder_1.append("if ( tsk_create(\"");
                    String _convert_4 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    _builder_1.append(_convert_4);
                    _builder_1.append("\",");
                    Long _priority = PropertyParser.getPriority(((ThreadSubcomponent)sub_2));
                    _builder_1.append(_priority);
                    _builder_1.append(", 0,\"\", 0,(FUNCPTR)");
                    String _convert_5 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    _builder_1.append(_convert_5);
                    _builder_1.append(", 0, 0, 0) == ERROR)");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("{");
                    _builder_1.newLine();
                    _builder_1.append("\t");
                    _builder_1.append("printf(\"ERR:Memory Not Enough!\\n\");\t");
                    _builder_1.newLine();
                    _builder_1.append("\t");
                    _builder_1.append("tsk_suspend(0);\t");
                    _builder_1.newLine();
                    _builder_1.append("}");
                    _builder_1.newLine();
                    _switchResult_3 = _builder_1;
                  }
                  _builder.append(_switchResult_3, "\t\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.append("\t");
          _builder.append("/*任务创建完之后挂起任务*/");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("tsk_suspend(0);\t");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.newLine();
          _builder.append("/*init process*/");
          _builder.newLine();
          _builder.append("int ");
          String _convert_4 = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert_4);
          _builder.append("_init()");
          _builder.newLineIfNotEmpty();
          _builder.append("{");
          _builder.newLine();
          _builder.append("\t");
          Tools.addContent(Template.systemheadfile, ProcessTemplate.init);
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("time_current=-1;");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("ticks_pass=0;");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("time_cycle=");
          Long _period = PropertyParser.getPeriod(((ProcessClassifier)process));
          _builder.append(_period, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          {
            EList<Subcomponent> _allSubcomponents_4 = ((ProcessImplementation)process).getAllSubcomponents();
            boolean _notEquals_4 = (!Objects.equal(_allSubcomponents_4, null));
            if (_notEquals_4) {
              {
                EList<Subcomponent> _allSubcomponents_5 = ((ProcessImplementation)process).getAllSubcomponents();
                for(final Subcomponent sub_3 : _allSubcomponents_5) {
                  _builder.append("\t");
                  CharSequence _switchResult_4 = null;
                  boolean _matched_4 = false;
                  if (sub_3 instanceof ThreadSubcomponent) {
                    _matched_4=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    String _convert_5 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_1 = ("int " + _convert_5);
                    String _plus_2 = (_plus_1 + "_time;");
                    Tools.addContent(Template.systemheadfile, _plus_2);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_6 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_3 = ("short " + _convert_6);
                    String _plus_4 = (_plus_3 + "_finish;");
                    Tools.addContent(Template.systemheadfile, _plus_4);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_7 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_5 = ("int " + _convert_7);
                    String _plus_6 = (_plus_5 + "_failure_count;");
                    Tools.addContent(Template.systemheadfile, _plus_6);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_8 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_7 = ("int " + _convert_8);
                    String _plus_8 = (_plus_7 + "_begin;");
                    Tools.addContent(Template.systemheadfile, _plus_8);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_9 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_9 = ("int " + _convert_9);
                    String _plus_10 = (_plus_9 + "_end;");
                    Tools.addContent(Template.systemheadfile, _plus_10);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_10 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_11 = (_convert_10 + "_time");
                    _builder_1.append(_plus_11);
                    _builder_1.append("=");
                    int _period_1 = PropertyParser.getPeriod(((ThreadSubcomponent)sub_3));
                    _builder_1.append(_period_1);
                    _builder_1.append(";");
                    _builder_1.newLineIfNotEmpty();
                    String _convert_11 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_12 = (_convert_11 + "_finish");
                    _builder_1.append(_plus_12);
                    _builder_1.append("=1;");
                    _builder_1.newLineIfNotEmpty();
                    String _convert_12 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                    String _plus_13 = (_convert_12 + "_failure_count");
                    _builder_1.append(_plus_13);
                    _builder_1.append("=1;");
                    _builder_1.newLineIfNotEmpty();
                    {
                      List<Long> _time_Window = PropertyParser.getTime_Window(((ThreadSubcomponent)sub_3));
                      boolean _notEquals_5 = (!Objects.equal(_time_Window, null));
                      if (_notEquals_5) {
                        String _convert_13 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                        String _plus_14 = (_convert_13 + "_begin");
                        _builder_1.append(_plus_14);
                        _builder_1.append("=");
                        Long _get = PropertyParser.getTime_Window(((ThreadSubcomponent)sub_3)).get(0);
                        _builder_1.append(_get);
                        _builder_1.append(";");
                        _builder_1.newLineIfNotEmpty();
                        String _convert_14 = StringUtils.convert(((ThreadSubcomponent)sub_3).getClassifier().getName());
                        String _plus_15 = (_convert_14 + "_end");
                        _builder_1.append(_plus_15);
                        _builder_1.append("=");
                        Long _get_1 = PropertyParser.getTime_Window(((ThreadSubcomponent)sub_3)).get(1);
                        _builder_1.append(_get_1);
                        _builder_1.append(";");
                        _builder_1.newLineIfNotEmpty();
                      }
                    }
                    _switchResult_4 = _builder_1;
                  }
                  _builder.append(_switchResult_4, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.append("\t");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.append("/*for fixedtimeline*/");
          _builder.newLine();
          _builder.append("int timer_schedule()");
          _builder.newLine();
          _builder.append("{");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("time_current++;");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("time_current=(time_current+time_cycle)%time_cycle;");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.newLine();
          _builder.newLine();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence template(final BehaviorVariable bv) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    DataClassifier _dataClassifier = bv.getDataClassifier();
    boolean _matched = false;
    if (_dataClassifier instanceof DataImplementationImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name = bv.getName();
      _builder_1.append(_name);
      _builder_1.append(" is a ");
      String _name_1 = bv.getDataClassifier().getName();
      _builder_1.append(_name_1);
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (_dataClassifier instanceof DataTypeImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name = bv.getName();
        _builder_1.append(_name);
        _builder_1.append(" is a ");
        String _name_1 = bv.getDataClassifier().getName();
        _builder_1.append(_name_1);
        _switchResult = _builder_1;
      }
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    _builder.append("BehaviorVariable end!");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence template(final BehaviorTransition bt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append(" ");
    _builder.append("BehaviorTransition: ");
    String _name = bt.getName();
    _builder.append(_name, " ");
    _builder.newLineIfNotEmpty();
    {
      BehaviorCondition _condition = bt.getCondition();
      boolean _notEquals = (!Objects.equal(_condition, null));
      if (_notEquals) {
        _builder.append("\t");
        EList<Element> _children = bt.getCondition().getChildren();
        _builder.append(_children, "\t");
        _builder.newLineIfNotEmpty();
        {
          EList<Element> _children_1 = bt.getCondition().getChildren();
          for(final Element condition : _children_1) {
            _builder.append("\t");
            Object _switchResult = null;
            boolean _matched = false;
            if (condition instanceof RelationImpl) {
              _matched=true;
              Object _xblockexpression = null;
              {
                Value k = ((RelationImpl)condition).getFirstExpression().getTerms().get(0).getFactors().get(0).getFirstValue();
                Object _switchResult_1 = null;
                boolean _matched_1 = false;
                if (k instanceof DataComponentReferenceImpl) {
                  _matched_1=true;
                  Object _xblockexpression_1 = null;
                  {
                    DataHolder kk = ((DataComponentReferenceImpl)k).getData().get(1);
                    Object _switchResult_2 = null;
                    boolean _matched_2 = false;
                    if (kk instanceof BehaviorVariableHolderImpl) {
                      _matched_2=true;
                      _switchResult_2 = ((BehaviorVariableHolderImpl)kk).getBehaviorVariable().getName();
                    }
                    if (!_matched_2) {
                      if (kk instanceof DataSubcomponentHolderImpl) {
                        _matched_2=true;
                        _switchResult_2 = ((DataSubcomponentHolderImpl)kk).getSubcomponent();
                      }
                    }
                    _xblockexpression_1 = _switchResult_2;
                  }
                  _switchResult_1 = _xblockexpression_1;
                }
                if (!_matched_1) {
                  _switchResult_1 = k;
                }
                _xblockexpression = _switchResult_1;
              }
              _switchResult = _xblockexpression;
            }
            _builder.append(_switchResult, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      BehaviorCondition _condition_1 = bt.getCondition();
      boolean _equals = Objects.equal(_condition_1, null);
      if (_equals) {
        _builder.append("\t");
        _builder.append("NO condition NO condition NO condition ");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("this is actionBlock ");
    BehaviorActionBlock _actionBlock = bt.getActionBlock();
    _builder.append(_actionBlock, "\t");
    _builder.newLineIfNotEmpty();
    {
      BehaviorActionBlock _actionBlock_1 = bt.getActionBlock();
      boolean _notEquals_1 = (!Objects.equal(_actionBlock_1, null));
      if (_notEquals_1) {
        _builder.append("\t");
        EList<Element> _children_2 = bt.getActionBlock().getChildren();
        _builder.append(_children_2, "\t");
        _builder.append("\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("from ");
    String _name_1 = bt.getSourceState().getName();
    _builder.append(_name_1, "\t");
    _builder.append(" to ");
    String _name_2 = bt.getDestinationState().getName();
    _builder.append(_name_2, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("END of one Transition.");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence template(final BehaviorState bs) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("AadlBaVisitors:\t");
    List<BehaviorTransition> _transitionWhereSrc = AadlBaVisitors.getTransitionWhereSrc(bs);
    _builder.append(_transitionWhereSrc);
    _builder.newLineIfNotEmpty();
    String _name = bs.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("is initial? ");
    boolean _isInitial = bs.isInitial();
    _builder.append(_isInitial);
    _builder.newLineIfNotEmpty();
    _builder.append("is final? ");
    boolean _isFinal = bs.isFinal();
    _builder.append(_isFinal);
    _builder.newLineIfNotEmpty();
    _builder.append("END of one state.");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence init(final ProcessImplementation process) {
    StringConcatenation _builder = new StringConcatenation();
    String _convert = StringUtils.convert(process.getName());
    String _plus = (_convert + ".h");
    String _convert_1 = StringUtils.convert(process.getName());
    String _plus_1 = ("int " + _convert_1);
    String _plus_2 = (_plus_1 + "_cycle");
    String _plus_3 = (_plus_2 + ";");
    Tools.addContent(_plus, _plus_3);
    _builder.newLineIfNotEmpty();
    String _convert_2 = StringUtils.convert(process.getName());
    String _plus_4 = (_convert_2 + ".h");
    String _convert_3 = StringUtils.convert(process.getName());
    String _plus_5 = ("int " + _convert_3);
    String _plus_6 = (_plus_5 + "_current");
    String _plus_7 = (_plus_6 + ";");
    Tools.addContent(_plus_4, _plus_7);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
