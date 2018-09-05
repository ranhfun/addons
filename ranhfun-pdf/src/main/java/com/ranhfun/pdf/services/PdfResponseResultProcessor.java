package com.ranhfun.pdf.services;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.dynamicreports.report.exception.DRException;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.Response;

import com.ranhfun.pdf.PdfResponse;
import com.ranhfun.pdf.PdfType;

public class PdfResponseResultProcessor implements 
	ComponentEventResultProcessor<PdfResponse> {

    private final Response response;

    public PdfResponseResultProcessor(Response response)
    {
        this.response = response;
    }
	
	public void processResultValue(PdfResponse pdfResponse) throws IOException {
		OutputStream os = null;

        response.disableCompression();

        response.setHeader("Content-Disposition", (pdfResponse.getPdfType()==PdfType.INLINE?"inline":"attachment") + "; filename=" + pdfResponse.getFileName() + ".pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        
        try
        {
            os = response.getOutputStream("application/pdf");

            if (pdfResponse.getBuilder()!=null) {
            	pdfResponse.getBuilder().toPdf(os);
			} else {
				pdfResponse.getConcatenatedBuilder().toPdf(os);
			}
            
            os.close();
            os = null;
        } catch (DRException e) {
        	// 
		}
        finally
        {
            InternalUtils.close(os);
        }
	}

}
