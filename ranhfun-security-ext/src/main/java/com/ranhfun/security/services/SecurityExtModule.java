package com.ranhfun.security.services;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityModule;

import com.ranhfun.security.Ext;

@SubModule(SecurityModule.class)
public class SecurityExtModule {

	@SuppressWarnings("unchecked")
	public static void bind(final ServiceBinder binder)
	{
		binder.bind(WebSecurityManager.class, ExtRealmSecurityManager.class).withSimpleId().withMarker(Ext.class);
		binder.bind(ExtSecurityManager.class, ExtSecurityManagerImpl.class);
		binder.bind(ComponentRequestFilter.class, TouchComponentRequestFilter.class).withId("TouchComponentRequestFilter");
	}
	
	public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
	{
		
	}
	
	@Contribute(ServiceOverride.class)
	public static void setupApplicationServiceOverrides(MappedConfiguration<Class,Object> configuration,
			@Local WebSecurityManager webSecurityManager) {
		configuration.add(WebSecurityManager.class, webSecurityManager);
	}
	
	public static void contributeComponentRequestHandler(OrderedConfiguration<ComponentRequestFilter> configuration,
            @Local ComponentRequestFilter filter) {
		configuration.add("TouchFilter", filter, "after:SecurityFilter", "before:*");
	}
	
	@Advise(serviceInterface = LoginContextService.class)
	public static void adviseSecurityAssert(MethodAdviceReceiver receiver, final HttpServletRequest request) {
		Class<?> serviceInterface = receiver.getInterface();

		for (Method method : serviceInterface.getMethods()) {
			if (method.getName().equals("saveRequest")) {
				MethodAdvice advice = new MethodAdvice() {
					public void advise(MethodInvocation invocation) {
						String requestUri = WebUtils.getPathWithinApplication(request);
						if (requestUri.contains("listen.icepush")) {
							return ;
						}
						invocation.proceed();
					}
				};
				receiver.adviseMethod(method, advice);				
			}
		}
	}
}
