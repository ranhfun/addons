package com.ranhfun.soup.asset.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.AbstractResource;

public class FileResource extends AbstractResource {

	private static final int PRIME = 39;
	
	private final String filePlacePath;
	
    public FileResource(String filePlacePath, String path)
    {
        super(path);
        assert filePlacePath != null;
        this.filePlacePath = filePlacePath;
    }

    @Override
    public String toString()
    {
        return String.format("file:%s", getPath());
    }

    @Override
    protected Resource newResource(String path)
    {
        return new FileResource(filePlacePath, path);
    }

    public URL toURL()
    {
        // This is so easy to screw up; ClassLoader.getResource() doesn't want a leading slash,
        // and HttpServletContext.getResource() does. This is what I mean when I say that
        // a framework is an accumulation of the combined experience of many users and developers.

        // Always prefer the actual file to the URL.  This is critical for templates to
        // reload inside Tomcat.

        File file = new File(filePlacePath + "/" + getPath());
    	//File file = new File("target/files/test.jpg");
        if (file != null && file.exists())
        {
            try
            {
                return file.toURL();
            }
            catch (MalformedURLException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        // But, when packaged inside a WAR or JAR, the File will not be available, so use whatever
        // URL we get ... but reloading won't work.

        return null;
    }

    @Override
    public int hashCode()
    {
        return PRIME * filePlacePath.hashCode() + getPath().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final FileResource other = (FileResource) obj;

        return filePlacePath == other.filePlacePath && getPath().equals(other.getPath());
    }
}
