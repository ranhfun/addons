package com.ranhfun.excel.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;

import com.ranhfun.excel.ExcelResponse;
import com.ranhfun.excel.ExcelType;

public abstract class AbstractExcelResponse implements ExcelResponse {

	private InputStream stream = null;
	private Workbook workbook = null;
	private String fileName = "default";
	
	public AbstractExcelResponse(InputStream stream, String... args) {
		this.stream = stream;
		if (args!=null && args.length>0) {
			fileName = args[0];
		}
	}
	
	public AbstractExcelResponse(Workbook workbook, String... args) {
		this.workbook = workbook;
		if (args!=null && args.length>0) {
			fileName = args[0];
		}
	}
	
	public abstract ExcelType getExcelType();

	public InputStream getStream() throws IOException {
		return stream;
	}
	
	public Workbook getWorkbook() throws IOException {
		return workbook;
	}

	public String getFileName() {
		return fileName;
	}

}
