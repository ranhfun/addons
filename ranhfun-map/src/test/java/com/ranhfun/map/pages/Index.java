package com.ranhfun.map.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ranhfun.map.data.GeoCoding;
import com.ranhfun.map.data.Location;
import com.ranhfun.map.data.Route;
import com.ranhfun.map.data.RouteMatrix;
import com.ranhfun.map.services.BaiduService;

public class Index {

	@Inject
	private BaiduService baiduService;
	
	void onActionFromGeoconf() {
		Location[] locations = new Location[2];
		locations[0] = new Location(114.21892734521f, 29.575429778924f);
		locations[1] = new Location(114.21892734521f, 29.575429778924f);
		Location[] items = baiduService.geoconv(locations);
		System.out.println(items.length);
	}
	
	void onActionFromGeocoder() {
		GeoCoding geoCoding = baiduService.geocoder("百度大厦");
		System.out.println(geoCoding.getLocation());
	}
	
	void onActionFromGeocoderReverse() {
		GeoCoding geoCoding = baiduService.geocoder(new Location(116.322987f, 39.983424f), 1);
		System.out.println(geoCoding.getFormattedAddress());
	}
	
	void onActionFromRoutematrix() {
		RouteMatrix routeMatrix = baiduService.routematrix(new Location(116.30815f, 40.056878f), new Location(116.403857f, 39.915285f));
		System.out.println(routeMatrix);
	}
	
	void onActionFromDirection() {
		Route route = baiduService.directionSingle(new Location(116.30815f, 40.056878f), new Location(116.403857f, 39.915285f), "北京", "北京");
		System.out.println(route);
	}
	
}
