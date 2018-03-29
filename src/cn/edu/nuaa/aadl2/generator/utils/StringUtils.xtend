package cn.edu.nuaa.aadl2.generator.utils

class StringUtils {
	
	def static convert(String str){
		return str.toLowerCase.replace(".","_")
	}

	def static convertPoint(String str){
		return str.replace(".","_")
	}	
	
	def static clear(String str){
		return ""
	}
	
	def static clearspace(String str){
		return str.replaceAll("\r|\n","")
	}
	
	def static dealMultipleSpace(String str){
		return str.replaceAll("\\s+"," ")
	}
	
	def static formatParam(String str){
		if(str.endsWith(",")){
			return str.substring(0,str.length-1)
		}
		return str
	}
}