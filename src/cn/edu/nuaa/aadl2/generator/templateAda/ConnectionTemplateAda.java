package cn.edu.nuaa.aadl2.generator.templateAda;

import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.osate.aadl2.AccessConnection;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.Connection;
import org.osate.aadl2.ConnectionEnd;
import org.osate.aadl2.Context;
import org.osate.aadl2.DataAccess;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.PortConnection;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.ThreadSubcomponent;

@SuppressWarnings("all")
public class ConnectionTemplateAda {
  /**
   * 生成连接变量（当连接是组件内的两个子组件之间的连接时）
   * @param connections 当前组件下的所有连接
   */
  public static CharSequence genConnectionVar(final List<Connection> connections) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Connection connection : connections) {
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (connection instanceof PortConnection) {
          _matched=true;
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _dealPortConVar = ConnectionTemplateAda.dealPortConVar(((PortConnection)connection));
          _builder_1.append(_dealPortConVar);
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
        }
        if (!_matched) {
          if (connection instanceof AccessConnection) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _dealAccessConVar = ConnectionTemplateAda.dealAccessConVar(((AccessConnection)connection));
            _builder_1.append(_dealAccessConVar);
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
  
  /**
   * 根据组件之间的连接关系生成过程调用时的实参和形参的传递关系
   * @param connections 当前组件下的所有连接
   * @param componentName 当前组件的名称
   */
  public static CharSequence genConParam(final List<Connection> connections, final Subcomponent component) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Connection connection : connections) {
        {
          if (((connection.getSource().getContext() != null) && (connection.getDestination().getContext() != null))) {
            String _clearspace = StringUtils.clearspace(ConnectionTemplateAda.dealConParamTwoContext(connection, component).toString());
            _builder.append(_clearspace);
            _builder.newLineIfNotEmpty();
          } else {
            String _clearspace_1 = StringUtils.clearspace(ConnectionTemplateAda.dealConParamOneContext(connection, component).toString());
            _builder.append(_clearspace_1);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  /**
   * 处理当前组件端口和子组件端口之间的连接对应的参数传递关系
   * @param connection 连接对象
   * @param componentName 当前组件名称
   */
  public static String dealConParamOneContext(final Connection connection, final Subcomponent component) {
    if ((component instanceof ThreadSubcomponent)) {
      if (((connection.getDestination().getContext() != null) && connection.getDestination().getContext().getName().equals(((ThreadSubcomponent)component).getName()))) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = connection.getDestination().getConnectionEnd().getName();
        _builder.append(_name);
        _builder.append("=>");
        String _name_1 = connection.getSource().getConnectionEnd().getName();
        _builder.append(_name_1);
        _builder.append(",");
        return _builder.toString();
      }
    } else {
      Context _context = connection.getSource().getContext();
      boolean _tripleNotEquals = (_context != null);
      if (_tripleNotEquals) {
        boolean _equals = connection.getSource().getContext().getName().equals(component.getName());
        if (_equals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _name_2 = connection.getSource().getConnectionEnd().getName();
          _builder_1.append(_name_2);
          _builder_1.append("=>");
          String _name_3 = connection.getDestination().getConnectionEnd().getName();
          _builder_1.append(_name_3);
          _builder_1.append(",");
          return _builder_1.toString();
        }
      } else {
        boolean _equals_1 = connection.getDestination().getContext().getName().equals(component.getName());
        if (_equals_1) {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _name_4 = connection.getDestination().getConnectionEnd().getName();
          _builder_2.append(_name_4);
          _builder_2.append("=>");
          String _name_5 = connection.getSource().getConnectionEnd().getName();
          _builder_2.append(_name_5);
          _builder_2.append(",");
          return _builder_2.toString();
        }
      }
    }
    return "";
  }
  
  /**
   * 处理两个子组件之间的连接对应的参数传递关系
   * @param connection 连接对象
   * @param componentName 当前组件名称
   */
  public static String dealConParamTwoContext(final Connection connection, final Subcomponent component) {
    if ((component instanceof ThreadSubcomponent)) {
      boolean _equals = connection.getDestination().getContext().getName().equals(((ThreadSubcomponent)component).getName());
      if (_equals) {
        StringConcatenation _builder = new StringConcatenation();
        String _name = connection.getDestination().getConnectionEnd().getName();
        _builder.append(_name);
        _builder.append("=>");
        String _name_1 = connection.getName();
        _builder.append(_name_1);
        _builder.append(",");
        return _builder.toString();
      }
    } else {
      boolean _equals_1 = connection.getSource().getContext().getName().equals(component.getName());
      if (_equals_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_2 = connection.getSource().getConnectionEnd().getName();
        _builder_1.append(_name_2);
        _builder_1.append("=>");
        String _name_3 = connection.getName();
        _builder_1.append(_name_3);
        _builder_1.append(",");
        return _builder_1.toString();
      } else {
        boolean _equals_2 = connection.getDestination().getContext().getName().equals(component.getName());
        if (_equals_2) {
          StringConcatenation _builder_2 = new StringConcatenation();
          String _name_4 = connection.getDestination().getConnectionEnd().getName();
          _builder_2.append(_name_4);
          _builder_2.append("=>");
          String _name_5 = connection.getName();
          _builder_2.append(_name_5);
          _builder_2.append(",");
          return _builder_2.toString();
        }
      }
    }
    return "";
  }
  
  /**
   * 处理端口连接生成变量
   * @param portConnection 端口连接对象
   */
  public static CharSequence dealPortConVar(final PortConnection portConnection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((portConnection.getSource().getContext() != null) && (portConnection.getDestination().getContext() != null))) {
        String _name = portConnection.getName();
        _builder.append(_name);
        _builder.append(" : ");
        String _clearspace = StringUtils.clearspace(ConnectionTemplateAda.dealConnectionEndType(portConnection.getSource().getConnectionEnd()).toString());
        _builder.append(_clearspace);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 处理访问连接生成变量
   * @param accessConnection 访问连接对象
   */
  public static CharSequence dealAccessConVar(final AccessConnection accessConnection) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    String _name = accessConnection.getAccessCategory().getName();
    if (_name != null) {
      switch (_name) {
        case "data":
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _dealDataAccessVar = ConnectionTemplateAda.dealDataAccessVar(accessConnection);
          _builder_1.append(_dealDataAccessVar);
          _builder_1.newLineIfNotEmpty();
          _switchResult = _builder_1;
          break;
      }
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * 处理数据访问连接生成变量
   * @param accessConnection
   */
  public static CharSequence dealDataAccessVar(final AccessConnection accessConnection) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((accessConnection.getSource().getContext() != null) && (accessConnection.getDestination().getContext() != null))) {
        _builder.append("type ");
        String _clearspace = StringUtils.clearspace(ConnectionTemplateAda.dealConnectionEndType(accessConnection.getSource().getConnectionEnd()).toString());
        _builder.append(_clearspace);
        _builder.append("_access is access ");
        String _clearspace_1 = StringUtils.clearspace(ConnectionTemplateAda.dealConnectionEndType(accessConnection.getSource().getConnectionEnd()).toString());
        _builder.append(_clearspace_1);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        String _name = accessConnection.getName();
        _builder.append(_name);
        _builder.append(" : ");
        String _clearspace_2 = StringUtils.clearspace(ConnectionTemplateAda.dealConnectionEndType(accessConnection.getSource().getConnectionEnd()).toString());
        _builder.append(_clearspace_2);
        _builder.append("_access;");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * 处理连接中端口或者数据访问的类型（如果未指定变量类型，默认为Boolean）
   * @param connectionEnd
   */
  public static CharSequence dealConnectionEndType(final ConnectionEnd connectionEnd) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (connectionEnd instanceof DataPort) {
      _matched=true;
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        Classifier _classifier = ((DataPort)connectionEnd).getClassifier();
        boolean _tripleNotEquals = (_classifier != null);
        if (_tripleNotEquals) {
          String _convertPoint = StringUtils.convertPoint(((DataPort)connectionEnd).getClassifier().getName());
          _builder_1.append(_convertPoint);
        } else {
          _builder_1.append("Boolean");
        }
      }
      _builder_1.newLineIfNotEmpty();
      _switchResult = _builder_1;
    }
    if (!_matched) {
      if (connectionEnd instanceof EventPort) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          Classifier _classifier = ((EventPort)connectionEnd).getClassifier();
          boolean _tripleNotEquals = (_classifier != null);
          if (_tripleNotEquals) {
            String _convertPoint = StringUtils.convertPoint(((EventPort)connectionEnd).getClassifier().getName());
            _builder_1.append(_convertPoint);
          } else {
            _builder_1.append("Boolean");
          }
        }
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (connectionEnd instanceof EventDataPort) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          Classifier _classifier = ((EventDataPort)connectionEnd).getClassifier();
          boolean _tripleNotEquals = (_classifier != null);
          if (_tripleNotEquals) {
            String _convertPoint = StringUtils.convertPoint(((EventDataPort)connectionEnd).getClassifier().getName());
            _builder_1.append(_convertPoint);
          } else {
            _builder_1.append("Boolean");
          }
        }
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    if (!_matched) {
      if (connectionEnd instanceof DataAccess) {
        _matched=true;
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          Classifier _classifier = ((DataAccess)connectionEnd).getClassifier();
          boolean _tripleNotEquals = (_classifier != null);
          if (_tripleNotEquals) {
            String _convertPoint = StringUtils.convertPoint(((DataAccess)connectionEnd).getClassifier().getName());
            _builder_1.append(_convertPoint);
          } else {
            _builder_1.append("Boolean");
          }
        }
        _builder_1.newLineIfNotEmpty();
        _switchResult = _builder_1;
      }
    }
    _builder.append(_switchResult);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
