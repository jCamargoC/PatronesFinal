package com.universalbank.loan.ejb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.universalbank.loan.objects.Loan;
import com.universalbank.loan.objects.LoanList;

@Stateless
public class LoansEJB {
	
	private LoanList loansList;
	
	public Loan getLoan(String idType,String idNumber) {
		loadLoans();
		for (Loan account : loansList.getLoans()) {
			if(account.getIdentificationType().equals(idType)&&account.getIdentificationNumber().equals(idNumber)) {
				return account;
			}
		}
		return null;
	}
	
	public List<Loan> getLoans() {
		loadLoans();
		return loansList.getLoans();
	}
	
	public Loan payQuote(Loan loan) {
		loadLoans();
		Loan l=null;
		for (Loan account : loansList.getLoans()) {
			if(account.getIdentificationType().equals(loan.getIdentificationType())&&account.getIdentificationNumber().equals(loan.getIdentificationNumber())) {
				account.setPayed(account.getPayed()+loan.getQuoteAmount());
				l=account;
			}
		}
		saveLoans();
		return l;
	}
	
	private void saveLoans() {
		String path=System.getProperty("loansFile");
		if(path==null) {
			path="/apps/loanSystem/loans.json";
		}
		File file=new File(path);
		ObjectMapper mapper=new ObjectMapper();
		try {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(file, loansList);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadLoans() {
		String path=System.getProperty("loansFile");
		if(path==null) {
			path="/apps/loanSystem/loans.json";
		}
		File file=new File(path);
		ObjectMapper mapper=new ObjectMapper();
		try {
			loansList=mapper.readValue(file, LoanList.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
