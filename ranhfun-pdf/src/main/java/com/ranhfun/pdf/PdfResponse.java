package com.ranhfun.pdf;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public interface PdfResponse {

	PdfType getPdfType();
	
	JasperReportBuilder getBuilder() throws DRException;
	
	JasperConcatenatedReportBuilder getConcatenatedBuilder() throws DRException;
	
	String getFileName();
	
}
