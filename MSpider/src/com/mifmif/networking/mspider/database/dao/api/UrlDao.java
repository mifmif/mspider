package com.mifmif.networking.mspider.database.dao.api;

import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.Website;

public interface UrlDao extends Dao<Long, URL> {

	URL findUniqueByUrlAndUrlPattern(String url, String urlPattern);

	URL findByWebsiteAndValue(Website website, String urlValue);

}
