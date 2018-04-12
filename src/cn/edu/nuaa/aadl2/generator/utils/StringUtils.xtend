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
		var String temp = str;
		temp = temp.replaceAll("(; )+","; ")
		if(temp.endsWith(",")){
			temp = temp.substring(0,temp.length-1)
		}
		if(temp.startsWith("; ")){
			temp = temp.substring(2)
		}
		if(temp.endsWith("; ")){
			temp = temp.substring(0,temp.length-2)
		}
		return temp
	}
}