package com.ranhfun.vaadin.ui;

import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DemoUI extends UI {

	private static final long serialVersionUID = 1L;
	
	protected VerticalLayout mainLayout;
	protected VerticalLayout contentLayout;
	
	@Override
	public void init(VaadinRequest request) {
		
	/*	Window mainWindow = new Window(
				"MyVaadin Demo Application");
		addWindow(mainWindow);*/
		getPage().setTitle("MyVaadin Demo Application");
		
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);
		mainLayout.setSizeFull();
		setContent(mainLayout);

		mainLayout.addComponent(createIconsInfoLabel());
		mainLayout.addComponent(new Button("test1", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				Notification.show("tet1111.......");
			}
		}));
		
		
		contentLayout = new VerticalLayout();
		contentLayout.addComponent(new Label("content"));
		mainLayout.addComponent(contentLayout);
		mainLayout.addComponent(new Button("but1", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				getPage().setUriFragment("page1");
				//get().showPage1();
			}
		}));
		mainLayout.addComponent(new Button("but2", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			public void buttonClick(ClickEvent event) {
				getPage().setUriFragment("page2");
				//get().showPage2();
			}
		}));
		getPage().addUriFragmentChangedListener(new UriFragmentChangedListener() {
			
			public void uriFragmentChanged(UriFragmentChangedEvent event) {
				if (event.getUriFragment().equals("page1")) {
					showPage1();
				} else if (event.getUriFragment().equals("page2")) {
					showPage2();
				}
			}
		});
	}

	public void showPage1() {
		contentLayout.removeAllComponents();
		contentLayout.addComponent(new Page1());
	}
	
	public void showPage2() {
		contentLayout.removeAllComponents();
		contentLayout.addComponent(new Page2());
	}
	
	private Label createIconsInfoLabel() {
		Label label = new Label(
				"Icons from <a href=\"http://www.fatcow.com/free-icons\" target=\"_parent\">FatCow.com</a>",
				Label.CONTENT_XHTML);
		label.setSizeUndefined();
		return label;
	}
	
}
