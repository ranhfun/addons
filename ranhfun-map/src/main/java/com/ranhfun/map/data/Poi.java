package com.ranhfun.map.data;

import java.io.Serializable;

public class Poi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 地址信息
	 */
	private String addr;
	
	/**
	 * 数据来源
	 */
	private String cp;
	
	/**
	 * 离坐标点距离
	 */
	private String distance;
	
	/**
	 * poi名称
	 */
	private String name;
	
	/**
	 * poi类型，如’ 办公大厦,商务大厦’
	 */
	private String poiType;
	
	/**
	 * poi坐标{x,y} - {lng,lat}
	 */
	private Location point; 
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * poi唯一标识
	 */
	private String uid;
	
	/**
	 * 邮编
	 */
	private String zip;

	public Poi() {
	}
	
	public Poi(String addr, String cp, String distance, String name, String poiType,
			Location point, String tel, String uid, String zip) {
		this.addr = addr;
		this.cp = cp;
		this.distance = distance;
		this.name = name;
		this.poiType = poiType;
		this.point = point;
		this.tel = tel;
		this.uid = uid;
		this.zip = zip;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}

	public Location getPoint() {
		return point;
	}

	public void setPoint(Location point) {
		this.point = point;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
