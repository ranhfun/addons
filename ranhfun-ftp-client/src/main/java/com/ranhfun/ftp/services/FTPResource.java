package com.ranhfun.ftp.services;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.AbstractResource;

public class FTPResource extends AbstractResource {

	private static final int PRIME = 39;
	
	private final String site;
	
    public FTPResource(String site, String path)
    {
        super(path);
        this.site = site;
    }

    @Override
    public String toString()
    {
        return String.format("ftp:%s", getPath());
    }

    @Override
    protected Resource newResource(String path)
    {
        return new FTPResource(site, path);
    }

    public URL toURL()
    {
        try {
			return new URL(site + "/" + getPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public int hashCode()
    {
        return PRIME * site.hashCode() + getPath().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final FTPResource other = (FTPResource) obj;

        return site == other.site && getPath().equals(other.getPath());
    }
    
    public String getSite() {
    	return site;
    }
}
