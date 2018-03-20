package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DataType;

@SuppressWarnings("all")
public class DataTemplateAda {
  public static CharSequence create(final DataSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _template = DataTemplateAda.template(subcomponent);
    _builder.append(_template);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * 处理系统实现下的数据子组件
   * @param folder 系统目录
   * @param systemName 系统名称
   * @param dataSubcomponents 数据子组件列表
   */
  public static void genSystemDataSubcomponent(final String folder, final String systemName, final List<DataSubcomponent> dataSubcomponents) {
    Tools.createFile(folder, (systemName + "_data.ads"), DataTemplateAda.systemDataSubcomponent(systemName, dataSubcomponents).toString());
  }
  
  /**
   * 处理进程实现下的数据子组件
   * @param dataSubcomponents 数据子组件列表
   */
  public static CharSequence genProcessDataSubcomponent(final List<DataSubcomponent> dataSubcomponents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final DataSubcomponent dataSubcomponent : dataSubcomponents) {
        CharSequence _template = DataTemplateAda.template(dataSubcomponent);
        _builder.append(_template);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence template(final DataSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier data = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (data instanceof DataType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
      if (!_matched) {
        if (data instanceof DataImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          String _name = subcomponent.getName();
          _builder.append(_name);
          _builder.append(" : ");
          String _replace = ((DataImplementation)data).getName().replace(".", "_");
          _builder.append(_replace);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence systemDataSubcomponent(final String systemName, final List<DataSubcomponent> dataSubcomponents) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("packege ");
    _builder.append(systemName);
    _builder.append("_data is");
    _builder.newLineIfNotEmpty();
    {
      for(final DataSubcomponent dataSubcomponent : dataSubcomponents) {
        _builder.append("\t");
        CharSequence _template = DataTemplateAda.template(dataSubcomponent);
        _builder.append(_template, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end ");
    _builder.append(systemName);
    _builder.append("_data;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
