package com.ranhfun.proxool;

import com.ranhfun.proxool.ProxoolUtil;

public class ProxoolDataSource extends org.logicalcobwebs.proxool.ProxoolDataSource {
	
	public String getPassword() {
		if (ProxoolUtil.decryptFlag()) {
			return ProxoolUtil.decrypt(super.getPassword());
		}
		return super.getPassword();
	}

	public String getUser() {
		if (ProxoolUtil.decryptFlag()) {
			return ProxoolUtil.decrypt(super.getUser());
		}
		return super.getUser();
	}
	
}
