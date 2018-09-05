package com.ranhfun.ueditor.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class Index {

	@Persist
	@Property
	private String value;
	
	void onSuccess() {
		System.out.println(value);
	}
	
}
