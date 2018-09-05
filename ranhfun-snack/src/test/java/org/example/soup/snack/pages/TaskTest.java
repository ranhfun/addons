package org.example.soup.snack.pages;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Renderable;
import org.apache.tapestry5.internal.renderers.LocationRenderer;
import org.apache.tapestry5.internal.services.PageRenderQueue;
import org.apache.tapestry5.internal.services.TemplateParser;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.services.ObjectRenderer;

public class TaskTest {

	@Inject
	private TemplateParser parser;
	

	@Inject
	private ObjectRenderer renderer;
	
	private String getColor() {
		return "red";
	}
	
	void afterRender(MarkupWriter writer) throws InterruptedException, IOException {
		/*Thread thread = 
        new Thread(new Runnable(){
            public void run() {
            	System.out.println("run....................");
            }
        });
		thread.sleep(1000);
		thread.start();*/
		renderer.render(parser.parseTemplate(new ClasspathResource("/resource/test.html")).getResource().openStream(), writer);
		//System.out.println(inputStream2String(parser.parseTemplate(new ClasspathResource("/resource/test.html")).getResource().openStream()));

	}
	
	public   String   inputStream2String   (InputStream   in)   throws   IOException   { 
        StringBuffer   out   =   new   StringBuffer(); 
        byte[]   b   =   new   byte[4096]; 
        for   (int   n;   (n   =   in.read(b))   !=   -1;)   { 
                out.append(new   String(b,   0,   n)); 
        } 
        return   out.toString(); 
}

}
