package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.ComponentClassifier;
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
          _builder.append("task ");
          String _replace = subcomponent.getName().replace(".", "_");
          _builder.append(_replace);
          _builder.append("_task is");
          _builder.newLineIfNotEmpty();
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
                  String _convertPoint_1 = StringUtils.convertPoint(Tools.getCalledSubprogramName(subprogramCall.getCalledSubprogram().toString()));
                  _builder.append(_convertPoint_1, "\t");
                  _builder.append(";");
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
