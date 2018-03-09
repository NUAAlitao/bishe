package cn.edu.nuaa.aadl2.generator.templateAda

class TemplateAda {
	public static String systemfolder=null;
	public static String processfolder=null;
	
	public static String packageName=null;
	public static String subprogramsFileName=null;
	public static String typesFileName=null;
	
	public static String meta_systemheadfile=null;
	public static String meta_systemfolder=null;
	
	public static boolean isMeta=true;
	
	public static String head='''
		#include "taskLib.h"
	'''
	
	public static String bakhead='''
		#include "taskLib.h"
	'''
	
	public static String dhead='''
		typedef char* string;
	'''
	
	public static String bakdhead='''
		#include "taskLib.h"
		typedef char* string;
	'''
}