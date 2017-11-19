package com.universalbank.loan.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.universalbank.loan.ejb.LoansEJB;
import com.universalbank.loan.objects.Loan;

@Path("api/loans")
@Stateless
public class LoansService {

	@EJB
	private LoansEJB loansEJB;

	@GET
	@Path("{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Loan getLoan(@PathParam("idType") String idType, @PathParam("idNumber") String idNumber) {
		Loan loan = loansEJB.getLoan(idType, idNumber);
		if (loan != null) {
			return loan;
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Loan> getLoans() {
		return loansEJB.getLoans();
	}
	
	@GET
	@Path("hasloan/{idType}/{idNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean hasLoans(@PathParam("idType") String idType, @PathParam("idNumber") String idNumber) {
		Loan loan = loansEJB.getLoan(idType, idNumber);
		if (loan != null) {
			return true;
		}
		return false;
	}
	
	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String pay(Loan loan) {
		Loan l=loansEJB.payQuote(loan);
		if(l!=null) {
			return "Succesfully payed";
		}
		return "Failed payment";
	}
}
