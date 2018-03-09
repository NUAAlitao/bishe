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
}
