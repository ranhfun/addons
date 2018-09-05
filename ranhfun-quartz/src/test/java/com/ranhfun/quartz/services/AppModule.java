package com.ranhfun.quartz.services;

import static org.quartz.JobBuilder.newJob;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;

import com.ranhfun.quartz.RAMScheduler;
import com.ranhfun.quartz.services.JobSchedulingBundle;
import com.ranhfun.quartz.services.QuartzModule;
import com.ranhfun.quartz.services.SimpleJobSchedulingBundleImpl;

@SubModule(QuartzModule.class)
public class AppModule {

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(
			final MappedConfiguration<String, String> configuration) {
	       configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
	       configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
	}
	
	@RAMScheduler
    @Contribute(SchedulerFactory.class)
    public static void provideRAMSchedulerManager(OrderedConfiguration<JobSchedulingBundle> configuration) {
		JobDetail refreshPriceDetail = newJob(RefreshJob.class).withIdentity(RefreshJob.class.getName()).storeDurably().build();
		configuration.add("RefreshPriceJob.job", new SimpleJobSchedulingBundleImpl(refreshPriceDetail, null));
	} 
	
}
