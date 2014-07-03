package com.mifmif.networking.mspider.database.dao.api;

import java.util.List;

import com.mifmif.networking.mspider.model.DomainObjectInstance;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 *
 */
public interface DomainObjectInstanceDao extends Dao<Long, DomainObjectInstance> {
	List<DomainObjectInstance> findAllByWebsiteAndModelName(Website website, String modelName);

	List<DomainObjectInstance> findAllByWebsiteAndModelNameAndPayloadNameAndValue(Website website, String domainName, String payloadName, String payloadValue);

	List<DomainObjectInstance> findAllByWebsiteAndModelNameAndPayloadName(Website website, String domainName, String payloadName);
}
