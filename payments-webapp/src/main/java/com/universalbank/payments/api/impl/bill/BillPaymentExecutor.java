package com.universalbank.payments.api.impl.bill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.universalbank.external.objects.Account;
import com.universalbank.payments.api.IPayment;
import com.universalbank.utils.RESTInvoker;

public class BillPaymentExecutor implements IPayment {

	Properties properties = new Properties();

	public BillPaymentExecutor() {
		try {
			String propertiesPath = System.getProperty("getAccountInfoPath");
			if (propertiesPath == null) {
				propertiesPath = "/apps/accountSystem/getAccountInfo.properties";
			}

			properties.load(new FileInputStream(new File(propertiesPath)));
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
			String url = properties.getProperty("url") + idType + "/" + idNumber+"?amount="+amount;
			String accept=properties.getProperty("accept");
			Account account=RESTInvoker.doGet(Account.class, url, accept);
			return RESTInvoker.doPost(String.class, url, accept,accept,account);
		}
		return "No hay suficientes fondos en su cuenta para realizar la transacción";
	}

	private Boolean checkAvailability(String idType, String idNumber, Double amount) {

		String url = properties.getProperty("url") + "availability/" + idType + "/" + idNumber+"?amount="+amount;
		return new Boolean(RESTInvoker.doGet(String.class, url, properties.getProperty("accept")));

	}

}
