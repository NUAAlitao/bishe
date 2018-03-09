package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.DataTemplate;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.DataClassifier;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DirectionType;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.Feature;
import org.osate.aadl2.Parameter;

/**
 * Feature a
 * feature as method parameter while call method, eg: f(a);
 * feature as variable, eg: int a;
 * feature as variable in method defination,eg: int f(int a);
 */
@SuppressWarnings("all")
public class FeatureTemplate {
  public static CharSequence template(final Feature feature) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (feature instanceof DataPort) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = ((DataPort)feature).getDirection().equals(DirectionType.IN);
        if (_equals) {
          String _convert = StringUtils.convert(((DataPort)feature).getClassifier().getName());
          _builder.append(_convert);
          _builder.append(" ");
          String _convert_1 = StringUtils.convert(((DataPort)feature).getName());
          _builder.append(_convert_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_1 = ((DataPort)feature).getDirection().equals(DirectionType.OUT);
        if (_equals_1) {
        }
      }
      {
        boolean _equals_2 = ((DataPort)feature).getDirection().equals(DirectionType.IN_OUT);
        if (_equals_2) {
        }
      }
      _switchResult = _builder;
    }
    if (!_matched) {
      if (feature instanceof EventPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof EventDataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof Parameter) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t\t\t");
        _builder.newLine();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence asDefinations(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        String _clear = StringUtils.clear(FeatureTemplate.datadefination(feature).toString());
        _builder.append(_clear);
        _builder.newLineIfNotEmpty();
        {
          int _size = features.size();
          int _minus = (_size - 1);
          boolean _equals = feature.equals(features.get(_minus));
          boolean _not = (!_equals);
          if (_not) {
            CharSequence _asDefination = FeatureTemplate.asDefination(feature);
            String _clearspace = StringUtils.clearspace((_asDefination + ",").toString());
            _builder.append(_clearspace);
          }
        }
        _builder.newLineIfNotEmpty();
        {
          int _size_1 = features.size();
          int _minus_1 = (_size_1 - 1);
          boolean _equals_1 = feature.equals(features.get(_minus_1));
          if (_equals_1) {
            CharSequence _asDefination_1 = FeatureTemplate.asDefination(feature);
            _builder.append(_asDefination_1);
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence asDefination(final Feature feature) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (feature instanceof Parameter) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = ((Parameter)feature).getDirection().equals(DirectionType.IN);
        if (_equals) {
          String _convert = StringUtils.convert(((Parameter)feature).getClassifier().getName());
          _builder.append(_convert);
          _builder.append(" ");
          String _convert_1 = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert_1);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_1 = ((Parameter)feature).getDirection().equals(DirectionType.OUT);
        if (_equals_1) {
          String _convert_2 = StringUtils.convert(((Parameter)feature).getClassifier().getName());
          _builder.append(_convert_2);
          _builder.append(" *");
          String _convert_3 = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert_3);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_2 = ((Parameter)feature).getDirection().equals(DirectionType.IN_OUT);
        if (_equals_2) {
          _builder.append("\t");
          _builder.newLine();
        }
      }
      _switchResult = _builder;
    }
    if (!_matched) {
      if (feature instanceof EventPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof EventDataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof DataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _equals = ((DataPort)feature).getDirection().equals(DirectionType.IN);
          if (_equals) {
            String _convert = StringUtils.convert(((DataPort)feature).getClassifier().getName());
            _builder.append(_convert);
            _builder.append(" ");
            String _convert_1 = StringUtils.convert(((DataPort)feature).getName());
            _builder.append(_convert_1);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _equals_1 = ((DataPort)feature).getDirection().equals(DirectionType.OUT);
          if (_equals_1) {
            String _convert_2 = StringUtils.convert(((DataPort)feature).getClassifier().getName());
            _builder.append(_convert_2);
            _builder.append(" *");
            String _convert_3 = StringUtils.convert(((DataPort)feature).getName());
            _builder.append(_convert_3);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _equals_2 = ((DataPort)feature).getDirection().equals(DirectionType.IN_OUT);
          if (_equals_2) {
            _builder.append("\t");
            _builder.newLine();
          }
        }
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence asParameters(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        String _clear = StringUtils.clear(FeatureTemplate.datadefination(feature).toString());
        _builder.append(_clear);
        _builder.newLineIfNotEmpty();
        {
          int _size = features.size();
          int _minus = (_size - 1);
          boolean _equals = feature.equals(features.get(_minus));
          boolean _not = (!_equals);
          if (_not) {
            CharSequence _asParameter = FeatureTemplate.asParameter(feature);
            String _clearspace = StringUtils.clearspace((_asParameter + ",").toString());
            _builder.append(_clearspace);
          }
        }
        _builder.newLineIfNotEmpty();
        {
          int _size_1 = features.size();
          int _minus_1 = (_size_1 - 1);
          boolean _equals_1 = feature.equals(features.get(_minus_1));
          if (_equals_1) {
            CharSequence _asParameter_1 = FeatureTemplate.asParameter(feature);
            _builder.append(_asParameter_1);
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence asParameter(final Feature feature) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (feature instanceof Parameter) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = ((Parameter)feature).getDirection().equals(DirectionType.IN);
        if (_equals) {
          String _convert = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_1 = ((Parameter)feature).getDirection().equals(DirectionType.OUT);
        if (_equals_1) {
          String _convert_1 = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert_1);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_2 = ((Parameter)feature).getDirection().equals(DirectionType.IN_OUT);
        if (_equals_2) {
          String _convert_2 = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert_2);
          _builder.newLineIfNotEmpty();
        }
      }
      _switchResult = _builder;
    }
    if (!_matched) {
      if (feature instanceof EventPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof EventDataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof DataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence asVariables(final List<Feature> features) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Feature feature : features) {
        String _clear = StringUtils.clear(FeatureTemplate.datadefination(feature).toString());
        _builder.append(_clear);
        _builder.newLineIfNotEmpty();
        CharSequence _asVariable = FeatureTemplate.asVariable(feature);
        String _clearspace = StringUtils.clearspace((_asVariable + ";").toString());
        _builder.append(_clearspace);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence asVariable(final Feature feature) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (feature instanceof Parameter) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = ((Parameter)feature).getDirection().equals(DirectionType.IN);
        if (_equals) {
          String _convert = StringUtils.convert(((Parameter)feature).getClassifier().getName());
          _builder.append(_convert);
          _builder.append(" ");
          String _convert_1 = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert_1);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_1 = ((Parameter)feature).getDirection().equals(DirectionType.OUT);
        if (_equals_1) {
          String _convert_2 = StringUtils.convert(((Parameter)feature).getClassifier().getName());
          _builder.append(_convert_2);
          _builder.append(" *");
          String _convert_3 = StringUtils.convert(((Parameter)feature).getName());
          _builder.append(_convert_3);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals_2 = ((Parameter)feature).getDirection().equals(DirectionType.IN_OUT);
        if (_equals_2) {
          _builder.append("\t");
          _builder.newLine();
        }
      }
      _switchResult = _builder;
    }
    if (!_matched) {
      if (feature instanceof EventPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof EventDataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (feature instanceof DataPort) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _equals = ((DataPort)feature).getDirection().equals(DirectionType.IN);
          if (_equals) {
            String _convert = StringUtils.convert(((DataPort)feature).getClassifier().getName());
            _builder.append(_convert);
            _builder.append(" ");
            String _convert_1 = StringUtils.convert(((DataPort)feature).getName());
            _builder.append(_convert_1);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _equals_1 = ((DataPort)feature).getDirection().equals(DirectionType.OUT);
          if (_equals_1) {
            String _convert_2 = StringUtils.convert(((DataPort)feature).getClassifier().getName());
            _builder.append(_convert_2);
            _builder.append(" *");
            String _convert_3 = StringUtils.convert(((DataPort)feature).getName());
            _builder.append(_convert_3);
            _builder.newLineIfNotEmpty();
          }
        }
        {
          boolean _equals_2 = ((DataPort)feature).getDirection().equals(DirectionType.IN_OUT);
          if (_equals_2) {
            _builder.append("\t");
            _builder.newLine();
          }
        }
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence datadefination(final Feature feature) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _featuredata = FeatureTemplate.featuredata(feature.getClassifier());
    _builder.append(_featuredata);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence featuredata(final Classifier classifier) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (classifier instanceof DataClassifier) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _template = DataTemplate.template(((DataClassifier)classifier));
      _builder.append(_template);
      _switchResult = _builder;
    }
    return _switchResult;
  }
}
