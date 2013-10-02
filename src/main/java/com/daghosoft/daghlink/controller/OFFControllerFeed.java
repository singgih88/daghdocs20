package com.daghosoft.daghlink.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.dao.DaoLink;

@Controller
@RequestMapping("/secure/feedOFF")
public class OFFControllerFeed {

	@Autowired DaoLink daoLink;
	
	@RequestMapping(params={"uuid"} ,method=RequestMethod.GET)
	public ModelAndView getFeed(String uuid){
		
		Link link = daoLink.get(uuid);
		
		int father=link.getId();
		List<Link> linkList = daoLink.findLinkByGroup(father);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("linkList", linkList);
		mav.addObject("channelTitle", link.getTitle());
		mav.addObject("channelDescription", link.getDescription());
		mav.addObject("channelLink", "http://channelLink");
		mav.addObject("channelDate",link.getDate());
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getFeed(HttpSession session){
		User user = (User) session.getAttribute("userOs");
		List<Link> linkList = daoLink.findLink(user.getId());;
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("linkList", linkList);
		mav.addObject("channelTitle", "channelTitle");
		mav.addObject("channelDescription", "channelDescription");
		mav.addObject("channelLink", "http://channelLink");
		
		return mav;
	}
}
