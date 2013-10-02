package com.daghosoft.daghlink.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoGroupInterface;
import com.daghosoft.daghlink.dao.DaoLink;
import com.daghosoft.daghlink.util.Util;

@Controller
@RequestMapping("/feedLink")
public class ControllerLinkFeed {

	@Autowired DaoLink daoLink;
	@Autowired @Qualifier("daoGroupLink") DaoGroupInterface daoGroup;
	@Autowired Util util;
	
	@RequestMapping(params={"uuid"} ,method=RequestMethod.GET)
	public ModelAndView getFeed(String uuid){
		
		Group group = daoGroup.get(uuid);
		
		List<Link> linkList = daoLink.findLinkByGroup(group.getId());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("linkList", linkList);
		mav.addObject("channelTitle", group.getTitle());
		mav.addObject("channelDescription", group.getDescription());
		mav.addObject("channelLink",util.getInstalledUrl());
		mav.addObject("channelDate",group.getDate());
		return mav;
	}
	
	/*@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getFeed(HttpSession session){
		User user = (User) session.getAttribute("userOs");
		List<Link> linkList = daoLink.findLink(user.getId());;
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("linkList", linkList);
		mav.addObject("channelTitle", "channelTitle");
		mav.addObject("channelDescription", "channelDescription");
		mav.addObject("channelLink",util.getInstalledUrl());
		
		return mav;
	}*/
}
