package org.example.soup.snack.pages;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.example.soup.snack.data.MyObject;

import com.ranhfun.soup.snack.components.DateTimeField;

/**
 * Start page of application snack.
 */
public class Index
{
	public Date getCurrentTime() 
	{ 
		return new Date(); 
	}
	
	@Property
	private String editor;
	
	@Persist
	@Property
	private String number;
	
	@Persist
	@Property
	private String number2;
	
	@Persist
	@Property
	private String number3;
	
	@Property
	private Map<String, Object> propertyCheckMap;
	
	@Property
	private Map<String, Object> propertyAliasMap;
	
	@Property
	private MyObject object;
	
	@Component(parameters = {"value=actualDate1", "datePattern=dd-MM-yyyy HH:mm"})
	private DateTimeField dateTimeField1;

	@Component(parameters = {"value=actualDate2"})
	private DateTimeField dateTimeField2;

	@Component(parameters = {"value=actualDate3", "timePicker=true", "datePattern=dd-MM-yyyy HH:mm"})
	private DateTimeField dateTimeField3;

	@Component(parameters = {"value=actualDate4", "datePicker=false", "timePicker=true", "datePattern=HH:mm"})
	private DateTimeField dateTimeField4;

	@Property
	private Date actualDate1;
	@Property
	private Date actualDate2;
	@Property
	private Date actualDate3;
	@Property
	private Date actualDate4;
	
	void beginRender() {
		/*number = "1";
		number2 = "2";*/
	}
	
	String onProvidecompletionsFromNumber2(String value1) {
		return "<select><option>请选择</option><option selected='selected'>1</option></select>";
	}
	
	String onProvidecompletionsFromNumber3(String value) {
		return "<select><option>请选择</option><option>2</option></select>";
	}
	
	void onSuccess() {
		System.out.println("............." + number);
		/*System.out.println(propertyAliasMap.toString());
		System.out.println(propertyCheckMap.toString());*/
	}
}
