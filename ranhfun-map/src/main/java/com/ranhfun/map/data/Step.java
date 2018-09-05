package com.ranhfun.map.data;

import java.io.Serializable;

public class Step implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 进入道路的角度
	 * 枚举值，返回值在0-11之间的一个值，共12个枚举值，以30度递进，即每个值代表角度范围为30度；其中返回"0"代表345度到15度，以此类推，返回"11"代表315度到345度"；分别代表的含义是：0-[345°-15°]；1-[15°-45°]；2-[45°-75°]；3-[75°-105°]；4-[105°-135°]；5-[135°-165°]；6-[165°-195°]；7-[195°-225°]；8-[225°-255°]；9-[255°-285°]；10-[285°-315°]；11-[315°-345°] 
	 */
	private int direction;
	
	/**
	 * 路段距离   
	 * 单位：米 
	 */
	private int distance;
	
	/**
	 * 路段耗时
	 * 单位：秒 
	 */
	private int duration;
	
	/**
	 * 路段描述 
	 */
	private String instruction;
	
	/**
	 * 路段位置坐标描述 
	 * 如"116.30815169816,40.057096534337"
	 */
	private String path;
	
	/**
	 * 路段等级类别 
	 * 枚举值，返回在0-9之间的一个值，共10个枚举值；分别代表的含义是：0-高速公路；1-城市高速路；2-国道；3-省道；4-县道；5-乡镇村道；6-其他道路；7-九级路；8-航线；9-行人道路 
	 */
	private int type;
	
	/**
	 * 机动转向点，包括基准八个方向、环岛、分歧等 
	 * 枚举值，返回0-16之间的一个值，共17个枚举值；分别代表的含义是：0-无效；1-直行；2-右前方转弯；3-右转；4-右后方转弯；5-掉头；6-左后方转弯；7-左转；8-左前方转弯；9-左侧；10-右侧；11-分歧-左；12-分歧中央；13-分歧右；14-环岛；15-进渡口；16-出渡口 
	 */
	private int turn;
	
	/**
	 * 路段起点经度/纬度
	 */
	private Location stepOrigin;
	
	/**
	 * 路段终点经度/纬度
	 */
	private Location stepDestination;

	public Step() {
	}

	public Step(int direction, int distance, int duration, String instruction,
			String path, int type, int turn, Location stepOrigin,
			Location stepDestination) {
		this.direction = direction;
		this.distance = distance;
		this.duration = duration;
		this.instruction = instruction;
		this.path = path;
		this.type = type;
		this.turn = turn;
		this.stepOrigin = stepOrigin;
		this.stepDestination = stepDestination;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
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

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Location getStepOrigin() {
		return stepOrigin;
	}

	public void setStepOrigin(Location stepOrigin) {
		this.stepOrigin = stepOrigin;
	}

	public Location getStepDestination() {
		return stepDestination;
	}

	public void setStepDestination(Location stepDestination) {
		this.stepDestination = stepDestination;
	}
}
