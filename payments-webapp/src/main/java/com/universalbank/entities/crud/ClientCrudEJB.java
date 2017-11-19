package com.universalbank.entities.crud;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.universalbank.entities.Client;
import com.universalbank.entities.crud.api.IClientCrud;

@Stateless
@Local(IClientCrud.class)
public class ClientCrudEJB implements IClientCrud{

	@PersistenceContext
	private EntityManager entityManager;
	@Resource
	SessionContext ctx;
	
	@Override
	public Client create(Client object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client update(Client object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Client> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client getClientByUserName(String username) {
		Query query=entityManager.createNamedQuery("Client.getByUsername").setParameter("username", username);
		try {
			Client client=(Client)query.getSingleResult();
			if(client!=null) {
				return client;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Principal getPrincipal() {
		// TODO Auto-generated method stub
		return ctx.getCallerPrincipal();
	}

}
