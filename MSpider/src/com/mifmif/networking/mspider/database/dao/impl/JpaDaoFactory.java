package com.mifmif.networking.mspider.database.dao.impl;

import com.mifmif.networking.mspider.database.dao.api.FieldDao;
import com.mifmif.networking.mspider.database.dao.api.PayloadDao;
import com.mifmif.networking.mspider.database.dao.api.UrlDao;
import com.mifmif.networking.mspider.database.dao.api.UrlPatternDao;
import com.mifmif.networking.mspider.database.dao.api.WebsiteDao;

public class JpaDaoFactory {
	private JpaDaoFactory() {

	}

	private static final FieldDao jpaFieldDao = new JpaFieldDao();
	private static final UrlDao jpaUrlDao = new JpaUrlDao();
	private static final PayloadDao jpaPayloadDao = new JpaPayloadDao();
	private static final UrlPatternDao jpaUrlPatternDao = new JpaUrlPatternDao();
	private static final WebsiteDao jpaWebsiteDao = new JpaWebsiteDao();

	public static FieldDao getJpaFieldDao() {
		return jpaFieldDao;
	}

	public static UrlDao getJpaUrlDao() {
		return jpaUrlDao;
	}

	public static PayloadDao getJpaPayloadDao() {
		return jpaPayloadDao;
	}

	public static UrlPatternDao getJpaUrlPatternDao() {
		return jpaUrlPatternDao;
	}

	public static WebsiteDao getJpaWebsiteDao() {
		return jpaWebsiteDao;
	}
}
