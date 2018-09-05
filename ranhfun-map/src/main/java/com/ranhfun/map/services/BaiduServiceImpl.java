package com.ranhfun.map.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.slf4j.Logger;

import com.ranhfun.map.MapConstants;
import com.ranhfun.map.data.AddressComponent;
import com.ranhfun.map.data.Distance;
import com.ranhfun.map.data.Duration;
import com.ranhfun.map.data.GeoCoding;
import com.ranhfun.map.data.Location;
import com.ranhfun.map.data.Poi;
import com.ranhfun.map.data.Route;
import com.ranhfun.map.data.RouteMatrix;
import com.ranhfun.map.data.Step;

public class BaiduServiceImpl implements BaiduService {

	@Inject
	private Logger logger;
	
	@Inject
	@Symbol(MapConstants.BAIDU_AK)
	private String ak;
	
	public Location geoconvSingle(Location coord) {
		Location[] locations = geoconv(coord);
		if (locations!=null && locations.length>0) {
			return locations[0];
		}
		return null;
	}

	public Location[] geoconv(Location... coords) {
		StringBuffer coordsValue = new StringBuffer();
		for (Location coord : coords) {
			coordsValue.append(coord.getLng()).append(",").append(coord.getLat()).append(";");
		}
		Map<String, String> params = CollectionFactory.newMap();
		params.put("coords", coordsValue.substring(0, coordsValue.length()-1));
		String convertUrl = converUrl("http://api.map.baidu.com/geoconv/v1/", params);
		JSONObject jso = retrieveJSON(convertUrl);
		if (jso.getInt("status")==0) {
			JSONArray result = jso.getJSONArray("result");
			Location[] results = new Location[result.length()*2];
			for (int i = 0; i < result.length(); i++) {
				JSONObject item = result.getJSONObject(i);
				results[i] = new Location((float)item.getDouble("x"), (float)item.getDouble("y"));
			}
			return results;
		} else {
			logger.error(String.format("[geoconv Fail]retrieveJson fail for URL:%s", convertUrl));
		}
		return null;
	}

	public GeoCoding geocoder(String address) {
		return geocoder(address, null);
	}

	public GeoCoding geocoder(String address, String city) {
		if (StringUtils.isEmpty(address)) {
			throw new RuntimeException("[geocoder Fail]Not allowed address empty.");
		}
		Map<String, String> params = CollectionFactory.newMap();
		params.put("address", address);
		if (!StringUtils.isEmpty(city)) {
			params.put("city", city);
		}
		params.put("output", "json");
		String convertUrl = converUrl("http://api.map.baidu.com/geocoder/v2/", params);
		JSONObject jso = retrieveJSON(convertUrl);
		if (jso.getInt("status")==0) {
			JSONObject result = jso.getJSONObject("result");
			GeoCoding geoCoding = new GeoCoding();
			JSONObject location = result.getJSONObject("location");
			geoCoding.setLocation(new Location((float)location.getDouble("lng"), (float)location.getDouble("lat")));
			geoCoding.setPrecise(result.getInt("precise"));
			geoCoding.setConfidence(result.getInt("confidence"));
			geoCoding.setLevel(result.getString("level"));
			return geoCoding;
		} else {
			logger.error(String.format("[geocoder Fail]retrieveJson fail for URL:%s", convertUrl));
		}
		return null;
	}


	public GeoCoding geocoder(Location location) {
		return geocoder(location, 0);
	}

	public GeoCoding geocoder(Location location, int pois) {
		if (location==null) {
			throw new RuntimeException("[geocoder Fail]Not allowed location empty.");
		}
		Map<String, String> params = CollectionFactory.newMap();
		params.put("location", 
				new StringBuffer().append(location.getLat()).append(",")
					.append(location.getLng()).toString());
		params.put("pois", pois+"");
		params.put("output", "json");
		String convertUrl = converUrl("http://api.map.baidu.com/geocoder/v2/", params);
		JSONObject jso = retrieveJSON(convertUrl);
		if (jso.getInt("status")==0) {
			JSONObject result = jso.getJSONObject("result");
			GeoCoding geoCoding = new GeoCoding();
			JSONObject locationReverse = result.getJSONObject("location");
			geoCoding.setLocation(new Location((float)locationReverse.getDouble("lng"), (float)locationReverse.getDouble("lat")));
			geoCoding.setFormattedAddress(result.getString("formatted_address"));
			geoCoding.setBusiness(result.getString("business"));
			JSONObject addressComponentJso = result.getJSONObject("addressComponent");
			AddressComponent addressComponent = new AddressComponent();
			addressComponent.setCity(addressComponentJso.getString("city"));
			addressComponent.setDistrict(addressComponentJso.getString("district"));
			addressComponent.setProvince(addressComponentJso.getString("province"));
			addressComponent.setStreet(addressComponentJso.getString("street"));
			addressComponent.setStreetNumber(addressComponentJso.getString("street_number"));
			geoCoding.setAddressComponent(addressComponent);
			if (result.has("pois")) {
				JSONArray poisJsa = result.getJSONArray("pois");
				Poi[] poisArr = new Poi[poisJsa.length()];
				for (int i = 0; i < poisJsa.length(); i++) {
					JSONObject item = poisJsa.getJSONObject(i);
					JSONObject point = item.getJSONObject("point");
					poisArr[i] = new Poi(item.getString("addr"),
							item.getString("cp"),
							item.getString("distance"),
							item.getString("name"),
							item.getString("poiType"),
							new Location((float)point.getDouble("x"), (float)point.getDouble("y")),
							item.getString("tel"),
							item.getString("uid"),
							item.getString("zip"));
				}
				geoCoding.setPois(poisArr);
			}
			return geoCoding;
		} else {
			logger.error(String.format("[geocoder Fail]retrieveJson fail for URL:%s", convertUrl));
		}
		return null;
	}


	public RouteMatrix routematrix(Location origin, Location destination) {
		RouteMatrix[] elementsArr = routematrix(ArrayUtils.toArray(origin), ArrayUtils.toArray(destination));
		if (elementsArr!=null && elementsArr.length>0) {
			return elementsArr[0];
		}
		return null;
	}

	public RouteMatrix[] routematrix(Location[] origins,
			Location[] destinations) {
		if (origins==null || origins.length==0) {
			throw new RuntimeException("[routematrix Fail]Not allowed origins empty.");
		}
		if (destinations==null || destinations.length==0) {
			throw new RuntimeException("[routematrix Fail]Not allowed destinations empty.");
		}
		Map<String, String> params = CollectionFactory.newMap();
		StringBuffer originsBuff = new StringBuffer();
		for (Location origin : origins) {
			originsBuff.append(origin.getLat()).append(",").append(origin.getLng()).append("|");
		}
		params.put("origins", originsBuff.substring(0, originsBuff.length()-1));
		StringBuffer destinationsBuff = new StringBuffer();
		for (Location destination : destinations) {
			destinationsBuff.append(destination.getLat()).append(",").append(destination.getLng()).append("|");
		}
		params.put("destinations", destinationsBuff.substring(0, destinationsBuff.length()-1));
		params.put("output", "json");
		String convertUrl = converUrl("http://api.map.baidu.com/direction/v1/routematrix", params);
		JSONObject jso = retrieveJSON(convertUrl);
		if (jso.getInt("status")==0) {
			JSONObject result = jso.getJSONObject("result");
			JSONArray elementsJsa = result.getJSONArray("elements");
			RouteMatrix[] elementsArr = new RouteMatrix[elementsJsa.length()];
			for (int i = 0; i < elementsJsa.length(); i++) {
				JSONObject item = elementsJsa.getJSONObject(i);
				JSONObject distance = item.getJSONObject("distance");
				JSONObject duration = item.getJSONObject("duration");
				elementsArr[i] = new RouteMatrix(
							new Distance(distance.getString("text"), distance.getString("value")),
							new Duration(duration.getString("text"), duration.getString("value"))
						);
			}
			return elementsArr;
		} else {
			logger.error(String.format("[routematrix Fail]retrieveJson fail for URL:%s", convertUrl));
		}
		return null;
	}

	public Route directionSingle(Location origin, Location destination,
			String originRegion, String destinationRegion) {
		Route[] routes = direction(origin, destination, originRegion, destinationRegion);
		if (routes!=null && routes.length>0) {
			return routes[0];
		}
		return null;
	}

	public Route[] direction(Location origin, Location destination,
			String originRegion, String destinationRegion) {
		if (origin==null) {
			throw new RuntimeException("[direction Fail]Not allowed origin empty.");
		}
		if (destination==null) {
			throw new RuntimeException("[direction Fail]Not allowed destination empty.");
		}
		if (StringUtils.isEmpty(originRegion)) {
			throw new RuntimeException("[direction Fail]Not allowed originRegion empty.");
		}
		if (StringUtils.isEmpty(destinationRegion)) {
			throw new RuntimeException("[direction Fail]Not allowed destinationRegion empty.");
		}
		Map<String, String> params = CollectionFactory.newMap();
		StringBuffer originBuff = new StringBuffer();
		StringBuffer destinationBuff = new StringBuffer();
		originBuff.append(origin.getLat()).append(",").append(origin.getLng());
		destinationBuff.append(destination.getLat()).append(",").append(destination.getLng());
		params.put("origin", originBuff.toString());
		params.put("destination", destinationBuff.toString());
		params.put("origin_region", originRegion);
		params.put("destination_region", destinationRegion);
		params.put("output", "json");
		String convertUrl = converUrl("http://api.map.baidu.com/direction/v1", params);
		JSONObject jso = retrieveJSON(convertUrl);
		if (jso.getInt("status")==0) {
			JSONObject result = jso.getJSONObject("result");
			JSONArray routesJsa = result.getJSONArray("routes");
			Route[] routesArr = new Route[routesJsa.length()];
			for (int i = 0; i < routesJsa.length(); i++) {
				JSONObject item = routesJsa.getJSONObject(i);
				JSONObject originLocation = item.getJSONObject("originLocation");
				JSONObject destinationLocation = item.getJSONObject("destinationLocation");
				JSONArray setpsJsa = item.getJSONArray("steps");
				Step[] stepsArr = new Step[setpsJsa.length()];
				for (int j = 0; j < setpsJsa.length(); j++) {
					JSONObject stepItem = setpsJsa.getJSONObject(i);
					JSONObject stepOriginLocation = stepItem.getJSONObject("stepOriginLocation");
					JSONObject stepDestinationLocation = stepItem.getJSONObject("stepDestinationLocation");
					stepsArr[j] = new Step(stepItem.getInt("direction"), 
							stepItem.getInt("distance"),
							stepItem.getInt("duration"),
							stepItem.getString("instructions"), 
							stepItem.getString("path"), 
							stepItem.getInt("type"), 
							stepItem.getInt("turn"), 
							new Location((float)stepOriginLocation.getDouble("lng"), (float)stepOriginLocation.getDouble("lat")),
							new Location((float)stepDestinationLocation.getDouble("lng"), (float)stepDestinationLocation.getDouble("lat")));
				}
				routesArr[i] = new Route(item.getInt("distance"), 
						item.getInt("duration"), 
						stepsArr, 
						item.getInt("toll"), 
						new Location((float)originLocation.getDouble("lng"), (float)originLocation.getDouble("lat")),
						new Location((float)destinationLocation.getDouble("lng"), (float)destinationLocation.getDouble("lat")));
			}
			return routesArr;
		} else {
			logger.error(String.format("[direction Fail]retrieveJson fail for URL:%s", convertUrl));
		}
		return null;
	}

	public String converUrl(String apiUrl, Map<String, String> params) {
		StringBuffer str = new StringBuffer(apiUrl);
		if (apiUrl.indexOf("?")!=-1) {
			str.append("&");
		} else {
			str.append("?");
		}
		str.append("ak=").append(ak);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			str.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		return StringUtils.replace(str.toString(), " ", "%20");
	}	
	
	public JSONObject retrieveJSON(String content) {
		try {
			URL url = new URL(content);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            InputStreamReader reader = new InputStreamReader(httpConnection.getInputStream());
            BufferedReader bReader = new BufferedReader(reader);
            return new JSONObject(bReader.readLine());
		} catch (Exception e) {
			logger.error(String.format("resuce send Exception[content:%s]", content), e);
			return null;
		}
	}
	
}
