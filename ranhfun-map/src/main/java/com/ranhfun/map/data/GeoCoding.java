package com.ranhfun.map.data;

import java.io.Serializable;

public class GeoCoding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 经纬度坐标
	 */
	private Location location;
	/**
	 * 位置的附加信息，是否精确查找。1为精确查找，0为不精确。
	 */
	private int precise;
	/**
	 * 可信度
	 */
	private int confidence;
	/**
	 * 地址类型
	 */
	private String level;
	
	/**
	 * 结构化地址信息
	 */
	private String formattedAddress;
	
	/**
	 * 所在商圈信息，如 "人民大学,中关村,苏州街"
	 */
	private String business;
	
	private AddressComponent addressComponent;
	
	private Poi[] pois;
	
	public GeoCoding() {
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getPrecise() {
		return precise;
	}
	public void setPrecise(int precise) {
		this.precise = precise;
	}
	public int getConfidence() {
		return confidence;
	}
	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public Poi[] getPois() {
		return pois;
	}

	public void setPois(Poi[] pois) {
		this.pois = pois;
	}
}
