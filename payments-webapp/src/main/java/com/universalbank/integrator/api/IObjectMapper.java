package com.universalbank.integrator.api;

public interface IObjectMapper {
	
	public <S,T>T convertResponseObject(S sourceObject,T targetObject);
}
