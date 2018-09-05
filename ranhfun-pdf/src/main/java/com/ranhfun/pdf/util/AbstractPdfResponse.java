package com.ranhfun.pdf.util;

import com.ranhfun.pdf.PdfResponse;
import com.ranhfun.pdf.PdfType;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public abstract class AbstractPdfResponse implements PdfResponse {

	private JasperReportBuilder builder = null;
	private JasperConcatenatedReportBuilder concatentatedbuilder = null;
	private String fileName = "default";

	public AbstractPdfResponse(JasperReportBuilder builder, String... args) {
		this.builder = builder;
		if (args != null && args.length > 0) {
			fileName = args[0];
		}
	}

	public AbstractPdfResponse(
			JasperConcatenatedReportBuilder concatentatedbuilder,
			String... args) {
		this.concatentatedbuilder = concatentatedbuilder;
		if (args != null && args.length > 0) {
			fileName = args[0];
		}
	}

	public abstract PdfType getPdfType();

	public JasperReportBuilder getBuilder() throws DRException {
		return builder;
	}

	public JasperConcatenatedReportBuilder getConcatenatedBuilder()
			throws DRException {
		return concatentatedbuilder;
	}

	public String getFileName() {
		return fileName;
	}

}
