package com.ranhfun.resteasy.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.NoJackson;
import org.jboss.resteasy.annotations.providers.jaxb.WrappedMap;

import com.ranhfun.resteasy.data.VersionApi;

@Path("/update")
public class UpdateResource {
	
	@GET
	@Path("version")
	@Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
	public List<VersionApi> getVersionApi(@Context UriInfo uriInfo) {
		List<VersionApi> versionApis=new ArrayList<VersionApi>();
		VersionApi versionApi=new VersionApi();
		versionApi.setvCode(10104);
		versionApi.setvName("1.1.4");
		versionApis.add(versionApi);
		return versionApis;
	}
	
	@GET
	@Path("version2")
	@Produces("application/jaxbext+xml;charset=UTF-8")
	public List<VersionApi> getVersionApi2(@Context UriInfo uriInfo) {
		return getVersionApi(uriInfo);
	}
	
	@GET
	@Path("version2map")
	@Produces("application/jaxbext+xml;charset=UTF-8")
	@WrappedMap(namespace = "")
	public Map<String,VersionApi> getVersionApi2Map(@Context UriInfo uriInfo) {
		VersionApi versionApi = getVersionApi(uriInfo).get(0);
		Map<String, VersionApi> map = new HashMap<String, VersionApi>();
		map.put(versionApi.getvName(), versionApi);
		return map;
	}
	
	@GET
	@Path("version3")
	@Produces("application/jacksonext+json;charset=UTF-8")
	public List<VersionApi> getVersionApi3(@Context UriInfo uriInfo) {
		return getVersionApi(uriInfo);
	}
	
	@GET
	@Path("version3map")
	@Produces("application/jacksonext+json;charset=UTF-8")
	@WrappedMap(namespace = "")
	public Map<String,VersionApi> getVersionApi3Map(@Context UriInfo uriInfo) {
		VersionApi versionApi = getVersionApi(uriInfo).get(0);
		Map<String, VersionApi> map = new HashMap<String, VersionApi>();
		map.put(versionApi.getvName(), versionApi);
		map.put(versionApi.getvName() + "1", versionApi);
		return map;
	}
	
}
