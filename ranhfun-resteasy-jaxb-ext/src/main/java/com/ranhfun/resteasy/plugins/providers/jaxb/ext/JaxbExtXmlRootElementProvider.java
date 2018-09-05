package com.ranhfun.resteasy.plugins.providers.jaxb.ext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.stream.StreamSource;

import org.jboss.resteasy.annotations.providers.jaxb.DoNotUseJAXBProvider;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.plugins.providers.jaxb.AbstractJAXBProvider;
import org.jboss.resteasy.plugins.providers.jaxb.IgnoredMediaTypes;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBContextFinder;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBMarshalException;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBUnmarshalException;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBXmlRootElementProvider;
import org.jboss.resteasy.plugins.providers.jaxb.JaxbCollection;
import org.jboss.resteasy.plugins.providers.jaxb.JaxbMap;
import org.jboss.resteasy.util.FindAnnotation;
import org.jboss.resteasy.util.HttpHeaderNames;
import org.jboss.resteasy.util.Types;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.ranhfun.resteasy.data.Head;
import com.ranhfun.resteasy.data.Result;

@Provider
@Consumes("application/jaxbext+xml")
@Produces("application/jaxbext+xml")
public class JaxbExtXmlRootElementProvider extends AbstractJAXBProvider<Object>{

	@Override
	protected boolean isReadWritable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
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
		return (type.isAnnotationPresent(XmlRootElement.class)) && (FindAnnotation.findAnnotation(type, annotations, DoNotUseJAXBProvider.class) == null)
				&& !IgnoredMediaTypes.ignored(type, annotations, mediaType);
	}

	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException {
	      try
	      {
	         // return null if Content-Length is 0
	         String contentLength = httpHeaders.getFirst("Content-Length");
	         if (contentLength != null)
	         {
	            try
	            {
	               long length = Long.valueOf(contentLength);
	               if (length == 0)
	               {
	                  return null;
	               }
	            }
	            catch (NumberFormatException ignored)
	            {
	            }
	         }
			boolean colFlag = false;
			boolean mapFlag = false;
		    Class baseType = null;
		    
			JaxbCollection col = null;
			JaxbMap jaxbMap = null;
		    
			if (Collection.class.isAssignableFrom(type) || type.isArray()) {
				baseType = Types.getCollectionBaseType(type, genericType);
				colFlag = true;
			} else if (Map.class.isAssignableFrom(type)) {
				baseType = Types.getMapValueType(genericType);
				mapFlag = true;
			} else {
				baseType = type;
			}
		     JAXBContext jaxb = findJAXBContext(baseType, annotations, mediaType, true);
		     Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		     unmarshaller = decorateUnmarshaller(type, annotations, mediaType, unmarshaller);
		  
		     if (suppressExpandEntityExpansion())
		     {
		        return processWithoutEntityExpansion(unmarshaller, entityStream);
		     }
		     
		     Result result = (Result) unmarshaller.unmarshal(new StreamSource(entityStream));
	         
	        if (colFlag) {
	        	col = (JaxbCollection) result.getData();
	            if (type.isArray())
	               {
	                  Object array = Array.newInstance(baseType, col.getValue().size());
	                  for (int i = 0; i < col.getValue().size(); i++)
	                  {
	                     Element val = (Element) col.getValue().get(i);
	                     Array.set(array, i, unmarshaller.unmarshal(val));
	                  }
	                  return array;
	               }
	               else
	               {
	                  Collection outCol = null;
	                  if (type.isInterface())
	                  {
	                     if (List.class.isAssignableFrom(type)) outCol = new ArrayList();
	                     else if (Set.class.isAssignableFrom(type)) outCol = new HashSet();
	                     else outCol = new ArrayList();
	                  }
	                  else
	                  {
	                     try
	                     {
	                        outCol = (Collection) type.newInstance();
	                     }
	                     catch (Exception e)
	                     {
	                        throw new JAXBUnmarshalException(e);
	                     }
	                  }
	                  for (Object obj : col.getValue())
	                  {
	                     Element val = (Element) obj;
	                     outCol.add(unmarshaller.unmarshal(val));
	                  }
	                  return outCol;
	               }
			} else if (mapFlag) {
				jaxbMap = (JaxbMap) result.getData();
				HashMap map = new HashMap();
				for (int i = 0; i < jaxbMap.getValue().size(); i++) {
		            Element element = (Element) jaxbMap.getValue().get(i);
		            NamedNodeMap attributeMap = element.getAttributes();
		            String keyValue = null;
		               if (attributeMap.getLength() == 0)
		                  throw new JAXBUnmarshalException("Map wrapped failed, could not find map entry key attribute");
		               for (int j = 0; j < attributeMap.getLength(); j++)
		               {
		                  Attr key = (Attr) attributeMap.item(j);
		                  if (!key.getName().startsWith("xmlns"))
		                  {
		                     keyValue = key.getValue();
		                     break;
		                  }
		               }

		            Object value = unmarshaller.unmarshal(element.getFirstChild());

		            map.put(keyValue, value);
				}
		        return map;
			}
	         return result.getData();
	      }
	      catch (JAXBException e)
	      {
	         throw new JAXBUnmarshalException(e);
	      }
	}
	
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream outputStream) throws IOException {
		System.out.println(t);
		MediaType xmlType = new MediaType(mediaType.getType(), "xml", mediaType.getParameters());
		//httpHeaders.putSingle(HttpHeaderNames.CONTENT_TYPE, xmlType);
		Result result = new Result();
		Head head = new Head(1, "success");
		result.setHead(head);
		boolean colFlag = false;
		boolean mapFlag = false;
		Class baseType = null;
		
		JaxbCollection col = null;
		JaxbMap map = null;
		
		if (Collection.class.isAssignableFrom(type) || type.isArray()) {
			baseType = Types.getCollectionBaseType(type, genericType);
			colFlag = true;
			
			col = new JaxbCollection();
			
			if (type.isArray()) {
				Object[] array = (Object[]) t;
				for (Object obj : array) {
					col.getValue().add(obj);
				}
		   } else if(t instanceof Collection){
			   Collection collection = (Collection) t;
			   for (Object obj : collection) col.getValue().add(obj);
		   }
			
		} else if (Map.class.isAssignableFrom(type)) {
			baseType = Types.getMapValueType(genericType);
			mapFlag = true;
			
			String mapName = "map";
	        String entryName = "entry";
	        String keyName = "key";
	        String namespaceURI = "";
	        String prefix = "";
			map = new JaxbMap(entryName, keyName, namespaceURI);
	        Map<Object, Object> tMap = (Map) t;
	        for (Map.Entry mapEntry : tMap.entrySet()) {
	           map.addEntry(mapEntry.getKey().toString(), mapEntry.getValue());
	        }
		} else {
			baseType = type;
		}
		
        /*String element = "collection";
        String namespaceURI = "";
        String prefix = "";

        Wrapped wrapped = FindAnnotation.findAnnotation(annotations, Wrapped.class);
        if (wrapped != null)
        {
           element = wrapped.element();
           namespaceURI = wrapped.namespace();
           prefix = wrapped.prefix();
        }*/
		if (colFlag) {
			result.setData(col);
		} else if (mapFlag) {
			result.setData(map);
		} else {
			result.setData(t);
		}
		super.writeTo(result, baseType, genericType, annotations, mediaType,
				httpHeaders, outputStream);
	}
	
	public JAXBContext findJAXBContext(Class<?> type, Annotation[] annotations,
			MediaType mediaType, boolean reader) throws JAXBException {
	      ContextResolver<JAXBContextFinder> resolver = providers.getContextResolver(JAXBContextFinder.class, mediaType);
	      JAXBContextFinder finder = resolver.getContext(type);
	      if (finder == null) {
	         if (reader) throw new JAXBUnmarshalException("Could not find JAXBContextFinder for media type: " + mediaType);
	         else throw new JAXBMarshalException("Could not find JAXBContextFinder for media type: " + mediaType);
	      }
	      return finder.findCacheContext(mediaType, annotations, JaxbCollection.class, JaxbMap.class, JaxbMap.Entry.class, Result.class, type);
	}
	
}
