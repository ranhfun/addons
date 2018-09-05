package com.ranhfun.security.services;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

public interface ExtSecurityListener {

	public void performWebSecurityManager(DefaultWebSecurityManager webSecurityManager);
	
}
