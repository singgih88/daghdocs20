package com.daghosoft.daghlink.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.daghosoft.daghlink.dao.DaoProperty;

@Service
public class ServiceInstall {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceInstall.class);

	@Autowired DaoProperty daoProperty;
	@Autowired HttpSession session;
	
	public Boolean createDb(){
		return executeSqlFile("com/daghosoft/daghlink/zzz/db.sql");
	}
	
	@Secured("ROLE_ADMIN")
	public Boolean deltaDb(){
		return executeSqlFile("com/daghosoft/daghlink/zzz/deltadb.sql");
	}
	
	@Secured("ROLE_ADMIN")
	public Boolean defaultDb(){
		daoProperty.clear();
		Boolean out =executeSqlFile("com/daghosoft/daghlink/zzz/default.sql");
		if (out){
			session.setAttribute("alertType","success");
		}
		return out;
	}
	
	
	
	
	private Boolean executeSqlFile(String path){
		Boolean out = false;
		try {
			  Resource resource = new ClassPathResource(path);
			  InputStream fstream = resource.getInputStream();
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			  String strLine;
			  String sql = "";
			  while ((strLine = br.readLine()) != null)   {
				  if(!strLine.startsWith("#")){
					  sql = sql + " " + strLine; 
				  }	  	 
				  if (sql.indexOf(";")>0){
					  daoProperty.executeSql(sql);
					  sql="";
				  }		  
			  }
			  in.close();
			  out = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
	
	
}
