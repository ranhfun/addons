/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ranhfun.price.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ranhfun.price.util.IOExceptionSupport;

/**
 * Helps keep track of the current transaction/JDBC connection.
 * 
 * 
 */
public class TransactionContext {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionContext.class);

    private final DataSource dataSource;
    private final boolean changeAutoCommitAllowed;
    private final Logger logger;
    private Connection connection;
    private boolean inTx;
    private PreparedStatement addAgentInfoStatement;
    private PreparedStatement updateAgentInfoStatement;
    private PreparedStatement addNumberInfoStatement;
    private PreparedStatement updateNumberInfoStatement;
    // a cheap dirty level that we can live with    
    private int transactionIsolation = Connection.TRANSACTION_READ_UNCOMMITTED;
    
    public TransactionContext(boolean changeAutoCommitAllowed, DataSource dataSource, Logger logger) {
    	this.changeAutoCommitAllowed = changeAutoCommitAllowed;
    	this.dataSource = dataSource;
    	this.logger = logger;
    }

    public Connection getConnection() throws IOException {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                if (changeAutoCommitAllowed) {
                    boolean autoCommit = !inTx;
                    if (connection.getAutoCommit() != autoCommit) {
                        LOG.trace("Setting auto commit to {} on connection {}", autoCommit, connection);
                        connection.setAutoCommit(autoCommit);
                    }
                }
            } catch (SQLException e) {
                logger.warn("Could not get JDBC connection: ", e);
                IOException ioe = IOExceptionSupport.create(e);
                throw ioe;

            }

            try {
                connection.setTransactionIsolation(transactionIsolation);
            } catch (Throwable e) {
            }
        }
        return connection;
    }

    public void executeBatch() throws SQLException {
        try {
            executeBatch(addAgentInfoStatement, "Failed add a agent");
        } finally {
        	addAgentInfoStatement = null;
            try {
                executeBatch(updateAgentInfoStatement, "Failed to update a agent");
            } finally {
            	updateAgentInfoStatement = null;
                try {
                    executeBatch(addNumberInfoStatement, "Failed to add a number");
                } finally {
                	addNumberInfoStatement = null;
                    try {
                        executeBatch(updateNumberInfoStatement, "Failed to update a number");
                    } finally {
                    	updateNumberInfoStatement = null;
                    }
                }
            }
        }
    }

    private void executeBatch(PreparedStatement p, String message) throws SQLException {
        if (p == null) {
            return;
        }

        try {
            int[] rc = p.executeBatch();
            for (int i = 0; i < rc.length; i++) {
                int code = rc[i];
                if (code < 0 && code != Statement.SUCCESS_NO_INFO) {
                    throw new SQLException(message + ". Response code: " + code);
                }
            }
        } finally {
            try {
                p.close();
            } catch (Throwable e) {
            }
        }
    }

    public void close() throws IOException {
        if (!inTx) {
            try {

                /**
                 * we are not in a transaction so should not be committing ??
                 * This was previously commented out - but had adverse affects
                 * on testing - so it's back!
                 * 
                 */
                try {
                    executeBatch();
                } finally {
                    if (connection != null && !connection.getAutoCommit()) {
                        connection.commit();
                    }
                }

            } catch (SQLException e) {
                logger.warn("Error while closing connection: ", e);
                IOException ioe = IOExceptionSupport.create(e);
                throw ioe;
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Throwable e) {
                    LOG.warn("Close failed: " + e.getMessage(), e);
                } finally {
                    connection = null;
                }
            }
        }
    }

    public void begin() throws IOException {
        if (inTx) {
            throw new IOException("Already started.");
        }
        inTx = true;
        connection = getConnection();
    }

    public void commit() throws IOException {
        if (!inTx) {
            throw new IOException("Not started.");
        }
        try {
            executeBatch();
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            logger.warn("Commit failed: ", e);
            
            this.rollback(); 
            
            throw IOExceptionSupport.create(e);
        } finally {
            inTx = false;
            close();
        }
    }

    public void rollback() throws IOException {
        if (!inTx) {
            throw new IOException("Not started.");
        }
        try {
            if (addAgentInfoStatement != null) {
            	addAgentInfoStatement.close();
            	addAgentInfoStatement = null;
            }
            if (updateAgentInfoStatement != null) {
            	updateAgentInfoStatement.close();
            	updateAgentInfoStatement = null;
            }
            if (addNumberInfoStatement != null) {
            	addNumberInfoStatement.close();
            	addNumberInfoStatement = null;
            }
            if (updateNumberInfoStatement != null) {
            	updateNumberInfoStatement.close();
            	updateNumberInfoStatement = null;
            }
            connection.rollback();

        } catch (SQLException e) {
            logger.warn("Rollback failed: ", e);
            throw IOExceptionSupport.create(e);
        } finally {
            inTx = false;
            close();
        }
    }

    public PreparedStatement getAddAgentInfoStatement() {
		return addAgentInfoStatement;
	}

	public void setAddAgentInfoStatement(PreparedStatement addAgentInfoStatement) {
		this.addAgentInfoStatement = addAgentInfoStatement;
	}

	public PreparedStatement getUpdateAgentInfoStatement() {
		return updateAgentInfoStatement;
	}

	public void setUpdateAgentInfoStatement(
			PreparedStatement updateAgentInfoStatement) {
		this.updateAgentInfoStatement = updateAgentInfoStatement;
	}

	public PreparedStatement getAddNumberInfoStatement() {
		return addNumberInfoStatement;
	}

	public void setAddNumberInfoStatement(PreparedStatement addNumberInfoStatement) {
		this.addNumberInfoStatement = addNumberInfoStatement;
	}

	public PreparedStatement getUpdateNumberInfoStatement() {
		return updateNumberInfoStatement;
	}

	public void setUpdateNumberInfoStatement(
			PreparedStatement updateNumberInfoStatement) {
		this.updateNumberInfoStatement = updateNumberInfoStatement;
	}

	public void setTransactionIsolation(int transactionIsolation) {
        this.transactionIsolation = transactionIsolation;
    }

}
