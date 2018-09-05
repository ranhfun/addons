package com.ranhfun.quartz.services;

import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class QuartzJobListener implements JobListener {

	private PerthreadManager perthreadManager;
	
	private static final String QUARTZ_JOB_LISTENER = "Quartz.JobListener";
	
	public QuartzJobListener(PerthreadManager perthreadManager) {
		this.perthreadManager = perthreadManager;
	}
	
	public String getName() {
		return QUARTZ_JOB_LISTENER;
	}

	public void jobToBeExecuted(JobExecutionContext context) {

	}

	public void jobExecutionVetoed(JobExecutionContext context) {

	}

	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		perthreadManager.cleanup();
	}

}
