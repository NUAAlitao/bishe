package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.Element;
import org.osate.ba.aadlba.AssignmentAction;
import org.osate.ba.aadlba.BehaviorAction;
import org.osate.ba.aadlba.BehaviorActionBlock;
import org.osate.ba.aadlba.BehaviorActionSequence;
import org.osate.ba.aadlba.BehaviorAnnex;
import org.osate.ba.aadlba.BehaviorBooleanLiteral;
import org.osate.ba.aadlba.BehaviorCondition;
import org.osate.ba.aadlba.BehaviorIntegerLiteral;
import org.osate.ba.aadlba.BehaviorRealLiteral;
import org.osate.ba.aadlba.BehaviorState;
import org.osate.ba.aadlba.BehaviorTime;
import org.osate.ba.aadlba.BehaviorTransition;
import org.osate.ba.aadlba.BehaviorVariable;
import org.osate.ba.aadlba.BehaviorVariableHolder;
import org.osate.ba.aadlba.DataComponentReference;
import org.osate.ba.aadlba.DataHolder;
import org.osate.ba.aadlba.DataPortHolder;
import org.osate.ba.aadlba.DispatchCondition;
import org.osate.ba.aadlba.DispatchConjunction;
import org.osate.ba.aadlba.DispatchTrigger;
import org.osate.ba.aadlba.DispatchTriggerCondition;
import org.osate.ba.aadlba.DispatchTriggerLogicalExpression;
import org.osate.ba.aadlba.EventDataPortHolder;
import org.osate.ba.aadlba.EventPortHolder;
import org.osate.ba.aadlba.IntegerValue;
import org.osate.ba.aadlba.LogicalOperator;
import org.osate.ba.aadlba.PortDequeueAction;
import org.osate.ba.aadlba.PortSendAction;
import org.osate.ba.aadlba.Relation;
import org.osate.ba.aadlba.RelationalOperator;
import org.osate.ba.aadlba.SimpleExpression;
import org.osate.ba.aadlba.Target;
import org.osate.ba.aadlba.UnaryBooleanOperator;
import org.osate.ba.aadlba.Value;
import org.osate.ba.aadlba.ValueExpression;
import org.osate.ba.aadlba.impl.BehaviorVariableHolderImpl;
import org.osate.ba.utils.AadlBaVisitors;

@SuppressWarnings("all")
public class AnnexSubclauseTemplateAda {
  public static CharSequence genBehaviorAnnexVarible(final DefaultAnnexSubclause defaultAnnexSubclause) {
    AnnexSubclause _parsedAnnexSubclause = defaultAnnexSubclause.getParsedAnnexSubclause();
    return AnnexSubclauseTemplateAda.dealBehaviorAnnexVariable(((BehaviorAnnex) _parsedAnnexSubclause));
  }
  
  public static String genBehaviorAnnexState(final DefaultAnnexSubclause defaultAnnexSubclause) {
    AnnexSubclause _parsedAnnexSubclause = defaultAnnexSubclause.getParsedAnnexSubclause();
    return StringUtils.clearspace(AnnexSubclauseTemplateAda.dealBehaviorAnnexState(((BehaviorAnnex) _parsedAnnexSubclause)).toString());
  }
  
  public static CharSequence genBehaviorAnnexTransition(final DefaultAnnexSubclause defaultAnnexSubclause) {
    AnnexSubclause _parsedAnnexSubclause = defaultAnnexSubclause.getParsedAnnexSubclause();
    return AnnexSubclauseTemplateAda.dealBehaviorAnnexTransition(((BehaviorAnnex) _parsedAnnexSubclause));
  }
  
  public static String initBehaviorAnnexState(final DefaultAnnexSubclause defaultAnnexSubclause) {
    AnnexSubclause _parsedAnnexSubclause = defaultAnnexSubclause.getParsedAnnexSubclause();
    return StringUtils.clearspace(AnnexSubclauseTemplateAda.dealInitState(((BehaviorAnnex) _parsedAnnexSubclause)).toString());
  }
  
  public static CharSequence dealInitState(final BehaviorAnnex behaviorAnnex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("current_state ");
    _builder.newLine();
    {
      EList<BehaviorState> _states = behaviorAnnex.getStates();
      for(final BehaviorState behaviorState : _states) {
        {
          boolean _isInitial = behaviorState.isInitial();
          boolean _equals = (_isInitial == true);
          if (_equals) {
            _builder.append(":= ");
            String _name = behaviorState.getName();
            _builder.append(_name);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence dealBehaviorAnnexVariable(final BehaviorAnnex behaviorAnnex) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<BehaviorVariable> _variables = behaviorAnnex.getVariables();
      for(final BehaviorVariable behaviorVariable : _variables) {
        String _name = behaviorVariable.getName();
        _builder.append(_name);
        _builder.append(" : ");
        String _convertPoint = StringUtils.convertPoint(behaviorVariable.getDataClassifier().getName());
        _builder.append(_convertPoint);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence dealBehaviorAnnexState(final BehaviorAnnex behaviorAnnex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("type States is (");
    _builder.newLine();
    {
      EList<BehaviorState> _states = behaviorAnnex.getStates();
      int _size = behaviorAnnex.getStates().size();
      int _minus = (_size - 1);
      List<BehaviorState> _subList = _states.subList(0, _minus);
      for(final BehaviorState behaviorState : _subList) {
        String _name = behaviorState.getName();
        _builder.append(_name);
        _builder.append(", ");
        _builder.newLineIfNotEmpty();
      }
    }
    EList<BehaviorState> _states_1 = behaviorAnnex.getStates();
    int _size_1 = behaviorAnnex.getStates().size();
    int _minus_1 = (_size_1 - 1);
    String _name_1 = _states_1.get(_minus_1).getName();
    _builder.append(_name_1);
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence dealBehaviorAnnexTransition(final BehaviorAnnex behaviorAnnex) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case current_state is");
    _builder.newLine();
    Map<BehaviorTransition, BehaviorTransition> isUsed = new HashMap<BehaviorTransition, BehaviorTransition>();
    _builder.newLineIfNotEmpty();
    {
      EList<BehaviorTransition> _transitions = behaviorAnnex.getTransitions();
      for(final BehaviorTransition behaviorTransition : _transitions) {
        {
          BehaviorTransition _get = isUsed.get(behaviorTransition);
          boolean _tripleEquals = (_get == null);
          if (_tripleEquals) {
            _builder.append("when ");
            String _name = behaviorTransition.getSourceState().getName();
            _builder.append(_name);
            _builder.append(" =>");
            _builder.newLineIfNotEmpty();
            {
              List<BehaviorTransition> _transitionWhereSrc = AadlBaVisitors.getTransitionWhereSrc(behaviorTransition.getSourceState());
              for(final BehaviorTransition behaviorTransition1 : _transitionWhereSrc) {
                {
                  BehaviorTransition _get_1 = isUsed.get(behaviorTransition1);
                  boolean _tripleEquals_1 = (_get_1 == null);
                  if (_tripleEquals_1) {
                    _builder.append("\t");
                    _builder.append("if (");
                    String _dealMultipleSpace = StringUtils.dealMultipleSpace(StringUtils.clearspace(AnnexSubclauseTemplateAda.dealBehaviorAnnexTransitionCondition(behaviorTransition1).toString()));
                    _builder.append(_dealMultipleSpace, "\t");
                    _builder.append(") then");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    CharSequence _dealBehaviorAnnexTransitionAction = AnnexSubclauseTemplateAda.dealBehaviorAnnexTransitionAction(behaviorTransition1);
                    _builder.append(_dealBehaviorAnnexTransitionAction, "\t\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("current_state := ");
                    String _name_1 = behaviorTransition1.getDestinationState().getName();
                    _builder.append(_name_1, "\t\t");
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("end if;");
                    _builder.newLine();
                    _builder.append("\t");
                    BehaviorTransition _put = isUsed.put(behaviorTransition1, behaviorTransition1);
                    _builder.append(_put, "\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("end case;");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence dealBehaviorAnnexTransitionAction(final BehaviorTransition behaviorTransition) {
    StringConcatenation _builder = new StringConcatenation();
    {
      BehaviorActionBlock _actionBlock = behaviorTransition.getActionBlock();
      boolean _tripleNotEquals = (_actionBlock != null);
      if (_tripleNotEquals) {
        BehaviorActionBlock actionBlock = behaviorTransition.getActionBlock();
        _builder.newLineIfNotEmpty();
        {
          EList<Element> _children = actionBlock.getChildren();
          for(final Element actionElement : _children) {
            CharSequence _switchResult = null;
            boolean _matched = false;
            if (actionElement instanceof BehaviorActionSequence) {
              _matched=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((BehaviorActionSequence)actionElement));
              _builder_1.append(_dealActionElement);
              _builder_1.newLineIfNotEmpty();
              _switchResult = _builder_1;
            }
            if (!_matched) {
              if (actionElement instanceof PortSendAction) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealActionElement(((PortSendAction)actionElement)).toString());
                _builder_1.append(_clearspace);
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            if (!_matched) {
              if (actionElement instanceof PortDequeueAction) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((PortDequeueAction)actionElement));
                _builder_1.append(_dealActionElement);
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            if (!_matched) {
              if (actionElement instanceof AssignmentAction) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((AssignmentAction)actionElement));
                _builder_1.append(_dealActionElement);
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            if (!_matched) {
              _switchResult = null;
            }
            _builder.append(_switchResult);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          BehaviorTime _timeout = actionBlock.getTimeout();
          boolean _tripleNotEquals_1 = (_timeout != null);
          if (_tripleNotEquals_1) {
            _builder.append("delay ");
            String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealActionTimeOut(actionBlock.getTimeout()).toString());
            _builder.append(_clearspace);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence dealActionTimeOut(final BehaviorTime behaviorTime) {
    CharSequence _xblockexpression = null;
    {
      float base = 0;
      String _name = behaviorTime.getUnit().getName();
      if (_name != null) {
        switch (_name) {
          case "ms":
            base = 0.001f;
            break;
          default:
            base = 1;
            break;
        }
      } else {
        base = 1;
      }
      CharSequence _switchResult_1 = null;
      IntegerValue _integerValue = behaviorTime.getIntegerValue();
      boolean _matched = false;
      if (_integerValue instanceof BehaviorIntegerLiteral) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        IntegerValue _integerValue_1 = behaviorTime.getIntegerValue();
        int _parseInt = Integer.parseInt(StringUtils.clearspace(AnnexSubclauseTemplateAda.dealBehaviorIntegerLiteral(((BehaviorIntegerLiteral) _integerValue_1)).toString()));
        String _string = Float.valueOf((_parseInt * base)).toString();
        _builder.append(_string);
        _builder.newLineIfNotEmpty();
        _switchResult_1 = _builder;
      }
      _xblockexpression = _switchResult_1;
    }
    return _xblockexpression;
  }
  
  public static CharSequence dealActionElement(final BehaviorActionSequence behaviorActionSequence) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<BehaviorAction> _actions = behaviorActionSequence.getActions();
      for(final BehaviorAction action : _actions) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (action instanceof BehaviorActionSequence) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          Object _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((BehaviorActionSequence)action));
          _builder_1.append(_dealActionElement);
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        if (!_matched) {
          if (action instanceof PortSendAction) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealActionElement(((PortSendAction)action)).toString());
            _builder_1.append(_clearspace);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (action instanceof PortDequeueAction) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((PortDequeueAction)action));
            _builder_1.append(_dealActionElement);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (action instanceof AssignmentAction) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((AssignmentAction)action));
            _builder_1.append(_dealActionElement);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          _switchResult = null;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence dealActionElement(final PortSendAction portSendAction) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = portSendAction.getPort().getElement().getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    {
      ValueExpression _valueExpression = portSendAction.getValueExpression();
      boolean _tripleNotEquals = (_valueExpression != null);
      if (_tripleNotEquals) {
        _builder.append(":=");
        String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealValueExpression(portSendAction.getValueExpression()));
        _builder.append(_clearspace);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(";");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence dealActionElement(final PortDequeueAction portDequeueAction) {
    StringConcatenation _builder = new StringConcatenation();
    String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealActionTarget(portDequeueAction.getTarget()).toString());
    _builder.append(_clearspace);
    _builder.append(" := ");
    String _name = portDequeueAction.getPort().getElement().getName();
    _builder.append(_name);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealActionElement(final AssignmentAction assignmentAction) {
    StringConcatenation _builder = new StringConcatenation();
    String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealActionTarget(assignmentAction.getTarget()).toString());
    _builder.append(_clearspace);
    _builder.append(" := ");
    String _clearspace_1 = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealValueExpression(assignmentAction.getValueExpression()));
    _builder.append(_clearspace_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealActionTarget(final Target target) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (target instanceof BehaviorVariableHolderImpl) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name = ((BehaviorVariableHolderImpl)target).getElement().getName();
      _builder_1.append(_name);
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealBehaviorAnnexTransitionCondition(final BehaviorTransition behaviorTransition) {
    StringConcatenation _builder = new StringConcatenation();
    {
      BehaviorCondition _condition = behaviorTransition.getCondition();
      boolean _tripleNotEquals = (_condition != null);
      if (_tripleNotEquals) {
        CharSequence _switchResult = null;
        BehaviorCondition _condition_1 = behaviorTransition.getCondition();
        boolean _matched = false;
        if (_condition_1 instanceof ValueExpression) {
          _matched=true;
          BehaviorCondition _condition_2 = behaviorTransition.getCondition();
          _switchResult = AnnexSubclauseTemplateAda.dealValueExpression(((ValueExpression) _condition_2));
        }
        if (!_matched) {
          if (_condition_1 instanceof DispatchCondition) {
            _matched=true;
            BehaviorCondition _condition_2 = behaviorTransition.getCondition();
            _switchResult = AnnexSubclauseTemplateAda.dealDispatchCondition(((DispatchCondition) _condition_2));
          }
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("True");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public static CharSequence dealDispatchCondition(final DispatchCondition dispatchCondition) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    DispatchTriggerCondition _dispatchTriggerCondition = dispatchCondition.getDispatchTriggerCondition();
    boolean _matched = false;
    if (_dispatchTriggerCondition instanceof DispatchTriggerLogicalExpression) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      DispatchTriggerCondition _dispatchTriggerCondition_1 = dispatchCondition.getDispatchTriggerCondition();
      CharSequence _dealDispatchTriggerLogicalExpression = AnnexSubclauseTemplateAda.dealDispatchTriggerLogicalExpression(((DispatchTriggerLogicalExpression) _dispatchTriggerCondition_1));
      _builder_1.append(_dealDispatchTriggerLogicalExpression);
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealDispatchTriggerLogicalExpression(final DispatchTriggerLogicalExpression logicalExpression) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<DispatchConjunction> _dispatchConjunctions = logicalExpression.getDispatchConjunctions();
      for(final DispatchConjunction dispatchConjunction : _dispatchConjunctions) {
        {
          EList<DispatchTrigger> _dispatchTriggers = dispatchConjunction.getDispatchTriggers();
          for(final DispatchTrigger dispatchTrigger : _dispatchTriggers) {
            CharSequence _switchResult = null;
            boolean _matched = false;
            if (dispatchTrigger instanceof EventDataPortHolder) {
              _matched=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              String _name = ((EventDataPortHolder)dispatchTrigger).getElement().getName();
              _builder_1.append(_name);
              _builder_1.newLineIfNotEmpty();
              _switchResult = _builder_1;
            }
            if (!_matched) {
              if (dispatchTrigger instanceof EventPortHolder) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                String _name = ((EventPortHolder)dispatchTrigger).getElement().getName();
                _builder_1.append(_name);
                _builder_1.newLineIfNotEmpty();
                _switchResult = _builder_1;
              }
            }
            if (!_matched) {
              if (dispatchTrigger instanceof DataPortHolder) {
                _matched=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                String _name = ((DataPortHolder)dispatchTrigger).getElement().getName();
                _builder_1.append(_name);
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
    return _builder;
  }
  
  public static String dealValueExpression(final ValueExpression valueExpression) {
    StringConcatenation _builder = new StringConcatenation();
    EList<Relation> relations = valueExpression.getRelations();
    _builder.newLineIfNotEmpty();
    EList<LogicalOperator> logicalOperators = valueExpression.getLogicalOperators();
    _builder.newLineIfNotEmpty();
    int i = 0;
    _builder.newLineIfNotEmpty();
    {
      for(final Relation relation : relations) {
        Value firstValue = relation.getFirstExpression().getTerms().get(0).getFactors().get(0).getFirstValue();
        _builder.newLineIfNotEmpty();
        UnaryBooleanOperator _unaryBooleanOperator = relation.getFirstExpression().getTerms().get(0).getFactors().get(0).getUnaryBooleanOperator();
        _builder.append(_unaryBooleanOperator);
        _builder.newLineIfNotEmpty();
        _builder.append(" ");
        _builder.append("(");
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (firstValue instanceof DataComponentReference) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealDataComponentReference(((DataComponentReference) firstValue)).toString());
          _builder_1.append(_clearspace);
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        if (!_matched) {
          if (firstValue instanceof BehaviorIntegerLiteral) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealBehaviorIntegerLiteral = AnnexSubclauseTemplateAda.dealBehaviorIntegerLiteral(((BehaviorIntegerLiteral) firstValue));
            _builder_1.append(_dealBehaviorIntegerLiteral);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (firstValue instanceof BehaviorRealLiteral) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealBehaviorRealLiteral = AnnexSubclauseTemplateAda.dealBehaviorRealLiteral(((BehaviorRealLiteral) firstValue));
            _builder_1.append(_dealBehaviorRealLiteral);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (firstValue instanceof BehaviorBooleanLiteral) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealBehaviorBooleanLiteral = AnnexSubclauseTemplateAda.dealBehaviorBooleanLiteral(((BehaviorBooleanLiteral) firstValue));
            _builder_1.append(_dealBehaviorBooleanLiteral);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (firstValue instanceof BehaviorVariableHolder) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealBehaviorVariableHolder = AnnexSubclauseTemplateAda.dealBehaviorVariableHolder(((BehaviorVariableHolder) firstValue));
            _builder_1.append(_dealBehaviorVariableHolder);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (firstValue instanceof EventDataPortHolder) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealEventDataPortHolder = AnnexSubclauseTemplateAda.dealEventDataPortHolder(((EventDataPortHolder) firstValue));
            _builder_1.append(_dealEventDataPortHolder);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (firstValue instanceof ValueExpression) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            String _dealValueExpression = AnnexSubclauseTemplateAda.dealValueExpression(((ValueExpression) firstValue));
            _builder_1.append(_dealValueExpression);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        _builder.append(_switchResult, " ");
        _builder.append(" ");
        RelationalOperator _relationalOperator = relation.getRelationalOperator();
        _builder.append(_relationalOperator, " ");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        {
          SimpleExpression _secondExpression = relation.getSecondExpression();
          boolean _tripleNotEquals = (_secondExpression != null);
          if (_tripleNotEquals) {
            Value secondValue = relation.getSecondExpression().getTerms().get(0).getFactors().get(0).getFirstValue();
            _builder.newLineIfNotEmpty();
            CharSequence _switchResult_1 = null;
            boolean _matched_1 = false;
            if (secondValue instanceof DataComponentReference) {
              _matched_1=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealDataComponentReference(((DataComponentReference) secondValue)).toString());
              _builder_1.append(_clearspace);
              _builder_1.newLineIfNotEmpty();
              _switchResult_1 = _builder_1;
            }
            if (!_matched_1) {
              if (secondValue instanceof BehaviorIntegerLiteral) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealBehaviorIntegerLiteral = AnnexSubclauseTemplateAda.dealBehaviorIntegerLiteral(((BehaviorIntegerLiteral) secondValue));
                _builder_1.append(_dealBehaviorIntegerLiteral);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            if (!_matched_1) {
              if (secondValue instanceof BehaviorRealLiteral) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealBehaviorRealLiteral = AnnexSubclauseTemplateAda.dealBehaviorRealLiteral(((BehaviorRealLiteral) secondValue));
                _builder_1.append(_dealBehaviorRealLiteral);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            if (!_matched_1) {
              if (secondValue instanceof BehaviorBooleanLiteral) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealBehaviorBooleanLiteral = AnnexSubclauseTemplateAda.dealBehaviorBooleanLiteral(((BehaviorBooleanLiteral) secondValue));
                _builder_1.append(_dealBehaviorBooleanLiteral);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            if (!_matched_1) {
              if (secondValue instanceof BehaviorVariableHolder) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealBehaviorVariableHolder = AnnexSubclauseTemplateAda.dealBehaviorVariableHolder(((BehaviorVariableHolder) secondValue));
                _builder_1.append(_dealBehaviorVariableHolder);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            if (!_matched_1) {
              if (secondValue instanceof EventDataPortHolder) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealEventDataPortHolder = AnnexSubclauseTemplateAda.dealEventDataPortHolder(((EventDataPortHolder) secondValue));
                _builder_1.append(_dealEventDataPortHolder);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            if (!_matched_1) {
              if (secondValue instanceof ValueExpression) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                String _dealValueExpression = AnnexSubclauseTemplateAda.dealValueExpression(((ValueExpression) secondValue));
                _builder_1.append(_dealValueExpression);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            _builder.append(_switchResult_1);
          }
        }
        _builder.append(") ");
        _builder.newLineIfNotEmpty();
        {
          int _size = logicalOperators.size();
          boolean _lessThan = (i < _size);
          if (_lessThan) {
            int _plusPlus = i++;
            LogicalOperator _get = logicalOperators.get(_plusPlus);
            _builder.append(_get);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public static CharSequence dealEventDataPortHolder(final EventDataPortHolder eventDataPortHolder) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = eventDataPortHolder.getElement().getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealDataComponentReference(final DataComponentReference dataComponentReference) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<DataHolder> _data = dataComponentReference.getData();
      int _size = dataComponentReference.getData().size();
      int _minus = (_size - 1);
      List<DataHolder> _subList = _data.subList(0, _minus);
      for(final DataHolder dataHolder : _subList) {
        String _name = dataHolder.getElement().getName();
        _builder.append(_name);
        _builder.append(".");
        _builder.newLineIfNotEmpty();
      }
    }
    EList<DataHolder> _data_1 = dataComponentReference.getData();
    int _size_1 = dataComponentReference.getData().size();
    int _minus_1 = (_size_1 - 1);
    String _name_1 = _data_1.get(_minus_1).getElement().getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealBehaviorIntegerLiteral(final BehaviorIntegerLiteral behaviorIntegerLiteral) {
    StringConcatenation _builder = new StringConcatenation();
    long _value = behaviorIntegerLiteral.getValue();
    _builder.append(_value);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealBehaviorRealLiteral(final BehaviorRealLiteral behaviorRealLiteral) {
    StringConcatenation _builder = new StringConcatenation();
    double _value = behaviorRealLiteral.getValue();
    _builder.append(_value);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealBehaviorBooleanLiteral(final BehaviorBooleanLiteral behaviorBooleanLiteral) {
    StringConcatenation _builder = new StringConcatenation();
    boolean _value = behaviorBooleanLiteral.getValue();
    _builder.append(_value);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealBehaviorVariableHolder(final BehaviorVariableHolder behaviorVariableHolder) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = behaviorVariableHolder.getElement().getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
