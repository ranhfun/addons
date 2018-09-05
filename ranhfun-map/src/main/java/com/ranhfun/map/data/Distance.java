package com.ranhfun.map.data;

import java.io.Serializable;

/**
 * 线路距离
 * @author virgil
 *
 */
public class Distance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 文本描述
	 */
	private String text;
	/**
	 * 数值
	 */
	private String value;
	
	public Distance() {
	}
	
	public Distance(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return String.format("Distance[text:%s, value:%s]", text, value);
	}
	
}
