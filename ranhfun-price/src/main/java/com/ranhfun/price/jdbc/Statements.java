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

/**
 * 
 * 
 * @org.apache.xbean.XBean element="statements"
 * 
 */
public class Statements {

    protected String agentTableName = "PRICE_AGENT";
    protected String numberTableName = "PRICE_NUMBER";
    protected String priceTypeDataType = "VARCHAR(250)";
    protected String agentTypeDataType = "VARCHAR(250)";
    protected String sequenceDataType = "BIGINT";
    protected String longDataType = "BIGINT";
    protected String totalNumberDataType = "DOUBLE";
    protected String pointDataType = "DOUBLE";
    protected boolean useExternalMessageReferences;

    private String tablePrefix = "";
    private String insertAgentInfoStatement;
    private String updateAgentInfoStatement;
    private String findAgentInfoStatement;
    private String findAgentInfoByPriceTypeStatement;
    private String findAllAgentInfosStatement;
    private String existsAgentInfoByPriceTypeStatement;
    private String existsAgentInfoByAgentTypeStatement;
    private String removeAgentInfoStatement;
    private String removeAllAgentInfosStatement;
    private String agentInfoCountStatement;
    private String insertNumberInfoStatement;
    private String updateNumberInfoStatement;
    private String findNumberInfoStatement;
    private String findNumberInfoByPriceTypeStatement;
    private String findAllNumberInfosStatement;
    private String removeNumberInfoStatement;
    private String removeAllNumberInfosStatement;
    private String removeAllNumberInfosWithPriceTypeStatement;
    private String numberInfoCountStatement;   
    private String numberInfoCountWithPriceTypeStatement;  
    private String lastAgentInfoSequenceIdStatement;
    private String lastNumberInfoSequenceIdStatement;
    private String findAgentPointByPriceTypeAndAgentTypeStatement;
    private String findNumberPointByPriceTypeAndTotalNumberStatement;
    
    private String[] createSchemaStatements;
    private String[] dropSchemaStatements;
    
	public String[] getCreateSchemaStatements() {
        if (createSchemaStatements == null) {
            createSchemaStatements = new String[] {
                "CREATE TABLE " + getFullAgentInfoTableName() + "(" + "ID " + sequenceDataType + " NOT NULL"
                    + ", PRICE_TYPE " + priceTypeDataType + " NOT NULL" + ", AGENT_TYPE " 
                    + agentTypeDataType + ", AGENT_POINT " + pointDataType + " NOT NULL" 
                    + ", PRIMARY KEY ( ID ) )",
                "CREATE INDEX " + getFullAgentInfoTableName() + "_AIDX ON " + getFullAgentInfoTableName() + " (AGENT_TYPE)",
                "CREATE TABLE " + getFullNumberInfoTableName() + "(" + "ID " + sequenceDataType + " NOT NULL"
                + ", PRICE_TYPE " + priceTypeDataType + " NOT NULL" + ", TOTAL_NUMBER " 
                + totalNumberDataType + ", TOTAL_POINT " + pointDataType + " NOT NULL" 
                + ", PRIMARY KEY ( ID ) )",
                "CREATE INDEX " + getFullNumberInfoTableName() + "_NIDX ON " + getFullNumberInfoTableName() + " (TOTAL_NUMBER)"
            };
        }
        return createSchemaStatements;
	}
	public String[] getDropSchemaStatements() {
        if (dropSchemaStatements == null) {
            dropSchemaStatements = new String[] {"DROP TABLE " + getFullAgentInfoTableName() + "",
                                                 "DROP TABLE " + getFullNumberInfoTableName() + ""};
        }
		return dropSchemaStatements;
	}
	public String getInsertAgentInfoStatement() {
        if (insertAgentInfoStatement == null) {
        	insertAgentInfoStatement = "INSERT INTO "
                                  + getFullAgentInfoTableName()
                                  + "(ID, PRICE_TYPE, AGENT_TYPE, AGENT_POINT) VALUES (?, ?, ?, ?)";
        }
		return insertAgentInfoStatement;
	}
	public String getUpdateAgentInfoStatement() {
        if (updateAgentInfoStatement == null) {
        	updateAgentInfoStatement = "UPDATE " + getFullAgentInfoTableName() + " SET PRICE_TYPE=?, AGENT_TYPE=?, AGENT_POINT=? WHERE ID=?";
        }
		return updateAgentInfoStatement;
	}
	public String getFindAgentInfoStatement() {
        if (findAgentInfoStatement == null) {
        	findAgentInfoStatement = "SELECT ID, PRICE_TYPE, AGENT_TYPE, AGENT_POINT FROM " + getFullAgentInfoTableName()
                                       + " WHERE ID=?";
        }
		return findAgentInfoStatement;
	}
	public String getFindAgentInfoByPriceTypeStatement() {
        if (findAgentInfoByPriceTypeStatement == null) {
        	findAgentInfoByPriceTypeStatement = "SELECT ID, PRICE_TYPE, AGENT_TYPE, AGENT_POINT FROM " + getFullAgentInfoTableName()
                                       + " WHERE PRICE_TYPE=?";
        }		
		return findAgentInfoByPriceTypeStatement;
	}
	public String getFindAllAgentInfosStatement() {
        if (findAllAgentInfosStatement == null) {
        	findAllAgentInfosStatement = "SELECT ID, PRICE_TYPE, AGENT_TYPE, AGENT_POINT FROM " + getFullAgentInfoTableName();
        }				
		return findAllAgentInfosStatement;
	}
	
	public String getExistsAgentInfoByPriceTypeStatement() {
        if (existsAgentInfoByPriceTypeStatement == null) {
        	existsAgentInfoByPriceTypeStatement = "SELECT COUNT(*) FROM " + getFullAgentInfoTableName() 
        						+ " WHERE PRICE_TYPE=? AND AGENT_TYPE=?";
        }	
		return existsAgentInfoByPriceTypeStatement;
	}
	public String getExistsAgentInfoByAgentTypeStatement() {
        if (existsAgentInfoByAgentTypeStatement == null) {
        	existsAgentInfoByAgentTypeStatement = "SELECT COUNT(*) FROM " + getFullAgentInfoTableName() 
        						+ " WHERE AGENT_TYPE=?";
        }	
		return existsAgentInfoByAgentTypeStatement;
	}
	public String getRemoveAgentInfoStatement() {
        if (removeAgentInfoStatement == null) {
        	removeAgentInfoStatement = "DELETE FROM " + getFullAgentInfoTableName() + " WHERE ID=?";
        }
		return removeAgentInfoStatement;
	}
	public String getRemoveAllAgentInfosStatement() {
        if (removeAllAgentInfosStatement == null) {
        	removeAllAgentInfosStatement = "DELETE FROM " + getFullAgentInfoTableName();
        }
		return removeAllAgentInfosStatement;
	}
	public String getAgentInfoCountStatement() {
        if (agentInfoCountStatement == null) {
        	agentInfoCountStatement = "SELECT COUNT(*) FROM " + getFullAgentInfoTableName();
        }
		return agentInfoCountStatement;
	}
	public String getInsertNumberInfoStatement() {
        if (insertNumberInfoStatement == null) {
        	insertNumberInfoStatement = "INSERT INTO "
                                  + getFullNumberInfoTableName()
                                  + "(ID, PRICE_TYPE, TOTAL_NUMBER, TOTAL_POINT) VALUES (?, ?, ?, ?)";
        }
		return insertNumberInfoStatement;
	}
	public String getUpdateNumberInfoStatement() {
        if (updateNumberInfoStatement == null) {
        	updateNumberInfoStatement = "UPDATE " + getFullNumberInfoTableName() + " SET PRICE_TYPE=?, TOTAL_NUMBER=?, TOTAL_POINT=? WHERE ID=?";
        }
		return updateNumberInfoStatement;
	}
	public String getFindNumberInfoStatement() {
        if (findNumberInfoStatement == null) {
        	findNumberInfoStatement = "SELECT ID, PRICE_TYPE, TOTAL_NUMBER, TOTAL_POINT FROM " + getFullNumberInfoTableName()
                                       + " WHERE ID=?";
        }
		return findNumberInfoStatement;
	}
	public String getFindNumberInfoByPriceTypeStatement() {
        if (findNumberInfoByPriceTypeStatement == null) {
        	findNumberInfoByPriceTypeStatement = "SELECT ID, PRICE_TYPE, TOTAL_NUMBER, TOTAL_POINT FROM " + getFullNumberInfoTableName()
                                       + " WHERE PRICE_TYPE=?";
        }
		return findNumberInfoByPriceTypeStatement;
	}
	public String getFindAllNumberInfosStatement() {
        if (findAllNumberInfosStatement == null) {
        	findAllNumberInfosStatement = "SELECT ID, PRICE_TYPE, TOTAL_NUMBER, TOTAL_POINT FROM " + getFullNumberInfoTableName();
        }			
		return findAllNumberInfosStatement;
	}
	public String getRemoveNumberInfoStatement() {
        if (removeNumberInfoStatement == null) {
        	removeNumberInfoStatement = "DELETE FROM " + getFullNumberInfoTableName() + " WHERE ID=?";
        }
		return removeNumberInfoStatement;
	}
	public String getRemoveAllNumberInfosStatement() {
        if (removeAllNumberInfosStatement == null) {
        	removeAllNumberInfosStatement = "DELETE FROM " + getFullNumberInfoTableName();
        }
		return removeAllNumberInfosStatement;
	}
	public String getRemoveAllNumberInfosWithPriceTypeStatement() {
        if (removeAllNumberInfosStatement == null) {
        	removeAllNumberInfosStatement = "DELETE FROM " + getFullNumberInfoTableName();
        }
		return removeAllNumberInfosWithPriceTypeStatement;
	}
	public String getNumberInfoCountStatement() {
        if (numberInfoCountStatement == null) {
        	numberInfoCountStatement = "SELECT COUNT(*) FROM " + getFullNumberInfoTableName();
        }
		return numberInfoCountStatement;
	}
	public String getNumberInfoCountWithPriceTypeStatement() {
        if (numberInfoCountWithPriceTypeStatement == null) {
        	numberInfoCountWithPriceTypeStatement = "SELECT COUNT(*) FROM " + getFullNumberInfoTableName()
        											+ " WHERE PRICE_TYPE=?";
        }
		return numberInfoCountWithPriceTypeStatement;
	}
	public String getLastAgentInfoSequenceIdStatement() {
        if (lastAgentInfoSequenceIdStatement == null) {
        	lastAgentInfoSequenceIdStatement = "SELECT MAX(ID) FROM " + getFullAgentInfoTableName();                                                    
        }
		return lastAgentInfoSequenceIdStatement;
	}
	public String getLastNumberInfoSequenceIdStatement() {
        if (lastNumberInfoSequenceIdStatement == null) {
        	lastNumberInfoSequenceIdStatement = "SELECT MAX(ID) FROM " + getFullNumberInfoTableName();                                                    
        }
		return lastNumberInfoSequenceIdStatement;
	}
	
    public String getFindAgentPointByPriceTypeAndAgentTypeStatement() {
        if (findAgentPointByPriceTypeAndAgentTypeStatement == null) {
        	findAgentPointByPriceTypeAndAgentTypeStatement = "SELECT AGENT_POINT FROM " + getFullAgentInfoTableName()
            		+ " WHERE PRICE_TYPE=? AND AGENT_TYPE=?";                                                   
        }
		return findAgentPointByPriceTypeAndAgentTypeStatement;
	}
    
	public String getFindNumberPointByPriceTypeAndTotalNumberStatement() {
        if (findNumberPointByPriceTypeAndTotalNumberStatement == null) {
        	findNumberPointByPriceTypeAndTotalNumberStatement = "SELECT TOTAL_POINT FROM " + getFullNumberInfoTableName()
    				+ " WHERE PRICE_TYPE=? AND TOTAL_NUMBER<=? ORDER BY TOTAL_NUMBER DESC";                                                      
        }
		return findNumberPointByPriceTypeAndTotalNumberStatement;
	}
	
	public String getFullAgentInfoTableName() {
        return getTablePrefix() + getAgentTableName();
    }

    public String getFullNumberInfoTableName() {
        return getTablePrefix() + getNumberTableName();
    }
	
	public String getAgentTableName() {
		return agentTableName;
	}
	public void setAgentTableName(String agentTableName) {
		this.agentTableName = agentTableName;
	}
	public String getNumberTableName() {
		return numberTableName;
	}
	public void setNumberTableName(String numberTableName) {
		this.numberTableName = numberTableName;
	}
	public String getPriceTypeDataType() {
		return priceTypeDataType;
	}
	public void setPriceTypeDataType(String priceTypeDataType) {
		this.priceTypeDataType = priceTypeDataType;
	}
	public String getSequenceDataType() {
		return sequenceDataType;
	}
	public void setSequenceDataType(String sequenceDataType) {
		this.sequenceDataType = sequenceDataType;
	}
	public String getLongDataType() {
		return longDataType;
	}
	public void setLongDataType(String longDataType) {
		this.longDataType = longDataType;
	}
	public String getTotalNumberDataType() {
		return totalNumberDataType;
	}
	public void setTotalNumberDataType(String totalNumberDataType) {
		this.totalNumberDataType = totalNumberDataType;
	}
	public String getPointDataType() {
		return pointDataType;
	}
	public void setPointDataType(String pointDataType) {
		this.pointDataType = pointDataType;
	}
	public boolean isUseExternalMessageReferences() {
		return useExternalMessageReferences;
	}
	public void setUseExternalMessageReferences(boolean useExternalMessageReferences) {
		this.useExternalMessageReferences = useExternalMessageReferences;
	}
	public String getTablePrefix() {
		return tablePrefix;
	}
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	public void setInsertAgentInfoStatement(String insertAgentInfoStatement) {
		this.insertAgentInfoStatement = insertAgentInfoStatement;
	}
	public void setUpdateAgentInfoStatement(String updateAgentInfoStatement) {
		this.updateAgentInfoStatement = updateAgentInfoStatement;
	}
	public void setFindAgentInfoStatement(String findAgentInfoStatement) {
		this.findAgentInfoStatement = findAgentInfoStatement;
	}
	public void setFindAgentInfoByPriceTypeStatement(
			String findAgentInfoByPriceTypeStatement) {
		this.findAgentInfoByPriceTypeStatement = findAgentInfoByPriceTypeStatement;
	}
	public void setFindAllAgentInfosStatement(String findAllAgentInfosStatement) {
		this.findAllAgentInfosStatement = findAllAgentInfosStatement;
	}
	public void setExistsAgentInfoByPriceTypeStatement(
			String existsAgentInfoByPriceTypeStatement) {
		this.existsAgentInfoByPriceTypeStatement = existsAgentInfoByPriceTypeStatement;
	}
	public void setExistsAgentInfoByAgentTypeStatement(
			String existsAgentInfoByAgentTypeStatement) {
		this.existsAgentInfoByAgentTypeStatement = existsAgentInfoByAgentTypeStatement;
	}
	public void setRemoveAgentInfoStatement(String removeAgentInfoStatement) {
		this.removeAgentInfoStatement = removeAgentInfoStatement;
	}
	public void setRemoveAllAgentInfosStatement(String removeAllAgentInfosStatement) {
		this.removeAllAgentInfosStatement = removeAllAgentInfosStatement;
	}
	public void setAgentInfoCountStatement(String agentInfoCountStatement) {
		this.agentInfoCountStatement = agentInfoCountStatement;
	}
	public void setInsertNumberInfoStatement(String insertNumberInfoStatement) {
		this.insertNumberInfoStatement = insertNumberInfoStatement;
	}
	public void setUpdateNumberInfoStatement(String updateNumberInfoStatement) {
		this.updateNumberInfoStatement = updateNumberInfoStatement;
	}
	public void setFindNumberInfoStatement(String findNumberInfoStatement) {
		this.findNumberInfoStatement = findNumberInfoStatement;
	}
	public void setFindNumberInfoByPriceTypeStatement(
			String findNumberInfoByPriceTypeStatement) {
		this.findNumberInfoByPriceTypeStatement = findNumberInfoByPriceTypeStatement;
	}
	public void setFindAllNumberInfosStatement(String findAllNumberInfosStatement) {
		this.findAllNumberInfosStatement = findAllNumberInfosStatement;
	}
	public void setRemoveNumberInfoStatement(String removeNumberInfoStatement) {
		this.removeNumberInfoStatement = removeNumberInfoStatement;
	}
	public void setRemoveAllNumberInfosStatement(
			String removeAllNumberInfosStatement) {
		this.removeAllNumberInfosStatement = removeAllNumberInfosStatement;
	}
	public void setRemoveAllNumberInfosWithPriceTypeStatement(
			String removeAllNumberInfosWithPriceTypeStatement) {
		this.removeAllNumberInfosWithPriceTypeStatement = removeAllNumberInfosWithPriceTypeStatement;
	}
	public void setNumberInfoCountStatement(String numberInfoCountStatement) {
		this.numberInfoCountStatement = numberInfoCountStatement;
	}
	public void setNumberInfoCountWithPriceTypeStatement(
			String numberInfoCountWithPriceTypeStatement) {
		this.numberInfoCountWithPriceTypeStatement = numberInfoCountWithPriceTypeStatement;
	}
	public void setLastAgentInfoSequenceIdStatement(
			String lastAgentInfoSequenceIdStatement) {
		this.lastAgentInfoSequenceIdStatement = lastAgentInfoSequenceIdStatement;
	}
	public void setLastNumberInfoSequenceIdStatement(
			String lastNumberInfoSequenceIdStatement) {
		this.lastNumberInfoSequenceIdStatement = lastNumberInfoSequenceIdStatement;
	}
	public void setCreateSchemaStatements(String[] createSchemaStatements) {
		this.createSchemaStatements = createSchemaStatements;
	}
	public void setDropSchemaStatements(String[] dropSchemaStatements) {
		this.dropSchemaStatements = dropSchemaStatements;
	}
	public void setFindAgentPointByPriceTypeAndAgentTypeStatement(
			String findAgentPointByPriceTypeAndAgentTypeStatement) {
		this.findAgentPointByPriceTypeAndAgentTypeStatement = findAgentPointByPriceTypeAndAgentTypeStatement;
	}
	public void setFindNumberPointByPriceTypeAndTotalNumberStatement(
			String findNumberPointByPriceTypeAndTotalNumberStatement) {
		this.findNumberPointByPriceTypeAndTotalNumberStatement = findNumberPointByPriceTypeAndTotalNumberStatement;
	}
}