package com.daghosoft.daghlink.util;

import java.awt.RenderingHints;
import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.service.ServiceProperty;

/**
 * @author andrea.matera
 *
 */
/**
 * @author andrea.matera
 *
 */
@Component
public class UtilThumb {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilThumb.class);
	@Autowired ServiceProperty property;
	
	public static String getName (File file,int width,int height){
		String fileName = file.getAbsolutePath();
		String separator = file.separator;
		return  getFolder(file)+ separator + width+"x"+height +"---"+fileName.substring(fileName.lastIndexOf(separator)+1);	
	}
	
	public static String getName (File file){
		String fileName = file.getAbsolutePath();
		String separator = file.separator;
		return  getFolder(file)+ fileName.substring(fileName.lastIndexOf(separator));	
	}

	public static String getName (String completepath){
		File file = new File(completepath);
		return getName(file);
	}
	
	/**
	 * Creare folder if doesn't exist and return complete folder Path.
	 * @param file 	
	 * @return folder full path.
	 */
	public static String getFolder (File file){
		String fileName = file.getAbsolutePath();
		String separator = file.separator;
		File folder = new File(fileName.substring(0, fileName.lastIndexOf(separator)) +separator+"thumb");
		if (!folder.exists()){
			folder.mkdirs();	
		}
		return  folder.getAbsolutePath();	
	}

	public static String getFolder (String completepath){
		File file = new File(completepath);
		return getFolder(file);
	}	

	@Async
	public static File thumb(File file,int width,int height,String mime){
		mime = mime.replace("image/", "").toLowerCase();
		File thumb = new File(getName(file,width,height));
		if (mime.contains("jpeg") || mime.contains("jpg") || mime.contains("png") || mime.contains("gif")){
			getFolder(file);
			
			if (!thumb.exists()){
				if(width>2000 || height>2000){
					width=100;
					height=100;
				}
				ImageResizer.scale(file, thumb, width,height, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true,mime);
			}
		}
		return thumb;
	}
	
	@Async
	public static File thumb(File file,int width,int height){
		String mime = FilenameUtils.getExtension(file.getName().toLowerCase());
		return thumb(file, width, height, mime);
	}
	
	public static void deleteThumb(File file){
		File fol = new File(getFolder(file));
		String uuid = FilenameUtils.removeExtension(file.getName());
		for (File f : fol.listFiles()){
			if (FilenameUtils.wildcardMatch(f.getName(), "*"+uuid+"*")){
				f.delete();
			}
		}
	}
	
	public File chooseThumb(File f){
		String basePath = property.get("general.basepath")+File.separator;
		if (f.exists()){
			String ext = FilenameUtils.getExtension(f.getName()).toLowerCase();
			if (ext.equals("jpg")||ext.equals("jpeg")||ext.equals("png")||ext.equals("bmp")||ext.equals("tif")||ext.equals("gif")){
				//f = f;
			}else if (ext.equals("pdf")){
				f = new File(basePath+"pdf.png");
			}else if (ext.equals("doc")||ext.equals("docx")||ext.equals("txt")||ext.equals("odg")){
				f = new File(basePath+"txt.png");
			}else{
				f = new File(basePath+"file.png");
			}
		}
		
		return f;
	}
}
