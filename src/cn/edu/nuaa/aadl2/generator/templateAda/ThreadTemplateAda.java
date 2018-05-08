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
import org.osate.aadl2.Connection;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SubprogramCallSequence;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.ThreadType;

@SuppressWarnings("all")
public class ThreadTemplateAda {
  /**
   * 处理进程实现下的线程子组件
   * @param threadSubcomponents 线程子组件列表
   * @param connections 进程中的connection列表
   */
  public static CharSequence genProcessThreadSubcomponent(final List<ThreadSubcomponent> threadSubcomponents, final List<Connection> connections) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final ThreadSubcomponent threadSubcomponent : threadSubcomponents) {
        CharSequence _head = ThreadTemplateAda.head(threadSubcomponent, connections);
        _builder.append(_head);
        _builder.newLineIfNotEmpty();
        CharSequence _template = ThreadTemplateAda.template(threadSubcomponent);
        _builder.append(_template);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence head(final ThreadSubcomponent subcomponent, final List<Connection> connections) {
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
          String _convert = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert);
          _builder.append("(");
          String _formatParam = StringUtils.formatParam(StringUtils.clearspace(FeatureTemplateAda.genThreadFeature(((ThreadImplementation)thread).getAllFeatures(), connections, subcomponent.getName()).toString()));
          _builder.append(_formatParam);
          _builder.append(") is");
          _builder.newLineIfNotEmpty();
          _builder.append("end ");
          String _convert_1 = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("type access_");
          String _convert_2 = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert_2);
          _builder.append(" is access ");
          String _convert_3 = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert_3);
          _builder.append(";");
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
          String _convert = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert);
          _builder.append(" is");
          _builder.newLineIfNotEmpty();
          {
            int _size = ((ThreadImplementation)thread).getAllFeatures().size();
            boolean _greaterThan = (_size > 0);
            if (_greaterThan) {
              _builder.append("\t");
              CharSequence _genThreadInPortVar = FeatureTemplateAda.genThreadInPortVar(((ThreadImplementation)thread).getAllFeatures());
              _builder.append(_genThreadInPortVar, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_1 = ((ThreadImplementation)thread).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_1 = (_size_1 > 0);
            if (_greaterThan_1) {
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
            EList<SubprogramCallSequence> _ownedSubprogramCallSequences = ((ThreadImplementation)thread).getOwnedSubprogramCallSequences();
            for(final SubprogramCallSequence subprogramCallSequence : _ownedSubprogramCallSequences) {
              {
                EList<SubprogramCall> _ownedSubprogramCalls = subprogramCallSequence.getOwnedSubprogramCalls();
                for(final SubprogramCall subprogramCall : _ownedSubprogramCalls) {
                  _builder.append("\t");
                  _builder.append(TemplateAda.packageName, "\t");
                  _builder.append("_");
                  String _convertPoint = StringUtils.convertPoint(Tools.getCalledSubprogramName(subprogramCall.getCalledSubprogram().toString()));
                  _builder.append(_convertPoint, "\t");
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          {
            int _size_2 = ((ThreadImplementation)thread).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_2 = (_size_2 > 0);
            if (_greaterThan_2) {
              {
                EList<AnnexSubclause> _ownedAnnexSubclauses_1 = ((ThreadImplementation)thread).getOwnedAnnexSubclauses();
                for(final AnnexSubclause annexSubclause_1 : _ownedAnnexSubclauses_1) {
                  _builder.append("\t");
                  String _clearspace = StringUtils.clearspace(AnnexSubclauseTemplateAda.initBehaviorAnnexState(((DefaultAnnexSubclause) annexSubclause_1)).toString());
                  _builder.append(_clearspace, "\t");
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
          String _convert_1 = StringUtils.convert(((ThreadImplementation)thread).getName());
          _builder.append(_convert_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  /**
   * 将线程类型声明中的out和 in out端口生成为进程中的变量
   * @param threadSubcomponents 进程中所有线程子组件
   */
  public static CharSequence genThreadPortVar(final List<ThreadSubcomponent> threadSubcomponents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final ThreadSubcomponent threadSubcomponent : threadSubcomponents) {
        ComponentClassifier thread = threadSubcomponent.getClassifier();
        _builder.newLineIfNotEmpty();
        CharSequence _genThreadFeatureVarInProc = FeatureTemplateAda.genThreadFeatureVarInProc(thread.getAllFeatures(), threadSubcomponent.getName());
        _builder.append(_genThreadFeatureVarInProc);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 生成线程类型的访问类型的变量（进程中的所有子线程的访问类型）
   * @param threadSubcomponents 进程中的线程子组件列表
   */
  public static CharSequence genThreadAccessVar(final List<ThreadSubcomponent> threadSubcomponents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final ThreadSubcomponent threadSubcomponent : threadSubcomponents) {
        ComponentClassifier thread = threadSubcomponent.getClassifier();
        _builder.newLineIfNotEmpty();
        String _name = threadSubcomponent.getName();
        _builder.append(_name);
        _builder.append("_task : access_");
        String _convert = StringUtils.convert(thread.getName());
        _builder.append(_convert);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
