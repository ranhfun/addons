package com.ranhfun.soup.asset.services;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.AssetResourceLocator;
import org.apache.tapestry5.internal.services.RequestConstants;
import org.apache.tapestry5.internal.services.ResourceDigestManager;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.ClasspathAssetAliasManager;
import org.apache.tapestry5.services.ClasspathProvider;
import org.apache.tapestry5.services.ContextProvider;
import org.apache.tapestry5.services.Response;

import com.ranhfun.soup.asset.FileAssetConstants;

public class FileAssetResourceLocatorImpl implements AssetResourceLocator {

    private final ClasspathAssetAliasManager aliasManager;

    private final ResourceDigestManager digestManager;

    private final AssetFactory contextAssetFactory;

    private final Response response;

    private final String applicationAssetPrefix;
    
    private final AssetFactory fileAssetFactory;

    private final String fileAssetPrefix;
    
    public FileAssetResourceLocatorImpl(ClasspathAssetAliasManager aliasManager,

    		ResourceDigestManager digestManager,

                                    @Inject @Symbol(SymbolConstants.APPLICATION_VERSION)
                                    String applicationVersion,

                                    @ContextProvider
                                    AssetFactory contextAssetFactory,
                                    
                                    @ClasspathProvider
                                    AssetFactory classpathAssetFactory,

                                    Response response,
                                    
                                    @FileProvider
                                    AssetFactory fileAssetFactory
    								)

    {
        this.aliasManager = aliasManager;
        this.digestManager = digestManager;
        this.contextAssetFactory = contextAssetFactory;
        this.response = response;
        this.fileAssetFactory = fileAssetFactory;

        applicationAssetPrefix = RequestConstants.CONTEXT_FOLDER + RequestConstants.CONTEXT_FOLDER + applicationVersion + "/";
        fileAssetPrefix = RequestConstants.CONTEXT_FOLDER + FileAssetConstants.FILE_ASSET_CLIENT_FOLDER + applicationVersion + "/";
    }

    public Resource findClasspathResourceForPath(String path) throws IOException
    {
        if (path.startsWith(applicationAssetPrefix))
            return findContextResource(path.substring(applicationAssetPrefix.length()));
        
        if (path.startsWith(fileAssetPrefix))
            return findFileResource(path.substring(fileAssetPrefix.length()));
        
        String resourcePath = aliasManager.toClientURL(path);

        Resource resource = new ClasspathResource(resourcePath);

        if (!digestManager.requiresDigest(resource)) return resource;

        return validateChecksumOfClasspathResource(resource);
    }
    
    /**
     * Validates the checksome encoded into the resource, and returns the true resource (with the checksum
     * portion removed from the file name).
     */
    private Resource validateChecksumOfClasspathResource(Resource resource) throws IOException
    {
        String file = resource.getFile();

        // Somehow this code got real ugly, but it's all about preventing NPEs when a resource
        // that should have a digest doesn't.

        boolean valid = false;
        Resource result = resource;

        int lastdotx = file.lastIndexOf('.');

        if (lastdotx > 0)
        {
            int prevdotx = file.lastIndexOf('.', lastdotx - 1);

            if (prevdotx > 0)
            {
                String requestDigest = file.substring(prevdotx + 1, lastdotx);

                // Strip the digest out of the file name.

                String realFile = file.substring(0, prevdotx) + file.substring(lastdotx);

                result = resource.forFile(realFile);

                String actualDigest = digestManager.getDigest(result);

                valid = requestDigest.equals(actualDigest);
            }
        }

        if (valid)
            return result;

        // TODO: Perhaps we should send an exception here, so that the caller can decide
        // to send the error. I'm not happy with this.

        response.sendError(HttpServletResponse.SC_FORBIDDEN, FileAssetMessages.wrongAssetDigest(result));

        return null;
    }

    private Resource findContextResource(String contextPath)
    {
        return contextAssetFactory.getRootResource().forFile(contextPath);
    }
    
    private Resource findFileResource(String contextPath)
    {
        return fileAssetFactory.getRootResource().forFile(contextPath);
    }

}
