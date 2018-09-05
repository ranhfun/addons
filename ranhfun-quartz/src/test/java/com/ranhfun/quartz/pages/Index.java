package com.ranhfun.quartz.pages;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;

import com.ranhfun.quartz.RAMScheduler;
import com.ranhfun.quartz.services.RefreshJob;

public class Index {

	@Inject
	private Logger logger;
	
	@Inject
	@RAMScheduler
	private Scheduler scheduler;
	
	void onActionFromTrigger() {
    	JobDetail refreshPriceDetail = newJob(RefreshJob.class).withIdentity(RefreshJob.class.getName() + "1").build();
		JobDataMap map = new JobDataMap();
		map.put("item", 1);
    	Trigger trigger = newTrigger().withIdentity(RefreshJob.class.getName() + "1")
    						.startAt(new Date()).forJob(refreshPriceDetail).usingJobData(map).build();
		try {
			scheduler.scheduleJob(refreshPriceDetail, trigger);
		} catch (SchedulerException e) {
			logger.error("refresh price schedule[CollStandardPriceTimeInsertEvent] " + e.getMessage(), e);
		}
	}
	
}
