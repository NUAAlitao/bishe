package cn.edu.nuaa.aadl2.generator.workflow;

import cn.edu.nuaa.aadl2.generator.template.SubcomponentTemplate;
import cn.edu.nuaa.aadl2.generator.template.Template;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SystemImplementation;

@SuppressWarnings("all")
public class Generate {
  private final static String CFolder = "C_codes";
  
  public static CharSequence generate(final SystemImplementation system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    {
      String _name = system.getName();
      boolean _notEquals = (!Objects.equal(_name, null));
      if (_notEquals) {
        Tools.folder(Generate.CFolder);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        String _convert = StringUtils.convert(system.getName());
        String _plus = ((Generate.CFolder + "/") + _convert);
        Tools.folder(_plus);
        _builder.newLineIfNotEmpty();
        _builder.append("//在工程下创建名为 system_name 的文件夹");
        _builder.newLine();
        String _convert_1 = StringUtils.convert(system.getName());
        String _plus_1 = ((Generate.CFolder + "/") + _convert_1);
        String _systemfolder = Template.systemfolder = _plus_1;
        _builder.append(_systemfolder);
        _builder.newLineIfNotEmpty();
        _builder.append("//设置生成代码系统的文件夹名");
        _builder.newLine();
        String _convert_2 = StringUtils.convert(system.getName());
        String _plus_2 = (_convert_2 + ".h");
        Tools.createFile(_plus_2, Template.dhead);
        _builder.newLineIfNotEmpty();
        _builder.append("//在 system_name 文件夹中生成名为system_name.h的文件，内容来自Template中的dhead");
        _builder.newLine();
        {
          if (Template.isMeta) {
            String _convert_3 = StringUtils.convert(system.getName());
            String _plus_3 = ((Generate.CFolder + "/") + _convert_3);
            String _meta_systemfolder = Template.meta_systemfolder = _plus_3;
            _builder.append(_meta_systemfolder);
            _builder.newLineIfNotEmpty();
            String _convert_4 = StringUtils.convert(system.getName());
            String _plus_4 = (_convert_4 + ".h");
            String _meta_systemheadfile = Template.meta_systemheadfile = _plus_4;
            _builder.append(_meta_systemheadfile);
            _builder.newLineIfNotEmpty();
            _builder.append(Template.isMeta = false);
            _builder.newLineIfNotEmpty();
          }
        }
        String _convert_5 = StringUtils.convert(system.getName());
        String _plus_5 = (_convert_5 + ".h");
        String _systemheadfile = Template.systemheadfile = _plus_5;
        _builder.append(_systemheadfile);
        _builder.newLineIfNotEmpty();
        _builder.append("//设置系统（C实现）的头文件（主？）system_name.h");
        _builder.newLine();
      }
    }
    {
      EList<Subcomponent> _allSubcomponents = system.getAllSubcomponents();
      boolean _notEquals_1 = (!Objects.equal(_allSubcomponents, null));
      if (_notEquals_1) {
        _builder.append("//如果systemImplementation对象拥有Subcomponent");
        _builder.newLine();
        {
          EList<Subcomponent> _allSubcomponents_1 = system.getAllSubcomponents();
          for(final Subcomponent subcomponent : _allSubcomponents_1) {
            _builder.append("\t");
            _builder.append("//遍历所有Subcomponent，通过Template生成对应代码");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            Object _template = SubcomponentTemplate.template(subcomponent);
            _builder.append(_template, "\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            System.out.println(subcomponent.getName());
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    return _builder;
  }
}
