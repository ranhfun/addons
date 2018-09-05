package org.example.soup.snack.data;

import org.apache.tapestry5.beaneditor.Validate;

public class MyObject {

	@Validate("required")
	private Integer objectId;
	
	@Validate("required")
	private String objectName;
	
	public MyObject() {}

	public MyObject(Integer objectId, String objectName) {
		this.objectId = objectId;
		this.objectName = objectName;
	}
	
	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
