package com.daghosoft.daghlink.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.daghosoft.daghlink.bean.FileBean;
import com.daghosoft.daghlink.util.Util;
import com.daghosoft.daghlink.util.UtilFile;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Item;

public class ViewFeedFile extends AbstractRssFeedView {
	
	@Autowired Util util;
	
	
	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,HttpServletRequest request){
		
		
		feed.setTitle((String) model.get("channelTitle"));
		feed.setDescription((String) model.get("channelDescription"));
		feed.setLink((String) model.get("channelLink"));
		feed.setPubDate((Date) model.get("channelDate"));
		
		super.buildFeedMetadata(model, feed, request);
	}

	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<FileBean> fileList = (List<FileBean>) model.get("fileList");
		List<Item> items = new ArrayList<Item>(fileList.size());
		
		for (FileBean file : fileList){
			Item item = new Item();
			item.setTitle(file.getTitle());
			item.setTitle(file.getFilename());
			item.setLink(util.getInstalledUrl()+"/file" + file.getPath());
			Description des = new Description();
			des.setValue(file.getDescription());
			item.setDescription(des);
			item.setPubDate(file.getDate());
			
			items.add(item);
		}
		return items;
	}

}
