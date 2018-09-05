package com.ranhfun.swagger.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.tynamo.resteasy.ResteasySymbols;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/update")
@Api(value = "update", description = "版本接口", position = 1, basePath = "http://localhost:8080/api")
public class UpdateResource {

	@Inject @Symbol(ResteasySymbols.MAPPING_PREFIX)
	private String mappingPrefix;
	
	@Inject  @Symbol(SymbolConstants.APPLICATION_VERSION)
	private String applicationVersion;
	
	@GET
	@Path("version")
	//@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	@ApiOperation(httpMethod="GET", nickname="getVersion", value="获取版本信息")
	public Response getVersion(@Context UriInfo uriInfo) {
		return Response.ok().entity("<xml><version>中文</version></xml>").build();
		//return Response.ok().entity(new JSONObject("version", "中文").toString()).build();
	}
	
	@GET
	@Path("version2")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@ApiOperation(httpMethod="GET", nickname="getVersion2", value="获取版本信息")
	public Response getVersion2(@Context UriInfo uriInfo) {
		return Response.ok().entity(new JSONObject("version", "中文").toString()).build();
	}
}
