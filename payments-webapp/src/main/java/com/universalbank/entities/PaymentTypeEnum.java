package com.universalbank.entities;
import com.universalbank.payments.api.impl.bill.BillPaymentExecutor;
import com.universalbank.payments.api.impl.loan.LoanPaymentExecutor;
import com.universalbank.payments.api.impl.pse.PSEPaymentExecutor;
import com.universalbank.payments.api.impl.swift.SWIFTPaymentExecutor;
public enum PaymentTypeEnum implements PaymentType{

	ACCOUNT(BillPaymentExecutor.class),LOAN(LoanPaymentExecutor.class),NATIONAL(PSEPaymentExecutor.class),INTERNATIONAL(SWIFTPaymentExecutor.class);
	
	private Class<?> clazz;
	
	private PaymentTypeEnum(Class<?> clazz) {
		this.clazz=clazz;
	}

	public Class<?> getImplementationClass() {
		// TODO Auto-generated method stub
		return clazz;
	}
	
	
}
