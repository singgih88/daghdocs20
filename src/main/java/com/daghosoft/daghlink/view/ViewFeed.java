package com.daghosoft.daghlink.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.daghosoft.daghlink.bean.Link;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Item;

public class ViewFeed extends AbstractRssFeedView {
	
	
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
		List<Link> linkList = (List<Link>) model.get("linkList");
		List<Item> items = new ArrayList<Item>(linkList.size());
		
		for (Link link : linkList){
			Item item = new Item();
			item.setTitle(link.getTitle());
			if (link.getTitle()==null){
				item.setTitle(link.getLink());
			}
			item.setLink(link.getLink());
			Description des = new Description();
			des.setValue(link.getDescription());
			item.setDescription(des);
			item.setPubDate(link.getDate());
			
			items.add(item);
		}
		return items;
	}

}
