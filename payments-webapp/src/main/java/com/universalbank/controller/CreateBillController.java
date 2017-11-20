package com.universalbank.controller;

import java.io.IOException;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.universalbank.ejb.PrincipalEJB;
import com.universalbank.ejb.api.SchedulingEJBContract;
import com.universalbank.entities.BillSubscription;
import com.universalbank.entities.PaymentTypeEnum;
import com.universalbank.entities.ScheduleTypeEnum;
import com.universalbank.entities.SchedulingData;
import com.universalbank.entities.crud.api.IBillSubscriptionCrud;
import com.universalbank.entities.crud.api.ISchedulingDataCrud;

@Named("createBillController")
@ViewScoped
public class CreateBillController implements Serializable {
	@Inject
	private IBillSubscriptionCrud billSubscriptionCrud;
	@Inject
	private ISchedulingDataCrud schedulingDataCrud;
	@Inject
	private PrincipalEJB principalEJB;
	@Inject
	private SchedulingEJBContract schedulingEJB;

	private BillSubscription billSubscription = new BillSubscription();

	private PaymentTypeEnum paymentType;

	private Date dueDate;

	private ScheduleTypeEnum scheduleType;

	private String[] selectedMonths;

	private Integer day;
	private Integer[] days = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
			22, 23, 24, 25, 26, 27, 28 };

	private Integer hour;
	private Integer[] hours = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
			21, 22, 23 };

	private Integer minute;
	private Integer[] minutes = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
			20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
			47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59 };

	private Boolean showScheduleData = false;
	private Boolean editMode = false;

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String id = request.getParameter("billId");
		if (id != null) {
			billSubscription = billSubscriptionCrud.find(Long.parseLong(id));
			editMode = true;
			dueDate = Date.from(billSubscription.getDueDate().toInstant());
			if (billSubscription.getScheduleType().equals(ScheduleTypeEnum.PERSONALIZADA)) {
				SchedulingData schedulingData = billSubscription.getScheduleInfo();
				if (schedulingData != null) {
					hour = schedulingData.getHour() != null ? Integer.parseInt(schedulingData.getHour()) : null;
					minute = schedulingData.getMinute() != null ? Integer.parseInt(schedulingData.getMinute()) : null;
					day = schedulingData.getDay() != null ? Integer.parseInt(schedulingData.getDay()) : null;

					selectedMonths = schedulingData.getMonths() != null ? schedulingData.getMonths().split(",") : null;
				}

			}
		}
	}

	public BillSubscription getBillSubscription() {
		return billSubscription;
	}

	public void setBillSubscription(BillSubscription billSubscription) {
		this.billSubscription = billSubscription;
	}

	public PaymentTypeEnum[] getPaymentTypes() {
		return PaymentTypeEnum.values();
	}

	public PaymentTypeEnum getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEnum paymentType) {
		this.paymentType = paymentType;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public ScheduleTypeEnum[] getScheduleTypes() {
		return ScheduleTypeEnum.values();
	}

	public ScheduleTypeEnum getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(ScheduleTypeEnum scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String[] getSelectedMonths() {
		return selectedMonths;
	}

	public void setSelectedMonths(String[] selectedMonths) {
		this.selectedMonths = selectedMonths;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer[] getDays() {
		return days;
	}

	public void setDays(Integer[] days) {
		this.days = days;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer[] getHours() {
		return hours;
	}

	public void setHours(Integer[] hours) {
		this.hours = hours;
	}

	public Boolean getShowScheduleData() {
		return showScheduleData;
	}

	public void setShowScheduleData(Boolean showScheduleData) {
		this.showScheduleData = showScheduleData;
	}

	public void manageTypeChange() {
		if (ScheduleTypeEnum.PERSONALIZADA.equals(scheduleType)) {
			showScheduleData = true;
		} else {
			showScheduleData = false;
		}
	}

	public void saveBill() {
		billSubscription.setDueDate(
				dueDate != null ? ZonedDateTime.ofInstant(dueDate.toInstant(), ZoneId.systemDefault()) : null);
		billSubscription.setDueDate(
				dueDate != null ? ZonedDateTime.ofInstant(dueDate.toInstant(), ZoneId.systemDefault()) : null);
		if (!editMode) {
			
			billSubscription.setClient(principalEJB.getLoggedClient());
			billSubscriptionCrud.create(billSubscription);
			if (ScheduleTypeEnum.PERSONALIZADA.equals(billSubscription.getScheduleType())) {
				if ((selectedMonths != null && day != null)) {
					SchedulingData schedulingData = new SchedulingData();
					String months = "";
					for (int i = 0; i < selectedMonths.length; i++) {
						String month = selectedMonths[i];
						months += month + (i != selectedMonths.length - 1 ? "," : "");
					}
					schedulingData.setMonths(months);
					schedulingData.setDay(day != null ? day.toString() : null);
					schedulingData.setHour(hour != null ? hour.toString() : null);
					schedulingData.setMinute(minute != null ? minute.toString() : null);
					schedulingData.setBillSubscription(billSubscription);
					schedulingDataCrud.create(schedulingData);
					billSubscription.setScheduleInfo(schedulingData);
					billSubscriptionCrud.update(billSubscription);
				}
				schedulingEJB.createTimer(billSubscription);

			}

		} else {
			billSubscriptionCrud.update(billSubscription);
			if (ScheduleTypeEnum.PERSONALIZADA.equals(billSubscription.getScheduleType())) {
				if ((selectedMonths != null && day != null)) {
					SchedulingData schedulingData = new SchedulingData();
					String months = "";
					for (int i = 0; i < selectedMonths.length; i++) {
						String month = selectedMonths[i];
						months += month + (i != selectedMonths.length - 1 ? "," : "");
					}
					schedulingData.setMonths(months);
					schedulingData.setDay(day != null ? day.toString() : null);
					schedulingData.setHour(hour != null ? hour.toString() : null);
					schedulingData.setMinute(minute != null ? minute.toString() : null);
					schedulingData.setBillSubscription(billSubscription);
					schedulingDataCrud.create(schedulingData);
					billSubscription.setScheduleInfo(schedulingData);
					billSubscriptionCrud.update(billSubscription);
					if (billSubscription.getScheduleInfo() != null) {
						schedulingData.setId(billSubscription.getScheduleInfo().getId());
						schedulingData.setBillSubscription(billSubscription);
						schedulingDataCrud.update(schedulingData);

					} else {
						schedulingDataCrud.create(schedulingData);
					}
					billSubscription.setScheduleInfo(schedulingData);
					billSubscriptionCrud.update(billSubscription);
					schedulingEJB.createTimer(billSubscription);
				}

			}
		}
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		try {
			ec.redirect(ec.getRequestContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath());
    }
	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer[] getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer[] minutes) {
		this.minutes = minutes;
	}

}
