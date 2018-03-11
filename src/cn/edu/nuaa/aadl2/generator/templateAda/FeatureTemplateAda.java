package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Feature;

@SuppressWarnings("all")
public class FeatureTemplateAda {
  public static void genSystemFeature(final String folderPath, final String systemName, final List<Feature> features) {
    Tools.createFile(folderPath, (systemName + "_feature.ads"), FeatureTemplateAda.systemFeature(systemName, features).toString());
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
