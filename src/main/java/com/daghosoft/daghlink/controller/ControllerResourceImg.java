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

import com.daghosoft.daghlink.service.ServiceDownload;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.util.UtilThumb;
import com.daghosoft.daghlink.util.Util;

@Controller
@RequestMapping("/img/**")
public class ControllerResourceImg {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerResourceImg.class);
	
	@Autowired Util utility;
	@Autowired ServiceProperty property;
	@Autowired UtilThumb utilThumb;
	@Autowired HttpSession session; 
	@Autowired ServiceDownload serviceDownload;
	
	@RequestMapping(value="{path1}/{path2}/{filePath:.*}",method=RequestMethod.GET)
	public void redim(@PathVariable("path1") String path1,
					  @PathVariable("path2") String path2,
					  @PathVariable("filePath") String filePath,
					  String width,String height,
					  HttpServletResponse response){

		String basePath = property.get("general.basepath");
		String notAvailable = property.get("general.basepath.notAvailable");
		String imgDefault = notAvailable;
		String separator = File.separator;
		
		filePath = path1+separator+path2+separator+filePath;
		File file = new File(basePath+separator+filePath);
		
		if (!file.exists() || !file.canRead()){
			file = new File(basePath+separator+imgDefault);
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
				
			String ext = FilenameUtils.getExtension(file.getName());
			file = UtilThumb.thumb(file, wid, hei, ext);
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
