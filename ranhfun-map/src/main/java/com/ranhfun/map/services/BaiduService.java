package com.ranhfun.map.services;

import com.ranhfun.map.data.GeoCoding;
import com.ranhfun.map.data.Location;
import com.ranhfun.map.data.Route;
import com.ranhfun.map.data.RouteMatrix;

public interface BaiduService {

	/**
	 * 经纬度坐标转换成百度坐标
	 * @param coord 格式：经度,纬度
	 * @return
	 */
	public Location geoconvSingle(Location coord);
	
	/**
	 * 经纬度坐标转换成百度坐标
	 * @param coords 格式：经度,纬度;经度,纬度
	 * @return
	 */
	public Location[] geoconv(Location...coords);

	/**
	 * 地理编码服务
	 * @param address 根据指定地址进行坐标的反定向解析,北京市海淀区上地十街10号
	 * @return
	 */
	public GeoCoding geocoder(String address);
	
	/**
	 * 地理编码服务
	 * @param address 根据指定地址进行坐标的反定向解析,北京市海淀区上地十街10号
	 * @param city 地址所在的城市名,“广州市”
	 * @return
	 */
	public GeoCoding geocoder(String address, String city);
	
	/**
	 * 逆地理编码服务
	 * @param location 根据经纬度坐标获取地址(38.76623,116.43213 lat<纬度>,lng<经度>)
	 * @return
	 */
	public GeoCoding geocoder(Location location);
	
	/**
	 * 逆地理编码服务
	 * @param location 根据经纬度坐标获取地址(38.76623,116.43213 lat<纬度>,lng<经度>)
	 * @param pois 是否显示指定位置周边的poi
	 * @return
	 */
	public GeoCoding geocoder(Location location, int pois);
	
	/**
	 * 该接口适用于仅获取线路距离和时间，无需获取详细线路信息或者需要同时获取多起点、多终点线路距离等的情况。
	 * @param origin
	 * @param destination
	 * @return
	 */
	public RouteMatrix routematrix(Location origin, Location destination);
	
	/**
	 * 该接口适用于仅获取线路距离和时间，无需获取详细线路信息或者需要同时获取多起点、多终点线路距离等的情况。
	 * @param origins
	 * @param destinations
	 * @return
	 */
	public RouteMatrix[] routematrix(Location[] origins, Location[] destinations);
	
	/**
	 * 根据起/终点名称或经纬度，提供驾车数据检索服务
	 * @param origin 
	 * @param destination
	 * @param originRegion 起始点所在城市，驾车导航时必填。 
	 * @param destinationRegion 终点所在城市，驾车导航时必填。 
	 * @return
	 */
	public Route directionSingle(Location origin, Location destination, String originRegion, String destinationRegion);
	
	/**
	 * 根据起/终点名称或经纬度，提供驾车数据检索服务
	 * @param origin 
	 * @param destination
	 * @param originRegion 起始点所在城市，驾车导航时必填。 
	 * @param destinationRegion 终点所在城市，驾车导航时必填。 
	 * @return
	 */
	public Route[] direction(Location origin, Location destination, String originRegion, String destinationRegion);
	
}
