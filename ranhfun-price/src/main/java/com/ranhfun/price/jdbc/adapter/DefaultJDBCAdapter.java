package com.ranhfun.price.jdbc.adapter;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ranhfun.price.data.AgentInfo;
import com.ranhfun.price.data.NumberInfo;
import com.ranhfun.price.jdbc.JDBCAdapter;
import com.ranhfun.price.jdbc.Statements;
import com.ranhfun.price.jdbc.TransactionContext;

public class DefaultJDBCAdapter implements JDBCAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultJDBCAdapter.class);
    protected Statements statements;
    protected boolean batchStatments = true;
    protected boolean prioritizedMessages;
    protected ReadWriteLock cleanupExclusiveLock = new ReentrantReadWriteLock();

    protected void setBinaryData(PreparedStatement s, int index, byte data[]) throws SQLException {
        s.setBytes(index, data);
    }

    protected byte[] getBinaryData(ResultSet rs, int index) throws SQLException {
        return rs.getBytes(index);
    }

    public void doCreateTables(TransactionContext c) throws SQLException, IOException {
        Statement s = null;
        cleanupExclusiveLock.writeLock().lock();
        try {
            // Check to see if the table already exists. If it does, then don't
            // log warnings during startup.
            // Need to run the scripts anyways since they may contain ALTER
            // statements that upgrade a previous version
            // of the table
            boolean alreadyExists = false;
            ResultSet rs = null;
            try {
                rs = c.getConnection().getMetaData().getTables(null, null, this.statements.getFullAgentInfoTableName(),
                        new String[] { "TABLE" });
                alreadyExists = rs.next();
            } catch (Throwable ignore) {
            } finally {
                close(rs);
            }
            s = c.getConnection().createStatement();
            String[] createStatments = this.statements.getCreateSchemaStatements();
            for (int i = 0; i < createStatments.length; i++) {
                // This will fail usually since the tables will be
                // created already.
                try {
                    LOG.debug("Executing SQL: " + createStatments[i]);
                    s.execute(createStatments[i]);
                } catch (SQLException e) {
                    if (alreadyExists) {
                        LOG.debug("Could not create JDBC tables; The price table already existed." + " Failure was: "
                                + createStatments[i] + " Message: " + e.getMessage() + " SQLState: " + e.getSQLState()
                                + " Vendor code: " + e.getErrorCode());
                    } else {
                        LOG.warn("Could not create JDBC tables; they could already exist." + " Failure was: "
                                + createStatments[i] + " Message: " + e.getMessage() + " SQLState: " + e.getSQLState()
                                + " Vendor code: " + e.getErrorCode());
                        LOG.warn("Failure details: ", e);
                    }
                }
            }
           // c.getConnection().commit();
        } finally {
            cleanupExclusiveLock.writeLock().unlock();
            try {
                s.close();
            } catch (Throwable e) {
            }
        }
    }

    public void doDropTables(TransactionContext c) throws SQLException, IOException {
        Statement s = null;
        cleanupExclusiveLock.writeLock().lock();
        try {
            s = c.getConnection().createStatement();
            String[] dropStatments = this.statements.getDropSchemaStatements();
            for (int i = 0; i < dropStatments.length; i++) {
                // This will fail usually since the tables will be
                // created already.
                try {
                    LOG.debug("Executing SQL: " + dropStatments[i]);
                    s.execute(dropStatments[i]);
                } catch (SQLException e) {
                    LOG.warn("Could not drop JDBC tables; they may not exist." + " Failure was: " + dropStatments[i]
                            + " Message: " + e.getMessage() + " SQLState: " + e.getSQLState() + " Vendor code: "
                            + e.getErrorCode());
                    LOG.warn("Failure details: ", e);
                }
            }
          //  c.getConnection().commit();
        } finally {
            cleanupExclusiveLock.writeLock().unlock();
            try {
                s.close();
            } catch (Throwable e) {
            }
        }
    }

    public long doGetLastMessageStoreSequenceId(TransactionContext c) throws SQLException, IOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getLastAgentInfoSequenceIdStatement());
            rs = s.executeQuery();
            long seq1 = 0;
            if (rs.next()) {
                seq1 = rs.getLong(1);
            }
            rs.close();
            s.close();
            s = c.getConnection().prepareStatement(this.statements.getLastNumberInfoSequenceIdStatement());
            rs = s.executeQuery();
            long seq2 = 0;
            if (rs.next()) {
                seq2 = rs.getLong(1);
            }
            long seq = Math.max(seq1, seq2);
            return seq;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
    }

	public void doAddAgentInfo(TransactionContext c, AgentInfo agentInfo)
			throws SQLException, IOException {
        PreparedStatement s = c.getAddAgentInfoStatement();
        cleanupExclusiveLock.readLock().lock();
        try {
            if (s == null) {
                s = c.getConnection().prepareStatement(this.statements.getInsertAgentInfoStatement());
                if (this.batchStatments) {
                    c.setAddAgentInfoStatement(s);
                }
            }
            s.setLong(1, agentInfo.getId());
            s.setString(2, agentInfo.getPriceType());
            s.setString(3, agentInfo.getAgentType());
            s.setDouble(4, agentInfo.getAgentPoint());
            if (this.batchStatments) {
                s.addBatch();
            } else if (s.executeUpdate() != 1) {
                throw new SQLException("Failed add a agent");
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            if (!this.batchStatments) {
                if (s != null) {
                    s.close();
                }
            }
        }
	}

	public void doSetAgentInfo(TransactionContext c, AgentInfo agentInfo)
			throws SQLException, IOException {
        PreparedStatement s = c.getUpdateAgentInfoStatement();
        cleanupExclusiveLock.readLock().lock();
        try {
            if (s == null) {
                s = c.getConnection().prepareStatement(this.statements.getUpdateAgentInfoStatement());
                if (this.batchStatments) {
                    c.setUpdateAgentInfoStatement(s);
                }
            }
            s.setString(1, agentInfo.getPriceType());
            s.setString(2, agentInfo.getAgentType());
            s.setDouble(3, agentInfo.getAgentPoint());
            s.setLong(4, agentInfo.getId());
            if (this.batchStatments) {
                s.addBatch();
            } else if (s.executeUpdate() != 1) {
                throw new SQLException("Failed set a agent");
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            if (!this.batchStatments) {
                if (s != null) {
                    s.close();
                }
            }
        }
	}

	public AgentInfo doGetAgentInfo(TransactionContext c, long id)
			throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindAgentInfoStatement());
            s.setLong(1, id);
            rs = s.executeQuery();
            if (!rs.next()) {
                return null;
            }
            AgentInfo agentInfo = new AgentInfo();
            agentInfo.setId(rs.getLong(1));
            agentInfo.setPriceType(rs.getString(2));
            agentInfo.setAgentType(rs.getString(3));
            agentInfo.setAgentPoint(rs.getDouble(4));
            return agentInfo;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public AgentInfo[] doGetAgentInfoByPriceType(TransactionContext c,
			String priceType) throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindAgentInfoByPriceTypeStatement());
            s.setString(1, priceType);
            rs = s.executeQuery();
            ArrayList<AgentInfo> rc = new ArrayList<AgentInfo>();
            while (rs.next()) {
                AgentInfo agentInfo = new AgentInfo();
                agentInfo.setId(rs.getLong(1));
                agentInfo.setPriceType(rs.getString(2));
                agentInfo.setAgentType(rs.getString(3));
                agentInfo.setAgentPoint(rs.getDouble(4));
	            rc.add(agentInfo);
            }
            return rc.toArray(new AgentInfo[rc.size()]);
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public AgentInfo[] doGetAllAgentInfos(TransactionContext c)
			throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindAllAgentInfosStatement());
            rs = s.executeQuery();
            ArrayList<AgentInfo> rc = new ArrayList<AgentInfo>();
            while (rs.next()) {
	            AgentInfo agentInfo = new AgentInfo();
	            agentInfo.setId(rs.getLong(1));
	            agentInfo.setPriceType(rs.getString(2));
	            agentInfo.setAgentType(rs.getString(3));
	            agentInfo.setAgentPoint(rs.getDouble(4));
	            rc.add(agentInfo);
            }
            return rc.toArray(new AgentInfo[rc.size()]);
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public boolean doExistsAgentInfo(TransactionContext c, String priceType,
			String agentType) throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getExistsAgentInfoByPriceTypeStatement());
            s.setString(1, priceType);
            s.setString(2, agentType);
            rs = s.executeQuery();
            if (rs.next()) {
            	return rs.getInt(1)>0;
            }
            return false;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}
	
	public boolean doExistsAgentInfo(TransactionContext c, String agentType) throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getExistsAgentInfoByAgentTypeStatement());
            s.setString(1, agentType);
            rs = s.executeQuery();
            if (rs.next()) {
            	return rs.getInt(1)>0;
            }
            return false;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public void doRemoveAgentInfo(TransactionContext c, long id)
			throws SQLException, IOException {
        PreparedStatement s = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getRemoveAgentInfoStatement());
            s.setLong(1, id);
            s.executeUpdate();
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(s);
        }
	}

	public void doRemoveAllAgentInfos(TransactionContext c)
			throws SQLException, IOException {
        PreparedStatement s = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getRemoveAllAgentInfosStatement());
            s.executeUpdate();
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(s);
        }
	}

	public int doGetAgentInfoCount(TransactionContext c) throws SQLException,
			IOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getAgentInfoCountStatement());
            rs = s.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
        return result;
	}

	public void doAddNumberInfo(TransactionContext c, NumberInfo numberInfo)
			throws SQLException, IOException {
        PreparedStatement s = c.getAddNumberInfoStatement();
        cleanupExclusiveLock.readLock().lock();
        try {
            if (s == null) {
                s = c.getConnection().prepareStatement(this.statements.getInsertNumberInfoStatement());
                if (this.batchStatments) {
                    c.setAddNumberInfoStatement(s);
                }
            }
            s.setLong(1, numberInfo.getId());
            s.setString(2, numberInfo.getPriceType());
            s.setDouble(3, numberInfo.getTotalNumber());
            s.setDouble(4, numberInfo.getTotalPoint());
            if (this.batchStatments) {
                s.addBatch();
            } else if (s.executeUpdate() != 1) {
                throw new SQLException("Failed add a number");
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            if (!this.batchStatments) {
                if (s != null) {
                    s.close();
                }
            }
        }
	}

	public void doSetNumberInfo(TransactionContext c, NumberInfo numberInfo)
			throws SQLException, IOException {
        PreparedStatement s = c.getUpdateNumberInfoStatement();
        cleanupExclusiveLock.readLock().lock();
        try {
            if (s == null) {
                s = c.getConnection().prepareStatement(this.statements.getUpdateNumberInfoStatement());
                if (this.batchStatments) {
                    c.setUpdateNumberInfoStatement(s);
                }
            }
            s.setString(1, numberInfo.getPriceType());
            s.setDouble(2, numberInfo.getTotalNumber());
            s.setDouble(3, numberInfo.getTotalPoint());
            s.setLong(4, numberInfo.getId());
            if (this.batchStatments) {
                s.addBatch();
            } else if (s.executeUpdate() != 1) {
                throw new SQLException("Failed set a number");
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            if (!this.batchStatments) {
                if (s != null) {
                    s.close();
                }
            }
        }
	}

	public NumberInfo doGetNumberInfo(TransactionContext c, long id)
			throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindNumberInfoStatement());
            s.setLong(1, id);
            rs = s.executeQuery();
            if (!rs.next()) {
                return null;
            }
            NumberInfo numberInfo = new NumberInfo();
            numberInfo.setId(rs.getLong(1));
            numberInfo.setPriceType(rs.getString(2));
            numberInfo.setTotalNumber(rs.getDouble(3));
            numberInfo.setTotalPoint(rs.getDouble(4));
            return numberInfo;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public NumberInfo[] doGetNumberInfoByPriceType(TransactionContext c,
			String priceType) throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindNumberInfoByPriceTypeStatement());
            s.setString(1, priceType);
            rs = s.executeQuery();
            ArrayList<NumberInfo> rc = new ArrayList<NumberInfo>();
            while (rs.next()) {
            	NumberInfo numberInfo = new NumberInfo();
            	numberInfo.setId(rs.getLong(1));
            	numberInfo.setPriceType(rs.getString(2));
            	numberInfo.setTotalNumber(rs.getDouble(3));
            	numberInfo.setTotalPoint(rs.getDouble(4));
	            rc.add(numberInfo);
            }
            return rc.toArray(new NumberInfo[rc.size()]);
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public NumberInfo[] doGetAllNumberInfos(TransactionContext c)
			throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindAllNumberInfosStatement());
            rs = s.executeQuery();
            ArrayList<NumberInfo> rc = new ArrayList<NumberInfo>();
            while (rs.next()) {
            	NumberInfo numberInfo = new NumberInfo();
            	numberInfo.setId(rs.getLong(1));
            	numberInfo.setPriceType(rs.getString(2));
            	numberInfo.setTotalNumber(rs.getDouble(3));
            	numberInfo.setTotalPoint(rs.getDouble(4));
	            rc.add(numberInfo);
            }
            return rc.toArray(new NumberInfo[rc.size()]);
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public void doRemoveNumberInfo(TransactionContext c, long id)
			throws SQLException, IOException {
        PreparedStatement s = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getRemoveNumberInfoStatement());
            s.setLong(1, id);
            s.executeUpdate();
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(s);
        }
	}

	public void doRemoveAllNumberInfos(TransactionContext c)
			throws SQLException, IOException {
        PreparedStatement s = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getRemoveAllNumberInfosStatement());
            s.executeUpdate();
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(s);
        }
	}

	public void doRemoveAllNumberInfos(TransactionContext c, String priceType)
			throws SQLException, IOException {
        PreparedStatement s = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getRemoveAllNumberInfosWithPriceTypeStatement());
            s.setString(1, priceType);
            s.executeUpdate();
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(s);
        }
	}

	public int doGetNumberInfoCount(TransactionContext c) throws SQLException,
			IOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getNumberInfoCountStatement());
            rs = s.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
        return result;
	}

	public int doGetNumberInfoCount(TransactionContext c, String priceType)
			throws SQLException, IOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        int result = 0;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getNumberInfoCountWithPriceTypeStatement());
            s.setString(1, priceType);
            rs = s.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
        return result;
	}

	public long doGetLastPriceInfoSequenceId(TransactionContext c)
			throws SQLException, IOException {
        PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getLastAgentInfoSequenceIdStatement());
            rs = s.executeQuery();
            long seq1 = 0;
            if (rs.next()) {
                seq1 = rs.getLong(1);
            }
            rs.close();
            s.close();
            s = c.getConnection().prepareStatement(this.statements.getLastNumberInfoSequenceIdStatement());
            rs = s.executeQuery();
            long seq2 = 0;
            if (rs.next()) {
                seq2 = rs.getLong(1);
            }
            long seq = Math.max(seq1, seq2);
            return seq;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public Double doGetAgentPointByPriceTypeAndAgentType(TransactionContext c,
			String priceType, String agentType) throws SQLException,
			IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindAgentPointByPriceTypeAndAgentTypeStatement());
            s.setString(1, priceType);
            s.setString(2, agentType);
            rs = s.executeQuery();
            if (rs.next()) {
				return rs.getDouble(1);
			}
            return null;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}

	public Double doGetNumberPointByPriceTypeAndTotalNumber(
			TransactionContext c, String priceType, Double totalNumber)
			throws SQLException, IOException {
		PreparedStatement s = null;
        ResultSet rs = null;
        cleanupExclusiveLock.readLock().lock();
        try {
            s = c.getConnection().prepareStatement(this.statements.getFindNumberPointByPriceTypeAndTotalNumberStatement());
            s.setString(1, priceType);
            s.setDouble(2, totalNumber);
            rs = s.executeQuery();
            if (rs.next()) {
				return rs.getDouble(1);
			}
            return null;
        } finally {
            cleanupExclusiveLock.readLock().unlock();
            close(rs);
            close(s);
        }
	}
	
    protected static void close(PreparedStatement s) {
        try {
            s.close();
        } catch (Throwable e) {
        }
    }

    protected static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (Throwable e) {
        }
    }

    public void setStatements(Statements statements) {
        this.statements = statements;
    }
}
