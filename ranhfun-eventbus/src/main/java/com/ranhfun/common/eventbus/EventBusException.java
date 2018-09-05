package com.ranhfun.common.eventbus;

public class EventBusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Throwable target;

	protected EventBusException() {
		super((Throwable) null); // Disallow initCause
	}

	public EventBusException(Throwable target) {
		super((Throwable) null); // Disallow initCause
		this.target = target;
	}

	public EventBusException(Throwable target, String s) {
		super(s, null); // Disallow initCause
		this.target = target;
	}

	public Throwable getTargetException() {
		return target;
	}
}
