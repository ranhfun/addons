package com.ranhfun.vfs.services;

import java.io.File;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.tapestry5.internal.services.ResourceStreamer;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Advise;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.RegistryShutdownHub;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.AssetPathConverter;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.assets.AssetPathConstructor;
import org.apache.tapestry5.services.assets.AssetRequestHandler;

import com.ranhfun.vfs.VFSAssetConstants;
import com.ranhfun.vfs.VFSProvider;

public class VFSModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(VFSAssetDispatherAdvisor.class, VFSAssetDispatherAdvisorImpl.class);
	}
	
    public VFSService buildVFSService(ChainBuilder chainBuilder, List<VFSService> configuration)
    {
        return chainBuilder.build(VFSService.class, configuration);
    }
	
	@Contribute(VFSService.class)
	public static void contributeVFSService(
			@Symbol(VFSAssetConstants.FILE_FULL_PLACE) 
			final String fileFullPlace, OrderedConfiguration<VFSService> configuration)
	{
		configuration.add("v", new VFSService() {

			public File retrieveFile(String path) {
				return new File(fileFullPlace, path);
			}
		});
	}
    
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(VFSAssetConstants.FILE_FULL_PLACE, "target/vfs/files");
    }
	
    @EagerLoad
    public static FileSystemManager buildFileSystemManager(@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
        	String fileFullPlace, 
        	final RegistryShutdownHub registryShutdownHub) {
    	final DefaultFileSystemManager fs;
		try {
			fs = (DefaultFileSystemManager)VFS.getManager();
	    	if (!fs.hasProvider("file")) {
	    		fs.addProvider("file", new DefaultLocalFileProvider());
			}
	    	File file = new File(fileFullPlace);
	    	FileObject fo = fs.toFileObject(file);
	    	if (!fo.exists()) {
				fo.createFolder();
			}
	    	registryShutdownHub.addRegistryShutdownListener(new Runnable() {
				public void run() {
					fs.close();
				}
			});
	    	return fs;
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
    	return null;
    }
	
    @Marker(VFSProvider.class)
    public AssetFactory buildFileAssetFactory(ApplicationGlobals globals,

    	    AssetPathConstructor assetPathConstructor,

    	    AssetPathConverter converter,
    	    
    	FileSystemManager fs,
    	
    	@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
    	String fileFullPlace)
    {
        return new VFSAssetFactory(assetPathConstructor, globals.getContext(), converter, fs, fileFullPlace);
    }
    
    public void contributeAssetSource(MappedConfiguration<String, AssetFactory> configuration,
            						  @VFSProvider AssetFactory vfsAssetFactory)
	{
    	configuration.add(VFSAssetConstants.VFS_ASSET, vfsAssetFactory);
	}
    
	public void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration,
			@VFSProvider AssetFactory assetFactory,
			FileSystemManager fs,
			@Symbol(VFSAssetConstants.FILE_FULL_PLACE)
	    	String fileFullPlace)
	{
	    configuration.add(VFSAssetConstants.VFS_ASSET, new VFSBindingFactory(assetFactory, fs, fileFullPlace));
	}
    
    public static void contributeAssetDispatcher(MappedConfiguration<String, AssetRequestHandler> configuration,

    	    @VFSProvider
    	    AssetFactory contextAssetFactory,

    	    ResourceStreamer streamer)
    {
        configuration.add(VFSAssetConstants.VFS_ASSET_CLIENT_FOLDER,
                new VFSAssetRequestHandler(streamer, contextAssetFactory.getRootResource()));
    }

	@Advise(serviceInterface=Dispatcher.class, id="VFSExt")
    public static void vfsExtAdvise(final VFSAssetDispatherAdvisor advisor,
            final MethodAdviceReceiver receiver)
    {
        advisor.addDispatchAdvice(receiver);
    }
}
