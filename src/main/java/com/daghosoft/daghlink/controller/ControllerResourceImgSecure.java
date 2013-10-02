package com.daghosoft.daghlink.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.util.UtilThumb;
import com.daghosoft.daghlink.util.Util;

@Controller
@RequestMapping("/secure/img/**")
public class ControllerResourceImgSecure {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerResourceImgSecure.class);
	
	@Autowired Util utility;
	@Autowired ServiceProperty property;
	@Autowired UtilThumb utilThumb;
	@Autowired HttpSession session; 
	
	@RequestMapping(value="{path1}/{path2}/{filePath:.*}",method=RequestMethod.GET)
	public void redim(@PathVariable("path1") String path1,
					  @PathVariable("path2") String path2,
					  @PathVariable("filePath") String filePath,
					  String width,String height,
					  HttpServletResponse response,String type){

		String basePath = property.get("general.basepath");
		String notAvailable = property.get("general.basepath.notAvailable");
		String avatar = property.get("general.basepath.avatar");
		String imgDefault = notAvailable;
		String s = File.separator;
		
		if (type!=null){
			imgDefault = avatar;
		}
		
		filePath = path1+s+path2+s+filePath;
		File file = new File(basePath+s+filePath);
		
		if (!file.exists() || !file.canRead()){
			file = new File(basePath+s+imgDefault);
		}
		
		file = utilThumb.chooseThumb(file);

		int wid = 0;
		int hei = 0;
		if(width!=null){
			wid= NumberUtils.toInt(width);
		}
		if(height!=null){
			hei=NumberUtils.toInt(height);
		}
		if (wid==0 && hei==0){
			wid=100;
		}
		
		try {
				if(!file.exists()){
					file = new File(basePath+"/avatar.png");	
				}else{
					String ext = FilenameUtils.getExtension(file.getName());
					file = UtilThumb.thumb(file, wid, hei, ext);
				}
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
