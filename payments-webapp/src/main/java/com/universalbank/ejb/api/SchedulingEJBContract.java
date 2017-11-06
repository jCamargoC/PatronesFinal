package com.universalbank.ejb.api;

import javax.ejb.Timer;

import com.universalbank.entities.BillSubscription;

public interface SchedulingEJBContract {
	public void executeTimer(Timer timer)	;
	public void createTimer(BillSubscription bill);
	public void stopTimer(Long billId);
}
