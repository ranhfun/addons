package com.ranhfun.excel.services;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Response;

import com.ranhfun.excel.ExcelResponse;
import com.ranhfun.excel.ExcelType;

public class ExcelResponseResultProcessor implements 
	ComponentEventResultProcessor<ExcelResponse> {

    private final Response response;

    public ExcelResponseResultProcessor(Response response)
    {
        this.response = response;
    }
	
	public void processResultValue(ExcelResponse excelResponse) throws IOException {
		OutputStream os = null;
        InputStream is = null;

        response.disableCompression();

        response.setHeader("Content-Disposition", (excelResponse.getExcelType()==ExcelType.INLINE?"inline":"attachment") + "; filename=" + excelResponse.getFileName() + ".xls");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        
        try
        {
        	os = response.getOutputStream("application/vnd.ms-excel");
        	
        	if (excelResponse.getWorkbook()!=null) {
				excelResponse.getWorkbook().write(os);
			} else {
	            is = new BufferedInputStream(excelResponse.getStream());
	            TapestryInternalUtils.copy(is, os);

	            is.close();
	            is = null;
			}
        	
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
