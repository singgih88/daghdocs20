package com.daghosoft.daghlink.service;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.dao.DaoDownload;
import com.daghosoft.daghlink.dao.DaoFile;


@Service
public class ServiceDownload {

	private static final Logger logger = LoggerFactory.getLogger(ServiceDownload.class);
	
	@Autowired DaoDownload daoDownload;
	@Autowired HttpServletRequest request;
	@Autowired DaoFile daoFile;
	
	public void register(String fileName){
			String ext = FilenameUtils.getExtension(fileName);
			String uuid = fileName.replace("."+ext, "");
			
			FileBean fileBean =  daoFile.get(uuid);
			String ip = request.getRemoteAddr();
			daoDownload.create(fileBean.getId(),ip);
	}
}
