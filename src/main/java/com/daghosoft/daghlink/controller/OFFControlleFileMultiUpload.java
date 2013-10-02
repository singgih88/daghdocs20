package com.daghosoft.daghlink.controller;

import java.io.File;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.service.ServiceProperty;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilFile;
import com.daghosoft.daghlink.util.UtilPagination;
import com.daghosoft.daghlink.util.UtilThumb;


@Controller
@RequestMapping("/secure/uploadMultiOFF")
public class OFFControlleFileMultiUpload {
	 
	 private static final Logger logger = LoggerFactory.getLogger(OFFControlleFileMultiUpload.class);
	 @Autowired HttpSession session;
	 @Autowired HttpServletRequest request;
	 @Autowired DaoFile daoFile;
	 @Autowired UtilPagination page;
	 @Autowired UtilFile utilFile;
	 @Autowired ServiceProperty serviceProperty;
	 
	    @RequestMapping(method=RequestMethod.POST)
		public String upload(Model model) {
	    	
	    	User user = (User) session.getAttribute("userOs");

			// File baseRepository = new File(basePath);
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			Long limit =NumberUtils.toLong(serviceProperty.get("general.upload.size.limit"));
			upload.setSizeMax(limit);
			
			try {
				
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> itr = items.iterator();
				
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.getName() != null && item.getName() != "") {
						String filename = FilenameUtils.getName(item.getName());
						//File destFile = new File(destFolder,filename);
						
						FileBean fileBean = new FileBean();
						fileBean.setFilename(filename);
						fileBean.setSize(item.getSize());
						fileBean.setUuid(UUID.randomUUID().toString());
						fileBean.setFather(Util.groupId(session));
						fileBean = utilFile.genPath(fileBean);
						
						String s = File.separator;
					    String basePath = serviceProperty.get("general.basepath");
			
					    UtilFile.createFolder(basePath+s+user.getId()+s+"file");
					   
					    File dest = new File(basePath+fileBean.getPath());
						
						daoFile.createFile(fileBean, user.getId());
						UtilThumb.thumb(dest, 70, 0);
				    	UtilThumb.thumb(dest, 140, 0);
				    	
						item.write(dest);
					}
				}
				page.clear();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//resp.setAll("ko", e.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//resp.setAll("ko", "error.file.null");
			}
			return "redirect:../secure/file?";
		}

	    @RequestMapping(method=RequestMethod.GET)
		public void next(Model model) {
			logger.debug("GET MULTIFILE");
		}
	 
}
