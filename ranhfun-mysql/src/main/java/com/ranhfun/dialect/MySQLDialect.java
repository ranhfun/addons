package com.ranhfun.dialect;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import com.ranhfun.util.MysqlFunctionConstants;

public class MySQLDialect extends org.hibernate.dialect.MySQLDialect {
	
	public MySQLDialect() {
		super();
	}
	
	protected void registerVarcharTypes() {
		registerFunction(MysqlFunctionConstants.GROUP_CONCAT_WITHOUT_DISTINCT, new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat((?1))"));
		registerFunction(MysqlFunctionConstants.GROUP_CONCAT, new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(distinct(?1))"));
		registerFunction(MysqlFunctionConstants.GROUP_CONCAT_DISTINCT, new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(distinct(?1) separator '')"));
		registerFunction(MysqlFunctionConstants.CURR_MONTH_FORMAT, new SQLFunctionTemplate(StandardBasicTypes.DATE, "date_format(curdate(),'%Y%m')"));
		registerFunction(MysqlFunctionConstants.DATE_FORMAT_YEAR_MONTH, new SQLFunctionTemplate(StandardBasicTypes.DATE, "date_format(?1,'%Y%m')"));
		registerFunction(MysqlFunctionConstants.PERID_DIFF_YEAR_MONTH, new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "period_diff(date_format(now(),'%Y%m'), date_format(?1, '%Y%m'))"));
		registerFunction(MysqlFunctionConstants.PERID_DIFF_SECOND, new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "period_diff(timediff(now(),'%Y%m'), date_format(?1, '%Y%m'))"));
		
		registerFunction(MysqlFunctionConstants.IF_NULL, new StandardSQLFunction("ifnull", StandardBasicTypes.DOUBLE));
		registerFunction(MysqlFunctionConstants.DATE_ADD_DAY, new SQLFunctionTemplate(StandardBasicTypes.DATE, "date_add(?1,INTERVAL ?2 DAY)"));
		registerFunction(MysqlFunctionConstants.TIMESTAMP_MINUTE_DIFF, new SQLFunctionTemplate(StandardBasicTypes.LONG, "timestampdiff(MINUTE,?1,?2)"));
		//(2 * 6371 * ASIN(SQRT(POW(SIN(PI() * (res.lat - site.lat) / 360), 2) + COS(PI() * res.lat / 180) * COS(site.lat * PI() / 180) * POW(SIN(PI() * (res.lon - site.lon) / 360), 2))))
		registerFunction(MysqlFunctionConstants.DISTANCE, new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "(2 * 6371 * ASIN(SQRT(POW(SIN(PI() * (?1 - ?2) / 360), 2) + COS(PI() * ?3 / 180) * COS(?4 * PI() / 180) * POW(SIN(PI() * (?5 - ?6) / 360), 2))))"));
	}
}
