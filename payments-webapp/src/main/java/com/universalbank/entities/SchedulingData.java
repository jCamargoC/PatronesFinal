package com.universalbank.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries(
		{@NamedQuery(name="SchedulingData.getByBillSubscription",query="SELECT b FROM SchedulingData b WHERE b.billSubscription.id=:billId"),
		 @NamedQuery(name="SchedulingData.getAllByClient",query="SELECT b FROM SchedulingData b WHERE b.billSubscription.client.id=:clientId")})
public class SchedulingData implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String months;
	
	private String day;
	
	private String hour;
	
	private String minute;
	
	@OneToOne(mappedBy="scheduleInfo")
	private BillSubscription billSubscription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public BillSubscription getBillSubscription() {
		return billSubscription;
	}

	public void setBillSubscription(BillSubscription billSubscription) {
		this.billSubscription = billSubscription;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}
	
}
