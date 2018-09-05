package org.example.soup.snack.pages;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.MarkupWriterFactory;
import org.apache.tapestry5.services.ResponseRenderer;

import com.ranhfun.soup.snack.SoupEventConstants;

public class ControllInputTest {

	@Property
	private int number3;
	
	@Property
	private String number2;
	
	@Inject
	private MarkupWriterFactory markupWriterFactory;

	@Inject
	private ResponseRenderer responseRenderer;
	
	@OnEvent(value = SoupEventConstants.CONTROLL_DISPLAY, component = "controllerInput")
	public JSONArray controllInputDisplay(String controllValue) {
		return new JSONArray(new JSONObject("id", "test", "value", "tetvalue333"),
				new JSONObject("id", "test1", "value", "tetvalue1"),
				new JSONObject("id", "controlled2", "value", setSelectMatch(controllValue)));
	}
	
	public String setSelectMatch(String fourId) {
		MarkupWriter writer = markupWriterFactory
				.newPartialMarkupWriter(responseRenderer.findContentType(this));
		writer.element("select");
		for (int i = 0; i < 10; i++) {
			writer.element("option", "value", i);
			writer.write("value" + i);
			writer.end();
		}
		writer.end();
		return writer.toString();
	}
	
	@OnEvent(value = SoupEventConstants.CONTROLL_DISPLAY, component = "controllerInput2")
	public JSONArray controllInputDisplay2(String controllValue) {
		return new JSONArray(new JSONObject("id", "test2", "value", "input22"));
	}
	
}
