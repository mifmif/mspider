package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.DomainObjectInstanceDao;
import com.mifmif.networking.mspider.model.DomainObjectInstance;
import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class JpaDomainObjectInstanceDao extends JpaDao<Long, DomainObjectInstance> implements DomainObjectInstanceDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mifmif.networking.mspider.database.dao.api.DomainObjectInstanceDao
	 * #findByWebsiteAndModelName(com.mifmif.networking.mspider.model.Website,
	 * java.lang.String)
	 */
	@Override
	public List<DomainObjectInstance> findByWebsiteAndModelName(Website website, String modelName) {
		List<DomainObjectInstance> found = entityManager.createNamedQuery("DomainObjectInstance.findByWebsiteAndModelName", DomainObjectInstance.class)
				.setParameter("website", website).setParameter("modelName", modelName).getResultList();
		return found;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mifmif.networking.mspider.database.dao.api.DomainObjectInstanceDao
	 * #findByWebsiteAndPayloadNameAndValue
	 * (com.mifmif.networking.mspider.model.Website, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<DomainObjectInstance> findByWebsiteAndDomainNameAndPayloadNameAndValue(Website website, String modelName, String payloadName,
			String payloadValue) {
		List<DomainObjectInstance> found = entityManager
				.createNamedQuery("DomainObjectInstance.findByWebsiteAndModelNameAndPayloadNameAndValue", DomainObjectInstance.class)
				.setParameter("website", website).setParameter("modelName", modelName).setParameter("payloadName", payloadName)
				.setParameter("payloadValue", payloadValue).getResultList();
		return found;
	}

}
