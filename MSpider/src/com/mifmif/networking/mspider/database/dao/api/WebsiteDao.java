package com.mifmif.networking.mspider.database.dao.api;

import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 *
 */
public interface WebsiteDao extends Dao<Long, Website> {
	Website finbByHost(String host);
}
