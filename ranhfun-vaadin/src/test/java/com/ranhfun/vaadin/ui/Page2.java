package com.ranhfun.vaadin.ui;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Page2 extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public Page2() {
		addComponent(new Label("PAGE 2"));
		/*VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.addComponent(new Label("PAGE 2"));
        setCompositionRoot(layout);*/
	}
	
}
