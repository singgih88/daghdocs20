package com.daghosoft.daghlink.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class UtilBundle {
  static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
    Map<String, String> map = new HashMap<String, String>();
    Enumeration<String> keys = resource.getKeys();
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      map.put(key, resource.getString(key));
    }
    return map;
  }
  
  public String getMessage(String key){
		ResourceBundle rb = null;
		rb = ResourceBundle.getBundle("messages");
		String out = "???"+key+"???";
		if (rb.containsKey(key)){
			out= rb.getString(key);
		}
		return out;
	}
	
	public Map<String, String> getMessageBundle(String bundle,Locale locale){
		ResourceBundle rb = null;
		System.out.println("Locale - "+Locale.getDefault());
		rb = ResourceBundle.getBundle(bundle,locale);
		return (UtilBundle.convertResourceBundleToMap(rb));
	}
}
