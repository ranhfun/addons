package com.ranhfun.soup.pages;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

import com.ranhfun.soup.annotations.ScrollClean;
import com.ranhfun.soup.components.IScroll;

@Import(library={/*"iscroll.js","mobile.js"*/}/*, stylesheet={"mobile.css","icon.css"}*/)
public class Scroll {

	@Inject
	private ComponentResources resources;
	
	@Persist
	private Integer page;
	
	@ScrollClean
	@InjectComponent
	private IScroll wrapper;
	
	void onActivate(@RequestParameter(value="page", allowBlank=true) Integer page) {
		this.page = page;
	}
	
	Integer onPassivate() {
		return page;
	}
	
	void setupRender() {
		wrapper.setCurrentPage(1);
	}
	
	public Integer getPage() {
		return page;
	}
	
	public GridDataSource getSource() {
		return new GridDataSource() {
			
			@Override
			public void prepare(int startIndex, int endIndex,
					List<SortConstraint> sortConstraints) {
				
			}
			
			@Override
			public Object getRowValue(int index) {
				return "pretty Row" + index;
			}
			
			@Override
			public Class getRowType() {
				return String.class;
			}
			
			@Override
			public int getAvailableRows() {
				return 20;
			}
		};
	}
	
	void onActionFromClear() {
		wrapper.getSortModel().clear();
	}
}
