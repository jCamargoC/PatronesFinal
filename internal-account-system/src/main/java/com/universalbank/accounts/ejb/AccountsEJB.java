package com.universalbank.accounts.ejb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.universalbank.account.objects.Account;
import com.universalbank.account.objects.AccountsList;

@Stateless
public class AccountsEJB {
	
	private AccountsList accountsList;
	
	public Account getAccount(String idType,String idNumber) {
		loadAccounts();
		for (Account account : accountsList.getAccounts()) {
			if(account.getIdentificationType().equals(idType)&&account.getIdentificationNumber().equals(idNumber)) {
				return account;
			}
		}
		return null;
	}
	
	public List<Account> getAccounts() {
		loadAccounts();
		return accountsList.getAccounts();
	}
	
	
	private void loadAccounts() {
		String path=System.getProperty("accountsFile");
		if(path==null) {
			path="/apps/accountSystem/accounts.json";
		}
		File file=new File(path);
		ObjectMapper mapper=new ObjectMapper();
		try {
			accountsList=mapper.readValue(file, AccountsList.class);
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

	public Account doDebit(String idType, String idNumber,Double amount) {
		loadAccounts();
		Account account=getAccount(idType, idNumber);
		if(account!=null) {
			account.setAmount(account.getAmount()-amount);
			saveAccounts();
			return account;
		}
		
		return null;
	}
	
	private void saveAccounts() {
		String path=System.getProperty("accountsFile");
		if(path==null) {
			path="/apps/accountSystem/accounts.json";
		}
		File file=new File(path);
		ObjectMapper mapper=new ObjectMapper();
		try {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(file, accountsList);
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
