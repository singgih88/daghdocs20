package com.daghosoft.daghlink.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;

import com.daghosoft.daghlink.service.ServiceProperty;


@Component
public class UtilPagination {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilPagination.class);
	@Autowired HttpSession session;
	@Autowired ServiceProperty property;

	/**
	 * It manipulate a session atribute called "contentList"
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void pagedList(List<?> list){
		PagedListHolder<Object> pagedList = new PagedListHolder<Object>();
		int pageSize=NumberUtils.toInt(property.get("general.page.size"));
		pagedList.setPageSize(pageSize);
		
		if(session.getAttribute("contentList")==null){
			pagedList.setSource((List<Object>) list);
			session.setAttribute("contentList", pagedList);
		}
	}
	
	public void next(){
		if(session.getAttribute("contentList")!=null){
		@SuppressWarnings("unchecked")
		PagedListHolder<Object> pagedList = (PagedListHolder<Object> ) session.getAttribute("contentList");
		pagedList.nextPage();
		session.setAttribute("contentList", pagedList);
		}
	}
	
	public void prev(){
		if(session.getAttribute("contentList")!=null){
		@SuppressWarnings("unchecked")
		PagedListHolder<Object> pagedList = (PagedListHolder<Object> ) session.getAttribute("contentList");
		pagedList.previousPage();
		session.setAttribute("contentList", pagedList);
		}
	}
	
	public void pageSize(){
		if(session.getAttribute("contentList")!=null){
		@SuppressWarnings("unchecked")
		PagedListHolder<Object> pagedList = (PagedListHolder<Object> ) session.getAttribute("contentList");
		int pageSize=NumberUtils.toInt(property.get("general.page.size"));
		pagedList.setPageSize(pageSize);
		session.setAttribute("contentList", pagedList);
		}
	}
	
	public void clear(){
		session.setAttribute("contentList", null);
	}
	
	public void page(int id){
		if(session.getAttribute("contentList")!=null){
		@SuppressWarnings("unchecked")
		PagedListHolder<Object> pagedList = (PagedListHolder<Object> ) session.getAttribute("contentList");
		pagedList.setPage(id);
		session.setAttribute("contentList", pagedList);
		}
	}
	
	public void type(String type){
		String stype = (String)	session.getAttribute("contentListType");
		if(session.getAttribute("contentListType")==null || stype!=type){
		  session.setAttribute("contentListType",type);
		  session.setAttribute("contentList",null);
		  session.setAttribute("groupId", 0);
		  session.setAttribute("groupTitle", "");
		}
	}
}
