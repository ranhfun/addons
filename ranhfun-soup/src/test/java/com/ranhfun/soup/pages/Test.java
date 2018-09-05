package com.ranhfun.soup.pages;

import java.io.File;
import java.io.IOException;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.services.ContextResource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.ContextProvider;

import com.ranhfun.soup.components.InPlaceEditor;

public class Test {
	
	@Property
	private String value;
	
	@Property
	private Integer index;
	
	@Inject
	private Context context;
	
	@Inject
	@ContextProvider
	private AssetFactory assetFactory;
	
	void setupRender() throws IOException {
		value = "dafdsda";
		index = 7;
		File file = new File(new ContextResource(context, "templates/member.xls").getPath());
		System.out.println(file.getCanonicalPath());
	}
	
	@OnEvent(component = "lastName", value = InPlaceEditor.SAVE_EVENT)
	void setLastName(Integer index,String value)
	{
		System.out.println(value);
	}
	
	void onActionFromLastName2()
	{
		System.out.println("fsfads");
	}
}
