package com.ranhfun.soup.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.MarkupWriterFactory;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.util.TextStreamResponse;

import com.ranhfun.jquery.services.javascript.js.JSSupport;
import com.ranhfun.soup.SoupEventConstants;
import com.ranhfun.soup.components.InPlaceEditor;

public class Index {

	//@Validate("required, minLength=3, maxLength=10")
	@Property
	private String number;
	
	//@Validate("required,min=5")
	@Property
	private int number3;
	
	@Property
	private String number2;
	
	@Property
	private String number22;
	
	@Inject
	private JSSupport jsSupport;
	
	@Property
	private String person;
	
	@Property
	private Date myDate;
	
	@Inject
	private MarkupWriterFactory markupWriterFactory;

	@Inject
	private ResponseRenderer responseRenderer;
	
	@Inject
	private AlertManager alertManager;
	
	@Property
	private String inplaceText;
	
	void setupRender() {
		alertManager.alert(Duration.TRANSIENT, Severity.INFO, "成功");
		jsSupport.addScript("$(\"#project\").tapestryFieldEventManager();");
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled")
	public String secCateMatch(String topId, String blankLabel) {
		return "<select><option>" + blankLabel + "</option></select>";
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled2")
	public String secCateMatch2(String topId) {
		return "<select><option>请选择</option></select>";
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled22")
	public String secCateMatch22(String topId) {
		return "<select><option>测试</option></select>";
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled3")
	public String secCateMatch3(String topId) {
		return "ControlledSelectText";
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled4")
	public String secCateMatch4(String topId) {
		return "ControlledInputText";
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controlled5")
	public String secCateMatch5(String topId) {
		return "ControlledInputTextTEster....";
	}
	
	public void onActionFromDel() {
		
	}
	
	Object onSuccess() {
		jsSupport.addScript("alert('tetes');");
		return new TextStreamResponse("text/html", "<script type='text/javascript'>alert('testes');</script>");
	}

	@OnEvent(component = "inplace", value = InPlaceEditor.SAVE_EVENT)
	void setInplace(String context, String text) {
		System.out.println(text);
	}
	
	@OnEvent(value = SoupEventConstants.CONTROLL_DISPLAY, component = "controller")
	public JSONArray controllDisplay(String controllValue) {
		return new JSONArray(new JSONObject("id", "test", "value", "tetvalue"),new JSONObject("id", "test1", "value", "tetvalue1"));
	}
	
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
	
	@OnEvent(value = SoupEventConstants.CONTROLL_DISPLAY, component = "controllerLink")
	public JSONArray controllLinkDisplay() {
		return new JSONArray(new JSONObject("id", "test", "value", "编号获取"));
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "person")
	List<String> setPersonMatch(String name) {
		List<String> strs = new ArrayList<String>();
		strs.add("11");
		strs.add("22");
		return strs;
	}	
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "controllerInput3")
	List<String> setNumber2Match(String name) {
		List<String> strs = new ArrayList<String>();
		strs.add("轮胎修补");
		return strs;
	}	
	
	// http://jqueryui.com/autocomplete/#custom-data
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "project")
	List<JSONObject> setNumber2MatchByProject(String name) {
		List<JSONObject> strs = new ArrayList<JSONObject>();
		/*strs.add("11");
		strs.add("22");*/
		strs.add(new JSONObject("value", "jquery", "label", "JQuery", "desc", "the write less, do more, JavaScript library"));
		strs.add(new JSONObject("value", "jquery-ui", "label", "jQuery UI", "desc", "the official user interface library for jQuery"));
		return strs;
	}		
	
	public JSONObject getOptions(){
		JSONObject json = new JSONObject("minLength", "0");
		json.put("focus", "function( event, ui ) { $( \"#project\" ).val( ui.item.label ); return false; }");
		json.put("select", "function( event, ui ) { alert(ui.item.desc);$( \"#project\" ).val( ui.item.label ); $( \"#project-id\" ).val( ui.item.value ); $( \"#project-description\" ).html( ui.item.desc ); $( \"#controllerInput\" ).trigger(\"change\"); return false; }");
		json.put("renderItem", new JSONLiteral("function( ul, item ) {return $( \"<li>\" ).append( \"<a>\" + item.label + \"<br>\" + item.desc + \"</a>\" ).appendTo( ul );}"));
		return json;
	}
	
}
