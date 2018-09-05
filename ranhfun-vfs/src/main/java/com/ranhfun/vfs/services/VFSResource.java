package com.ranhfun.vfs.services;

import java.io.File;
import java.net.URL;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.AbstractResource;

public class VFSResource extends AbstractResource {

	private static final int PRIME = 39;
	
	private FileSystemManager fs;
	
	private final String fileFullPlace;
	
    public VFSResource(FileSystemManager fs,String fileFullPlace, String path)
    {
        super(path);
        this.fs = fs;
        this.fileFullPlace = fileFullPlace;
    }

    @Override
    public String toString()
    {
        return String.format("vfs:%s", getPath());
    }

    @Override
    protected Resource newResource(String path)
    {
        return new VFSResource(fs, fileFullPlace, path);
    }

    public URL toURL()
    {
    	
    	FileObject fo;
		try {
			fo = fs.toFileObject(new File(fileFullPlace + "/" + getPath()));
			return fo.getURL();
		} catch (FileSystemException e) {
			e.printStackTrace();
		}

        return null;
    }

    @Override
    public int hashCode()
    {
        return PRIME * fileFullPlace.hashCode() + getPath().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final VFSResource other = (VFSResource) obj;

        return fileFullPlace == other.fileFullPlace && getPath().equals(other.getPath());
    }
}
