package com.mifmif.networking.mspider.database.dao.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mifmif.networking.mspider.database.dao.api.Dao;

public abstract class JpaDao<K, E> implements Dao<K, E> {
	protected Class<E> entityClass;
	private static final String PERSISTENCE_UNIT_NAME = "MSpider";
	private final EntityManagerFactory emf;
	protected EntityManager entityManager;

	public JpaDao() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		entityManager = emf.createEntityManager();
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public void persist(E entity) {
		entityManager.clear();
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();

	}

	public E merge(E entity) {
		entityManager.clear();
		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();

		return entity;
	}

	public void remove(E entity) {
		entityManager.remove(entity);
	}

	public E findById(K id) {
		return entityManager.find(entityClass, id);
	}
}