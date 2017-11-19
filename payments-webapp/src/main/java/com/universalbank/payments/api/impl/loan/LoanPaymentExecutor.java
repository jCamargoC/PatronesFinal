package com.universalbank.payments.api.impl.loan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.universalbank.external.objects.Account;
import com.universalbank.external.objects.Loan;
import com.universalbank.payments.api.IPayment;
import com.universalbank.utils.RESTInvoker;

public class LoanPaymentExecutor implements IPayment {

	Properties propertiesAccount = new Properties();

	Properties propertiesLoan = new Properties();

	public LoanPaymentExecutor() {
		try {
			String propertiesPath = System.getProperty("getAccountInfoPath");
			if (propertiesPath == null) {
				propertiesPath = "/apps/accountSystem/getAccountInfo.properties";
			}

			propertiesAccount.load(new FileInputStream(new File(propertiesPath)));

			String propertiesLoan = System.getProperty("getLoanInfoPath");
			if (propertiesLoan == null) {
				propertiesLoan = "/apps/loanSystem/getLoanInfo.properties";
			}

			this.propertiesLoan.load(new FileInputStream(new File(propertiesLoan)));

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
		Loan loan = getLoanInfo(idType, idNumber);
		if (loan != null) {
			if (loan.getAmount() > 0) {
				Double amount = loan.getQuoteAmount();
				Boolean availableInAccount = checkAvailability(idType, idNumber, amount);
				if (availableInAccount) {
					Object[] loanPayResult = doLoanPayment(loan);
					String result = loanPayResult[1].toString() + "\n";
					if ((Boolean) loanPayResult[0]) {
						String url = propertiesAccount.getProperty("url") + idType + "/" + idNumber + "?amount="
								+ amount;
						String accept = propertiesAccount.getProperty("accept");
						Account account = RESTInvoker.doGet(Account.class, url, accept);
						result += RESTInvoker.doPost(String.class, url, accept, accept, account) + "\n";
					}
					return result;
				}
				return "No hay suficientes fondos en su cuenta para realizar la transacción";
			}
			return "El préstamo fue pagado en su totalidad";
		}
		return "No hay préstamo para pagar";
	}

	private Boolean checkAvailability(String idType, String idNumber, Double amount) {

		String url = propertiesAccount.getProperty("url") + "availability/" + idType + "/" + idNumber + "?amount="
				+ amount;
		return new Boolean(RESTInvoker.doGet(String.class, url, propertiesAccount.getProperty("accept")));

	}

	private Object[] doLoanPayment(Loan loan) {

		String url = propertiesLoan.getProperty("url");
		String accept = propertiesLoan.getProperty("accept");
		String result = RESTInvoker.doPost(String.class, url, accept, accept, loan);
		return new Object[] { result.equals("Préstamo pagado exitosamente"), "Respuesta Sistema de Préstamos: "+result };

	}

	private Loan getLoanInfo(String idType, String idNumber) {
		String url = propertiesLoan.getProperty("url") + idType + "/" + idNumber;
		String accept = propertiesLoan.getProperty("accept");
		Loan loan = RESTInvoker.doGet(Loan.class, url, accept);
		return loan;
	}

}
