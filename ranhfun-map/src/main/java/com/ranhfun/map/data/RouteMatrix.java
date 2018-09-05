package com.ranhfun.map.data;

import java.io.Serializable;

public class RouteMatrix implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Distance distance;
	private Duration duration;
	
	public RouteMatrix() {
	}
	
	public RouteMatrix(Distance distance, Duration duration) {
		this.distance = distance;
		this.duration = duration;
	}

	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public String toString() {
		return String.format("RouteMatrix[<%s>, <%s>]", distance.toString(), duration.toString());
	}
}
