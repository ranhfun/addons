package com.ranhfun.price.jdbc;

import java.io.IOException;
import java.sql.SQLException;

import com.ranhfun.price.data.AgentInfo;
import com.ranhfun.price.data.NumberInfo;

/**
 * 
 */
public interface JDBCAdapter {

    void setStatements(Statements statementProvider);
    
    void doCreateTables(TransactionContext c) throws SQLException, IOException;

    void doDropTables(TransactionContext c) throws SQLException, IOException;

    void doAddAgentInfo(TransactionContext c, AgentInfo agentInfo) throws SQLException, IOException;
    
    void doSetAgentInfo(TransactionContext c, AgentInfo agentInfo) throws SQLException, IOException;

    AgentInfo doGetAgentInfo(TransactionContext c, long id) throws SQLException, IOException;
    
    AgentInfo[] doGetAgentInfoByPriceType(TransactionContext c, String priceType) throws SQLException, IOException;
    
    AgentInfo[] doGetAllAgentInfos(TransactionContext c) throws SQLException, IOException;
    
    boolean doExistsAgentInfo(TransactionContext c, String priceType, String agentType) throws SQLException, IOException;
    
    boolean doExistsAgentInfo(TransactionContext c, String agentType) throws SQLException, IOException;
    
    void doRemoveAgentInfo(TransactionContext c, long id) throws SQLException, IOException;
    
    void doRemoveAllAgentInfos(TransactionContext c) throws SQLException, IOException;
    
    int doGetAgentInfoCount(TransactionContext c) throws SQLException, IOException;
    
    void doAddNumberInfo(TransactionContext c, NumberInfo numberInfo) throws SQLException, IOException;
    
    void doSetNumberInfo(TransactionContext c, NumberInfo numberInfo) throws SQLException, IOException;

    NumberInfo doGetNumberInfo(TransactionContext c, long id) throws SQLException, IOException;
    
    NumberInfo[] doGetNumberInfoByPriceType(TransactionContext c, String priceType) throws SQLException, IOException;
    
    NumberInfo[] doGetAllNumberInfos(TransactionContext c) throws SQLException, IOException;
    
    void doRemoveNumberInfo(TransactionContext c, long id) throws SQLException, IOException;
    
    void doRemoveAllNumberInfos(TransactionContext c) throws SQLException, IOException;
    
    void doRemoveAllNumberInfos(TransactionContext c, String priceType) throws SQLException, IOException;
    
    int doGetNumberInfoCount(TransactionContext c) throws SQLException, IOException;
    
    int doGetNumberInfoCount(TransactionContext c, String priceType) throws SQLException, IOException;
    
    long doGetLastPriceInfoSequenceId(TransactionContext c) throws SQLException, IOException;
    
    Double doGetAgentPointByPriceTypeAndAgentType(TransactionContext c, String priceType, String agentType) throws SQLException, IOException;
    
    Double doGetNumberPointByPriceTypeAndTotalNumber(TransactionContext c, String priceType, Double totalNumber) throws SQLException, IOException;
}
