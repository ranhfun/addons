package com.ranhfun.oauth2.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

import com.ranhfun.oauth2.OAuthToken;

public class OAuthRealm extends AuthorizingRealm {

	public OAuthRealm() {
		setAuthenticationTokenClass(OAuthToken.class);
		setName("OAuthRealm");
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		OAuthToken oauthToken = (OAuthToken) token;
		if (token == null) {
			return null;
		}

		String code = (String) oauthToken.getCredentials();
		if (!StringUtils.hasText(code)) {
			return null;
		}
		oauthToken.setUserId(code);
		System.out.println("code.........." + code);
		List<String> principals = CollectionUtils.asList(code);
        PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
        return new SimpleAuthenticationInfo(principalCollection, code);
	}

    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }
	
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return new SimpleAuthorizationInfo();
	}

}
