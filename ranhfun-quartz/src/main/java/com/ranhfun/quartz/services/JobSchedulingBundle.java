package com.ranhfun.quartz.services;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * job detail/trigger bundle.
 *
 * @version $Id: JobSchedulingBundle.java 358 2008-11-25 12:52:26Z homburgs $
 */
@SuppressWarnings({"JavaDoc"})
public interface JobSchedulingBundle
{
    /**
     * get the scheduler id.
     * <p/>
     * may be null
     */
    String getSchedulerId();

    /**
     * get the job detail.
     */
    JobDetail getJobDetail();

    /**
     * get the trigger.
     */
    Trigger getTrigger();
}
