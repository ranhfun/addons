package com.ranhfun.price.data;

import com.ranhfun.price.util.IntrospectionSupport;

public class AgentInfo {

	private Long id;
	private String priceType;
	private String agentType;
	private Double agentPoint;
	
	public AgentInfo() {
	}

	public AgentInfo(Long id, String priceType, String agentType,
			Double agentPoint) {
		this.id = id;
		this.priceType = priceType;
		this.agentType = agentType;
		this.agentPoint = agentPoint;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public Double getAgentPoint() {
		return agentPoint;
	}

	public void setAgentPoint(Double agentPoint) {
		this.agentPoint = agentPoint;
	}

    public String toString() {
        return IntrospectionSupport.toString(this);
    }
	
    public int hashCode() {
        int h1 = priceType != null ? priceType.hashCode() : -1;
        int h2 = agentType != null ? agentType.hashCode() : -1;
        return h1 ^ h2;
    }

    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof AgentInfo) {
        	AgentInfo other = (AgentInfo)obj;
            result = (priceType == null && other.priceType == null || priceType != null
                                                                    && other.priceType != null
                                                                    && priceType.equals(other.priceType))
                     && (agentType == null && other.agentType == null || agentType != null
                                                                                       && other.agentType != null
                                                                                       && agentType
                                                                                           .equals(other.agentType));
        }
        return result;
    }
}
