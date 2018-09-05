package com.ranhfun.ipengine.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ranhfun.ipengine.services.IPSeeker;

public class Index {

	@Inject
	private IPSeeker ipSeeker;
	
	void setupRender() {
		System.out.println(ipSeeker.getCountry("61.133.231.115"));
	}
	
}
