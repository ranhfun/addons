package com.ranhfun.security.services;

import java.util.List;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

public class ExtSecurityManagerImpl implements ExtSecurityManager {

	private List<ExtSecurityListener> listeners;
	
	public ExtSecurityManagerImpl(List<ExtSecurityListener> listeners) {
		this.listeners = listeners;
	}
	
	public void performWebSecurityManager(
			DefaultWebSecurityManager webSecurityManager) {
		for (ExtSecurityListener listener : listeners) {
			listener.performWebSecurityManager(webSecurityManager);
		}
	}

}
