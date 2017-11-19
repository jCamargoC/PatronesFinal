package com.external.pse.service;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.external.pse.objects.Payment;

@Path("pse")
@Stateless
public class PSEService {

	
	
	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String pay(Payment payment) {		
		if(payment!=null&&payment.getIdNumber()!=null&&payment.getIdType()!=null&&payment.getTargetEntityCode()!=null&&payment.getAmount()!=null&&payment.getAmount()>0) {
			return "Pago exitoso";
		}
		return "Pago fallido";
	}
}
