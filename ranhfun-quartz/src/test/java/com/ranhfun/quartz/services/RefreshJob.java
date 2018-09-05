package com.ranhfun.quartz.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

public class RefreshJob implements Job{

	@Inject
	private Logger logger;
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap triggerMap = context.getTrigger().getJobDataMap();
		try {
			logger.info("success" + triggerMap.get("item"));
		} catch (Exception e) {
			logger.error("error refresh price + " + e.getMessage(), e);
			JobExecutionException e2 = new JobExecutionException(e);
			e2.setUnscheduleAllTriggers(true);
		}
		
	}

}
