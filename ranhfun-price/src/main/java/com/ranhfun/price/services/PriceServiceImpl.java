package com.ranhfun.price.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.ranhfun.price.PriceConstants;
import com.ranhfun.price.data.AgentInfo;
import com.ranhfun.price.data.NumberInfo;
import com.ranhfun.price.jdbc.JDBCAdapter;
import com.ranhfun.price.jdbc.JDBCPersistentAdapter;
import com.ranhfun.price.jdbc.Statements;
import com.ranhfun.price.jdbc.TransactionContext;
import com.ranhfun.price.util.IOExceptionSupport;
import com.ranhfun.price.util.LongSequenceGenerator;

public class PriceServiceImpl implements PriceService {

	private JDBCAdapter adapter;
	private DataSource dataSource;
	private int transactionIsolation;
	private Logger logger;
	protected LongSequenceGenerator sequenceGenerator = new LongSequenceGenerator();
	
	public PriceServiceImpl(@Symbol(PriceConstants.PRICE_TRANSACTION_ISOLATION) int transactionIsolation, 
			Logger logger, List<JDBCPersistentAdapter> contributions) {
		this.transactionIsolation = transactionIsolation;
		this.logger = logger;
    	if (contributions.isEmpty())
    		throw new RuntimeException("Configuration to JDBCPersistentAdapter services needed");
		for (JDBCPersistentAdapter persistentAdapter : contributions) {
			adapter = persistentAdapter.getJdbcAdapter();
			dataSource = persistentAdapter.getDataSource();
		}
		try {
			doCreateTables();
			sequenceGenerator.setLastSequenceId(doGetLastPriceInfoSequenceId());
		} catch (IOException e) {
			//
		}
	}
	
	public void setStatements(Statements statementProvider) {
		adapter.setStatements(statementProvider);
	}

	public void doCreateTables() throws
			IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doCreateTables(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to create table: " + e, e);
		} finally {
			c.close();
		}
	}

	public void doDropTables() throws
			IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doDropTables(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to drop table: " + e, e);
		} finally {
			c.close();
		}
	}

	public void doAddAgentInfo(AgentInfo agentInfo)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			agentInfo.setId(getNextSequenceId());
			adapter.doAddAgentInfo(c, agentInfo);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to add agentInfo for " + agentInfo.getPriceType() + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doSetAgentInfo(AgentInfo agentInfo)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doSetAgentInfo(c, agentInfo);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to set agentInfo for " + agentInfo.getPriceType() + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public AgentInfo doGetAgentInfo(long id)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetAgentInfo(c, id);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get agentInfo for id:" + id + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public AgentInfo[] doGetAgentInfoByPriceType(String priceType) 
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetAgentInfoByPriceType(c, priceType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get agentInfo for priceType:" + priceType + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public AgentInfo[] doGetAllAgentInfos()
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetAllAgentInfos(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get all agentInfo. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public boolean doExistsAgentInfo(String priceType, String agentType)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doExistsAgentInfo(c, priceType, agentType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to exists agentInfo for priceType:" + priceType + ",agentType:" + agentType + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}
	
	public boolean doExistsAgentInfo(String agentType) throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doExistsAgentInfo(c, agentType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to exists agentInfo for agentType:" + agentType + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doRemoveAgentInfo(long id)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doRemoveAgentInfo(c, id);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to remove agentInfo for id:" + id + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doRemoveAllAgentInfos()
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doRemoveAllAgentInfos(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to remove all agentInfo. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public int doGetAgentInfoCount() throws
			IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetAgentInfoCount(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to count agentInfo. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doAddNumberInfo(NumberInfo numberInfo)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			numberInfo.setId(getNextSequenceId());
			adapter.doAddNumberInfo(c, numberInfo);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to add numberInfo for " + numberInfo.getPriceType() + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doSetNumberInfo(NumberInfo numberInfo)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doSetNumberInfo(c, numberInfo);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to set numberInfo for " + numberInfo.getPriceType() + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public NumberInfo doGetNumberInfo(long id)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetNumberInfo(c, id);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get numberInfo for id:" + id + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public NumberInfo[] doGetNumberInfoByPriceType(String priceType) 
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetNumberInfoByPriceType(c, priceType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get all numberInfo by priceType:" + priceType + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public NumberInfo[] doGetAllNumberInfos()
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetAllNumberInfos(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get all numberInfo. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doRemoveNumberInfo(long id)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doRemoveNumberInfo(c, id);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to remove numberInfo for id:" + id + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doRemoveAllNumberInfos()
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doRemoveAllNumberInfos(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to remove all numberInfo. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public void doRemoveAllNumberInfos(String priceType)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			adapter.doRemoveAllNumberInfos(c, priceType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to remove all numberInfo by priceType:" + priceType + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public int doGetNumberInfoCount() throws
			IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetNumberInfoCount(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to count numberInfo. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public int doGetNumberInfoCount(String priceType)
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetNumberInfoCount(c, priceType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to count numberInfo by priceType:" + priceType + ". Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public long doGetLastPriceInfoSequenceId()
			throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return adapter.doGetLastPriceInfoSequenceId(c);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to get last sequenceId. Reason:" + e, e);
		} finally {
			c.close();
		}
	}

    public TransactionContext getTransactionContext() throws IOException {
        TransactionContext answer = new TransactionContext(true, dataSource, logger);
        if (transactionIsolation > 0) {
            answer.setTransactionIsolation(transactionIsolation);
        }
        return answer;
    }
	
    public long getNextSequenceId() {
        synchronized(sequenceGenerator) {
            return sequenceGenerator.getNextSequenceId();
        }
    }

	public Double doCalculateAgentInfo(String priceType, String agentType,
			Double buyPrice) throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return buyPrice*adapter.doGetAgentPointByPriceTypeAndAgentType(c, priceType, agentType);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to calculate agentInfo for: priceType(" 
					+ priceType + "),agentType(" + agentType +  "),buyPrice(" + buyPrice + "). Reason:" + e, e);
		} finally {
			c.close();
		}
	}

	public Double doCalculateNumberInfo(String priceType, Double totalNumber,
			Double buyPrice) throws IOException {
		TransactionContext c = getTransactionContext();
		try {
			return buyPrice*adapter.doGetNumberPointByPriceTypeAndTotalNumber(c, priceType, totalNumber);
		} catch (SQLException e) {
			logger.warn("JDBC Failure: ", e);
			throw IOExceptionSupport.create("Failed to calculate agentInfo for: priceType(" 
					+ priceType + "),totalNumber(" + totalNumber +  "),buyPrice(" + buyPrice + "). Reason:" + e, e);
		} finally {
			c.close();
		}
	}
}
