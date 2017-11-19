package com.universalbank.entities;

import com.universalbank.external.objects.Account;
import com.universalbank.external.objects.Loan;
import com.universalbank.external.objects.Client;
import com.universalbank.integrator.api.IExternalAppInvoker;
import com.universalbank.integrator.api.impl.account.AccountSystemInvoker;
import com.universalbank.integrator.api.impl.loan.LoanSystemInvoker;
import com.universalbank.integrator.api.impl.crm.CRMSystemInvoker;

public enum ExternalAppEnum {
	
	ACCOUNT("AccountSystem",Account.class,AccountSystemInvoker.class),LOANS("LoansSystem",Loan.class,LoanSystemInvoker.class),CRM("CRMSystem",Client.class,CRMSystemInvoker.class);
	
	private String name;
	
	private Class<?> returnObject;
	
	private Class<? extends IExternalAppInvoker> implementationClass;

	private ExternalAppEnum(String name, Class<?> returnObject, Class<? extends IExternalAppInvoker> implementationClass) {
		this.name = name;
		this.returnObject = returnObject;
		this.implementationClass = implementationClass;
	}

	public String getName() {
		return name;
	}

	public Class<?> getReturnObject() {
		return returnObject;
	}

	public Class<?> getImplementationClass() {
		return implementationClass;
	}
	
	
}
