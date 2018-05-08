package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.Connection;
import org.osate.aadl2.DataAccess;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.Feature;
import org.osate.aadl2.Port;

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
      for(final Feature feature : features) {
        CharSequence _dealFeature = FeatureTemplateAda.dealFeature(feature);
        _builder.append(_dealFeature);
        _builder.append("; ");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 处理线程类型声明中的输入输出端口，生成形参
   * @param features 线程类型声明中的features列表
   * @param connections 线程所在进程中的connections列表
   * @param threadName 线程子组件名称
   */
  public static CharSequence genThreadFeature(final List<Feature> features, final List<Connection> connections, final String threadName) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        CharSequence _dealThreadFeature = FeatureTemplateAda.dealThreadFeature(feature, FeatureTemplateAda.getConnection(connections, threadName, feature.getName()));
        _builder.append(_dealThreadFeature);
        _builder.append("; ");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 得到和子线程对应端口连接的连接对象
   * @param connections 连接列表
   * @param threadName 线程名称
   * @param featureName 线程的feature端口名称
   * @return 符合条件的connection对象或者null
   */
  public static Connection getConnection(final List<Connection> connections, final String threadName, final String featureName) {
    for (final Connection connection : connections) {
      {
        if ((((connection.getSource().getContext() != null) && connection.getSource().getContext().getName().equals(threadName)) && connection.getSource().getConnectionEnd().getName().equals(featureName))) {
          return connection;
        }
        if ((((connection.getDestination().getContext() != null) && connection.getDestination().getContext().getName().equals(threadName)) && connection.getDestination().getConnectionEnd().getName().equals(featureName))) {
          return connection;
        }
      }
    }
    return null;
  }
  
  public static CharSequence dealThreadFeature(final Feature feature, final Connection connection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((connection == null)) {
        String _println = InputOutput.<String>println("存在端口没有连接");
        _builder.append(_println);
        _builder.newLineIfNotEmpty();
      }
    }
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (feature instanceof DataPort) {
      _matched=true;
    }
    if (!_matched) {
      if (feature instanceof EventPort) {
        _matched=true;
      }
    }
    if (!_matched) {
      if (feature instanceof EventDataPort) {
        _matched=true;
      }
    }
    if (_matched) {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name = ((Port)feature).getName();
      _builder_1.append(_name);
      _builder_1.append("_temp : access ");
      String _name_1 = connection.getName();
      _builder_1.append(_name_1);
      _builder_1.append(".base");
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (feature instanceof DataAccess) {
        _matched=true;
        StringConcatenation _builder_2 = new StringConcatenation();
        String _name_2 = ((DataAccess)feature).getName();
        _builder_2.append(_name_2);
        _builder_2.append("_temp : access ");
        CharSequence _dealClassisfy = FeatureTemplateAda.dealClassisfy(feature);
        _builder_2.append(_dealClassisfy);
        _builder_2.newLineIfNotEmpty();
        _switchResult = _builder_2;
      }
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * 将线程的out和in out端口生成为进程中的变量
   * @param features 线程类型声明中的features列表
   * @param threadName 线程名称
   */
  public static CharSequence genThreadFeatureVarInProc(final List<Feature> features, final String threadName) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (feature instanceof DataPort) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            boolean _isOut = ((DataPort)feature).isOut();
            boolean _equals = (_isOut == true);
            if (_equals) {
              String _name = ((DataPort)feature).getName();
              _builder_1.append(_name);
              _builder_1.append("_");
              _builder_1.append(threadName);
              _builder_1.append(" : ");
              String _clearspace = StringUtils.clearspace(FeatureTemplateAda.dealClassisfy(feature).toString());
              _builder_1.append(_clearspace);
              _builder_1.append(";");
              _builder_1.newLineIfNotEmpty();
            }
          }
          _switchResult = _builder_1;
        }
        if (!_matched) {
          if (feature instanceof EventPort) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            {
              boolean _isOut = ((EventPort)feature).isOut();
              boolean _equals = (_isOut == true);
              if (_equals) {
                String _name = ((EventPort)feature).getName();
                _builder_1.append(_name);
                _builder_1.append("_");
                _builder_1.append(threadName);
                _builder_1.append(" : ");
                String _clearspace = StringUtils.clearspace(FeatureTemplateAda.dealClassisfy(feature).toString());
                _builder_1.append(_clearspace);
                _builder_1.append(";");
                _builder_1.newLineIfNotEmpty();
              }
            }
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (feature instanceof EventDataPort) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            {
              boolean _isOut = ((EventDataPort)feature).isOut();
              boolean _equals = (_isOut == true);
              if (_equals) {
                String _name = ((EventDataPort)feature).getName();
                _builder_1.append(_name);
                _builder_1.append("_");
                _builder_1.append(threadName);
                _builder_1.append(" : ");
                String _clearspace = StringUtils.clearspace(FeatureTemplateAda.dealClassisfy(feature).toString());
                _builder_1.append(_clearspace);
                _builder_1.append(";");
                _builder_1.newLineIfNotEmpty();
              }
            }
            _switchResult = _builder_1;
          }
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 处理线程类型声明中的输入输出端口，生成线程内的局部变量
   * @param features 线程类型声明中的features列表
   */
  public static CharSequence genThreadInPortVar(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (feature instanceof DataPort) {
          _matched=true;
        }
        if (!_matched) {
          if (feature instanceof EventPort) {
            _matched=true;
          }
        }
        if (!_matched) {
          if (feature instanceof EventDataPort) {
            _matched=true;
          }
        }
        if (_matched) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _name = ((Port)feature).getName();
          _builder_1.append(_name);
          _builder_1.append(" : ");
          String _clearspace = StringUtils.clearspace(FeatureTemplateAda.dealClassisfy(feature).toString());
          _builder_1.append(_clearspace);
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 在线程的entry函数中初始化输入端口生成的局部变量
   * @param features 线程类型声明中的features列表
   */
  public static CharSequence initThreadInPortVar(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (feature instanceof DataPort) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            if (((((DataPort)feature).isIn() == true) && (((DataPort)feature).isOut() == false))) {
              String _name = ((DataPort)feature).getName();
              _builder_1.append(_name);
              _builder_1.append(" := ");
              String _name_1 = ((DataPort)feature).getName();
              _builder_1.append(_name_1);
              _builder_1.append("_temp;");
              _builder_1.newLineIfNotEmpty();
            }
          }
          _switchResult = _builder_1;
        }
        if (!_matched) {
          if (feature instanceof EventPort) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            {
              if (((((EventPort)feature).isIn() == true) && (((EventPort)feature).isOut() == false))) {
                String _name = ((EventPort)feature).getName();
                _builder_1.append(_name);
                _builder_1.append(" := ");
                String _name_1 = ((EventPort)feature).getName();
                _builder_1.append(_name_1);
                _builder_1.append("_temp;");
                _builder_1.newLineIfNotEmpty();
              }
            }
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (feature instanceof EventDataPort) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            {
              if (((((EventDataPort)feature).isIn() == true) && (((EventDataPort)feature).isOut() == false))) {
                String _name = ((EventDataPort)feature).getName();
                _builder_1.append(_name);
                _builder_1.append(" := ");
                String _name_1 = ((EventDataPort)feature).getName();
                _builder_1.append(_name_1);
                _builder_1.append("_temp;");
                _builder_1.newLineIfNotEmpty();
              }
            }
            _switchResult = _builder_1;
          }
        }
        if (!_matched) {
          if (feature instanceof DataAccess) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            String _name = ((DataAccess)feature).getName();
            _builder_1.append(_name);
            _builder_1.append(" := ");
            String _name_1 = ((DataAccess)feature).getName();
            _builder_1.append(_name_1);
            _builder_1.append("_temp;");
            _builder_1.newLineIfNotEmpty();
            _switchResult = _builder_1;
          }
        }
        _builder.append(_switchResult);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence dealClassisfy(final Feature feature) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Classifier _classifier = feature.getClassifier();
      boolean _tripleNotEquals = (_classifier != null);
      if (_tripleNotEquals) {
        String _convertPoint = StringUtils.convertPoint(feature.getClassifier().getName());
        _builder.append(_convertPoint);
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("Boolean");
        _builder.newLine();
      }
    }
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
        _builder.append("Boolean");
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
          boolean _tripleNotEquals = (_classifier != null);
          if (_tripleNotEquals) {
            String _convertPoint = StringUtils.convertPoint(feature.getClassifier().getName());
            _builder.append(_convertPoint, "\t");
          } else {
            _builder.append("Boolean");
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
