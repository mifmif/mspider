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

import com.mifmif.networking.mspider.database.dao.api.UrlPatternDao;
import com.mifmif.networking.mspider.model.Website;
import com.mifmif.networking.mspider.model.metamodel.URLPattern;

/**
 * @author y.mifrah
 * 
 */
public class JpaUrlPatternDao extends JpaDao<Long, URLPattern> implements UrlPatternDao {

	@Override
	public URLPattern findByPatternValue(String urlPattern) {
		List<URLPattern> found = entityManager.createNamedQuery("UrlPattern.findByPatternValue", URLPattern.class).setParameter("urlPattern", urlPattern)
				.getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

	@Override
	public List<URLPattern> findAllByWebsite(Website website) {
		List<URLPattern> found = entityManager.createNamedQuery("UrlPattern.findAllByWebsite", URLPattern.class).setParameter("website", website)
				.getResultList();
		return found;
	}

}
