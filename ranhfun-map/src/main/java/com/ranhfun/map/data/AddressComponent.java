package com.ranhfun.map.data;

import java.io.Serializable;

public class AddressComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 城市名
	 */
	private String city;
	
	/**
	 * 区县名
	 */
	private String district;
	
	/**
	 * 省名
	 */
	private String province;
	
	/**
	 * 街道名
	 */
	private String street;
	
	/**
	 * 街道门牌号
	 */
	private String streetNumber;
	
	public AddressComponent() {
	}
	
	public AddressComponent(String city, String district, String province,
			String street, String streetNumber) {
		this.city = city;
		this.district = district;
		this.province = province;
		this.street = street;
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
}
