package com.universalbank.integrator.api;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;

import com.universalbank.entities.Client;
import com.universalbank.entities.ExternalAppEnum;

@Singleton
public class ExternalAppsAggregator {

	public Map<String,Object> aggregateInvocations(Client client,ExternalAppEnum... apps){
		Map<String, Object> results=new HashMap<String, Object>();
		for (ExternalAppEnum externalAppEnum : apps) {
			try {
				IExternalAppInvoker appInvoker=(IExternalAppInvoker) externalAppEnum.getImplementationClass().newInstance();
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("idNumber", client.getDocumentNumber());
				data.put("idType", client.getDocumentType());
				Object response=appInvoker.invokeExternalApp(data, externalAppEnum.getReturnObject());
				results.put(externalAppEnum.getName(), response);
			} catch (InstantiationException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
