package com.ranhfun.photo.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.ranhfun.photo.PhotoConstants;

public class PhotoModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(PhotoFtpTransfer.class, PhotoFtpTransferImpl.class);
	}
	
    @Contribute(SymbolProvider.class)
    @FactoryDefaults
    public static void provideFactoryDefaults(
            final MappedConfiguration<String, String> configuration)
    {
        configuration.add(PhotoConstants.PHOTO_FTP_FOLDER, "autopart/files");
    }
	
}
