package com.ranhfun.quartz.services;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;

public class QuartzJobFactory implements JobFactory {

	private ObjectLocator locator;
	
	private Logger logger;
	
	public QuartzJobFactory(ObjectLocator locator, Logger logger) {
		this.locator = locator;
		this.logger = logger;
	}
	
	public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler)
			throws SchedulerException {
        JobDetail jobDetail = bundle.getJobDetail();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        try {
            if(logger.isDebugEnabled()) {
            	logger.debug(
                    "Producing instance of Job '" + jobDetail.getKey() + 
                    "', class=" + jobClass.getName());
            }
            return locator.autobuild(jobClass);
        } catch (Exception e) {
            SchedulerException se = new SchedulerException(
                    "Problem instantiating class '"
                            + jobDetail.getJobClass().getName() + "'", e);
            throw se;
        }
	}

}
