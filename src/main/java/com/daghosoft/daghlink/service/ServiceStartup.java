package com.daghosoft.daghlink.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Service
public class ServiceStartup {
	private static final Logger logger = LoggerFactory.getLogger(ServiceStartup.class);
	//@Autowired CommonsMultipartResolver multipartResolver;
	@Autowired ServiceProperty serviceProperty;

	/*@PostConstruct
	public void initIt() throws Exception {
	long maxSize = NumberUtils.toLong(serviceProperty.getNoContextNeeded("general.upload.size.limit"));
	multipartResolver.setMaxUploadSize(maxSize);
	logger.debug("Settings : "+maxSize+" --- " + multipartResolver);
	}*/
}
