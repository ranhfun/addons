package com.ranhfun.resteasy.client;



public class Proxy {

	public static void main(String[] args) {
		UpdateClient updateClient = ProxyUtil.getUpdateClient();
		System.out.println(updateClient.getVersionApi3().get(0).getvApp());
	}
	
}
