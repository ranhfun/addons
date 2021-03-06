package com.ranhfun.price.data;

import com.ranhfun.price.util.IntrospectionSupport;

public class NumberInfo {

	private Long id;
	private String priceType;
	private Double totalNumber;
	private Double totalPoint;
	
	public NumberInfo() {
	}

	public NumberInfo(Long id, String priceType, Double totalNumber, Double totalPoint) {
		this.id = id;
		this.priceType = priceType;
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
        int h2 = totalNumber != null ? totalNumber.hashCode() : -1;
        return h1 ^ h2;
    }

    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof NumberInfo) {
        	NumberInfo other = (NumberInfo)obj;
            result = (priceType == null && other.priceType == null || priceType != null
                                                                    && other.priceType != null
                                                                    && priceType.equals(other.priceType))
                     && (totalNumber == null && other.totalNumber == null || totalNumber != null
                                                                                       && other.totalNumber != null
                                                                                       && totalNumber
                                                                                           .equals(other.totalNumber));
        }
        return result;
    }
	
}
