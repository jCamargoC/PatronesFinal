package com.universalbank.ejb;

import java.util.HashMap;
import java.util.Map;

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
import com.universalbank.external.objects.Client;
import com.universalbank.integrator.api.impl.crm.CRMSystemInvoker;
import com.universalbank.payments.api.PaymentRouter;
import com.universalbank.utils.MailSender;

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
				String mailMessage="Apreciado cliente\n\n"
						+ "El banco universal ha realizado el siguiente pago: \n\n"
						+ "Tipo de pago: "+billSubscription.getPaymentType()+"\n"
						+ "Monto: "+billSubscription.getAmount().toString()+"\n"
						+ "Resultado: \n\n"+result+"\n\nCordialmente,\n\n\nBanco Universal";
				String subject="Resultado de pago automático";
				Map<String,Object> data=new HashMap<String,Object>();
				data.put("idType",billSubscription.getClient().getDocumentType());
				data.put("idNumber",billSubscription.getClient().getDocumentNumber());
				Client client=new CRMSystemInvoker().invokeExternalApp(data, Client.class);
				MailSender.sendMail(mailMessage, subject, client.getEmail());
				
				System.out.println(result);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
