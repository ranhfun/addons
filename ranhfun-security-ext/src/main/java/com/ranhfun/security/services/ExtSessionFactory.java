package com.ranhfun.security.services;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.util.WebUtils;

public class ExtSessionFactory implements SessionFactory {

	public Session createSession(SessionContext initData) {
		SimpleSession simpleSession = null;
		if (initData != null ) {
            String host = initData.getHost();
            if (host != null) {
            	simpleSession = new SimpleSession(host);
            } else {
            	simpleSession = new SimpleSession(host);
            }
            HttpServletRequest request = WebUtils.getHttpRequest(initData);
            try {
            	simpleSession.setId(request.getSession().getId());
			} catch (Exception e) {
				simpleSession.setId(UUID.randomUUID().toString());
			}
        } else {
        	simpleSession = new SimpleSession();
        	simpleSession.setId(UUID.randomUUID().toString());
        }
		return simpleSession;
	}

}
