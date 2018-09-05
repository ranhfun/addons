package com.ranhfun.soup.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import com.ranhfun.soup.data.MyObject;

public class CheckGroupTest {

	@Property
	private String editor;
	
	@Persist
	private List<MyObject> myos;
	
	private boolean selected;
	
	@Property
	private MyObject obj;
	
	@Persist
	@Property
	private boolean selectAll;
	
	void beginRender() {
		if (myos==null) {
			myos = CollectionFactory.newList();
		}
	}
	
	public boolean isSelected() {
		return myos.contains(obj);
	}
	
	public void setSelected(boolean selected) {
		if (selected) {
			getMyos().add(obj);
		} else {
			getMyos().remove(obj);
		}
	}
	
	public List<MyObject> getTestos() {
		List<MyObject> objs = new ArrayList<MyObject>(3);
		for (int i = 0; i < 3; i++) {
			objs.add(new MyObject(i, "test" + i));
		}
		return objs;
	}

	public List<MyObject> getMyos() {
		return myos;
	}

	public void setMyos(List<MyObject> myos) {
		this.myos = myos;
	}
}
