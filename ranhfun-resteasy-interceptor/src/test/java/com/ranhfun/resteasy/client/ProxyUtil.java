package com.ranhfun.resteasy.client;

import java.io.IOException;
import java.util.Properties;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class ProxyUtil {

	private static String updateUrl = "update-url";
	
	static {
		Properties urlProperties = new Properties();
		try {
			urlProperties.load(ProxyUtil.class.getResourceAsStream("url.properties"));
			updateUrl = urlProperties.getProperty(updateUrl);
		} catch (IOException e) {
			// should never occur
		}
		init();
	}
	
	public static UpdateClient getUpdateClient() {
		UpdateClient client = ProxyFactory.create(UpdateClient.class, updateUrl);
		return client;
	}
	
	private static void init() {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	}
	
}
