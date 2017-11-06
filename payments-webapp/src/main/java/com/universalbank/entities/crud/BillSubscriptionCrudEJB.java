package com.universalbank.entities.crud;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.universalbank.entities.BillSubscription;
import com.universalbank.entities.crud.api.IBillSubscriptionCrud;

@Stateless
@Local(IBillSubscriptionCrud.class)
public class BillSubscriptionCrudEJB implements IBillSubscriptionCrud{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public BillSubscription create(BillSubscription object) {
		if(object!=null) {
			entityManager.persist(object);
		}
		return object;
	}

	public BillSubscription update(BillSubscription object) {
		if(object!=null && object.getId()!=null) {
			entityManager.merge(object);
		}
		return object;
	}

	public BillSubscription find(Long id) {
		BillSubscription billSubscription=null;
		if(id!=null) {
			try {
				entityManager.find(BillSubscription.class, id);
			}catch(Exception e) {
				return null;
			}
		}
		return billSubscription;
	}

	public boolean delete(Long id) {
		BillSubscription  billSubscription=find(id);
		if(billSubscription!=null) {
			entityManager.remove(billSubscription);
			return true;
		}
		return false;
	}

	public List<BillSubscription> getAll() {
		Query query=entityManager.createNamedQuery("BillSubscription.getAll", BillSubscription.class);
		try {
			List<BillSubscription> result=query.getResultList();
			return result;
		} catch (Exception e) {			
		}
		return null;
	}

	public List<BillSubscription> getAllByClient(Long clientId) {
		Query query=entityManager.createNamedQuery("BillSubscription.getAllByClient", BillSubscription.class).setParameter("clientId", clientId);
		try {
			List<BillSubscription> result=query.getResultList();
			return result;
		} catch (Exception e) {			
		}
		return null;
	}

}
