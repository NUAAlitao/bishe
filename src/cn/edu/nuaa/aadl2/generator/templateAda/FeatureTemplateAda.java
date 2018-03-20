package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Feature;

@SuppressWarnings("all")
public class FeatureTemplateAda {
  /**
   * 处理系统类型声明中的features
   * @param folderPath 此系统目录路径
   * @param systemName 此系统名
   * @param features 系统类型声明中的features列表
   */
  public static void genSystemFeature(final String folderPath, final String systemName, final List<Feature> features) {
    Tools.createFile(folderPath, (systemName + "_feature.ads"), FeatureTemplateAda.systemFeature(systemName, features).toString());
  }
  
  /**
   * 处理进程类型声明中的features
   * @param features 进程类型声明中的features列表
   */
  public static CharSequence genProcessFeature(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        String _name = feature.getName();
        _builder.append(_name);
        _builder.append(" : ");
        String _convertPoint = StringUtils.convertPoint(feature.getClassifier().getName());
        _builder.append(_convertPoint);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence systemFeature(final String systemName, final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(systemName);
    _builder.append("_feature is");
    _builder.newLineIfNotEmpty();
    {
      for(final Feature feature : features) {
        _builder.append("\t");
        String _name = feature.getName();
        _builder.append(_name, "\t");
        _builder.append(" : ");
        String _convertPoint = StringUtils.convertPoint(feature.getClassifier().getName());
        _builder.append(_convertPoint, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end ");
    _builder.append(systemName);
    _builder.append("_feature;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
