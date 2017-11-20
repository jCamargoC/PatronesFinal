package com.universalbank.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;

import com.universalbank.ejb.api.SchedulingEJBContract;
import com.universalbank.entities.Client;
import com.universalbank.entities.ExternalAppEnum;
import com.universalbank.external.objects.Loan;
import com.universalbank.integrator.api.ExternalAppsAggregator;
import com.universalbank.integrator.api.impl.crm.CRMSystemInvoker;
import com.universalbank.integrator.api.impl.loan.LoanSystemInvoker;
import com.universalbank.payments.api.impl.bill.BillPaymentExecutor;
import com.universalbank.payments.api.impl.loan.LoanPaymentExecutor;
import com.universalbank.payments.api.impl.pse.PSEPaymentExecutor;
import com.universalbank.payments.api.impl.swift.SWIFTPaymentExecutor;

@Named("helloWorld")
@SessionScoped
public class PageController {
	@Inject
	private SchedulingEJBContract executemotherfucker;
	@Inject
	private ExternalAppsAggregator externalAppsAggregator;
	private String firstName = "John";
    private String lastName = "Doe";
    Integer i=1;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String showGreeting() throws JMSException {
    	Map<String,Object> data=new HashMap<String,Object>();
		data.put("idType","CC");
		data.put("idNumber","123456789");
		com.universalbank.external.objects.Client a=new CRMSystemInvoker().invokeExternalApp(data, com.universalbank.external.objects.Client.class);
		System.out.println(a.getClientType());
		
		Client c=new Client();
		c.setDocumentNumber("123456789");
		c.setDocumentType("CC");
		System.out.println(externalAppsAggregator.aggregateInvocations(c, ExternalAppEnum.CUENTA,ExternalAppEnum.CRM,ExternalAppEnum.PRESTAMO));
		Loan a2=new LoanSystemInvoker().invokeExternalApp(data, Loan.class);
		System.out.println(a2.getLoanType());
		data.put("amount", new Double(30000.00));
		System.out.println(new BillPaymentExecutor().executePayment(data));
		System.out.println(new LoanPaymentExecutor().executePayment(data));
		System.out.println(new PSEPaymentExecutor().executePayment(data));
		System.out.println(new SWIFTPaymentExecutor().executePayment(data));
        return "Hello" + " " + firstName + " " + lastName + "!";
    }
    
    public void createTimer() {
    	
    	
    	i++;
    }
    public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath());
    }
}
