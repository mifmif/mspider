/**
 * Copyright 2014 y.mifrah
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
