package com.ranhfun.zip.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Response;

import com.ranhfun.zip.ZipResponse;
import com.ranhfun.zip.util.ZipCompressor;

public class ZipResponseResultProcessor implements 
	ComponentEventResultProcessor<ZipResponse> {

    private final Response response;

    public ZipResponseResultProcessor(Response response)
    {
        this.response = response;
    }
	
	public void processResultValue(ZipResponse zipResponse) throws IOException {
		OutputStream os = null;
        InputStream is = null;

        response.disableCompression();

        response.setHeader("Content-Disposition", "attachment" + "; filename=" + zipResponse.getFileName() + ".zip");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        
        try
        {
        	os = response.getOutputStream("application/zip");
        	
        	ZipCompressor compressor = new ZipCompressor();
        	compressor.compress(zipResponse.getBasePath(), zipResponse.getFilePaths(), os);
        	
            os.close();
            os = null;
        }
        finally
        {
            InternalUtils.close(is);
            InternalUtils.close(os);
        }
	}

}
