package com.daghosoft.daghlink.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceProperty;

@Service
public class UtilFile {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilFile.class);
	
	@Autowired ServiceProperty property;
	//@Autowired ServiceProperty serviceProperty;
	@Autowired HttpSession session;

public void restoreFiles(){
	String basePath = property.get("general.basepath");
	try {
		copyDefaultFile("/avatar.jpg", "com/daghosoft/daghlink/zzz" , basePath);
		copyDefaultFile("/NotAvailable.jpg", "com/daghosoft/daghlink/zzz" , basePath);
		copyDefaultFile("/file.png", "com/daghosoft/daghlink/zzz" , basePath);
		copyDefaultFile("/img.png", "com/daghosoft/daghlink/zzz" , basePath);
		copyDefaultFile("/mp3.png", "com/daghosoft/daghlink/zzz" , basePath);
		copyDefaultFile("/pdf.png", "com/daghosoft/daghlink/zzz" , basePath);
		copyDefaultFile("/txt.png", "com/daghosoft/daghlink/zzz" , basePath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
private void copyDefaultFile(String Filename, String source, String dest) throws IOException{
	
	    Resource resource = new ClassPathResource(source+Filename);
	
	    File p = new File(dest);
	    p.mkdirs();
	    
		File f = new File(dest+Filename);
		InputStream fstream = resource.getInputStream(); 
		DataInputStream in = new DataInputStream(fstream);
		
		OutputStream out = new FileOutputStream(f);
		
		byte[] buffer = new byte[256];
		int read = 0;
		while ((read = in.read(buffer)) > 0) {
			out.write(buffer, 0, read);	
	   }
	   out.close();
	   in.close();
	}


	public static void createFolder(String path){
		File home = new File(path);
		home.mkdirs();
	}

	public FileBean genPath(FileBean file){
		User user = (User) session.getAttribute("userOs");
		return genPath(file,user.getId());
	}
	
	public FileBean genPath(FileBean file,int user_id){
		String s = "/"; // File.separator;
		String ext = FilenameUtils.getExtension(file.getFilename());
		file.setPath(s+user_id+s+"file"+s+file.getUuid()+"."+ext);
		return file;
	}
	
	public FileBean description(FileBean file){
		if (file.getDescription()==null){
			file.setDescription("");
		}
		return file;
	}
}
