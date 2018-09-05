package com.ranhfun.resteasy.plugins.providers.jackson.ext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.annotations.providers.NoJackson;
import org.jboss.resteasy.annotations.providers.jaxb.DoNotUseJAXBProvider;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.jboss.resteasy.plugins.providers.jaxb.IgnoredMediaTypes;
import org.jboss.resteasy.plugins.providers.jaxb.JaxbCollection;
import org.jboss.resteasy.plugins.providers.jaxb.JaxbMap;
import org.jboss.resteasy.util.FindAnnotation;
import org.jboss.resteasy.util.Types;

import com.ranhfun.resteasy.data.Head;
import com.ranhfun.resteasy.data.Result;

@Provider
@Consumes("application/jacksonext+json")
@Produces("application/jacksonext+json")
public class JacksonExtProvider extends ResteasyJacksonProvider {

   protected boolean isWrapped(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
      if ((Collection.class.isAssignableFrom(type) || type.isArray()) && genericType != null) {
		Class baseType = Types.getCollectionBaseType(type, genericType);
		if (baseType == null) return false;
		return (baseType.isAnnotationPresent(XmlRootElement.class) || baseType.isAnnotationPresent(XmlType.class) || baseType.isAnnotationPresent(XmlSeeAlso.class) || JAXBElement.class.equals(baseType)) && (FindAnnotation.findAnnotation(baseType, annotations, DoNotUseJAXBProvider.class) == null) && !IgnoredMediaTypes.ignored(baseType, annotations, mediaType);
      }
      if (Map.class.isAssignableFrom(type) && genericType != null) {
		Class keyType = Types.getMapKeyType(genericType);
		if (keyType == null) return false;
		if (!keyType.equals(String.class)) return false;
		
		Class valueType = Types.getMapValueType(genericType);
		if (valueType == null) return false;
		return (valueType.isAnnotationPresent(XmlRootElement.class) || valueType.isAnnotationPresent(XmlType.class) || valueType.isAnnotationPresent(XmlSeeAlso.class) || JAXBElement.class.equals(valueType)) && (FindAnnotation.findAnnotation(valueType, annotations, DoNotUseJAXBProvider.class) == null) && !IgnoredMediaTypes.ignored(valueType, annotations, mediaType);
	  }	      
      if (FindAnnotation.findAnnotation(type, annotations, NoJackson.class) != null) return false;
      return super.isWriteable(type, genericType, annotations, mediaType);
   }
   
   @Override
   public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
   {
      return isWrapped(aClass, type, annotations, mediaType);
   }

   @Override
   public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
   {
	   return isWrapped(aClass, type, annotations, mediaType);
   }
	
	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException {
        ObjectMapper mapper = locateMapper(Result.class, mediaType);
        JsonParser jp = mapper.getJsonFactory().createJsonParser(entityStream);
        jp.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        Result result = (Result)mapper.readValue(jp, mapper.constructType(Result.class));
		Class baseType = null;
		Object t = result.getData();
		if (Collection.class.isAssignableFrom(type) || type.isArray()) {
			baseType = Types.getCollectionBaseType(type, genericType);
			if (type.isArray()) {
				Object[] array = (Object[])t;
				Object[] array2 = new Object[array.length];
				for (int i = 0; i < array.length; i++) {
					array2[i] = mapper.convertValue(array[i], baseType);
				}
				return array2;
		   } else if(t instanceof Collection){
			   Collection collection = (Collection) t;
			   List<Object> collection2 = new ArrayList<Object>();
			   for (Object obj : collection) {
				   collection2.add(mapper.convertValue(obj, baseType));
			   }
			   return collection2;
		   }
		} else if (Map.class.isAssignableFrom(type)) {
			baseType = Types.getMapValueType(genericType);
	        Map<Object, Object> tMap = (Map) t;
	        Map<Object, Object> tMap2 = new HashMap<Object, Object>();
	        for (Map.Entry mapEntry : tMap.entrySet()) {
	        	tMap2.put(mapEntry.getKey(), mapper.convertValue(mapEntry.getValue(), baseType));
	        }
	        return tMap2;
		}
        return mapper.convertValue(result.getData(), type);
	}
   
   public void writeTo(Object value, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException {
		System.out.println(value);
		MediaType jsonType = new MediaType(mediaType.getType(), "json", mediaType.getParameters());
		//httpHeaders.putSingle(HttpHeaderNames.CONTENT_TYPE, jsonType);
		Result result = new Result();
		Head head = new Head(1, "success");
		result.setHead(head);
		result.setData(value);
		super.writeTo(result, Result.class, Result.class, annotations, mediaType, httpHeaders,
				entityStream);
	}
	
}
