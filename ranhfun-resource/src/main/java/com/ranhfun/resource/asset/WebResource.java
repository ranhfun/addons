package com.ranhfun.resource.asset;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.internal.services.ContextResource;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.AbstractResource;
import org.apache.tapestry5.services.Context;

public class WebResource extends AbstractResource {

	private static final int PRIME = 40;
	
	private final Context context;
	
	private final String site;
	
	private final boolean externalMode;
	
    public WebResource(String site, String path)
    {
    	this(null, site, path, true);
    }
    
    public WebResource(Context context, String site, String path, boolean externalMode) {
        super(path);
        this.site = site;
        this.externalMode = externalMode;
        this.context = context;
    }

    public String toString()
    {
        return String.format("web resource:%s", getPath());
    }

    protected Resource newResource(String path)
    {
    	if (!externalMode || !site.startsWith("http://")) {
			return new ContextResource(context, site + "/" + path);
		}
        return new WebResource(site, path);
    }

    public URL toURL()
    {
    	if (!externalMode || !site.startsWith("http://")) {
			return new ContextResource(context, site + "/" + getPath()).toURL();
		}
		try {
			return new URL(site + "/" + getPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        return null;
    }

    public int hashCode()
    {
        return PRIME * site.hashCode() + getPath().hashCode();
    }

    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final WebResource other = (WebResource) obj;

        return site == other.site && getPath().equals(other.getPath());
    }
    
    public String getSite() {
    	return site;
    }
}
