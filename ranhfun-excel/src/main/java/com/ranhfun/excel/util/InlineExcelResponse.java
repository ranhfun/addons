package com.ranhfun.excel.util;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;

import com.ranhfun.excel.ExcelType;

public class InlineExcelResponse extends AbstractExcelResponse {

	public InlineExcelResponse(InputStream stream, String... args) {
		super(stream, args);
	}
	
	public InlineExcelResponse(Workbook workbook, String... args) {
		super(workbook, args);
	}
	
	public ExcelType getExcelType() {
		return ExcelType.INLINE;
	}

}
