package com.external.swift.objects;

public class Payment {
	private String idNumber;
	private String idType;
	private String targetEntityCode;
	private Double amount;
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getTargetEntityCode() {
		return targetEntityCode;
	}
	public void setTargetEntityCode(String targetEntityCode) {
		this.targetEntityCode = targetEntityCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
