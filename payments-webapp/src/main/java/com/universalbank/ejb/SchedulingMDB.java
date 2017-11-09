package com.universalbank.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.universalbank.entities.BillSubscription;
import com.universalbank.entities.crud.api.IBillSubscriptionCrud;
import com.universalbank.payments.api.PaymentRouter;

@MessageDriven(name = "SchedulingMDB", activationConfig = {

		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/SchedulingQueue"),

		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class SchedulingMDB implements MessageListener {

	@EJB
	private PaymentRouter paymentRouter;
	
	@EJB
	private IBillSubscriptionCrud billSubscriptionCrud;
	
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			final ObjectMessage textMessage = (ObjectMessage) message;
			try {
				final Long id = (Long)textMessage.getObject();
				BillSubscription billSubscription=billSubscriptionCrud.find(id);
				String result=paymentRouter.executePayment(billSubscription);
				System.out.println(result);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
