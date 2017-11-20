package com.universalbank.payments.api;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;

import com.universalbank.entities.BillSubscription;

@Singleton
public class PaymentRouter {

	public String executePayment(BillSubscription billSubscription) {
		try {
			if (billSubscription != null) {
				IPayment iPayment = (IPayment) billSubscription.getPaymentType().getImplementationClass().newInstance();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("amount", billSubscription.getAmount());
				data.put("idNumber", billSubscription.getClient().getDocumentNumber());
				data.put("idType", billSubscription.getClient().getDocumentType());
				String result = iPayment.executePayment(data);
				return result;
			}
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		}
		return "Error al aplicar el pago";
	}
}
