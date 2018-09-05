package com.ranhfun.quartz.services;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.PropertyShadowBuilder;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;

import com.ranhfun.quartz.JDBCScheduler;
import com.ranhfun.quartz.JDBCSchedulerConstants;
import com.ranhfun.quartz.services.JobSchedulingBundle;
import com.ranhfun.quartz.services.QuartzJobListener;
import com.ranhfun.quartz.services.QuartzModule;

@SubModule(QuartzModule.class)
public class QuartzJdbcModule {
	
	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void provideFactoryDefaults(
			final MappedConfiguration<String, String> configuration) {
		configuration.add(JDBCSchedulerConstants.QUARTZ_JDBC_FILE, "quartz-jdbc.properties");
	}
	
    @Marker(JDBCScheduler.class)
    public SchedulerFactory buildJDBCSchedulerFactory(Logger logger,
            RegistryShutdownHub shutdownHub, List<JobSchedulingBundle> jobSchedulingBundles,
            @Symbol(JDBCSchedulerConstants.QUARTZ_JDBC_FILE)
            String quartzFile) { 
        if (logger.isInfoEnabled())
            logger.info("initialize JDBC Scheduler factory");

        try
        {
        	ClasspathResource config = new ClasspathResource(quartzFile);
        	Properties properties = new Properties();
        	try {
    			properties.load(config.openStream());
    		} catch (IOException e) {
    			throw new RuntimeException("Configuration to RAM SchedulerFactory services needed");
    		}
        	
            final SchedulerFactory factory = new StdSchedulerFactory(properties);

            shutdownHub.addRegistryShutdownListener(new Runnable()
            {
				public void run()
				{
					try
					{
						List<Scheduler> schedulers = CollectionFactory.newList(factory.getAllSchedulers());
						for (Scheduler scheduler : schedulers) {
							scheduler.shutdown();

				            int ct = 0;

				            // Try waiting for the scheduler to shutdown. Only wait 30 seconds.
				            while(ct < 30) {
				                ct++;
				                // Sleep for a second so the quartz worker threads die.  This 
				                // suppresses a warning from Tomcat during shutdown.
				                Thread.sleep(1000);
				                if (scheduler.isShutdown()) {
				                    break;
				                }
				            }
						}
					}
					catch (SchedulerException e)
					{
						throw new RuntimeException(e);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

            });

            Scheduler scheduler;

            try
            {
               scheduler = factory.getScheduler();
            }
            catch (SchedulerException e)
            {
                throw new RuntimeException(e);
            }
            
            for (JobSchedulingBundle jobSchedulingBundle : jobSchedulingBundles)
                addBundleToScheduler(scheduler, jobSchedulingBundle, logger);
            
            scheduler.start();
            return factory;
        }
        catch (SchedulerException se)
        {
            throw new RuntimeException(se);
        }
    }
    
    @SuppressWarnings({"JavaDoc"})
    private void addBundleToScheduler(Scheduler scheduler, JobSchedulingBundle jobSchedulingBundle, Logger logger) throws SchedulerException
    {
        JobDetail jobDetail = jobSchedulingBundle.getJobDetail();
        Trigger trigger = jobSchedulingBundle.getTrigger();
        String schedulerId = jobSchedulingBundle.getSchedulerId();

        if (schedulerId == null || schedulerId.length() == 0)
            schedulerId = "default";

        if (trigger != null)
        {

            if (logger.isInfoEnabled())
            {
                String triggerName = trigger.getKey().getName();
                if (trigger instanceof CronTrigger)
                    triggerName += " (" + ((CronTrigger)trigger).getCronExpression() + ")";

                logger.info("schedule job '{}' with trigger '{}' to scheduler '{}'",
                            new Object[]{jobDetail.getKey().getName(), triggerName, schedulerId});
            }

            scheduler.scheduleJob(jobDetail, trigger);
        }
        else
        {
            if (logger.isInfoEnabled())
                logger.info("add job '{}' to scheduler '{}'", jobDetail.getKey().getName(), schedulerId);

            scheduler.addJob(jobDetail, true);
        }
    }
    
    @Marker(JDBCScheduler.class)
    @EagerLoad
	public static Scheduler buildJDBCScheduler(@JDBCScheduler SchedulerFactory schedulerFactory,
            PropertyShadowBuilder propertyShadowBuilder, JobFactory jobFactory,
            PerthreadManager perthreadManager) throws SchedulerException {
		Scheduler scheduler = propertyShadowBuilder.build(schedulerFactory, "scheduler", Scheduler.class);
		scheduler.setJobFactory(jobFactory);
		scheduler.getListenerManager().addJobListener(new QuartzJobListener(perthreadManager));
		return scheduler;
	}   
	
}
