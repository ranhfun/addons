package com.ranhfun.resteasy.client;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.ranhfun.resteasy.data.VersionApi;

public interface UpdateClient {

	@GET
	@Path("version")
	@Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
	public List<VersionApi> getVersionApi();
	
	@GET
	@Path("version2")
	@Produces("application/jaxbext+xml;charset=UTF-8")
	public List<VersionApi> getVersionApi2();
	
	@GET
	@Path("version3")
	@Produces("application/jacksonext+json;charset=UTF-8")
	public List<VersionApi> getVersionApi3();
	
}
