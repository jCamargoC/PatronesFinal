package com.universalbank.accounts.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.universalbank.account.objects.Account;
import com.universalbank.accounts.ejb.AccountsEJB;


@Path("api/accounts")
@Stateless
public class AccountsService {

	@EJB
	private AccountsEJB accountsEJB;

	@GET
	@Path("{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("idType") String idType, @PathParam("idNumber") String idNumber) {
		Account account = accountsEJB.getAccount(idType, idNumber);
		if (account != null) {
			return account;
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Account> getAccounts() {
		return accountsEJB.getAccounts();
	}
	
	@GET
	@Path("availability/{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean checkAvailability( @PathParam("idType") String idType, @PathParam("idNumber") String idNumber,@QueryParam("amount") Double amount) {
		Account account = accountsEJB.getAccount(idType, idNumber);		
		if(account!=null) {
			if(account.getAmount()>amount) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	@POST
	@Path("{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String doDebit( @PathParam("idType") String idType, @PathParam("idNumber") String idNumber,@QueryParam("amount") Double amount) {
		Account account = accountsEJB.doDebit(idType, idNumber,amount);		
		if(account!=null) {
			return "Se realizó exitosamente el débito de su cuenta";
		}
		return "Débito fallido";
	}
}
