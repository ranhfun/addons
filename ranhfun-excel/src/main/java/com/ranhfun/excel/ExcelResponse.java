package com.ranhfun.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelResponse {

	ExcelType getExcelType();
	
	InputStream getStream() throws IOException;
	
	Workbook getWorkbook() throws IOException;
	
	String getFileName();
	
}
