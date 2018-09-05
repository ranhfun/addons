package org.example.app1.listener;

import org.example.app1.entities.Item;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;

@SuppressWarnings("serial")
public class ItemPostUpdateEventListener implements PostUpdateEventListener, PostInsertEventListener {

	public void onPostUpdate(PostUpdateEvent event) {
		if (event.getEntity() instanceof Item) {
			System.out.println("-----------------------");
		}
	}

	public void onPostInsert(PostInsertEvent event) {
		if (event.getEntity() instanceof Item) {
			System.out.println("-----------------------");
		}
	}

}
