package com.ranhfun.pdf.util;

import com.ranhfun.pdf.PdfType;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

public class AttachmentPdfResponse extends AbstractPdfResponse {

	public AttachmentPdfResponse(JasperReportBuilder builder, String... args) {
		super(builder, args);
	}
	
	public AttachmentPdfResponse(JasperConcatenatedReportBuilder concatentatedbuilder, String... args) {
		super(concatentatedbuilder, args);
	}
	
	public PdfType getPdfType() {
		return PdfType.ATTACHMENT;
	}


}
