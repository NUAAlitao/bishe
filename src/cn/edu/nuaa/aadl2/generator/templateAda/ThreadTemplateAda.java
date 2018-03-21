package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.AnnexSubclauseTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SubprogramCallSequence;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.ThreadType;

@SuppressWarnings("all")
public class ThreadTemplateAda {
  public static CharSequence create(final ThreadSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _head = ThreadTemplateAda.head(subcomponent);
    _builder.append(_head);
    _builder.newLineIfNotEmpty();
    CharSequence _template = ThreadTemplateAda.template(subcomponent);
    _builder.append(_template);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * 处理进程实现下的线程子组件
   * @param threadSubcomponents 线程子组件列表
   */
  public static CharSequence genProcessThreadSubcomponent(final List<ThreadSubcomponent> threadSubcomponents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final ThreadSubcomponent threadSubcomponent : threadSubcomponents) {
        CharSequence _head = ThreadTemplateAda.head(threadSubcomponent);
        _builder.append(_head);
        _builder.newLineIfNotEmpty();
        CharSequence _template = ThreadTemplateAda.template(threadSubcomponent);
        _builder.append(_template);
        _builder.newLineIfNotEmpty();
      }
    }
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
        _switchResult = _builder;
      }
      if (!_matched) {
        if (thread instanceof ThreadImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("task type ");
          String _replace = subcomponent.getName().replace(".", "_");
          _builder.append(_replace);
          _builder.append("_task is");
          _builder.newLineIfNotEmpty();
          {
            int _size = subcomponent.getInModes().size();
            boolean _greaterThan = (_size > 0);
            if (_greaterThan) {
              _builder.append("\t");
              _builder.append("entry Start(");
              {
                int _size_1 = ((ThreadImplementation)thread).getAllFeatures().size();
                boolean _greaterThan_1 = (_size_1 > 0);
                if (_greaterThan_1) {
                  String _clearspace = StringUtils.clearspace(FeatureTemplateAda.genThreadFeature(((ThreadImplementation)thread).getAllFeatures()).toString());
                  _builder.append(_clearspace, "\t");
                }
              }
              _builder.append(");");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("end ");
          String _replace_1 = subcomponent.getName().replace(",", "_");
          _builder.append(_replace_1);
          _builder.append("_task;");
          _builder.newLineIfNotEmpty();
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
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (thread instanceof ThreadImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("task body ");
          String _convertPoint = StringUtils.convertPoint(subcomponent.getName());
          _builder.append(_convertPoint);
          _builder.append("_task is");
          _builder.newLineIfNotEmpty();
          {
            int _size = ((ThreadImplementation)thread).getOwnedAnnexSubclauses().size();
            boolean _greaterThan = (_size > 0);
            if (_greaterThan) {
              {
                EList<AnnexSubclause> _ownedAnnexSubclauses = ((ThreadImplementation)thread).getOwnedAnnexSubclauses();
                for(final AnnexSubclause annexSubclause : _ownedAnnexSubclauses) {
                  _builder.append("\t");
                  CharSequence _genBehaviorAnnexVarible = AnnexSubclauseTemplateAda.genBehaviorAnnexVarible(((DefaultAnnexSubclause) annexSubclause));
                  _builder.append(_genBehaviorAnnexVarible, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  String _genBehaviorAnnexState = AnnexSubclauseTemplateAda.genBehaviorAnnexState(((DefaultAnnexSubclause) annexSubclause));
                  _builder.append(_genBehaviorAnnexState, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.append("current_state : States;");
                  _builder.newLine();
                }
              }
            }
          }
          _builder.append("begin");
          _builder.newLine();
          {
            int _size_1 = subcomponent.getInModes().size();
            boolean _greaterThan_1 = (_size_1 > 0);
            if (_greaterThan_1) {
              _builder.append("\t");
              _builder.append("accept Start(");
              {
                int _size_2 = ((ThreadImplementation)thread).getAllFeatures().size();
                boolean _greaterThan_2 = (_size_2 > 0);
                if (_greaterThan_2) {
                  String _clearspace = StringUtils.clearspace(FeatureTemplateAda.genThreadFeature(((ThreadImplementation)thread).getAllFeatures()).toString());
                  _builder.append(_clearspace, "\t");
                }
              }
              _builder.append(") do");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("end Start;");
              _builder.newLine();
            }
          }
          {
            EList<SubprogramCallSequence> _ownedSubprogramCallSequences = ((ThreadImplementation)thread).getOwnedSubprogramCallSequences();
            for(final SubprogramCallSequence subprogramCallSequence : _ownedSubprogramCallSequences) {
              {
                EList<SubprogramCall> _ownedSubprogramCalls = subprogramCallSequence.getOwnedSubprogramCalls();
                for(final SubprogramCall subprogramCall : _ownedSubprogramCalls) {
                  _builder.append("\t");
                  _builder.append(TemplateAda.packageName, "\t");
                  _builder.append("_");
                  String _convertPoint_1 = StringUtils.convertPoint(Tools.getCalledSubprogramName(subprogramCall.getCalledSubprogram().toString()));
                  _builder.append(_convertPoint_1, "\t");
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          {
            int _size_3 = ((ThreadImplementation)thread).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_3 = (_size_3 > 0);
            if (_greaterThan_3) {
              {
                EList<AnnexSubclause> _ownedAnnexSubclauses_1 = ((ThreadImplementation)thread).getOwnedAnnexSubclauses();
                for(final AnnexSubclause annexSubclause_1 : _ownedAnnexSubclauses_1) {
                  _builder.append("\t");
                  String _clearspace_1 = StringUtils.clearspace(AnnexSubclauseTemplateAda.initBehaviorAnnexState(((DefaultAnnexSubclause) annexSubclause_1)).toString());
                  _builder.append(_clearspace_1, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  CharSequence _genBehaviorAnnexTransition = AnnexSubclauseTemplateAda.genBehaviorAnnexTransition(((DefaultAnnexSubclause) annexSubclause_1));
                  _builder.append(_genBehaviorAnnexTransition, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _builder.append("end ");
          String _replace = subcomponent.getName().replace(".", "_");
          _builder.append(_replace);
          _builder.append("_task;");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
}
