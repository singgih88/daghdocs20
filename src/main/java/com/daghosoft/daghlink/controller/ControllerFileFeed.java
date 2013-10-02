package com.daghosoft.daghlink.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.dao.DaoFile;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.util.Util;

@Controller
@RequestMapping("/feedFile")
public class ControllerFileFeed {

	@Autowired DaoFile daoFile;
	@Autowired @Qualifier("daoGroupFile") DaoGroupInterface daoGroup;
	@Autowired Util util;
	
	@RequestMapping(params={"uuid"} ,method=RequestMethod.GET)
	public ModelAndView getFeed(String uuid){
		
		Group group = daoGroup.get(uuid);
		
		List<FileBean> fileList = daoFile.findFileByGroup(group.getId());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewerFile");
		mav.addObject("fileList", fileList);
		mav.addObject("channelTitle", group.getTitle());
		mav.addObject("channelDescription", group.getDescription());
		mav.addObject("channelLink",util.getInstalledUrl());
		mav.addObject("channelDate",group.getDate());
		return mav;
	}
	
}
