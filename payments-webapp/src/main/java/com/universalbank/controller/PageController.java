package com.universalbank.controller;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;

import com.universalbank.ejb.api.SchedulingEJBContract;

@Named("helloWorld")
@SessionScoped
public class PageController {
	@Inject
	private SchedulingEJBContract executemotherfucker;
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
