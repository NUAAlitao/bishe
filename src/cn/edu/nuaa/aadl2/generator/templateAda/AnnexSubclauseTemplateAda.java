package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import java.util.List;
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
import org.osate.ba.aadlba.BehaviorTransition;
import org.osate.ba.aadlba.BehaviorVariable;
import org.osate.ba.aadlba.BehaviorVariableHolder;
import org.osate.ba.aadlba.DataComponentReference;
import org.osate.ba.aadlba.DataHolder;
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
    _builder.append("type State is (");
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
    {
      EList<BehaviorState> _states = behaviorAnnex.getStates();
      for(final BehaviorState behaviorState : _states) {
        _builder.append("\t");
        _builder.append("when ");
        String _name = behaviorState.getName();
        _builder.append(_name, "\t");
        _builder.append(" =>");
        _builder.newLineIfNotEmpty();
        {
          List<BehaviorTransition> _transitionWhereSrc = AadlBaVisitors.getTransitionWhereSrc(behaviorState);
          for(final BehaviorTransition behaviorTransition : _transitionWhereSrc) {
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("if (");
            String _dealMultipleSpace = StringUtils.dealMultipleSpace(StringUtils.clearspace(AnnexSubclauseTemplateAda.dealBehaviorAnnexTransitionCondition(behaviorTransition).toString()));
            _builder.append(_dealMultipleSpace, "\t\t");
            _builder.append(") then");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("\t");
            CharSequence _dealBehaviorAnnexTransitionAction = AnnexSubclauseTemplateAda.dealBehaviorAnnexTransitionAction(behaviorTransition);
            _builder.append(_dealBehaviorAnnexTransitionAction, "\t\t\t");
            _builder.newLineIfNotEmpty();
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
                CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((PortSendAction)actionElement));
                _builder_1.append(_dealActionElement);
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
      }
    }
    return _builder;
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
            CharSequence _dealActionElement = AnnexSubclauseTemplateAda.dealActionElement(((PortSendAction)action));
            _builder_1.append(_dealActionElement);
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
    _builder.append(" = ");
    String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealValueExpression(portSendAction.getValueExpression()));
    _builder.append(_clearspace);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealActionElement(final PortDequeueAction portDequeueAction) {
    StringConcatenation _builder = new StringConcatenation();
    String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.dealActionTarget(portDequeueAction.getTarget()).toString());
    _builder.append(_clearspace);
    _builder.append(" = ");
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
    _builder.append(" = ");
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
        BehaviorCondition _condition_1 = behaviorTransition.getCondition();
        String _dealValueExpression = AnnexSubclauseTemplateAda.dealValueExpression(((ValueExpression) _condition_1));
        _builder.append(_dealValueExpression);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("True");
        _builder.newLine();
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
            CharSequence _dealBehaivorIntegerLiteral = AnnexSubclauseTemplateAda.dealBehaivorIntegerLiteral(((BehaviorIntegerLiteral) firstValue));
            _builder_1.append(_dealBehaivorIntegerLiteral);
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (firstValue instanceof BehaviorRealLiteral) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealBehaivorRealLiteral = AnnexSubclauseTemplateAda.dealBehaivorRealLiteral(((BehaviorRealLiteral) firstValue));
            _builder_1.append(_dealBehaivorRealLiteral);
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
                CharSequence _dealBehaivorIntegerLiteral = AnnexSubclauseTemplateAda.dealBehaivorIntegerLiteral(((BehaviorIntegerLiteral) secondValue));
                _builder_1.append(_dealBehaivorIntegerLiteral);
                _builder_1.newLineIfNotEmpty();
                _switchResult_1 = _builder_1;
              }
            }
            if (!_matched_1) {
              if (secondValue instanceof BehaviorRealLiteral) {
                _matched_1=true;
                StringConcatenation _builder_1 = new StringConcatenation();
                CharSequence _dealBehaivorRealLiteral = AnnexSubclauseTemplateAda.dealBehaivorRealLiteral(((BehaviorRealLiteral) secondValue));
                _builder_1.append(_dealBehaivorRealLiteral);
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
  
  public static CharSequence dealBehaivorIntegerLiteral(final BehaviorIntegerLiteral behaviorIntegerLiteral) {
    StringConcatenation _builder = new StringConcatenation();
    long _value = behaviorIntegerLiteral.getValue();
    _builder.append(_value);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealBehaivorRealLiteral(final BehaviorRealLiteral behaviorRealLiteral) {
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
