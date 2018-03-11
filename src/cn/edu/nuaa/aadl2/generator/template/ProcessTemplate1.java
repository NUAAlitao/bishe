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
import org.eclipse.xtext.xbase.lib.Conversions;
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
import org.osate.ba.aadlba.BehaviorAction;
import org.osate.ba.aadlba.BehaviorActionBlock;
import org.osate.ba.aadlba.BehaviorCondition;
import org.osate.ba.aadlba.BehaviorState;
import org.osate.ba.aadlba.BehaviorTransition;
import org.osate.ba.aadlba.BehaviorVariable;
import org.osate.ba.aadlba.DataHolder;
import org.osate.ba.aadlba.IntegerValue;
import org.osate.ba.aadlba.LogicalOperator;
import org.osate.ba.aadlba.Relation;
import org.osate.ba.aadlba.SimpleExpression;
import org.osate.ba.aadlba.Target;
import org.osate.ba.aadlba.Value;
import org.osate.ba.aadlba.impl.AssignmentActionImpl;
import org.osate.ba.aadlba.impl.BehaviorActionSequenceImpl;
import org.osate.ba.aadlba.impl.BehaviorAnnexImpl;
import org.osate.ba.aadlba.impl.BehaviorBooleanLiteralImpl;
import org.osate.ba.aadlba.impl.BehaviorIntegerLiteralImpl;
import org.osate.ba.aadlba.impl.BehaviorRealLiteralImpl;
import org.osate.ba.aadlba.impl.BehaviorTimeImpl;
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl;
import org.osate.ba.aadlba.impl.DataComponentReferenceImpl;
import org.osate.ba.aadlba.impl.DataSubcomponentHolderImpl;
import org.osate.ba.aadlba.impl.PortDequeueActionImpl;
import org.osate.ba.aadlba.impl.PortSendActionImpl;
import org.osate.ba.aadlba.impl.RelationImpl;
import org.osate.ba.aadlba.impl.ValueExpressionImpl;
import org.osate.ba.utils.AadlBaVisitors;

@SuppressWarnings("all")
public class ProcessTemplate1 {
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
    Tools.createFile(_plus, ProcessTemplate1.template(subcomponent).toString());
    _builder.newLineIfNotEmpty();
    String _replace_1 = subcomponent.getClassifier().getName().toLowerCase().replace(".", "_");
    String _plus_1 = (_replace_1 + ".h");
    Tools.createFile(_plus_1, ProcessTemplate1.head(subcomponent).toString());
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
        _builder.append("/*process type*/");
        _builder.newLine();
        _builder.append(Template.head);
        _builder.append("\t\t\t\t\t\t\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("int ");
        String _convert = StringUtils.convert(((ProcessType)process).getName());
        _builder.append(_convert);
        _builder.append("_init();");
        _builder.newLineIfNotEmpty();
        _builder.append("int timer_schedule();");
        _builder.newLine();
        _builder.append("int ");
        String _convert_1 = StringUtils.convert(((ProcessType)process).getName());
        _builder.append(_convert_1);
        _builder.append("_init_task();");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (process instanceof ProcessImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("/*process implementation*/");
          _builder.newLine();
          _builder.append(Template.head);
          _builder.append("\t\t\t\t\t\t\t\t");
          _builder.newLineIfNotEmpty();
          _builder.append("void ");
          String _convert = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert);
          _builder.append("_init();");
          _builder.newLineIfNotEmpty();
          _builder.append("void timer_schedule();");
          _builder.newLine();
          _builder.append("void ");
          String _convert_1 = StringUtils.convert(((ProcessImplementation)process).getName());
          _builder.append(_convert_1);
          _builder.append("_init_task();");
          _builder.newLineIfNotEmpty();
          {
            EList<Subcomponent> _allSubcomponents = ((ProcessImplementation)process).getAllSubcomponents();
            boolean _notEquals = (!Objects.equal(_allSubcomponents, null));
            if (_notEquals) {
              {
                EList<Subcomponent> _allSubcomponents_1 = ((ProcessImplementation)process).getAllSubcomponents();
                for(final Subcomponent sub : _allSubcomponents_1) {
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
                  _builder.append(_switchResult_1);
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
          _builder.append("\t\t");
          _builder.newLine();
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
                for(final Subcomponent sub : _allSubcomponents_1) {
                  _builder.append("\t");
                  CharSequence _switchResult_1 = null;
                  boolean _matched_1 = false;
                  if (sub instanceof ThreadSubcomponent) {
                    _matched_1=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    String _convert_1 = StringUtils.convert(((ThreadSubcomponent)sub).getClassifier().getName());
                    String _plus_1 = ("int " + _convert_1);
                    String _plus_2 = (_plus_1 + "_time;");
                    Tools.addContent(Template.systemheadfile, _plus_2);
                    _builder_1.newLineIfNotEmpty();
                    CharSequence _create = ThreadTemplate.create(((ThreadSubcomponent)sub));
                    _builder_1.append(_create);
                    _builder_1.newLineIfNotEmpty();
                    _switchResult_1 = _builder_1;
                  }
                  _builder.append(_switchResult_1, "\t");
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
                for(final Subcomponent sub_1 : _allSubcomponents_3) {
                  _builder.append("\t");
                  String _name = sub_1.getName();
                  _builder.append(_name, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.newLine();
                  _builder.append("\t");
                  _builder.append("\t");
                  CharSequence _switchResult_2 = null;
                  boolean _matched_2 = false;
                  if (sub_1 instanceof ThreadSubcomponent) {
                    _matched_2=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    _builder_1.append("if ( tsk_create(\"");
                    String _convert_4 = StringUtils.convert(((ThreadSubcomponent)sub_1).getClassifier().getName());
                    _builder_1.append(_convert_4);
                    _builder_1.append("\",");
                    Long _priority = PropertyParser.getPriority(((ThreadSubcomponent)sub_1));
                    _builder_1.append(_priority);
                    _builder_1.append(", 0,\"\", 0,(FUNCPTR)");
                    String _convert_5 = StringUtils.convert(((ThreadSubcomponent)sub_1).getClassifier().getName());
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
                    _switchResult_2 = _builder_1;
                  }
                  _builder.append(_switchResult_2, "\t\t");
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
          Tools.addContent(Template.systemheadfile, ProcessTemplate1.init);
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
                for(final Subcomponent sub_2 : _allSubcomponents_5) {
                  _builder.append("\t");
                  CharSequence _switchResult_3 = null;
                  boolean _matched_3 = false;
                  if (sub_2 instanceof ThreadSubcomponent) {
                    _matched_3=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    String _convert_5 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_1 = ("int " + _convert_5);
                    String _plus_2 = (_plus_1 + "_time;");
                    Tools.addContent(Template.systemheadfile, _plus_2);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_6 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_3 = ("short " + _convert_6);
                    String _plus_4 = (_plus_3 + "_finish;");
                    Tools.addContent(Template.systemheadfile, _plus_4);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_7 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_5 = ("int " + _convert_7);
                    String _plus_6 = (_plus_5 + "_failure_count;");
                    Tools.addContent(Template.systemheadfile, _plus_6);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_8 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_7 = ("int " + _convert_8);
                    String _plus_8 = (_plus_7 + "_begin;");
                    Tools.addContent(Template.systemheadfile, _plus_8);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_9 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_9 = ("int " + _convert_9);
                    String _plus_10 = (_plus_9 + "_end;");
                    Tools.addContent(Template.systemheadfile, _plus_10);
                    _builder_1.newLineIfNotEmpty();
                    String _convert_10 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_11 = (_convert_10 + "_time");
                    _builder_1.append(_plus_11);
                    _builder_1.append("=");
                    int _period_1 = PropertyParser.getPeriod(((ThreadSubcomponent)sub_2));
                    _builder_1.append(_period_1);
                    _builder_1.append(";");
                    _builder_1.newLineIfNotEmpty();
                    String _convert_11 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_12 = (_convert_11 + "_finish");
                    _builder_1.append(_plus_12);
                    _builder_1.append("=1;");
                    _builder_1.newLineIfNotEmpty();
                    String _convert_12 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                    String _plus_13 = (_convert_12 + "_failure_count");
                    _builder_1.append(_plus_13);
                    _builder_1.append("=1;");
                    _builder_1.newLineIfNotEmpty();
                    {
                      List<Long> _time_Window = PropertyParser.getTime_Window(((ThreadSubcomponent)sub_2));
                      boolean _notEquals_5 = (!Objects.equal(_time_Window, null));
                      if (_notEquals_5) {
                        String _convert_13 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                        String _plus_14 = (_convert_13 + "_begin");
                        _builder_1.append(_plus_14);
                        _builder_1.append("=");
                        Long _get = PropertyParser.getTime_Window(((ThreadSubcomponent)sub_2)).get(0);
                        _builder_1.append(_get);
                        _builder_1.append(";");
                        _builder_1.newLineIfNotEmpty();
                        String _convert_14 = StringUtils.convert(((ThreadSubcomponent)sub_2).getClassifier().getName());
                        String _plus_15 = (_convert_14 + "_end");
                        _builder_1.append(_plus_15);
                        _builder_1.append("=");
                        Long _get_1 = PropertyParser.getTime_Window(((ThreadSubcomponent)sub_2)).get(1);
                        _builder_1.append(_get_1);
                        _builder_1.append(";");
                        _builder_1.newLineIfNotEmpty();
                      }
                    }
                    _switchResult_3 = _builder_1;
                  }
                  _builder.append(_switchResult_3, "\t");
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
          {
            if ((children != null)) {
              {
                for(final Element sub_3 : children) {
                  CharSequence _switchResult_4 = null;
                  boolean _matched_4 = false;
                  if (sub_3 instanceof DefaultAnnexSubclauseImpl) {
                    _matched_4=true;
                    StringConcatenation _builder_1 = new StringConcatenation();
                    Tools.addContent(Template.systemheadfile, "有个行为附件");
                    _builder_1.newLineIfNotEmpty();
                    {
                      EList<Element> _children = ((DefaultAnnexSubclauseImpl)sub_3).getChildren();
                      for(final Element sub2 : _children) {
                        CharSequence _switchResult_5 = null;
                        boolean _matched_5 = false;
                        if (sub2 instanceof BehaviorAnnexImpl) {
                          _matched_5=true;
                          StringConcatenation _builder_2 = new StringConcatenation();
                          CharSequence _frame_template4BV = ProcessTemplate1.frame_template4BV(((BehaviorAnnexImpl)sub2));
                          _builder_2.append(_frame_template4BV);
                          _builder_2.newLineIfNotEmpty();
                          _builder_2.newLine();
                          _builder_2.append("void BehaviorAnnex(){");
                          _builder_2.newLine();
                          CharSequence _frame_template4BS = ProcessTemplate1.frame_template4BS(((BehaviorAnnexImpl)sub2));
                          _builder_2.append(_frame_template4BS);
                          _builder_2.newLineIfNotEmpty();
                          _builder_2.append("}\t\t\t\t\t                ");
                          _builder_2.newLine();
                          _switchResult_5 = _builder_2;
                        }
                        _builder_1.append(_switchResult_5);
                        _builder_1.newLineIfNotEmpty();
                      }
                    }
                    _switchResult_4 = _builder_1;
                  }
                  _builder.append(_switchResult_4);
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.newLine();
          _builder.newLine();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence frame_template4BV(final BehaviorAnnexImpl ba) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Element> _children = ba.getChildren();
      for(final Element sub : _children) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (sub instanceof BehaviorVariable) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _template = ProcessTemplate1.template(((BehaviorVariable)sub));
          _builder_1.append(_template);
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("enum State{");
    String _clearspace = StringUtils.clearspace(ProcessTemplate1.enum_template(ba).toString());
    _builder.append(_clearspace);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence enum_template(final BehaviorAnnexImpl ba) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Element> _children = ba.getChildren();
      for(final Element sub : _children) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (sub instanceof BehaviorState) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            boolean _isInitial = ((BehaviorState)sub).isInitial();
            if (_isInitial) {
              String _name = ((BehaviorState)sub).getName();
              _builder_1.append(_name);
            } else {
              _builder_1.append(" , ");
              String _name_1 = ((BehaviorState)sub).getName();
              _builder_1.append(_name_1);
            }
          }
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence frame_template4BS(final BehaviorAnnexImpl ba) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("State current_state = initialState;");
    _builder.newLine();
    _builder.append("while(0){");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("switch(current_state){");
    _builder.newLine();
    {
      EList<Element> _children = ba.getChildren();
      for(final Element st : _children) {
        _builder.append("\t");
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (st instanceof BehaviorState) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("case ");
          String _name = ((BehaviorState)st).getName();
          _builder_1.append(_name);
          _builder_1.append(" : ");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          CharSequence _template = ProcessTemplate1.template(((BehaviorState)st));
          _builder_1.append(_template, "\t");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          {
            boolean _isFinal = ((BehaviorState)st).isFinal();
            if (_isFinal) {
              _builder_1.append("return \"finish\";");
            }
          }
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("break;");
          _builder_1.newLine();
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("break;\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence template(final BehaviorVariable bv) {
    StringConcatenation _builder = new StringConcatenation();
    DataClassifier BV = bv.getDataClassifier();
    _builder.newLineIfNotEmpty();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (BV instanceof DataImplementationImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      String _qualifiedName = ((DataImplementationImpl)BV).getQualifiedName();
      _builder_1.append(_qualifiedName);
      _builder_1.append("  ");
      String _name = bv.getName();
      _builder_1.append(_name);
      _builder_1.append(";");
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (BV instanceof DataTypeImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        String _qualifiedName = ((DataTypeImpl)BV).getQualifiedName();
        _builder_1.append(_qualifiedName);
        _builder_1.append("  ");
        String _name = bv.getName();
        _builder_1.append(_name);
        _builder_1.append(";");
        _switchResult = _builder_1;
      }
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence template(final BehaviorState bs) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<BehaviorTransition> _transitionWhereSrc = AadlBaVisitors.getTransitionWhereSrc(bs);
      for(final BehaviorTransition transition : _transitionWhereSrc) {
        {
          BehaviorState _sourceState = transition.getSourceState();
          boolean _equals = Objects.equal(bs, _sourceState);
          if (_equals) {
            CharSequence _template = ProcessTemplate1.template(transition);
            _builder.append(_template);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence getInitialStateEx(final BehaviorAnnexImpl ba) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("State initialState = ");
    String _name = ba.getInitialState().getName();
    _builder.append(_name);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence getFinalState(final BehaviorAnnexImpl ba) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Element> _children = ba.getChildren();
      for(final Element bs : _children) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (bs instanceof BehaviorState) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            boolean _isFinal = ((BehaviorState)bs).isFinal();
            if (_isFinal) {
              _builder_1.append("State finalState = ");
              String _name = ((BehaviorState)bs).getName();
              _builder_1.append(_name);
              _builder_1.append(";");
              _builder_1.newLineIfNotEmpty();
            }
          }
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence getFinalStateName(final BehaviorAnnexImpl ba) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Element> _children = ba.getChildren();
      for(final Element bs : _children) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (bs instanceof BehaviorState) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            boolean _isFinal = ((BehaviorState)bs).isFinal();
            if (_isFinal) {
              String _name = ((BehaviorState)bs).getName();
              _builder_1.append(_name);
              _builder_1.newLineIfNotEmpty();
            }
          }
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence template(final BehaviorTransition bt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if( ");
    String _clearspace = StringUtils.clearspace(ProcessTemplate1.condition_template(bt.getCondition()).toString());
    _builder.append(_clearspace);
    _builder.append(" ){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _actionblock_template = ProcessTemplate1.actionblock_template(bt.getActionBlock());
    _builder.append(_actionblock_template, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("current_state = ");
    String _name = bt.getDestinationState().getName();
    _builder.append(_name, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence condition_template(final BehaviorCondition bc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((bc != null)) {
        BehaviorCondition BC = bc;
        _builder.newLineIfNotEmpty();
        boolean tag = false;
        _builder.newLineIfNotEmpty();
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (BC instanceof ValueExpressionImpl) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            boolean _isSetLogicalOperators = ((ValueExpressionImpl)BC).isSetLogicalOperators();
            if (_isSetLogicalOperators) {
              EList<LogicalOperator> logic = ((ValueExpressionImpl)BC).getLogicalOperators();
              _builder_1.newLineIfNotEmpty();
              final EList<LogicalOperator> _converted_logic = (EList<LogicalOperator>)logic;
              int logic_index = ((Object[])Conversions.unwrapArray(_converted_logic, Object.class)).length;
              _builder_1.newLineIfNotEmpty();
              int index = 0;
              _builder_1.newLineIfNotEmpty();
              {
                EList<Relation> _relations = ((ValueExpressionImpl)BC).getRelations();
                for(final Relation condition : _relations) {
                  {
                    String _string = condition.getFirstExpression().getTerms().get(0).getFactors().get(0).getUnaryBooleanOperator().toString();
                    boolean _equals = Objects.equal(_string, "!");
                    if (_equals) {
                      _builder_1.append("!(");
                      CharSequence _conditionChildren_template = ProcessTemplate1.conditionChildren_template(condition);
                      _builder_1.append(_conditionChildren_template);
                      _builder_1.append(")");
                      _builder_1.newLineIfNotEmpty();
                    } else {
                      _builder_1.append("(");
                      CharSequence _conditionChildren_template_1 = ProcessTemplate1.conditionChildren_template(condition);
                      _builder_1.append(_conditionChildren_template_1);
                      _builder_1.append(")");
                      {
                        if ((((ValueExpressionImpl)BC).isSetLogicalOperators() && (index < logic_index))) {
                          int _plusPlus = index++;
                          CharSequence _translate_logOperator = ProcessTemplate1.translate_logOperator(logic.get(_plusPlus));
                          _builder_1.append(_translate_logOperator);
                        }
                      }
                      _builder_1.newLineIfNotEmpty();
                    }
                  }
                }
              }
            } else {
              {
                EList<Relation> _relations_1 = ((ValueExpressionImpl)BC).getRelations();
                for(final Relation condition_1 : _relations_1) {
                  {
                    String _string_1 = condition_1.getFirstExpression().getTerms().get(0).getFactors().get(0).getUnaryBooleanOperator().toString();
                    boolean _equals_1 = Objects.equal(_string_1, "!");
                    if (_equals_1) {
                      _builder_1.append("!(");
                      CharSequence _conditionChildren_template_2 = ProcessTemplate1.conditionChildren_template(condition_1);
                      _builder_1.append(_conditionChildren_template_2);
                      _builder_1.append(")");
                      _builder_1.newLineIfNotEmpty();
                    } else {
                      _builder_1.append("(");
                      CharSequence _conditionChildren_template_3 = ProcessTemplate1.conditionChildren_template(condition_1);
                      _builder_1.append(_conditionChildren_template_3);
                      _builder_1.append(")");
                      _builder_1.newLineIfNotEmpty();
                    }
                  }
                }
              }
            }
          }
          _switchResult = _builder_1;
        }
        if (!_matched) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("default ");
          Class<? extends BehaviorCondition> _class = BC.getClass();
          _builder_1.append(_class);
          _builder_1.append(" at processtemplate.xtend line 351");
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      } else {
        if ((bc == null)) {
          _builder.append("true");
          _builder.newLine();
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence conditionChildren_template(final Relation relation) {
    StringConcatenation _builder = new StringConcatenation();
    {
      SimpleExpression _secondExpression = relation.getSecondExpression();
      boolean _tripleNotEquals = (_secondExpression != null);
      if (_tripleNotEquals) {
      }
    }
    return _builder;
  }
  
  public static CharSequence actionblock_template(final BehaviorActionBlock bb) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _notEquals = (!Objects.equal(bb, null));
      if (_notEquals) {
        {
          EList<Element> _children = bb.getChildren();
          for(final Element bb_child : _children) {
            CharSequence _switchResult = null;
            boolean _matched = false;
            if (bb_child instanceof BehaviorActionSequenceImpl) {
              _matched=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              {
                EList<BehaviorAction> _actions = ((BehaviorActionSequenceImpl)bb_child).getActions();
                for(final BehaviorAction action : _actions) {
                  CharSequence _baAction_template = ProcessTemplate1.baAction_template(action);
                  _builder_1.append(_baAction_template);
                  _builder_1.newLineIfNotEmpty();
                }
              }
              _switchResult = _builder_1;
            }
            if (!_matched) {
              if (bb_child instanceof BehaviorTimeImpl) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append("time = ");
                IntegerValue _integerValue = ((BehaviorTimeImpl)bb_child).getIntegerValue();
                _builder_1.append(_integerValue);
                _builder_1.append(" ");
                String _name = ((BehaviorTimeImpl)bb_child).getUnit().getName();
                _builder_1.append(_name);
                _builder_1.append(" ;");
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            if (!_matched) {
              if (bb_child instanceof PortSendActionImpl) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _baAction_template = ProcessTemplate1.baAction_template(((BehaviorAction)bb_child));
                _builder_1.append(_baAction_template);
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            if (!_matched) {
              if (bb_child instanceof AssignmentActionImpl) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _baAction_template = ProcessTemplate1.baAction_template(((BehaviorAction)bb_child));
                _builder_1.append(_baAction_template);
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            _builder.append(_switchResult);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    {
      boolean _equals = Objects.equal(bb, null);
      if (_equals) {
        _builder.append("printf(\"Without actionBlock\");");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public static CharSequence target_template(final Target target) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (target instanceof BehaviorVariableHolderImpl) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _name = ((BehaviorVariableHolderImpl)target).getBehaviorVariable().getName();
      _builder.append(_name);
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (target instanceof DataSubcomponentHolderImpl) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((DataSubcomponentHolderImpl)target).getDataSubcomponent().getName();
        _builder.append(_name);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("target_template has no strategy for ");
      Class<? extends Target> _class = target.getClass();
      _builder.append(_class);
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    return _switchResult;
  }
  
  public static CharSequence baAction_template(final BehaviorAction baAction) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (baAction instanceof AssignmentActionImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        EList<Relation> _relations = ((AssignmentActionImpl)baAction).getValueExpression().getRelations();
        for(final Relation relation : _relations) {
          String _clearspace = StringUtils.clearspace(ProcessTemplate1.target_template(((AssignmentActionImpl)baAction).getTarget()).toString());
          _builder_1.append(_clearspace);
          _builder_1.append(" = ");
          String _clearspace_1 = StringUtils.clearspace(ProcessTemplate1.relation_template(relation).toString());
          _builder_1.append(_clearspace_1);
          _builder_1.append(";   //:=");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (baAction instanceof PortDequeueActionImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name = ((PortDequeueActionImpl)baAction).getPort().getPort().getName();
        _builder_1.append(_name);
        _builder_1.append(" = ");
        String _clearspace = StringUtils.clearspace(ProcessTemplate1.target_template(((PortDequeueActionImpl)baAction).getTarget()).toString());
        _builder_1.append(_clearspace);
        _builder_1.append(";   //?=");
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (baAction instanceof PortSendActionImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          EList<Relation> _relations = ((PortSendActionImpl)baAction).getValueExpression().getRelations();
          for(final Relation relation : _relations) {
            String _name = ((PortSendActionImpl)baAction).getPort().getPort().getName();
            _builder_1.append(_name);
            _builder_1.append(" = ");
            String _clearspace = StringUtils.clearspace(ProcessTemplate1.relation_template(relation).toString());
            _builder_1.append(_clearspace);
            _builder_1.append(";   //!=");
            _builder_1.newLineIfNotEmpty();
            CharSequence _relation_template2 = ProcessTemplate1.relation_template2(relation);
            _builder_1.append(_relation_template2);
            _builder_1.newLineIfNotEmpty();
          }
        }
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("baAction_template has no strategy for ");
      Class<? extends BehaviorAction> _class = baAction.getClass();
      _builder_1.append(_class);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("you can find information in BehaviorAction");
      _builder_1.newLine();
      _switchResult = _builder_1;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence relation_template(final Relation relation) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (relation instanceof RelationImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _translate_value1 = ProcessTemplate1.translate_value1(((RelationImpl)relation).getFirstExpression().getTerms().get(0).getFactors().get(0).getFirstValue());
      _builder_1.append(_translate_value1);
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    if (!_matched) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("relation_template has no strategy for ");
      Class<? extends Relation> _class = relation.getClass();
      _builder_1.append(_class);
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence relation_template2(final Relation relation) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (relation instanceof RelationImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        SimpleExpression _secondExpression = ((RelationImpl)relation).getSecondExpression();
        boolean _notEquals = (!Objects.equal(_secondExpression, null));
        if (_notEquals) {
          CharSequence _translate_value1 = ProcessTemplate1.translate_value1(((RelationImpl)relation).getSecondExpression().getTerms().get(0).getFactors().get(0).getFirstValue());
          _builder_1.append(_translate_value1);
          _builder_1.newLineIfNotEmpty();
        }
      }
      _switchResult = _builder_1;
    }
    if (!_matched) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("relation_template2 has no strategy for ");
      Class<? extends Relation> _class = relation.getClass();
      _builder_1.append(_class);
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence translate_value1(final Value value) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (value instanceof DataComponentReferenceImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      DataHolder cc1 = ((DataComponentReferenceImpl)value).getData().get(0);
      _builder_1.newLineIfNotEmpty();
      CharSequence _switchResult_1 = null;
      boolean _matched_1 = false;
      if (cc1 instanceof BehaviorVariableHolderImpl) {
        _matched_1=true;
        StringConcatenation _builder_2 = new StringConcatenation();
        String _name = ((BehaviorVariableHolderImpl)cc1).getBehaviorVariable().getName();
        _builder_2.append(_name);
        _switchResult_1 = _builder_2;
      }
      if (!_matched_1) {
        if (cc1 instanceof DataSubcomponentHolderImpl) {
          _matched_1=true;
          StringConcatenation _builder_2 = new StringConcatenation();
          String _name = ((DataSubcomponentHolderImpl)cc1).getDataSubcomponent().getName();
          _builder_2.append(_name);
          _switchResult_1 = _builder_2;
        }
      }
      if (!_matched_1) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("line 470");
        _builder_2.append(cc1);
        _switchResult_1 = _builder_2;
      }
      _builder_1.append(_switchResult_1);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append(".");
      _builder_1.newLine();
      DataHolder cc2 = ((DataComponentReferenceImpl)value).getData().get(1);
      _builder_1.newLineIfNotEmpty();
      CharSequence _switchResult_2 = null;
      boolean _matched_2 = false;
      if (cc2 instanceof BehaviorVariableHolderImpl) {
        _matched_2=true;
        StringConcatenation _builder_3 = new StringConcatenation();
        String _name = ((BehaviorVariableHolderImpl)cc2).getBehaviorVariable().getName();
        _builder_3.append(_name);
        _switchResult_2 = _builder_3;
      }
      if (!_matched_2) {
        if (cc2 instanceof DataSubcomponentHolderImpl) {
          _matched_2=true;
          StringConcatenation _builder_3 = new StringConcatenation();
          String _name = ((DataSubcomponentHolderImpl)cc2).getDataSubcomponent().getName();
          _builder_3.append(_name);
          _switchResult_2 = _builder_3;
        }
      }
      if (!_matched_2) {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("line 479 ");
        _builder_3.append(cc1);
        _switchResult_2 = _builder_3;
      }
      _builder_1.append(_switchResult_2);
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (value instanceof BehaviorVariableHolderImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name = ((BehaviorVariableHolderImpl)value).getBehaviorVariable().getName();
        _builder_1.append(_name);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (value instanceof BehaviorIntegerLiteralImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        long _value = ((BehaviorIntegerLiteralImpl)value).getValue();
        _builder_1.append(_value);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (value instanceof BehaviorBooleanLiteralImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        boolean _value = ((BehaviorBooleanLiteralImpl)value).getValue();
        _builder_1.append(_value);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (value instanceof BehaviorRealLiteralImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        double _value = ((BehaviorRealLiteralImpl)value).getValue();
        _builder_1.append(_value);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (value instanceof ValueExpressionImpl) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _condition_template = ProcessTemplate1.condition_template(((BehaviorCondition)value));
        _builder_1.append(_condition_template);
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(value);
      _builder_1.append("~~~~~~");
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence translate_value2(final Value value) {
    StringConcatenation _builder = new StringConcatenation();
    String _switchResult = null;
    boolean _matched = false;
    if (value instanceof DataComponentReferenceImpl) {
      _matched=true;
      String _xblockexpression = null;
      {
        DataHolder cc2 = ((DataComponentReferenceImpl)value).getData().get(1);
        String _switchResult_1 = null;
        boolean _matched_1 = false;
        if (cc2 instanceof BehaviorVariableHolderImpl) {
          _matched_1=true;
          _switchResult_1 = ((BehaviorVariableHolderImpl)cc2).getBehaviorVariable().getName();
        }
        if (!_matched_1) {
          if (cc2 instanceof DataSubcomponentHolderImpl) {
            _matched_1=true;
            _switchResult_1 = ((DataSubcomponentHolderImpl)cc2).getDataSubcomponent().getName();
          }
        }
        _xblockexpression = _switchResult_1;
      }
      _switchResult = _xblockexpression;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence translate_logOperator(final LogicalOperator logOperator) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    int _value = logOperator.getValue();
    switch (_value) {
      case 0:
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("~");
        _builder_1.newLine();
        _switchResult = _builder_1;
        break;
      case 1:
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("&&");
        _builder_2.newLine();
        _switchResult = _builder_2;
        break;
      case 2:
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("||");
        _builder_3.newLine();
        _switchResult = _builder_3;
        break;
      case 3:
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("XOR");
        _builder_4.newLine();
        _switchResult = _builder_4;
        break;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
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
