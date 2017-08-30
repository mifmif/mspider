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
package com.mifmif.networking.mspider.service;

import com.mifmif.networking.mspider.database.dao.api.WebsiteDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.Website;

public class WebsiteService {
	private static WebsiteService instance;
	private WebsiteDao websiteDao = JpaDaoFactory.getJpaWebsiteDao();

	public Website finbByHost(String host) {

		return websiteDao.finbByHost(host);

	}

	public static WebsiteService getInstance() {
		if (instance == null)
			instance = new WebsiteService();
		return instance;
	}
}
