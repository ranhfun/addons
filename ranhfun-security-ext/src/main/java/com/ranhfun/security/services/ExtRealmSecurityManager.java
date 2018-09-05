package com.ranhfun.security.services;

import java.util.Collection;

import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.tynamo.security.Authenticator;
import org.tynamo.security.services.TapestryRealmSecurityManager;

public class ExtRealmSecurityManager extends DefaultWebSecurityManager {

	public ExtRealmSecurityManager(Authenticator authenticator, SubjectFactory subjectFactory, RememberMeManager rememberMeManager, final Collection<Realm> realms,
			@InjectService("WebSecurityManager")
			final WebSecurityManager webSecurityManager,final ObjectLocator locator, final ExtSecurityManager extSecurityManager) {
	    super();
	    //TapestryRealmSecurityManager securityManager = (TapestryRealmSecurityManager) locator.getService("WebSecurityManager", WebSecurityManager.class);
	    authenticator.setRealms(realms);
	    setAuthenticator(authenticator);
	    ((DefaultSubjectDAO) this.subjectDAO).setSessionStorageEvaluator(new DefaultWebSessionStorageEvaluator());
	    setSubjectFactory(subjectFactory);
	    setRememberMeManager(rememberMeManager);
	    setRealms(realms);
	    extSecurityManager.performWebSecurityManager(this);
	}
}
