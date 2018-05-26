package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DataType;
import org.osate.aadl2.PublicPackageSection;

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
  
  /**
   * 处理publicSection中的所有数据实现类型
   * @param folder 根目录路径
   * @param aadlPackage aadl包
   */
  public static void genDataType(final String folder, final AadlPackage aadlPackage) {
    ArrayList<DataImplementation> dataImpls = DataTemplateAda.getAllDataImplementation(aadlPackage.getPublicSection());
    int _size = dataImpls.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      Tools.createFile(folder, "data_type.ads", DataTemplateAda.dealDataComponent(dataImpls).toString());
    }
  }
  
  /**
   * 处理data implementation对象
   * @param datas publicSection下的所有数据实现组件
   */
  public static CharSequence dealDataComponent(final List<DataImplementation> datas) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package data_type is");
    _builder.newLine();
    {
      for(final DataImplementation data : datas) {
        _builder.append("type ");
        String _convertPoint = StringUtils.convertPoint(data.getName());
        _builder.append(_convertPoint);
        _builder.append(" is ");
        _builder.newLineIfNotEmpty();
        _builder.append("record");
        _builder.newLine();
        {
          EList<DataSubcomponent> _ownedDataSubcomponents = data.getOwnedDataSubcomponents();
          for(final DataSubcomponent subData : _ownedDataSubcomponents) {
            String _name = subData.getName();
            _builder.append(_name);
            _builder.append(" : ");
            String _clearspace = StringUtils.clearspace(DataTemplateAda.dealDataType(subData).toString());
            _builder.append(_clearspace);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("end record;");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("end data_type;");
    _builder.newLine();
    return _builder;
  }
  
  public static String dealDataType(final DataSubcomponent dataSubcomponent) {
    ComponentClassifier data = dataSubcomponent.getClassifier();
    return StringUtils.convertPoint(data.getName());
  }
  
  /**
   * 获得publicSection下的所有Data Implementation对象
   * @param publicSection
   * @return List<Classifier>
   */
  public static ArrayList<DataImplementation> getAllDataImplementation(final PublicPackageSection publicSection) {
    ArrayList<DataImplementation> result = new ArrayList<DataImplementation>();
    EList<Classifier> _ownedClassifiers = publicSection.getOwnedClassifiers();
    for (final Classifier classifier : _ownedClassifiers) {
      if ((classifier instanceof DataImplementation)) {
        result.add(((DataImplementation)classifier));
      }
    }
    return result;
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
    _builder.append("package ");
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
