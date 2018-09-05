package org.example.soup.snack.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import com.ranhfun.soup.snack.SoupEventConstants;

public class ControllLinkTest {

	@OnEvent(value = SoupEventConstants.CONTROLL_DISPLAY, component = "controllerLink")
	public JSONArray controllLinkDisplay() {
		return new JSONArray(new JSONObject("id", "test", "value", "成功"));
	}
	
}
