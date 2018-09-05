package org.example.app1.pages;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.jpa.annotations.CommitAfter;
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
		item = new Item();
		item.setName("test2");
		em.persist(item);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Item> root = criteria.from(Item.class);
		criteria.select(builder.count(root));
		em.flush();
		item = em.find(Item.class, 1L);
		System.out.println(item.getName());
		return new TextStreamResponse("text/plain", em.createQuery(criteria).getSingleResult().toString());
	}
	
}
