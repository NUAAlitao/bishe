package cn.edu.nuaa.aadl2.generator.workflow;

import cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ModeTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ProcessTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Mode;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.SystemSubcomponent;
import org.osate.aadl2.SystemSubcomponentType;

@SuppressWarnings("all")
public class GenerateAda {
  private static String adaFolder = "Ada_codes";
  
  public static void generate(final SystemImplementation system) {
    String _name = system.getName();
    boolean _notEquals = (!Objects.equal(_name, null));
    if (_notEquals) {
      Tools.folder(GenerateAda.adaFolder);
      TemplateAda.packageName = Tools.getPackageName(system.eContainer().eContainer().toString());
      TemplateAda.subprogramsFileName = (TemplateAda.packageName + "_Subprograms");
      GenerateAda.generateSystem(GenerateAda.adaFolder, system, system.getName());
    }
  }
  
  /**
   * 处理系统实现组件
   * @param parentFolderPath 此系统目录的父目录路径
   * @param system 此系统实现实例
   * @param systemName 此系统名称
   */
  public static void generateSystem(final String parentFolderPath, final SystemImplementation system, final String systemName) {
    String currentFolder = "system_";
    if ((systemName == null)) {
      String _currentFolder = currentFolder;
      String _convert = StringUtils.convert(system.getName());
      currentFolder = (_currentFolder + _convert);
    } else {
      String _currentFolder_1 = currentFolder;
      String _convert_1 = StringUtils.convert(systemName);
      currentFolder = (_currentFolder_1 + _convert_1);
    }
    String currentFolderPath = ((parentFolderPath + "/") + currentFolder);
    Tools.folder(currentFolderPath);
    int _size = system.getOwnedSystemSubcomponents().size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      EList<SystemSubcomponent> _ownedSystemSubcomponents = system.getOwnedSystemSubcomponents();
      for (final SystemSubcomponent systemsubcomponent : _ownedSystemSubcomponents) {
        {
          SystemSubcomponentType _systemSubcomponentType = systemsubcomponent.getSystemSubcomponentType();
          SystemImplementation systemImplementation = ((SystemImplementation) _systemSubcomponentType);
          GenerateAda.generateSystem(currentFolderPath, systemImplementation, systemsubcomponent.getName());
        }
      }
    }
    String _convert_2 = StringUtils.convert(systemName);
    String _plus = (_convert_2 + ".adb");
    Tools.createFile(currentFolderPath, _plus, GenerateAda.genSystemProcedure(system, systemName).toString());
    int _size_1 = system.getAllFeatures().size();
    boolean _greaterThan_1 = (_size_1 > 0);
    if (_greaterThan_1) {
      FeatureTemplateAda.genSystemFeature(currentFolderPath, systemName, system.getAllFeatures());
    }
    int _size_2 = system.getOwnedDataSubcomponents().size();
    boolean _greaterThan_2 = (_size_2 > 0);
    if (_greaterThan_2) {
      DataTemplateAda.genSystemDataSubcomponent(currentFolderPath, systemName, system.getOwnedDataSubcomponents());
    }
    int _size_3 = system.getOwnedProcessSubcomponents().size();
    boolean _greaterThan_3 = (_size_3 > 0);
    if (_greaterThan_3) {
      EList<ProcessSubcomponent> _ownedProcessSubcomponents = system.getOwnedProcessSubcomponents();
      for (final ProcessSubcomponent processsubcomponent : _ownedProcessSubcomponents) {
        ProcessTemplateAda.genSystemProcessSubcomponent(currentFolderPath, processsubcomponent, systemName);
      }
    }
  }
  
  /**
   * 生成系统主过程
   * @param system 系统实现实例
   * @param systemName 系统名称
   */
  public static CharSequence genSystemProcedure(final SystemImplementation system, final String systemName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("procedure ");
    String _convert = StringUtils.convert(systemName);
    _builder.append(_convert);
    _builder.append(" is");
    _builder.newLineIfNotEmpty();
    {
      EList<ProcessSubcomponent> _ownedProcessSubcomponents = system.getOwnedProcessSubcomponents();
      for(final ProcessSubcomponent processSubcomponent : _ownedProcessSubcomponents) {
        _builder.append("\t");
        _builder.append("procedure ");
        String _convert_1 = StringUtils.convert(processSubcomponent.getName());
        _builder.append(_convert_1, "\t");
        _builder.append(" is separate;");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      int _size = system.getOwnedModes().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("\t");
        String _clearspace = StringUtils.clearspace(ModeTemplateAda.genMode(system.getOwnedModes()).toString());
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
      int _size_1 = system.getOwnedModes().size();
      boolean _greaterThan_1 = (_size_1 > 0);
      if (_greaterThan_1) {
        _builder.append("\t");
        String _clearspace_1 = StringUtils.clearspace(ModeTemplateAda.initMode(system.getOwnedModes()).toString());
        _builder.append(_clearspace_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      int _size_2 = system.getOwnedModeTransitions().size();
      boolean _greaterThan_2 = (_size_2 > 0);
      if (_greaterThan_2) {
        _builder.append("\t");
        CharSequence _genModeTransition = ModeTemplateAda.genModeTransition(system.getOwnedModeTransitions());
        _builder.append(_genModeTransition, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      int _size_3 = system.getOwnedModes().size();
      boolean _greaterThan_3 = (_size_3 > 0);
      if (_greaterThan_3) {
        _builder.append("\t");
        CharSequence _dealSystemMode = GenerateAda.dealSystemMode(system.getOwnedModes(), system.getAllSubcomponents());
        _builder.append(_dealSystemMode, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end ");
    String _convert_2 = StringUtils.convert(systemName);
    _builder.append(_convert_2);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * 根据系统所处的不同模式调用该模式下的进程子组件
   * @param modes 系统拥有的模式列表
   * @param subcomponents 系统的子组件列表
   */
  public static CharSequence dealSystemMode(final List<Mode> modes, final List<Subcomponent> subcomponents) {
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
            if (subcomponent instanceof ProcessSubcomponent) {
              _matched=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              {
                if ((((ProcessSubcomponent)subcomponent).getInModes().contains(mode) || (((ProcessSubcomponent)subcomponent).getInModes().size() == 0))) {
                  String _convert = StringUtils.convert(((ProcessSubcomponent)subcomponent).getName());
                  _builder_1.append(_convert);
                  _builder_1.append(";");
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
