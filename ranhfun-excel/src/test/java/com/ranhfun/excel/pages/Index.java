package com.ranhfun.excel.pages;

import java.io.IOException;

import org.apache.tapestry5.ioc.internal.util.ClasspathResource;

import com.ranhfun.excel.util.AttachmentExcelResponse;
import com.ranhfun.excel.util.InlineExcelResponse;

public class Index {

	/**
	 * 以附件方式打开
	 * @return
	 * @throws IOException
	 */
	Object onActionFromAttachment() throws IOException {
		return new AttachmentExcelResponse(new ClasspathResource("output.xls").openStream());
	}
	
	/**
	 * 以浏览器内嵌方式打开
	 * @return
	 * @throws IOException
	 */
	Object onActionFromInline() throws IOException {
		return new InlineExcelResponse(new ClasspathResource("output.xls").openStream());
	}
	
}
