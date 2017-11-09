package com.universalbank.entities;

import com.universalbank.integrator.api.IExternalAppInvoker;

public enum ExternalAppEnum {
	
	ACCOUNT("AccountSystem",null,null),LOANS("LoansSystem",null,null),CRM("CRMSystem",null,null);
	
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
