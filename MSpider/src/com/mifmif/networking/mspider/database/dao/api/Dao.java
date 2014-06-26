package com.mifmif.networking.mspider.database.dao.api;

public interface Dao<K, E> {
	void persist(E entity);

	void remove(E entity);

	E merge(E entity);

	E findById(K id);
}