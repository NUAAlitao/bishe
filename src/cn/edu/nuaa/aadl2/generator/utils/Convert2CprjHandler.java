package cn.edu.nuaa.aadl2.generator.utils;

import org.eclipse.core.resources.IProject;

public class Convert2CprjHandler{
	
	  public boolean preCprjCreate() {
		return false;
		//在创建C工程之前需要实现的逻辑，如：AADL工程生成
	  }
	  public void postCprjCreate(IProject cprj) {
		//C工程创建完成之后需要实现的逻辑：如：AADL工程生成

	  }
	  
	  public void run(){
		  Convert2CprjHandler c=new Convert2CprjHandler();
		  if(c.preCprjCreate()){
			  IProject project=null; 
			  c.postCprjCreate(project);
		  }else{
			  //
		  }
		  
	  }
}
