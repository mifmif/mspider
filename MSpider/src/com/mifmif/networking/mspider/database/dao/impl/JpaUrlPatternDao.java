package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.UrlPatternDao;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class JpaUrlPatternDao extends JpaDao<Long, URLPattern> implements UrlPatternDao {

	@Override
	public URLPattern findUniqueByUrlPattern(String urlPattern) {
		List<URLPattern> found = entityManager.createNamedQuery("UrlPattern.find", URLPattern.class).setParameter("urlPattern", urlPattern).getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

	@Override
	public List<URLPattern> findByWebsite(Website website) {
		List<URLPattern> found = entityManager.createNamedQuery("UrlPattern.findByWebsite", URLPattern.class).setParameter("website", website).getResultList();
		return found;
	}

}
