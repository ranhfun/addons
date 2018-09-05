package com.ranhfun.price.services;

import java.io.IOException;

import com.ranhfun.price.data.AgentInfo;
import com.ranhfun.price.data.NumberInfo;
import com.ranhfun.price.jdbc.Statements;
import com.ranhfun.price.jdbc.TransactionContext;

public interface PriceService {

    void setStatements(Statements statementProvider);
    
    void doCreateTables() throws IOException;

    void doDropTables() throws IOException;

    /**
     * 增加代理策略
     * @param agentInfo
     * @throws IOException
     */
    void doAddAgentInfo(AgentInfo agentInfo) throws IOException;
    
    /**
     * 修改代理策略
     * @param agentInfo
     * @throws IOException
     */
    void doSetAgentInfo(AgentInfo agentInfo) throws IOException;

    /**
     * 获取代理策略
     * @param id
     * @throws IOException
     */
    AgentInfo doGetAgentInfo(long id) throws IOException;
    
    /**
     * 根据价格类型获取代理策略
     * @param priceType
     * @throws IOException
     */
    AgentInfo[] doGetAgentInfoByPriceType(String priceType) throws IOException;
    
    /**
     * 获取所有代理策略
     * @return
     * @throws IOException
     */
    AgentInfo[] doGetAllAgentInfos() throws IOException;
    
    /**
     * 判断代理是否存在
     * @param priceType
     * @param agentType
     * @return
     * @throws IOException
     */
    boolean doExistsAgentInfo(String priceType, String agentType) throws IOException;
    
    /**
     * 判断代理是否存在
     * @param agentType
     * @return
     * @throws IOException
     */
    boolean doExistsAgentInfo(String agentType) throws IOException;
    
    /**
     * 删除特定代理策略
     * @param id
     * @throws IOException
     */
    void doRemoveAgentInfo(long id) throws IOException;
    
    /**
     * 删除所有代理策略 
     * @throws IOException
     */
    void doRemoveAllAgentInfos() throws IOException;
    
    /**
     * 统计代理策略
     * @return
     * @throws IOException
     */
    int doGetAgentInfoCount() throws IOException;
    
    /**
     * 增加数量策略
     * @param agentInfo
     * @throws IOException
     */
    void doAddNumberInfo(NumberInfo numberInfo) throws IOException;
    
    /**
     * 修改数量策略
     * @param agentInfo
     * @throws IOException
     */
    void doSetNumberInfo(NumberInfo numberInfo) throws IOException;

    /**
     * 获取数量策略
     * @param id
     * @throws IOException
     */
    NumberInfo doGetNumberInfo(long id) throws IOException;
    
    /**
     * 根据价格类型获取数量策略
     * @param priceType
     * @return
     * @throws IOException
     */
    NumberInfo[] doGetNumberInfoByPriceType(String priceType) throws IOException;
    
    /**
     * 获取所有数量策略 
     * @return
     * @throws IOException
     */
    NumberInfo[] doGetAllNumberInfos() throws IOException;
    
    /**
     * 删除特定数量策略
     * @param id
     * @throws IOException
     */
    void doRemoveNumberInfo(long id) throws IOException;
    
    /**
     * 删除所有数量策略
     * @param id
     * @throws IOException
     */
    void doRemoveAllNumberInfos() throws IOException;
    
    /**
     * 根据价格类型删除数量策略
     * @param id
     * @throws IOException
     */
    void doRemoveAllNumberInfos(String priceType) throws IOException;
    
    /**
     * 统计数量策略
     * @return
     * @throws IOException
     */
    int doGetNumberInfoCount() throws IOException;
    
    /**
     * 根据价格类型统计数量策略
     * @param priceType
     * @return
     * @throws IOException
     */
    int doGetNumberInfoCount(String priceType) throws IOException;
    
    /**
     * 获取自动编号
     * @return
     * @throws IOException
     */
    long doGetLastPriceInfoSequenceId() throws IOException;
    
    /**
     * 根据代理类型统计代理价
     * @param priceType 价格类型
     * @param agentType	代理类型
     * @param buyPrice	采购价
     * @return
     * @throws IOException
     */
    Double doCalculateAgentInfo(String priceType, String agentType, Double buyPrice) throws IOException;
	
    /**
     * 根据总数统计代理价
     * @param priceType 价格类型
     * @param totalNumber 代理类型
     * @param buyPrice 采购价
     * @return
     * @throws IOException
     */
    Double doCalculateNumberInfo(String priceType, Double totalNumber, Double buyPrice) throws IOException;
}
