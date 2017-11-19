package com.universalbank.integrator.api.impl.crm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import com.universalbank.integrator.api.IExternalAppInvoker;
import com.universalbank.utils.RESTInvoker;

public class CRMSystemInvoker implements IExternalAppInvoker{

	public <T> T invokeExternalApp(Map<String, Object> data, Class<T> returnClazz) {
		try {
			String idNumber = (String) data.get("idNumber");
			String idType = (String) data.get("idType");
			String propertiesPath = System.getProperty("getClienInfoPath");
			if (propertiesPath == null) {
				propertiesPath = "/apps/CRMSystem/getClientInfo.properties";
			}
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File(propertiesPath)));
			String url = properties.getProperty("url") + idType + "/" + idNumber;
			T object = RESTInvoker.doGet(returnClazz, url, properties.getProperty("accept"));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
