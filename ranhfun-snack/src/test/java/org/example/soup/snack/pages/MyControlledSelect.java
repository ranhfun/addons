package org.example.soup.snack.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

public class MyControlledSelect {

	//@Validate("required, minLength=3, maxLength=10")
	@Property
	private String number;
	
	//@Validate("required,min=5")
	@Property
	private int number3;
	
	@Property
	private String number2;
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled")
	public String secCateMatch(String topId) {
		return "<select><option>testes</option></select>";
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled2")
	public String secCateMatch2(String topId) {
		return "<select><option>请选择</option></select>";
	}
	
}
