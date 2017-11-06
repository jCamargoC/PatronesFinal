package com.universalbank.entities.crud.api;

import com.universalbank.entities.SchedulingData;

public interface ISchedulingDataCrud extends ICrudContract<SchedulingData>{
	
	public SchedulingData getByBillSubscription(Long billId);
}
