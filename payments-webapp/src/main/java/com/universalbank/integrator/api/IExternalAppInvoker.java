package com.universalbank.integrator.api;

import java.util.Map;

public interface IExternalAppInvoker {
	
	public <T>T invokeExternalApp(Map<String,Object> data, Class<T> returnClazz); 
}
