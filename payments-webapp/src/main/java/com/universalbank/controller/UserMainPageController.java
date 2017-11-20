package com.universalbank.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.universalbank.ejb.api.SchedulingEJBContract;
import com.universalbank.entities.BillSubscription;
import com.universalbank.entities.ExternalAppEnum;
import com.universalbank.entities.crud.api.IBillSubscriptionCrud;
import com.universalbank.entities.crud.api.IClientCrud;
import com.universalbank.external.objects.Account;
import com.universalbank.external.objects.Client;
import com.universalbank.external.objects.Loan;
import com.universalbank.integrator.api.ExternalAppsAggregator;
import com.universalbank.payments.api.PaymentRouter;

@Named("mainPageController")
@SessionScoped
public class UserMainPageController {

	private Account accountData;
	private Loan loanData;
	private Client clientData;
	@Inject
	IClientCrud clientCrud;
	@Inject
	private ExternalAppsAggregator externalAppsAggregator;

	private List<BillSubscription> billSubscriptions;
	@Inject
	private IBillSubscriptionCrud billSubscriptionCrud;
	@Inject
	private PaymentRouter paymentRouter;
	private String payResult;
	@Inject
	private SchedulingEJBContract schedulingEJB; 

	@PostConstruct
	public void init() {
		Principal principal = clientCrud.getPrincipal();
		com.universalbank.entities.Client client = clientCrud.getClientByUserName(principal.getName());
		Map<String, Object> result = externalAppsAggregator.aggregateInvocations(client, ExternalAppEnum.CUENTA,
				ExternalAppEnum.CRM, ExternalAppEnum.PRESTAMO);
		accountData = (Account) result.get(ExternalAppEnum.CUENTA.getName());
		loanData = (Loan) result.get(ExternalAppEnum.PRESTAMO.getName());
		clientData = (Client) result.get(ExternalAppEnum.CRM.getName());
		billSubscriptions = billSubscriptionCrud.getAllByClient(client.getId());
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

	public List<BillSubscription> getBillSubscriptions() {
		return billSubscriptions;
	}

	public void setBillSubscriptions(List<BillSubscription> billSubscriptions) {
		this.billSubscriptions = billSubscriptions;
	}

	public void pay(BillSubscription billSubscription) {

		payResult = paymentRouter.executePayment(billSubscription);
		String summary = payResult;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath());
    }
	
	public void delete(BillSubscription billSubscription) {
		schedulingEJB.stopTimer(billSubscription.getId());
		Boolean result=billSubscriptionCrud.delete(billSubscription.getId());
		if(result) {
			
			String summary = "Borrado exitoso";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			try {
				ec.redirect(ec.getRequestContextPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
