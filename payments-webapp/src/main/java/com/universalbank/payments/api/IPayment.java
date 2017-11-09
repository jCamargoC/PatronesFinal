package com.universalbank.payments.api;

import java.util.Map;
	
public interface IPayment {
	public String executePayment(Map<String,Object> data);
	
	
}
