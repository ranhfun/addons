package org.example.app1.pages;

import javax.persistence.EntityManager;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.jpa.annotations.CommitAfter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.util.TextStreamResponse;
import org.example.app1.entities.Item;

public class Index {

	@Inject
	private EntityManager em;
	
	@CommitAfter
	Object onActivate() {
		Item item = new Item();
		item.setName("test1");
		em.persist(item);
		return new TextStreamResponse("text/plain", item.getName());
	}
	
}
