package com.universalbank.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({ @NamedQuery(name = "BillSubscription.getAll", query = "SELECT b FROM BillSubscription b"),
		@NamedQuery(name = "BillSubscription.getAllByClient", query = "SELECT b FROM BillSubscription b WHERE b.client.id=:clientId") })
public class BillSubscription implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String billId;

	@OneToOne
	@JoinColumn(name = "scheduleData_id")
	private SchedulingData scheduleInfo;

	@Enumerated(EnumType.STRING)
	private ScheduleTypeEnum scheduleType;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;

	private ZonedDateTime dueDate;

	@Enumerated(EnumType.STRING)
	private PaymentTypeEnum paymentType;

	private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public SchedulingData getScheduleInfo() {
		return scheduleInfo;
	}

	public void setScheduleInfo(SchedulingData scheduleInfo) {
		this.scheduleInfo = scheduleInfo;
	}

	public ScheduleTypeEnum getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(ScheduleTypeEnum scheduleType) {
		this.scheduleType = scheduleType;
	}

	public ZonedDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(ZonedDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public Double getAmount() {
		
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
