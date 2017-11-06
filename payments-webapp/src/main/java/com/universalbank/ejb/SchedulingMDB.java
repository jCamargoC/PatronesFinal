package com.universalbank.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@MessageDriven(name = "SchedulingMDB", activationConfig = {

		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/SchedulingQueue"),

		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class SchedulingMDB implements MessageListener {

	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			final ObjectMessage textMessage = (ObjectMessage) message;
			try {
				final Long question = (Long)textMessage.getObject();
				System.out.println("pa la mierda " + question);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
