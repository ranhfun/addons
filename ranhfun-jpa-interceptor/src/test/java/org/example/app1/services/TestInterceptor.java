package org.example.app1.services;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class TestInterceptor extends EmptyInterceptor {

	private TestService testService;
	
	public boolean onSave(Object entity, Serializable id,
			Object[] state, String[] propertyNames, Type[] types) {
		testService.print();
		for (int i = 0; i < propertyNames.length; i++) {
			if(propertyNames[i].equals("name")) {
				state[i] = "test2";
				return true;
			}
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}

	public void setTestService(TestService testService) {
		this.testService = testService;
	}
}
