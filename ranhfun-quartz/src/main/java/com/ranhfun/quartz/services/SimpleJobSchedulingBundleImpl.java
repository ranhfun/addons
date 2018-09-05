package com.ranhfun.quartz.services;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * a simple implementation of a job detail/trigger bundle.
 *
 * @version $Id: SimpleJobSchedulingBundleImpl.java 670 2010-07-19 09:22:02Z mlusetti $
 */
public class SimpleJobSchedulingBundleImpl implements JobSchedulingBundle
{
    private JobDetail jobDetail;
    private Trigger trigger;
    private String schedulerId;

    public SimpleJobSchedulingBundleImpl(JobDetail jobDetail, Trigger trigger)
    {
        this(null, jobDetail, trigger);
    }

    public SimpleJobSchedulingBundleImpl(String schedulerId, JobDetail jobDetail, Trigger trigger)
    {
        assert jobDetail != null;
        assert trigger != null;

        this.schedulerId = schedulerId;
        this.jobDetail = jobDetail;
        this.trigger = trigger;
    }

    /**
     * get the scheduler id.
     * <p/>
     * may be null
     */
    public String getSchedulerId()
    {
        return schedulerId;
    }

    /**
     * get the job detail.
     */
    public JobDetail getJobDetail()
    {
        return jobDetail;
    }

    /**
     * get the trigger.
     */
    public Trigger getTrigger()
    {
        return trigger;
    }
}
