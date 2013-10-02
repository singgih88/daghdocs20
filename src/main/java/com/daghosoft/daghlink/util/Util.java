package com.daghosoft.daghlink.util;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.bean.Group;
import com.daghosoft.daghlink.bean.Link;
import com.daghosoft.daghlink.bean.User;
import com.daghosoft.daghlink.service.ServiceProperty;

@Component
@PropertySource("conf.properties")
public class Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	@Autowired Environment env;
	@Autowired ServiceProperty serviceProperty;
	@Autowired JavaMailSenderImpl mailSender;
	@Autowired HttpServletRequest request;

	/**
	 * User to read from session from witch group show content.
	 * 
	 * @param session
	 * @return group id or 0 if no group is selected
	 */
	public static int groupId(HttpSession session){
		int out = 0;
		if(session.getAttribute("groupId")!=null){
			out = (Integer) session.getAttribute("groupId");
		}
		return out;
	}
	

	public static Map<Integer,String> selectGroup(List<Group> list){
		Map<Integer,String> uMap = new HashMap<Integer,String>();
		
		//for(int x=0;x<list.size();x++){
		for(int x=list.size()-1;x>=0;x--){
			Group group = list.get(x);
			uMap.put(group.getId(), group.getTitle());
			//logger.debug(group.getId() +" -- "+ group.getTitle());
		}
		uMap.put(0, "Home");
		System.out.println("umap");
		printMap(uMap);
		
		Map<Integer,String> treeMap = new TreeMap<Integer,String>(uMap);
		System.out.println("treeMap");
		printMap(treeMap);
		
		return treeMap;
	}
	
	
	public static void printMap(Map<Integer,String> map) {
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
				+ entry.getValue());
		}
	}
	
	public String getInstalledUrl(){
		String port="";
		if(request.getServerPort()!=80){
			port=":"+request.getServerPort();
		}
		String out=request.getScheme()+"://"+ request.getServerName()+port+request.getContextPath();
		return out;
	}
	
	public String formatName(User user){
		String out="";
		if (user.getName()!=null || !user.getName().equals("")){
			out=user.getName();
		}
		if (user.getSurname()!=null || !user.getSurname().equals("")){
			out=out+" "+user.getSurname();
		}
		if(out.equals("")){
			out= user.getUsername();
		}
		return out;	
	}
	
	public String formatUrl(String url){
		return url;
	}
	
}
