package com.ranhfun.price.jdbc;

import javax.sql.DataSource;

public class JDBCPersistentAdapter {

	private DataSource dataSource;
	private JDBCAdapter jdbcAdapter;
	
	public JDBCPersistentAdapter(DataSource dataSource, JDBCAdapter jdbcAdapter) {
		this.dataSource = dataSource;
		this.jdbcAdapter = jdbcAdapter;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public JDBCAdapter getJdbcAdapter() {
		return jdbcAdapter;
	}
	
}
