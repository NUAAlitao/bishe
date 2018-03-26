package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.DataAccess;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventPort;
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
    String _convert = StringUtils.convert(systemName);
    String _plus = (_convert + "_feature.ads");
    Tools.createFile(folderPath, _plus, FeatureTemplateAda.systemFeature(systemName, features).toString());
  }
  
  /**
   * 处理进程类型声明中的features
   * @param features 进程类型声明中的features列表
   */
  public static CharSequence genProcessFeature(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _size = features.size();
      int _minus = (_size - 1);
      List<Feature> _subList = features.subList(0, _minus);
      for(final Feature feature : _subList) {
        CharSequence _dealFeature = FeatureTemplateAda.dealFeature(feature);
        _builder.append(_dealFeature);
        _builder.append("; ");
        _builder.newLineIfNotEmpty();
      }
    }
    int _size_1 = features.size();
    int _minus_1 = (_size_1 - 1);
    CharSequence _dealFeature_1 = FeatureTemplateAda.dealFeature(features.get(_minus_1));
    _builder.append(_dealFeature_1);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * 处理线程类型声明中的features
   * @param features 线程类型声明中的features列表
   */
  public static CharSequence genThreadFeature(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _size = features.size();
      int _minus = (_size - 1);
      List<Feature> _subList = features.subList(0, _minus);
      for(final Feature feature : _subList) {
        CharSequence _dealFeature = FeatureTemplateAda.dealFeature(feature);
        _builder.append(_dealFeature);
        _builder.append("; ");
        _builder.newLineIfNotEmpty();
      }
    }
    int _size_1 = features.size();
    int _minus_1 = (_size_1 - 1);
    CharSequence _dealFeature_1 = FeatureTemplateAda.dealFeature(features.get(_minus_1));
    _builder.append(_dealFeature_1);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence dealFeature(final Feature feature) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = feature.getName();
    _builder.append(_name);
    _builder.append(" : ");
    _builder.newLineIfNotEmpty();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (feature instanceof DataPort) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _dealFeatureDirection = FeatureTemplateAda.dealFeatureDirection(((DataPort)feature));
      _builder_1.append(_dealFeatureDirection);
      _builder_1.append(" ");
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (feature instanceof EventPort) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _dealFeatureDirection = FeatureTemplateAda.dealFeatureDirection(((EventPort)feature));
        _builder_1.append(_dealFeatureDirection);
        _builder_1.append(" ");
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (feature instanceof EventDataPort) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _dealFeatureDirection = FeatureTemplateAda.dealFeatureDirection(((EventDataPort)feature));
        _builder_1.append(_dealFeatureDirection);
        _builder_1.append(" ");
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (feature instanceof DataAccess) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("access ");
        _builder_1.newLine();
        _switchResult = _builder_1;
      }
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    {
      Classifier _classifier = feature.getClassifier();
      boolean _tripleNotEquals = (_classifier != null);
      if (_tripleNotEquals) {
        String _convertPoint = StringUtils.convertPoint(feature.getClassifier().getName());
        _builder.append(_convertPoint);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("boolen");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public static CharSequence dealFeatureDirection(final DataPort portDirection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((portDirection.isIn() == true) && (portDirection.isOut() == true))) {
        _builder.append("in out ");
        _builder.newLine();
      } else {
        boolean _isIn = portDirection.isIn();
        boolean _equals = (_isIn == true);
        if (_equals) {
          _builder.append("in ");
          _builder.newLine();
        } else {
          boolean _isOut = portDirection.isOut();
          boolean _equals_1 = (_isOut == true);
          if (_equals_1) {
            _builder.append("out ");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence dealFeatureDirection(final EventPort portDirection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((portDirection.isIn() == true) && (portDirection.isOut() == true))) {
        _builder.append("in out ");
        _builder.newLine();
      } else {
        boolean _isIn = portDirection.isIn();
        boolean _equals = (_isIn == true);
        if (_equals) {
          _builder.append("in ");
          _builder.newLine();
        } else {
          boolean _isOut = portDirection.isOut();
          boolean _equals_1 = (_isOut == true);
          if (_equals_1) {
            _builder.append("out ");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence dealFeatureDirection(final EventDataPort portDirection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((portDirection.isIn() == true) && (portDirection.isOut() == true))) {
        _builder.append("in out ");
        _builder.newLine();
      } else {
        boolean _isIn = portDirection.isIn();
        boolean _equals = (_isIn == true);
        if (_equals) {
          _builder.append("in ");
          _builder.newLine();
        } else {
          boolean _isOut = portDirection.isOut();
          boolean _equals_1 = (_isOut == true);
          if (_equals_1) {
            _builder.append("out ");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence systemFeature(final String systemName, final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _convert = StringUtils.convert(systemName);
    _builder.append(_convert);
    _builder.append("_feature is");
    _builder.newLineIfNotEmpty();
    {
      for(final Feature feature : features) {
        _builder.append("\t");
        String _name = feature.getName();
        _builder.append(_name, "\t");
        _builder.append(" : ");
        {
          Classifier _classifier = feature.getClassifier();
          boolean _notEquals = (!Objects.equal(_classifier, null));
          if (_notEquals) {
            String _convertPoint = StringUtils.convertPoint(feature.getClassifier().getName());
            _builder.append(_convertPoint, "\t");
          }
        }
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("end ");
    String _convert_1 = StringUtils.convert(systemName);
    _builder.append(_convert_1);
    _builder.append("_feature;");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
