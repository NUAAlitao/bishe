package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.templateAda.AnnexSubclauseTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ConnectionTemplateAda;
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
import org.osate.aadl2.Connection;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.Mode;
import org.osate.aadl2.ModeTransition;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessType;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.ThreadSubcomponent;

@SuppressWarnings("all")
public class ProcessTemplateAda {
  private static String currentFolder = "";
  
  /**
   * 处理系统实现下的进程子组件
   * @param parentFolder 系统实现目录路径
   * @param processSubcomponent 进程子组件
   * @param systemName 系统名称
   */
  public static void genSystemProcessSubcomponent(final String parentFolder, final ProcessSubcomponent processSubcomponent, final String systemName) {
    TemplateAda.addLogMessage("进程", processSubcomponent.getName());
    String _convert = StringUtils.convert(processSubcomponent.getName());
    String _plus = ((parentFolder + "/process_") + _convert);
    ProcessTemplateAda.currentFolder = _plus;
    Tools.folder(ProcessTemplateAda.currentFolder);
    String _convert_1 = StringUtils.convert(systemName);
    String _plus_1 = (_convert_1 + "-");
    String _convert_2 = StringUtils.convert(processSubcomponent.getName());
    String _plus_2 = (_plus_1 + _convert_2);
    String _plus_3 = (_plus_2 + ".adb");
    Tools.createFile(ProcessTemplateAda.currentFolder, _plus_3, 
      ProcessTemplateAda.template(processSubcomponent, systemName).toString());
    Tools.createFile(ProcessTemplateAda.currentFolder, "base_protect.ads", TemplateAda.base_protect_ads);
    Tools.createFile(ProcessTemplateAda.currentFolder, "base_protect.adb", TemplateAda.base_protect_adb);
  }
  
  public static CharSequence template(final ProcessSubcomponent processSubcomponent, final String systemName) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier process = processSubcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (process instanceof ProcessType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (process instanceof ProcessImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("with Ada.Text_IO; use Ada.Text_IO;");
          _builder.newLine();
          _builder.append("with DataType; use DataType;");
          _builder.newLine();
          _builder.append("with protected_types; use protected_types;");
          _builder.newLine();
          _builder.newLine();
          _builder.append("separate(");
          String _convert = StringUtils.convert(systemName);
          _builder.append(_convert);
          _builder.append(")");
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
              String _formatParam = StringUtils.formatParam(StringUtils.clearspace(FeatureTemplateAda.genProcessFeature(((ProcessImplementation)process).getAllFeatures()).toString()));
              _builder.append(_formatParam);
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
              CharSequence _genProcessThreadSubcomponent = ThreadTemplateAda.genProcessThreadSubcomponent(((ProcessImplementation)process).getOwnedThreadSubcomponents(), ((ProcessImplementation)process).getOwnedConnections());
              _builder.append(_genProcessThreadSubcomponent, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_3 = ((ProcessImplementation)process).getOwnedConnections().size();
            boolean _greaterThan_3 = (_size_3 > 0);
            if (_greaterThan_3) {
              _builder.append("\t");
              Tools.createFile(ProcessTemplateAda.currentFolder, "protected_types.ads", ConnectionTemplateAda.genProtectType(((ProcessImplementation)process).getOwnedConnections()).toString());
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              CharSequence _genProtectObject = ConnectionTemplateAda.genProtectObject(((ProcessImplementation)process).getOwnedConnections());
              _builder.append(_genProtectObject, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_4 = ((ProcessImplementation)process).getOwnedThreadSubcomponents().size();
            boolean _greaterThan_4 = (_size_4 > 0);
            if (_greaterThan_4) {
              _builder.append("\t");
              CharSequence _genThreadAccessVar = ThreadTemplateAda.genThreadAccessVar(((ProcessImplementation)process).getOwnedThreadSubcomponents());
              _builder.append(_genThreadAccessVar, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_5 = ((ProcessImplementation)process).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_5 = (_size_5 > 0);
            if (_greaterThan_5) {
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
            int _size_6 = ((ProcessImplementation)process).getOwnedModes().size();
            boolean _greaterThan_6 = (_size_6 > 0);
            if (_greaterThan_6) {
              _builder.append("\t");
              String _clearspace = StringUtils.clearspace(ModeTemplateAda.genMode(((ProcessImplementation)process).getOwnedModes()).toString());
              _builder.append(_clearspace, "\t");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("current_mode : Modes;");
              _builder.newLine();
            }
          }
          _builder.append("begin");
          _builder.newLine();
          {
            int _size_7 = ((ProcessImplementation)process).getOwnedAnnexSubclauses().size();
            boolean _greaterThan_7 = (_size_7 > 0);
            if (_greaterThan_7) {
              {
                EList<AnnexSubclause> _ownedAnnexSubclauses_1 = ((ProcessImplementation)process).getOwnedAnnexSubclauses();
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
          {
            int _size_8 = ((ProcessImplementation)process).getOwnedModes().size();
            boolean _greaterThan_8 = (_size_8 > 0);
            if (_greaterThan_8) {
              _builder.append("\t");
              String _clearspace_2 = StringUtils.clearspace(ModeTemplateAda.initMode(((ProcessImplementation)process).getOwnedModes()).toString());
              _builder.append(_clearspace_2, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_9 = ((ProcessImplementation)process).getOwnedModeTransitions().size();
            boolean _greaterThan_9 = (_size_9 > 0);
            if (_greaterThan_9) {
              _builder.append("\t");
              CharSequence _genProcessModeTransition = ModeTemplateAda.genProcessModeTransition(((ProcessImplementation)process).getOwnedModeTransitions(), ((ProcessImplementation)process).getOwnedConnections());
              _builder.append(_genProcessModeTransition, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          {
            int _size_10 = ((ProcessImplementation)process).getOwnedModes().size();
            boolean _greaterThan_10 = (_size_10 > 0);
            if (_greaterThan_10) {
              _builder.append("\t");
              CharSequence _dealProcessMode = ProcessTemplateAda.dealProcessMode(((ProcessImplementation)process).getOwnedModes(), ((ProcessImplementation)process).getAllSubcomponents(), ((ProcessImplementation)process).getOwnedConnections(), ((ProcessImplementation)process).getOwnedModeTransitions());
              _builder.append(_dealProcessMode, "\t");
              _builder.newLineIfNotEmpty();
            } else {
              _builder.append("\t");
              CharSequence _dealTaskCall = ProcessTemplateAda.dealTaskCall(((ProcessImplementation)process).getAllSubcomponents(), ((ProcessImplementation)process).getOwnedConnections());
              _builder.append(_dealTaskCall, "\t");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("end ");
          String _convert_1 = StringUtils.convert(processSubcomponent.getName());
          _builder.append(_convert_1);
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
   * @param connections 进程的连接列表
   */
  public static CharSequence dealProcessMode(final List<Mode> modes, final List<Subcomponent> subcomponents, final List<Connection> connections, final List<ModeTransition> modeTransitions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case current_mode is");
    _builder.newLine();
    {
      for(final Mode mode : modes) {
        _builder.append("when ");
        String _name = mode.getName();
        _builder.append(_name);
        _builder.append(" =>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _genModeTriggerSend = ModeTemplateAda.genModeTriggerSend(modeTransitions, connections, mode.getName());
        _builder.append(_genModeTriggerSend, "\t");
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
                if ((((ThreadSubcomponent)subcomponent).getInModes().contains(mode) || ((((ThreadSubcomponent)subcomponent).getInModes().size() == 0) && (((ThreadSubcomponent)subcomponent).getAllFeatures().size() > 0)))) {
                  String _convert = StringUtils.convert(((ThreadSubcomponent)subcomponent).getName());
                  _builder_1.append(_convert);
                  _builder_1.append("_task := new ");
                  String _convert_1 = StringUtils.convert(((ThreadSubcomponent)subcomponent).getClassifier().getName());
                  _builder_1.append(_convert_1);
                  _builder_1.append("(");
                  String _formatParam = StringUtils.formatParam(StringUtils.clearspace(ConnectionTemplateAda.genThreadConParam(connections, ((ThreadSubcomponent)subcomponent)).toString()));
                  _builder_1.append(_formatParam);
                  _builder_1.append(");");
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
  
  /**
   * 当进程没有模式变换时调用进程下的子组件
   * @param subcomponents 进程的子组件列表
   * @param connections 进程的连接列表
   */
  public static CharSequence dealTaskCall(final List<Subcomponent> subcomponents, final List<Connection> connections) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Subcomponent subcomponent : subcomponents) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (subcomponent instanceof ThreadSubcomponent) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            int _size = ((ThreadSubcomponent)subcomponent).getAllFeatures().size();
            boolean _greaterThan = (_size > 0);
            if (_greaterThan) {
              String _convert = StringUtils.convert(((ThreadSubcomponent)subcomponent).getName());
              _builder_1.append(_convert);
              _builder_1.append("_task := new ");
              String _convert_1 = StringUtils.convert(((ThreadSubcomponent)subcomponent).getClassifier().getName());
              _builder_1.append(_convert_1);
              _builder_1.append("(");
              String _formatParam = StringUtils.formatParam(StringUtils.clearspace(ConnectionTemplateAda.genThreadConParam(connections, ((ThreadSubcomponent)subcomponent)).toString()));
              _builder_1.append(_formatParam);
              _builder_1.append(");");
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
}
