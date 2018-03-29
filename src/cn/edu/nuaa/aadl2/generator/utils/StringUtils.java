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
    boolean _endsWith = str.endsWith(",");
    if (_endsWith) {
      int _length = str.length();
      int _minus = (_length - 1);
      return str.substring(0, _minus);
    }
    return str;
  }
}
