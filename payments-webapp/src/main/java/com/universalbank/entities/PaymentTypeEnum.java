package com.universalbank.entities;

public enum PaymentTypeEnum implements PaymentType{

	ACCOUNT(null),LOAN(null),NATIONAL(null),INTERNATIONAL(null);
	
	private Class<?> clazz;
	
	private PaymentTypeEnum(Class<?> clazz) {
		this.clazz=clazz;
	}

	public Class<?> getImplementationClass() {
		// TODO Auto-generated method stub
		return clazz;
	}
	
	
}
