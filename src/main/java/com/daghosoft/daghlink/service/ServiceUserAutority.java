package com.daghosoft.daghlink.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daghosoft.daghlink.bean.UserAuthority;

@Service
public class ServiceUserAutority {

	public String[] convertArrToList(List<UserAuthority> list) {
		List<String> listString = new ArrayList<String>();
		for (UserAuthority ua : list) {
			listString.add(ua.getAuthority());
		}
		String[] arr = (String[]) listString.toArray(new String[0]);
		return arr;
	}

	public List<UserAuthority> convertListToArr(String[] sa, String username) {
		List<UserAuthority> list = new ArrayList<UserAuthority>();
		for (int x = 0; x < sa.length; x++) {
			UserAuthority u = new UserAuthority();
			u.setUsername(username);
			u.setAuthority(sa[x]);
			list.add(u);
		}
		return list;
	}
	

}
