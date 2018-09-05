package org.example.soup.asset.pages;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ranhfun.soup.asset.services.FileAssetPath;

public class Index {

	/*@Inject
	@Path("file:test.jpg")
	private Asset image;*/
	
	@Inject
	private FileAssetPath fileAssetPath;
	
	@Component
	private Zone zone;
	@Property
	private String message;
	public Object onActionFromTest() {
		message = "tesets" + System.currentTimeMillis();
		return zone.getBody();
	}
	/*public Asset getImage() {
		return image;
	}
	
	public String getFilePath() {
		return fileAssetPath.getPath("test.jpg");
	}*/
	

}
