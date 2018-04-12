package cn.edu.nuaa.aadl2.generator.utils;

@SuppressWarnings("all")
public class StringUtils {
  public static String convert(final String str) {
    return str.toLowerCase().replace(".", "_");
  }
  
  public static String convertPoint(final String str) {
    return str.replace(".", "_");
  }
  
  public static String clear(final String str) {
    return "";
  }
  
  public static String clearspace(final String str) {
    return str.replaceAll("\r|\n", "");
  }
  
  public static String dealMultipleSpace(final String str) {
    return str.replaceAll("\\s+", " ");
  }
  
  public static String formatParam(final String str) {
    String temp = str;
    temp = temp.replaceAll("(; )+", "; ");
    boolean _endsWith = temp.endsWith(",");
    if (_endsWith) {
      int _length = temp.length();
      int _minus = (_length - 1);
      temp = temp.substring(0, _minus);
    }
    boolean _startsWith = temp.startsWith("; ");
    if (_startsWith) {
      temp = temp.substring(2);
    }
    boolean _endsWith_1 = temp.endsWith("; ");
    if (_endsWith_1) {
      int _length_1 = temp.length();
      int _minus_1 = (_length_1 - 2);
      temp = temp.substring(0, _minus_1);
    }
    return temp;
  }
}
