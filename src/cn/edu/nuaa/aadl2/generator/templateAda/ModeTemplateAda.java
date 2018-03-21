package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.AadlModeVisitors;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Mode;
import org.osate.aadl2.ModeTransition;
import org.osate.aadl2.ModeTransitionTrigger;

@SuppressWarnings("all")
public class ModeTemplateAda {
  public static CharSequence genMode(final List<Mode> modes) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("type Modes is(");
    _builder.newLine();
    {
      int _size = modes.size();
      int _minus = (_size - 1);
      List<Mode> _subList = modes.subList(0, _minus);
      for(final Mode mode : _subList) {
        String _name = mode.getName();
        _builder.append(_name);
        _builder.append(",");
        _builder.newLineIfNotEmpty();
      }
    }
    int _size_1 = modes.size();
    int _minus_1 = (_size_1 - 1);
    String _name_1 = modes.get(_minus_1).getName();
    _builder.append(_name_1);
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence initMode(final List<Mode> modes) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("current_mode ");
    _builder.newLine();
    {
      for(final Mode mode : modes) {
        {
          boolean _isInitial = mode.isInitial();
          boolean _equals = (_isInitial == true);
          if (_equals) {
            _builder.append(":= ");
            String _name = mode.getName();
            _builder.append(_name);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence genModeTransition(final List<ModeTransition> modeTransitions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case current_mode is");
    _builder.newLine();
    Map<ModeTransition, ModeTransition> isUsed = new HashMap<ModeTransition, ModeTransition>();
    _builder.newLineIfNotEmpty();
    {
      for(final ModeTransition modeTransition : modeTransitions) {
        {
          ModeTransition _get = isUsed.get(modeTransition);
          boolean _equals = Objects.equal(_get, null);
          if (_equals) {
            _builder.append("when ");
            String _name = modeTransition.getSource().getName();
            _builder.append(_name);
            _builder.append(" =>");
            _builder.newLineIfNotEmpty();
            {
              List<ModeTransition> _transitionsWhereSrc = AadlModeVisitors.getTransitionsWhereSrc(modeTransition.getSource(), modeTransitions);
              for(final ModeTransition modeTransition1 : _transitionsWhereSrc) {
                {
                  ModeTransition _get_1 = isUsed.get(modeTransition1);
                  boolean _equals_1 = Objects.equal(_get_1, null);
                  if (_equals_1) {
                    _builder.append("\t");
                    _builder.append("if (");
                    String _clearspace = StringUtils.clearspace(ModeTemplateAda.dealModeTransitionTrigger(modeTransition1.getOwnedTriggers()).toString());
                    _builder.append(_clearspace, "\t");
                    _builder.append(") then");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("\t");
                    _builder.append("current_mode := ");
                    String _name_1 = modeTransition1.getDestination().getName();
                    _builder.append(_name_1, "\t\t");
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("end if;");
                    _builder.newLine();
                    _builder.append("\t");
                    ModeTransition _put = isUsed.put(modeTransition1, modeTransition1);
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
  
  public static CharSequence dealModeTransitionTrigger(final List<ModeTransitionTrigger> modeTransitionTriggers) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final ModeTransitionTrigger modeTransitionTrigger : modeTransitionTriggers) {
        String _name = modeTransitionTrigger.getTriggerPort().getName();
        _builder.append(_name);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
