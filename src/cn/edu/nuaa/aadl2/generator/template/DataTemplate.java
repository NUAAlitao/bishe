package cn.edu.nuaa.aadl2.generator.template;

import cn.edu.nuaa.aadl2.generator.template.Template;
import cn.edu.nuaa.aadl2.generator.utils.PropertyParser;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.DataClassifier;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DataType;

@SuppressWarnings("all")
public class DataTemplate {
  /**
   * base types in both c and AADL ,stored by hash set
   */
  public static List<String> basetype = Arrays.<String>asList("string", "Integer", "float", "double", "character");
  
  public static HashSet<String> baseTypes = new HashSet<String>(DataTemplate.basetype);
  
  public static CharSequence create(final DataSubcomponent subcomponent) {
    StringConcatenation _builder = new StringConcatenation();
    Tools.addContent(Template.systemheadfile, DataTemplate.template(subcomponent).toString());
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence template(final DataSubcomponent subcomponent) {
    CharSequence _xblockexpression = null;
    {
      ComponentClassifier data = subcomponent.getClassifier();
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (data instanceof DataClassifier) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _template = DataTemplate.template(((DataClassifier)data));
        _builder.append(_template);
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence template(final DataClassifier dc) {
    CharSequence _xblockexpression = null;
    {
      System.out.println(dc.getName());
      CharSequence _switchResult = null;
      boolean _matched = false;
      if (dc instanceof DataType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _equalsIgnoreCase = ((DataType)dc).getName().equalsIgnoreCase("Integer");
          if (_equalsIgnoreCase) {
            System.out.println("int");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("int");
            _builder.newLine();
          }
        }
        {
          boolean _equalsIgnoreCase_1 = ((DataType)dc).getName().equalsIgnoreCase("float");
          if (_equalsIgnoreCase_1) {
            _builder.append("float");
            _builder.newLine();
          }
        }
        {
          boolean _equalsIgnoreCase_2 = ((DataType)dc).getName().equalsIgnoreCase("long");
          if (_equalsIgnoreCase_2) {
            _builder.append("long");
            _builder.newLine();
          }
        }
        {
          boolean _equalsIgnoreCase_3 = ((DataType)dc).getName().equalsIgnoreCase("double");
          if (_equalsIgnoreCase_3) {
            _builder.append("double");
            _builder.newLine();
          }
        }
        {
          boolean _equalsIgnoreCase_4 = ((DataType)dc).getName().equalsIgnoreCase("character");
          if (_equalsIgnoreCase_4) {
            _builder.append("char");
            _builder.newLine();
          }
        }
        {
          boolean _equalsIgnoreCase_5 = ((DataType)dc).getName().equalsIgnoreCase("string");
          if (_equalsIgnoreCase_5) {
            _builder.append("string");
            _builder.newLine();
          }
        }
        {
          boolean _add = DataTemplate.baseTypes.add(((DataType)dc).getName().toLowerCase());
          if (_add) {
            {
              String _dataRepresentation = PropertyParser.getDataRepresentation(dc);
              boolean _tripleNotEquals = (_dataRepresentation != null);
              if (_tripleNotEquals) {
                {
                  boolean _equalsIgnoreCase_6 = PropertyParser.getDataRepresentation(dc).equalsIgnoreCase("struct");
                  if (_equalsIgnoreCase_6) {
                    System.out.println("addContent");
                    _builder.newLineIfNotEmpty();
                    Tools.addContent(Template.systemheadfile, DataTemplate.struct(dc).toString());
                    _builder.newLineIfNotEmpty();
                  }
                }
                {
                  boolean _equalsIgnoreCase_7 = PropertyParser.getDataRepresentation(dc).equalsIgnoreCase("union");
                  if (_equalsIgnoreCase_7) {
                    System.out.println("addContent");
                    _builder.newLineIfNotEmpty();
                    Tools.addContent(Template.systemheadfile, DataTemplate.union(dc).toString());
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
        _switchResult = _builder;
      }
      if (!_matched) {
        if (dc instanceof DataImplementation) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          {
            boolean _equalsIgnoreCase = ((DataImplementation)dc).getName().equalsIgnoreCase("integer");
            if (_equalsIgnoreCase) {
              System.out.println("int");
              _builder.newLineIfNotEmpty();
              _builder.append("int");
              _builder.newLine();
            }
          }
          {
            boolean _equalsIgnoreCase_1 = ((DataImplementation)dc).getName().equalsIgnoreCase("float");
            if (_equalsIgnoreCase_1) {
              _builder.append("float");
              _builder.newLine();
            }
          }
          {
            boolean _equalsIgnoreCase_2 = ((DataImplementation)dc).getName().equalsIgnoreCase("double");
            if (_equalsIgnoreCase_2) {
              _builder.append("double");
              _builder.newLine();
            }
          }
          {
            boolean _equalsIgnoreCase_3 = ((DataImplementation)dc).getName().equalsIgnoreCase("character");
            if (_equalsIgnoreCase_3) {
              _builder.append("char");
              _builder.newLine();
            }
          }
          {
            boolean _equalsIgnoreCase_4 = ((DataImplementation)dc).getName().equalsIgnoreCase("string");
            if (_equalsIgnoreCase_4) {
              _builder.append("string");
              _builder.newLine();
            }
          }
          {
            boolean _add = DataTemplate.baseTypes.add(((DataImplementation)dc).getName().toLowerCase());
            if (_add) {
              _builder.newLine();
              {
                boolean _equalsIgnoreCase_5 = PropertyParser.getDataRepresentation(dc).equalsIgnoreCase("struct");
                if (_equalsIgnoreCase_5) {
                  System.out.println("addContent");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  Tools.addContent(Template.systemheadfile, DataTemplate.struct(dc).toString());
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  String _string = DataTemplate.struct(dc).toString();
                  String _plus = (_string + "s");
                  System.out.println(_plus);
                  _builder.newLineIfNotEmpty();
                }
              }
              {
                boolean _equalsIgnoreCase_6 = PropertyParser.getDataRepresentation(dc).equalsIgnoreCase("union");
                if (_equalsIgnoreCase_6) {
                  System.out.println("addContent");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  Tools.addContent(Template.systemheadfile, DataTemplate.union(dc).toString());
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  String _string_1 = DataTemplate.union(dc).toString();
                  String _plus_1 = (_string_1 + "s");
                  System.out.println(_plus_1);
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
          _switchResult = _builder;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public static CharSequence struct(final DataClassifier dc) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (dc instanceof DataType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("typedef struct");
      _builder.newLine();
      _builder.append("{\t");
      _builder.newLine();
      _builder.append("\t");
      String _data = DataTemplate.data(PropertyParser.getBaseTypes(dc), PropertyParser.getElementNames(dc));
      _builder.append(_data, "\t");
      _builder.append("\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      String _convert = StringUtils.convert(((DataType)dc).getName());
      _builder.append(_convert);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (dc instanceof DataImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("typedef struct");
        _builder.newLine();
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        String _data = DataTemplate.data(PropertyParser.getBaseTypes(dc), PropertyParser.getElementNames(dc));
        _builder.append(_data, "\t");
        _builder.append("\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        String _convert = StringUtils.convert(((DataImplementation)dc).getName());
        _builder.append(_convert);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static CharSequence union(final DataClassifier dc) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (dc instanceof DataType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("typedef union");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      String _data = DataTemplate.data(PropertyParser.getBaseTypes(dc), PropertyParser.getElementNames(dc));
      _builder.append(_data, "\t");
      _builder.append("\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      String _convert = StringUtils.convert(((DataType)dc).getName());
      _builder.append(_convert);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _switchResult = _builder;
    }
    if (!_matched) {
      if (dc instanceof DataImplementation) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("typedef union");
        _builder.newLine();
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        String _data = DataTemplate.data(PropertyParser.getBaseTypes(dc), PropertyParser.getElementNames(dc));
        _builder.append(_data, "\t");
        _builder.append("\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        String _convert = StringUtils.convert(((DataImplementation)dc).getName());
        _builder.append(_convert);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _switchResult = _builder;
      }
    }
    return _switchResult;
  }
  
  public static String data(final List<DataClassifier> datas, final List<String> vars) {
    String result = "";
    int i = 0;
    int size = datas.size();
    for (i = 0; (i < size); i++) {
      String _replaceAll = DataTemplate.template(datas.get(i)).toString().replaceAll("\r|\n", "");
      String _plus = (result + _replaceAll);
      String _plus_1 = (_plus + " ");
      String _get = vars.get(i);
      String _plus_2 = (_plus_1 + _get);
      String _plus_3 = (_plus_2 + ";\n");
      result = _plus_3;
    }
    return result;
  }
}
