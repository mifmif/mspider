package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.UrlDao;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class JpaUrlDao extends JpaDao<Long, URL> implements UrlDao {

	@Override
	public URL findUniqueByUrlAndUrlPattern(String url, String urlPattern) {
		List<URL> found = entityManager.createNamedQuery("Url.findByUrlPatternAndValue", URL.class).setParameter("urlPattern", urlPattern)
				.setParameter("url", url).getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

	@Override
	public URL findByWebsiteAndValue(Website website, String urlValue) {
		List<URL> found = entityManager.createNamedQuery("Url.findByWebsiteAndValue", URL.class).setParameter("website", website).setParameter("url", urlValue)
				.getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

}
