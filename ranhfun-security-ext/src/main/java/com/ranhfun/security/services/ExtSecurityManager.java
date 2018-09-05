package com.ranhfun.security.services;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;

@UsesOrderedConfiguration(value = ExtSecurityListener.class)
public interface ExtSecurityManager {

	public void performWebSecurityManager(DefaultWebSecurityManager webSecurityManager);
	
}
