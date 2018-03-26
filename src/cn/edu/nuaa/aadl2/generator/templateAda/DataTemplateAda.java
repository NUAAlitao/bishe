package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DataType;

@SuppressWarnings("all")
public class DataTemplateAda {
  /**
   * 处理系统实现下的数据子组件
   * @param folder 系统目录
   * @param systemName 系统名称
   * @param dataSubcomponents 数据子组件列表
   */
  public static void genSystemDataSubcomponent(final String folder, final String systemName, final List<DataSubcomponent> dataSubcomponents) {
    String _convert = StringUtils.convert(systemName);
    String _plus = (_convert + "_data.ads");
    Tools.createFile(folder, _plus, DataTemplateAda.systemDataSubcomponent(systemName, dataSubcomponents).toString());
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
        String _name = subcomponent.getName();
        _builder.append(_name);
        _builder.append(" : ");
        String _replace = ((DataType)data).getName().replace(".", "_");
        _builder.append(_replace);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
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
    String _convert = StringUtils.convert(systemName);
    _builder.append(_convert);
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
    String _convert_1 = StringUtils.convert(systemName);
    _builder.append(_convert_1);
    _builder.append("_data;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
