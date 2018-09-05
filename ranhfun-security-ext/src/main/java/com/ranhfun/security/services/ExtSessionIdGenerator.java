package com.ranhfun.security.services;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class ExtSessionIdGenerator implements SessionIdGenerator {

	public Serializable generateId(Session session) {
		return session.getId();
	}

}
