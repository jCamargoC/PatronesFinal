package com.universalbank.payments.api.impl.pse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.universalbank.external.objects.Account;
import com.universalbank.external.objects.Payment;
import com.universalbank.payments.api.IPayment;
import com.universalbank.utils.RESTInvoker;

public class PSEPaymentExecutor implements IPayment {

	Properties propertiesAccount = new Properties();

	Properties propertiesPSE = new Properties();

	public PSEPaymentExecutor() {
		try {
			String propertiesPath = System.getProperty("getAccountInfoPath");
			if (propertiesPath == null) {
				propertiesPath = "/apps/accountSystem/getAccountInfo.properties";
			}

			propertiesAccount.load(new FileInputStream(new File(propertiesPath)));

			String propertiesPSEPath = System.getProperty("getPSEPaymentPath");
			if (propertiesPSEPath == null) {
				propertiesPSEPath = "/apps/PSE/getPSEPayment.properties";
			}

			this.propertiesPSE.load(new FileInputStream(new File(propertiesPSEPath)));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String executePayment(Map<String, Object> data) {

		String idNumber = (String) data.get("idNumber");
		String idType = (String) data.get("idType");
		Double amount = (Double) data.get("amount");
		
		Boolean availableInAccount = checkAvailability(idType, idNumber, amount);
		if (availableInAccount) {
			Payment payment = new Payment();			
			payment.setAmount(amount);
			payment.setIdNumber(idNumber);
			payment.setIdType(idType);
			payment.setTargetEntityCode("BANCOLOMBIA");
			Object[] psePayResult = doPSEPayment(payment);
			String result = psePayResult[1].toString() + "\n";
			if ((Boolean) psePayResult[0]) {
				String url = propertiesAccount.getProperty("url") + idType + "/" + idNumber + "?amount=" + amount;
				String accept = propertiesAccount.getProperty("accept");
				Account account = RESTInvoker.doGet(Account.class, url, accept);
				result += RESTInvoker.doPost(String.class, url, accept, accept, account) + "\n";
			}
			return result;
		}
		return "No hay suficientes fondos en su cuenta para realizar la transacción";
	}

	private Boolean checkAvailability(String idType, String idNumber, Double amount) {

		String url = propertiesAccount.getProperty("url") + "availability/" + idType + "/" + idNumber + "?amount="
				+ amount;
		return new Boolean(RESTInvoker.doGet(String.class, url, propertiesAccount.getProperty("accept")));

	}

	private Object[] doPSEPayment(Payment payment) {

		String url = propertiesPSE.getProperty("url");
		String accept = propertiesPSE.getProperty("accept");
		String result = RESTInvoker.doPost(String.class, url, accept, accept, payment);
		return new Object[] { result.equals("Pago exitoso"), "Respuesta PSE: "+result };

	}
	

}
