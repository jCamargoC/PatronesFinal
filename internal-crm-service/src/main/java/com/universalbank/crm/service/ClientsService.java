package com.universalbank.crm.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.universalbank.crm.ejb.ClientsEJB;
import com.universalbank.crm.objects.Client;

@Path("api/clients")
@Stateless
public class ClientsService {

	@EJB
	private ClientsEJB clientsEJB;

	@GET
	@Path("{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Client getLoan(@PathParam("idType") String idType, @PathParam("idNumber") String idNumber) {
		Client loan = clientsEJB.getClient(idType, idNumber);
		if (loan != null) {
			return loan;
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Client> getLoans() {
		return clientsEJB.getClients();
	}
	
	@GET
	@Path("hasloan/{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean clientExists(@PathParam("idType") String idType, @PathParam("idNumber") String idNumber) {
		Client loan = clientsEJB.getClient(idType, idNumber);
		if (loan != null) {
			return true;
		}
		return false;
	}	
	
}
