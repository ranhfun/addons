package com.ranhfun.resource.pages;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.Cookies;

import com.ranhfun.resource.data.Score;

@Import(library = "front:style/js/main.js")
public class Index {

	@Persist
	@Property
	private Score score;
	
	@Inject
	private Cookies cookies;
	
	private AtomicLong atomicLong = new AtomicLong(115);
	
	@Inject
	private AssetSource assetSource;
	
	@Inject
	private BaseURLSource baseURLSource;
	
	void setupRender() {
		score = new Score();
		if (StringUtils.isEmpty(cookies.readCookieValue("city"))) {
			cookies.writeCookieValue("city", "115");
		}
		System.out.println(assetSource.getExpandedAsset("front:style/js/main.js"));
		//baseURLSource.getBaseURL(false) + assetSource.getExpandedAsset("front:style/js/rater.js").toClientURL();
	}
	
	public String getCity() {
		return cookies.readCookieValue("city");
	}
	
	public void onActionFromReset() {
		System.out.println("before============" + cookies.readCookieValue("city"));
		cookies.writeCookieValue("city", ""+atomicLong.incrementAndGet());
		System.out.println("after============" + cookies.readCookieValue("city"));
	}
	
}
