package com.external.swift.service;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.external.swift.objects.Payment;

@Path("swift")
@Stateless
public class SwiftService {

	
	
	@POST	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String pay(Payment payment) {		
		if(payment!=null&&payment.getIdNumber()!=null&&payment.getIdType()!=null&&payment.getTargetEntityCode()!=null&&payment.getAmount()!=null&&payment.getAmount()>0) {
			return "Succesfully payed";
		}
		return "Failed payment";
	}
}
