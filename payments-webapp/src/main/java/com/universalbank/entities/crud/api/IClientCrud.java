package com.universalbank.entities.crud.api;

import java.security.Principal;

import com.universalbank.entities.Client;

public interface IClientCrud extends ICrudContract<Client>{
	public Client getClientByUserName(String username);
	
	public Principal getPrincipal();
}
