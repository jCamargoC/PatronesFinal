package com.universalbank.ejb;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import com.universalbank.ejb.api.SchedulingEJBContract;
import com.universalbank.entities.BillSubscription;
import com.universalbank.entities.ScheduleTypeEnum;

@Stateless
@Local(SchedulingEJBContract.class)
public class SchedulingEJB implements SchedulingEJBContract {
	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/queue/SchedulingQueue")
	private Queue schedulingQueue;

	@Resource
	TimerService timerService;

	@Timeout
	public void executeTimer(Timer timer) {
		if (timer.getInfo() instanceof BillSubscription) {
			BillSubscription billSubscription = (BillSubscription) timer.getInfo();
			try {
				final Connection connection = connectionFactory.createConnection();
				connection.start();
				final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				final MessageProducer scheduling = session.createProducer(schedulingQueue);
				scheduling.send(session.createObjectMessage(billSubscription.getId()));
			} catch (Exception e) {
				throw new RuntimeException("Error invocando pago de factura");
			}
		}
	}

	public void createTimer(BillSubscription billSubscription) {
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(billSubscription);
		timerConfig.setPersistent(true);
		ScheduleExpression scheduleExpression = null;
		if (ScheduleTypeEnum.DUE_DATE.equals(billSubscription.getScheduleType())) {
			scheduleExpression = new ScheduleExpression();
			scheduleExpression.month("*");
			scheduleExpression.dayOfMonth(billSubscription.getDueDate().getDayOfMonth());

		} else {
			scheduleExpression = new ScheduleExpression();
			scheduleExpression.month(billSubscription.getScheduleInfo().getMonths());
			scheduleExpression.dayOfMonth(billSubscription.getScheduleInfo().getDay());
			scheduleExpression.hour(billSubscription.getScheduleInfo().getHour());
		}
		timerService.createCalendarTimer(scheduleExpression, timerConfig);
	}

	public void stopTimer(Long billId) {
		for (Timer timer : timerService.getTimers()) {
			if(timer.getInfo() instanceof BillSubscription) {
				BillSubscription billSubscription=(BillSubscription)timer.getInfo();
				if(billId.equals(billSubscription.getId())) {
					timer.cancel();
				}
			}
		}
	}
	
	@PreDestroy
	public void destroy() {
		for (Timer timer : timerService.getTimers()) {
			timer.cancel();
		}
	}
}
