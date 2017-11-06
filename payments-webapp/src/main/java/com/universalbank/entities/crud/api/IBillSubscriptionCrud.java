package com.universalbank.entities.crud.api;

import java.util.List;

import com.universalbank.entities.BillSubscription;

public interface IBillSubscriptionCrud extends ICrudContract<BillSubscription>{
	
	public List<BillSubscription> getAllByClient(Long clientId);

}
