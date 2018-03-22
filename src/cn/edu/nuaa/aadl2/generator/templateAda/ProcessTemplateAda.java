package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.AnnexSubclauseTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ModeTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ThreadTemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.AnnexSubclause;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.Mode;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessType;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.ThreadSubcomponent;

@SuppressWarnings("all")
public class ProcessTemplateAda {
  /**
   * 处理系统实现下的进程子组件
   * @param parentFolder 系统实现目录路径
   * @param processSubcomponent 进程子组件
   */
  public static void genSystemProcessSubcomponent(final String parentFolder, final ProcessSubcomponent processSubcomponent) {
    String _replace = processSubcomponent.getName().toLowerCase().replace(".", "_");
    String _plus = ((parentFolder + "/process_") + _replace);
    Tools.folder(_plus);
    String _replace_1 = processSubcomponent.getName().toLowerCase().replace(".", "_");
    String _plus_1 = ((parentFolder + "/process_") + _replace_1);
    String _replace_2 = processSubcomponent.getName().replace(".", "_");
    String _plus_2 = (_replace_2 + ".adb");
    Tools.createFile(_plus_1, _plus_2, ProcessTemplateAda.template(processSubcomponent).toString());
  }
  
  public static CharSequence template(final ProcessSubcomponent processSubcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier process = processSubcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (process instanceof ProcessType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (process instanceof ProcessImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("with ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_Subprograms; use ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_Subprograms;");
          _builder.newLineIfNotEmpty();
          _builder.append("with ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_types; use ");
          _builder.append(TemplateAda.packageName);
          _builder.append("_types;");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("procedure ");
          String _replace = processSubcomponent.getName().replace(".", "_");
          _builder.append(_replace);
          _builder.append(" (");
          {
            int _size = ((ProcessImplementation)process).getAllFeatures().size();
            boolean _greaterThan = (_size > 0);
            if (_greaterThan) {
              String _clearspace = StringUtils.clearspace(FeatureTemplateAda.genProcessFeature(((ProcessImplementation)process).getAllFeatures()).toString());
              _builder.append(_clearspace);
            }
          }
          _builder.append(") is");
          _builder.newLineIfNotEmpty();
          {
            int _size_1 = ((ProcessImplementation)process).getOwnedDataSubcomponents().size();
            boolean _greaterThan_1 = (_size_1 > 0);
            if (_greaterThan_1) {
              _builder.append("\t");
              CharSequence _genProcessDataSubcomponent = DataTemplateAda.genProcessDataSubcomponent(((ProcessImplementation)process).getOwnedDataSubcomponents());
              _builder.append(_genProcessDataSubcomponent, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_2 = ((ProcessImplementation)process).getOwnedThreadSubcomponents().size();
            boolean _greaterThan_2 = (_size_2 > 0);
            if (_greaterThan_2) {
              _builder.append("\t");
              CharSequence _genProcessThreadSubcomponent = ThreadTemplateAda.genProcessThreadSubcomponent(((ProcessImplementation)process).getOwnedThreadSubcomponents());
              _builder.append(_genProcessThreadSubcomponent, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_3 = ((ProcessImplementation)process).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_3 = (_size_3 > 0);
            if (_greaterThan_3) {
              {
                EList<AnnexSubclause> _ownedAnnexSubclauses = ((ProcessImplementation)process).getOwnedAnnexSubclauses();
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
          {
            int _size_4 = ((ProcessImplementation)process).getOwnedModes().size();
            boolean _greaterThan_4 = (_size_4 > 0);
            if (_greaterThan_4) {
              _builder.append("\t");
              String _clearspace_1 = StringUtils.clearspace(ModeTemplateAda.genMode(((ProcessImplementation)process).getOwnedModes()).toString());
              _builder.append(_clearspace_1, "\t");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("current_mode : Modes;");
              _builder.newLine();
            }
          }
          _builder.append("begin");
          _builder.newLine();
          {
            int _size_5 = ((ProcessImplementation)process).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_5 = (_size_5 > 0);
            if (_greaterThan_5) {
              {
                EList<AnnexSubclause> _ownedAnnexSubclauses_1 = ((ProcessImplementation)process).getOwnedAnnexSubclauses();
                for(final AnnexSubclause annexSubclause_1 : _ownedAnnexSubclauses_1) {
                  _builder.append("\t");
                  String _clearspace_2 = StringUtils.clearspace(AnnexSubclauseTemplateAda.initBehaviorAnnexState(((DefaultAnnexSubclause) annexSubclause_1)).toString());
                  _builder.append(_clearspace_2, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  CharSequence _genBehaviorAnnexTransition = AnnexSubclauseTemplateAda.genBehaviorAnnexTransition(((DefaultAnnexSubclause) annexSubclause_1));
                  _builder.append(_genBehaviorAnnexTransition, "\t");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          {
            int _size_6 = ((ProcessImplementation)process).getOwnedModes().size();
            boolean _greaterThan_6 = (_size_6 > 0);
            if (_greaterThan_6) {
              _builder.append("\t");
              String _clearspace_3 = StringUtils.clearspace(ModeTemplateAda.initMode(((ProcessImplementation)process).getOwnedModes()).toString());
              _builder.append(_clearspace_3, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_7 = ((ProcessImplementation)process).getOwnedModeTransitions().size();
            boolean _greaterThan_7 = (_size_7 > 0);
            if (_greaterThan_7) {
              _builder.append("\t");
              CharSequence _genModeTransition = ModeTemplateAda.genModeTransition(((ProcessImplementation)process).getOwnedModeTransitions());
              _builder.append(_genModeTransition, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_8 = ((ProcessImplementation)process).getOwnedModes().size();
            boolean _greaterThan_8 = (_size_8 > 0);
            if (_greaterThan_8) {
              _builder.append("\t");
              CharSequence _dealProcessMode = ProcessTemplateAda.dealProcessMode(((ProcessImplementation)process).getOwnedModes(), ((ProcessImplementation)process).getAllSubcomponents());
              _builder.append(_dealProcessMode, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("end ");
          String _replace_1 = processSubcomponent.getName().replace(".", "_");
          _builder.append(_replace_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  /**
   * 根据进程所处的不同模式调用该模式下的子组件
   * @param modes 进程拥有的模式列表
   * @param subcomponents 进程的子组件列表
   */
  public static CharSequence dealProcessMode(final List<Mode> modes, final List<Subcomponent> subcomponents) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case surrent_mode is");
    _builder.newLine();
    {
      for(final Mode mode : modes) {
        _builder.append("when ");
        String _name = mode.getName();
        _builder.append(_name);
        _builder.append(" =>");
        _builder.newLineIfNotEmpty();
        {
          for(final Subcomponent subcomponent : subcomponents) {
            _builder.append("\t");
            CharSequence _switchResult = null;
            boolean _matched = false;
            if (subcomponent instanceof ThreadSubcomponent) {
              _matched=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              {
                boolean _contains = ((ThreadSubcomponent)subcomponent).getInModes().contains(mode);
                if (_contains) {
                  String _name_1 = ((ThreadSubcomponent)subcomponent).getName();
                  _builder_1.append(_name_1);
                  _builder_1.append("_task.Start();");
                  _builder_1.newLineIfNotEmpty();
                }
              }
              _switchResult = _builder_1;
            }
            _builder.append(_switchResult, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("end case;");
    _builder.newLine();
    return _builder;
  }
}
