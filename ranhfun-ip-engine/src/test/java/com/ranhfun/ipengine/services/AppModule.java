package com.ranhfun.ipengine.services;

import java.net.URL;
import java.util.Arrays;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.PipelineBuilder;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.slf4j.Logger;

import com.ranhfun.ipengine.services.IPEngineModule;

@SubModule({IPEngineModule.class})
public class AppModule {
	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	       //configuration.add(JQueryConstants.SUPPRESS_PROTOTYPE, "false");
	}

    public static void contributeIPSeeker(OrderedConfiguration<Resource> configuration)
    {
    	configuration.add("QQIp.data", new ClasspathResource("QQWry.dat"));
    }
    
    @Startup
    public static void startupP(PipelineBuilder builder, Logger logger) {
        StandardFilter subtracter = new StandardFilter()
        {
            public int run(int i, StandardService service)
            {
                return service.run(i) - 2;
            }
        };

        StandardFilter multiplier = new StandardFilter()
        {
            public int run(int i, StandardService service)
            {
                return 2 * service.run(i);
            }
        };

        StandardFilter adder = new StandardFilter()
        {
            public int run(int i, StandardService service)
            {
                return service.run(i + 3);
            }
        };

        StandardService terminator = new StandardService()
        {
            public int run(int i)
            {
                return i;
            }
        };

        StandardService pipeline = builder.build(
                logger,
                StandardService.class,
                StandardFilter.class,
                Arrays.asList(subtracter, multiplier, adder),
                terminator);
        
        System.out.println(pipeline.run(5));
        System.out.println(pipeline.run(10));
    }
    
    public interface StandardFilter
    {
        public int run(int i, StandardService service);
    }
    
    public interface StandardService
    {
        public int run(int i);
    }
}
