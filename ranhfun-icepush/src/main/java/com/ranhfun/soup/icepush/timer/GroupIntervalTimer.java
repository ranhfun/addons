package com.ranhfun.soup.icepush.timer;

import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.ranhfun.soup.icepush.services.PushContextHolder;

public class GroupIntervalTimer implements Runnable {

	private Timer timer;
	private Map<String, TimerTask> timerTasks;
	private PushContextHolder pushContextHolder;

	public GroupIntervalTimer(PushContextHolder pushContextHolder) {
		this.pushContextHolder = pushContextHolder;
		timer = new Timer(true);
		timerTasks = new Hashtable<String, TimerTask>();
	}

	public void addGroup(String group, long interval)
			throws IllegalStateException {
		GroupTimerTask timerTask = (GroupTimerTask) timerTasks.get(group);
		if (timerTask == null) {
			if (interval > 0) {
				// New group;
				startTimerTask(group, interval);
			}
		} else {
			// Existing group;
			if (interval > 0) {
				if (interval != timerTask.getInterval()) {
					// Start with new interval;
					timerTask.cancel();
					startTimerTask(group, interval);
				} else {
					// Same interval so do nothing;
				}
			} else {
				// Interval zero, so get rid of it;
				timerTasks.remove(group);
			}
		}
	}

	private void startTimerTask(String group, long interval)
			throws IllegalStateException {
		GroupTimerTask timerTask = new GroupTimerTask(group, interval);
		timerTasks.put(group, timerTask);
		try {
			timer.scheduleAtFixedRate(timerTask, interval, interval);
		} catch (Exception e) {
			throw new IllegalStateException(
					"GroupIntervalTimer could not start timerTask for group: "
							+ group);
		}
	}

	private class GroupTimerTask extends TimerTask {
		private long interval;
		private String group;

		public GroupTimerTask(String group, long interval) {
			super();
			this.interval = interval;
			this.group = group;
		}

		public long getInterval() {
			return interval;
		}

		@SuppressWarnings("unused")
		public void setInterval(long interval) {
			this.interval = interval;
		}

		public void run() {
			if (pushContextHolder.isSet()) {
				pushContextHolder.get().push(group);
			}
		}
	}

	public void run() {
		timer.cancel();		
	}
	
}
