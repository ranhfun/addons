package com.ranhfun.map.data;

import java.io.Serializable;

public class Route implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 方案距离   
	 * 单位：米 
	 */
	private int distance;
	
	/**
	 * 线路耗时
	 * 单位：秒 
	 */
	private int duration;
	
	private Step[] steps;

	/**
	 * 过路费 
	 */
	private int toll;
	
	/**
	 * 起点经纬度坐标 
	 */
	private Location origin;
	
	/**
	 * 终点经纬度坐标 
	 */
	private Location destination;

	public Route() {
	}
	
	public Route(int distance, int duration, Step[] steps, int toll,
			Location origin, Location destination) {
		this.distance = distance;
		this.duration = duration;
		this.steps = steps;
		this.toll = toll;
		this.origin = origin;
		this.destination = destination;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Step[] getSteps() {
		return steps;
	}

	public void setSteps(Step[] steps) {
		this.steps = steps;
	}

	public int getToll() {
		return toll;
	}

	public void setToll(int toll) {
		this.toll = toll;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}
}
