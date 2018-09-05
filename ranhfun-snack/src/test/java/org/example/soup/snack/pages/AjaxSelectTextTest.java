package org.example.soup.snack.pages;

import org.apache.tapestry5.annotations.Property;

public class AjaxSelectTextTest {

	@Property
	private String color;
	
	public String getOutput() {
		return "test";
	}
	
	Object onProvidecompletionsFromColor(Long colorId,String value) {
		System.out.println(colorId + " === " + value);
		return "1";
	}
	
	public Long getColorId() {
		return 1L;
	}
	
}
