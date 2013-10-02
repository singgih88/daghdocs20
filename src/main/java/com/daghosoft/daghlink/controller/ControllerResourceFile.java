package com.daghosoft.daghlink.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.service.ServiceDownload;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.util.Util;

@Controller
@RequestMapping("/file/**")
public class ControllerResourceFile {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerResourceFile.class);
	
	@Autowired Util utility;
	@Autowired ServiceProperty property;
	@Autowired ServiceDownload serviceDownload;
	@Autowired HttpSession session;
	
	@RequestMapping(value="{path1}/file/{filePath:.*}",method=RequestMethod.GET)
	public void redim(@PathVariable("path1") String path1,
					  @PathVariable("filePath") String filePath,
					  HttpServletResponse response){

		String basePath = property.get("general.basepath");
		String notAvailable = property.get("general.basepath.notAvailable");
		String imgDefault = notAvailable;
		String s = File.separator;
		
		filePath = path1+s+"file"+s+filePath;
		File file = new File(basePath+s+filePath);
		
		if (!file.exists() || !file.canRead()){
			file = new File(basePath+s+imgDefault);
		}
		
		try {
			if(file.exists()){
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream(), 256);
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file), 256);
			byte[] buffer = new byte[256];
			int read = 0;
			while ((read = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, read);
			}
			inputStream.close();
			outputStream.flush();
			outputStream.close();
			}
			if(session.getAttribute("userOs")==null){
				serviceDownload.register(file.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
