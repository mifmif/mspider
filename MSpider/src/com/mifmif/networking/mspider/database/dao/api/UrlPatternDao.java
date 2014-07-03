package com.mifmif.networking.mspider.database.dao.api;

import java.util.List;

import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 *
 */
public interface UrlPatternDao extends Dao<Long, URLPattern> {

	URLPattern findByPatternValue(String urlPattern);

	List<URLPattern> findAllByWebsite(Website website);

}
