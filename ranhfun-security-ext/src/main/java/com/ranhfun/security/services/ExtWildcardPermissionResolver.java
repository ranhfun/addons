package com.ranhfun.security.services;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

public class ExtWildcardPermissionResolver implements PermissionResolver {

	private String prefix;
	
	public ExtWildcardPermissionResolver(String prefix) {
		if (prefix==null) {
			throw new IllegalArgumentException("Not Illegal [ExtWildcardPermissionResolver:prefix]");
		}
		this.prefix = prefix;
	}
	
    public Permission resolvePermission(String permissionString) {
        return new WildcardPermission(prefix + ":" + permissionString);
    }
	
}
