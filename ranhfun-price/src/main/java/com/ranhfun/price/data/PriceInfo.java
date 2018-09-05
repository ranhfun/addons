package com.ranhfun.price.data;

import com.ranhfun.price.util.IntrospectionSupport;

public class PriceInfo {

	private Long id;
	private String priceType;
	private String agentType;
	private Double agentPoint;
	private Double totalNumber;
	private Double totalPoint;
	
	public PriceInfo() {
	}

	public PriceInfo(Long id, String priceType, String agentType,
			Double agentPoint, Double totalNumber, Double totalPoint) {
		this.id = id;
		this.priceType = priceType;
		this.agentType = agentType;
		this.agentPoint = agentPoint;
		this.totalNumber = totalNumber;
		this.totalPoint = totalPoint;
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

	public Double getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Double totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Double getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Double totalPoint) {
		this.totalPoint = totalPoint;
	}
	
    public String toString() {
        return IntrospectionSupport.toString(this);
    }
	
    public int hashCode() {
        int h1 = priceType != null ? priceType.hashCode() : -1;
        int h2 = agentType != null ? agentType.hashCode() : -1;
        int h3 = totalNumber != null ? totalNumber.hashCode() : -1;
        return h1 ^ h2 ^ h3;
    }

    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof PriceInfo) {
        	PriceInfo other = (PriceInfo)obj;
            result = (priceType == null && other.priceType == null || priceType != null
                                                                    && other.priceType != null
                                                                    && priceType.equals(other.priceType))
                     && (agentType == null && other.agentType == null || agentType != null
                                                                                       && other.agentType != null
                                                                                       && agentType
                                                                                           .equals(other.agentType))
                     && (totalNumber == null && other.totalNumber == null || totalNumber != null
                                                                                       && other.totalNumber != null
                                                                                       && totalNumber
                                                                                           .equals(other.totalNumber));
        }
        return result;
    }
    
}