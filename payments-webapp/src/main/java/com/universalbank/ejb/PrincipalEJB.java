package com.universalbank.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.universalbank.entities.Client;
import com.universalbank.entities.crud.api.IClientCrud;

@Stateless
public class PrincipalEJB {

	@Resource
	private SessionContext ctx;
	@EJB
	IClientCrud clientCrud;
	
	public Client getLoggedClient() {
		Principal principal=ctx.getCallerPrincipal();
		return clientCrud.getClientByUserName(principal.getName());		
	}
	
}
