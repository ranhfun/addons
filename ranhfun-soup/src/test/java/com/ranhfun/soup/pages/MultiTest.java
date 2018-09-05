package com.ranhfun.soup.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

@Import(stylesheet={"jquery-ui.css","style.css"})
public class MultiTest {

	@Persist
	@Property
	private Integer number2;
	
	@Persist
	@Property
	private List<String> values;
	
	void onSuccess() {
		for (String value : values) {
			System.out.println(value);
		}
	}
	
	public SelectModel getModel() {
		return new AbstractSelectModel() {
			
			public List<OptionModel> getOptions() {
				List<OptionModel> opts = new ArrayList<OptionModel>(); 
				for (int i = 0; i < 10; i++) {
					opts.add(new OptionModelImpl("T" + i, "VA" + i));
				}
				return opts;
			}
			
			public List<OptionGroupModel> getOptionGroups() {
				return null;
			}
		};
	}
	
	public ValueEncoder<String> getEncoder() {
		return new ValueEncoder<String>() {

			public String toClient(String value) {
				return value;
			}

			public String toValue(String clientValue) {
				return clientValue;
			}
		};
	}
	
	@OnEvent(value = EventConstants.PROVIDE_COMPLETIONS, component = "select")
	public String secCateMatch22(String topId) {
		return "<select><option value='t1'>测试</option><option value='t2'>测试2</option></select>";
	}
}
