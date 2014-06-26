package com.mifmif.networking.mspider.database.dao.api;

import java.util.List;

import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

public interface UrlPatternDao extends Dao<Long, URLPattern> {

	URLPattern findUniqueByUrlPattern(String urlPattern);

	List<URLPattern> findByWebsite(Website website);

}
