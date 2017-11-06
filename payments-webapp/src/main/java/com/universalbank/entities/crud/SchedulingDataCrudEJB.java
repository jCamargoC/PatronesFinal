package com.universalbank.entities.crud;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.universalbank.entities.SchedulingData;
import com.universalbank.entities.crud.api.ISchedulingDataCrud;

@Stateless
@Local(ISchedulingDataCrud.class)
public class SchedulingDataCrudEJB implements ISchedulingDataCrud{

	@PersistenceContext
	private EntityManager entityManager;
	
	public SchedulingData create(SchedulingData object) {
		if(object!=null) {
			entityManager.persist(object);
		}
		return object;
	}

	public SchedulingData update(SchedulingData object) {
		if(object!=null && object.getId()!=null) {
			entityManager.merge(object);
		}
		return object;
	}

	public SchedulingData find(Long id) {
		SchedulingData SchedulingData=null;
		if(id!=null) {
			try {
				entityManager.find(SchedulingData.class, id);
			}catch(Exception e) {
				return null;
			}
		}
		return SchedulingData;
	}

	public boolean delete(Long id) {
		SchedulingData  SchedulingData=find(id);
		if(SchedulingData!=null) {
			entityManager.remove(SchedulingData);
			return true;
		}
		return false;
	}

	public List<SchedulingData> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public SchedulingData getByBillSubscription(Long billId) {
		Query query=entityManager.createNamedQuery("SchedulingData.getByBillSubscription", SchedulingData.class).setParameter("billId", billId);
		try {
			List<SchedulingData> result=query.getResultList();
			return result.get(0);
		} catch (Exception e) {			
		}
		return null;
	}
}
