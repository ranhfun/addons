package org.example.soup.snack.pages;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.util.EnumSelectModel;
import org.apache.tapestry5.util.EnumValueEncoder;
import org.example.soup.snack.data.ProgrammingLanguage;

public class ControlledSelectChecklistTest {

	@Property
	private String color;
	
	public String getOutput() {
		return "test";
	}
	
/*	Object onProvidecompletionsFromOutput(Long colorId,String value) {
		return value;
	}*/
	
	public Long getColorId() {
		return 1L;
	}
	
    @Property
    @Persist
    private List<ProgrammingLanguage> handling;

    @Inject
    private Messages messages;
    
    @Inject
    private TypeCoercer typeCoercer;

    public ValueEncoder getEncoder() {
        return new EnumValueEncoder(typeCoercer, ProgrammingLanguage.class);
    }

    public SelectModel getModel() {
        return new EnumSelectModel(ProgrammingLanguage.class, messages);
    }
	
}
