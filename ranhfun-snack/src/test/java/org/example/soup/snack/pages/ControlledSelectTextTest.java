package org.example.soup.snack.pages;

import org.apache.tapestry5.annotations.Property;

public class ControlledSelectTextTest {

	@Property
	private String color;
	
	public String getOutput() {
		return "test";
	}
	
	Object onProvidecompletionsFromOutput(Long colorId,String value) {
		return value;
	}
	
	public Long getColorId() {
		return 1L;
	}
	
	public String getOutput2() {
		return "test";
	}
	
	Object onProvidecompletionsFromOutput2(Long colorId,String value) {
		return value + "2";
	}
	
}
