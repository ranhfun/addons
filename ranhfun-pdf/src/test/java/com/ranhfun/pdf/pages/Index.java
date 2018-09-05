package com.ranhfun.pdf.pages;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.IOException;
import java.math.BigDecimal;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.jasperreports.engine.JRDataSource;

import com.ranhfun.pdf.util.AttachmentPdfResponse;
import com.ranhfun.pdf.util.DataSource;
import com.ranhfun.pdf.util.InlinePdfResponse;

public class Index {

	Object onActionFromAttachment() throws IOException {
		JasperReportBuilder builder = report()
		  .columns(
		  	col.column("Item",       "item",      type.stringType()),
		  	col.column("Quantity",   "quantity",  type.integerType()),
		  	col.column("Unit price", "unitprice", type.bigDecimalType()))
		  .title(cmp.text("Getting started"))//shows report title
		  .pageFooter(cmp.pageXofY())
		  .setDataSource(createDataSource());
		return new AttachmentPdfResponse(builder);
	}
	
	Object onActionFromInline() throws IOException {
		JasperReportBuilder builder = report()
		  .columns(
		  	col.column("Item",       "item",      type.stringType()),
		  	col.column("Quantity",   "quantity",  type.integerType()),
		  	col.column("Unit price", "unitprice", type.bigDecimalType()))
		  .title(cmp.text("Getting started"))//shows report title
		  .pageFooter(cmp.pageXofY())
		  .setDataSource(createDataSource());
		return new InlinePdfResponse(builder);
	}
	
	private JRDataSource createDataSource() {
		DataSource dataSource = new DataSource("item", "quantity", "unitprice");
		dataSource.add("Notebook", 1, new BigDecimal(500));
		dataSource.add("DVD", 5, new BigDecimal(30));
		dataSource.add("DVD", 1, new BigDecimal(28));
		dataSource.add("DVD", 5, new BigDecimal(32));
		dataSource.add("Book", 3, new BigDecimal(11));
		dataSource.add("Book", 1, new BigDecimal(15));
		dataSource.add("Book", 5, new BigDecimal(10));
		dataSource.add("Book", 8, new BigDecimal(9));
		return dataSource;
	}
	
}
