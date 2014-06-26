package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.WebsiteDao;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class JpaWebsiteDao extends JpaDao<Long, Website> implements WebsiteDao {

	@Override
	public Website finbByHost(String host) {
		List<Website> found = entityManager.createNamedQuery("Website.find", Website.class).setParameter("host", host).getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

}
