package com.universalbank.controller;

import java.security.Principal;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.universalbank.entities.ExternalAppEnum;
import com.universalbank.entities.crud.api.IClientCrud;
import com.universalbank.external.objects.Account;
import com.universalbank.external.objects.Client;
import com.universalbank.external.objects.Loan;
import com.universalbank.integrator.api.ExternalAppsAggregator;

@Named("mainPageController")
@SessionScoped
@Stateless
public class UserMainPageController {

	private Account accountData;
	private Loan loanData;
	private Client clientData;
	@Resource
	SessionContext ctx;
	@Inject
	IClientCrud clientCrud;
	@Inject
	private ExternalAppsAggregator externalAppsAggregator;

	@PostConstruct
	public void init() {
		Principal principal = clientCrud.getPrincipal();
		com.universalbank.entities.Client client = clientCrud.getClientByUserName(principal.getName());
		Map<String,Object> result=externalAppsAggregator.aggregateInvocations(client, ExternalAppEnum.ACCOUNT,ExternalAppEnum.CRM,ExternalAppEnum.LOANS);
		accountData=(Account)result.get(ExternalAppEnum.ACCOUNT.getName());
		loanData=(Loan)result.get(ExternalAppEnum.LOANS.getName());
		clientData=(Client)result.get(ExternalAppEnum.CRM.getName());
	}

	public Account getAccountData() {
		return accountData;
	}

	public void setAccountData(Account accountData) {
		this.accountData = accountData;
	}

	public Loan getLoanData() {
		return loanData;
	}

	public void setLoanData(Loan loanData) {
		this.loanData = loanData;
	}

	public Client getClientData() {
		return clientData;
	}

	public void setClientData(Client clientData) {
		this.clientData = clientData;
	}
	
	
}
