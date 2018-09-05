package com.ranhfun.jquery.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;

import com.ranhfun.jquery.data.ItemData;

public class Index {

	//@Validate("required,maxlength=20,regexp=^[1-9]\\d{5}$")
	@Persist
	@Property
	private String number;
	
	//@Validate("required")
	@Persist
	@Property
	private Double number2;
	
	@Persist
	@Property
	private List<ItemData> datas;
	
	@Property
	private ItemData data;
	
	private final AtomicInteger tempIdGenerator = new AtomicInteger(0);
	
	void setupRender() {
		//number2 = 3D;
		datas = new ArrayList<ItemData>();
	}
	
	ItemData onAddRowFromDatas() {
		ItemData data = new ItemData();
		data.setTempId(tempIdGenerator.incrementAndGet());
		datas.add(data);
		return data;
	}

	void onRemoveRowFromDatas(ItemData data) {
		datas.remove(data);
	}
	
	public ValueEncoder<ItemData> getDataEncoder() {
		return new ValueEncoder<ItemData>() {

			public String toClient(ItemData value) {
				return String.valueOf(value.getTempId());
			}

			public ItemData toValue(String tempIdStr) {
				Integer tempId = Integer.valueOf(tempIdStr);
				for (ItemData data : datas) {
					if (data.getTempId()==tempId) {
						return data;
					}
				}
				return null;
			}
		};
	}
}
