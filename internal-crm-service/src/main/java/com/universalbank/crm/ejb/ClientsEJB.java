package com.universalbank.crm.ejb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.universalbank.crm.objects.Client;
import com.universalbank.crm.objects.ClientList;

@Stateless
public class ClientsEJB {
	
	private ClientList clientsList;
	
	public Client getClient(String idType,String idNumber) {
		loadLoans();
		for (Client account : clientsList.getClients()) {
			if(account.getIdentificationType().equals(idType)&&account.getIdentificationNumber().equals(idNumber)) {
				return account;
			}
		}
		return null;
	}
	
	public List<Client> getClients() {
		loadLoans();
		return clientsList.getClients();
	}
	
	private void loadLoans() {
		String path=System.getProperty("clientsFile");
		if(path==null) {
			path="/apps/CRMSystem/clients.json";
		}
		File file=new File(path);
		ObjectMapper mapper=new ObjectMapper();
		try {
			clientsList=mapper.readValue(file, ClientList.class);
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
