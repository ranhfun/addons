package com.ranhfun.util.services;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldTranslator;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.NullFieldStrategy;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;

public class UtilModule {

	public static void bind(ServiceBinder binder)
	{
		binder.bind(ImageService.class, ImageServiceImpl.class);
		binder.bind(WaterService.class, WaterServiceImpl.class);
	}
	
	@SuppressWarnings("unchecked")
	@Advise(serviceInterface = FieldValidationSupport.class)
	public static void textfieldTrimParseMatches(MethodAdviceReceiver receiver) throws SecurityException, NoSuchMethodException {
		final TextFieldTrimAdvice trimAdvice = new TextFieldTrimAdvice();
		receiver.adviseMethod(receiver.getInterface().getMethod("parseClient", String.class, ComponentResources.class, FieldTranslator.class, NullFieldStrategy.class), trimAdvice);
	}
	
}
