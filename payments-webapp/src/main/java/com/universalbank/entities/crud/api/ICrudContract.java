package com.universalbank.entities.crud.api;

import java.util.List;

public interface ICrudContract <T>{

	public T create(T object);
	public T update(T object);
	public T find(Long id);
	public boolean delete(Long id);
	public List<T> getAll();
}
