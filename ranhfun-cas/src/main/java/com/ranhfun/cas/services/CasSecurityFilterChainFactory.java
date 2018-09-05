package com.ranhfun.cas.services;

import org.tynamo.security.shiro.authc.CasFilter;

public interface CasSecurityFilterChainFactory {
	
	CasFilter cas();
	
}
