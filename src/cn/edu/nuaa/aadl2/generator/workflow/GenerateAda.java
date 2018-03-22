package cn.edu.nuaa.aadl2.generator.workflow;

import cn.edu.nuaa.aadl2.generator.templateAda.DataTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.FeatureTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.ProcessTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.osate.aadl2.ProcessSubcomponent;
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
      GenerateAda.generateSystem(GenerateAda.adaFolder, system, null);
    }
  }
  
  /**
   * 处理系统实现组件
   * @param parentFolderPath 此系统目录的父目录路径
   * @param system 此系统实现实例
   * @param systemFolder 此系统目录名
   */
  public static void generateSystem(final String parentFolderPath, final SystemImplementation system, final String systemFolder) {
    String currentFolder = "system_";
    boolean _equals = Objects.equal(systemFolder, null);
    if (_equals) {
      String _currentFolder = currentFolder;
      String _convert = StringUtils.convert(system.getName());
      currentFolder = (_currentFolder + _convert);
    } else {
      String _currentFolder_1 = currentFolder;
      String _convert_1 = StringUtils.convert(systemFolder);
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
    int _size_1 = system.getAllFeatures().size();
    boolean _greaterThan_1 = (_size_1 > 0);
    if (_greaterThan_1) {
      FeatureTemplateAda.genSystemFeature(currentFolderPath, currentFolder, system.getAllFeatures());
    }
    int _size_2 = system.getOwnedDataSubcomponents().size();
    boolean _greaterThan_2 = (_size_2 > 0);
    if (_greaterThan_2) {
      DataTemplateAda.genSystemDataSubcomponent(currentFolderPath, currentFolder, system.getOwnedDataSubcomponents());
    }
    int _size_3 = system.getOwnedProcessSubcomponents().size();
    boolean _greaterThan_3 = (_size_3 > 0);
    if (_greaterThan_3) {
      EList<ProcessSubcomponent> _ownedProcessSubcomponents = system.getOwnedProcessSubcomponents();
      for (final ProcessSubcomponent processsubcomponent : _ownedProcessSubcomponents) {
        ProcessTemplateAda.genSystemProcessSubcomponent(currentFolderPath, processsubcomponent);
      }
    }
  }
}
