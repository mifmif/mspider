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
