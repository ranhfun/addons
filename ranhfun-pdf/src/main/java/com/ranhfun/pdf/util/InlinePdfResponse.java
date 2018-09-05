package com.ranhfun.pdf.util;

import com.ranhfun.pdf.PdfType;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

public class InlinePdfResponse extends AbstractPdfResponse {

	public InlinePdfResponse(JasperReportBuilder builder, String... args) {
		super(builder, args);
	}
	
	public InlinePdfResponse(JasperConcatenatedReportBuilder concatentatedbuilder, String... args) {
		super(concatentatedbuilder, args);
	}
	
	public PdfType getPdfType() {
		return PdfType.INLINE;
	}

}
