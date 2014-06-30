package com.mifmif.networking.mspider.database.dao.api;

import java.util.List;

import com.mifmif.networking.mspider.model.DomainObjectInstance;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 *
 */
public interface DomainObjectInstanceDao extends Dao<Long, DomainObjectInstance> {
	List<DomainObjectInstance> findByWebsiteAndModelName(Website website, String modelName);

	List<DomainObjectInstance> findByWebsiteAndDomainNameAndPayloadNameAndValue(Website website, String domainName,
			String payloadName, String payloadValue);
}
