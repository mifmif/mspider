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

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.DomainObjectInstanceDao;
import com.mifmif.networking.mspider.database.dao.api.PayloadDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.DomainObjectInstance;
import com.mifmif.networking.mspider.model.Payload;
import com.mifmif.networking.mspider.model.Website;
import com.mifmif.networking.mspider.model.metamodel.DomainObjectModel;

public class PayloadService {
	private PayloadDao payloadDao = JpaDaoFactory.getJpaPayloadDao();
	private DomainObjectInstanceDao objectInstanceDao = JpaDaoFactory.getJpaDomainObjectInstanceDao();

	private PayloadService() {
	}

	private static PayloadService instance;

	public static PayloadService getInstance() {
		if (instance == null)
			instance = new PayloadService();
		return instance;
	}

	/**
	 * Save <code>Payload</code> passed as parameter into the data store
	 * 
	 * @param payload
	 */
	public void savePayload(Payload payload) {
		payloadDao.persist(payload);

	}

	public void persistPayloads(List<Payload> payloads) {
		if (payloads.size() == 0)
			return;
		DomainObjectModel objectModel = null;
		for (Payload payload : payloads)
			// We suppose that each URL page will generate only one
			// DomainObjectInstance, so we look for one principal field and we
			// take the objectmodel associated to it
			if (payload.getField().getParentField() == null) {
				objectModel = payload.getField().getObjectModel();
				break;
			}
		DomainObjectInstance newObjectInstance = new DomainObjectInstance(objectModel);
		objectInstanceDao.persist(newObjectInstance);
		for (Payload payload : payloads) {
			DomainObjectInstance objectInstanceByPayload = getObjectInstanceByFieldIdentifierValue(payload);
			if (objectInstanceByPayload == null) {
				joinPayloadToDomainInstance(newObjectInstance, payload);
			} else {
				// TODO create a relationship between newObjectInstance and
				// objectInstanceByPayload
			}
		}
	}

	/**
	 * if the payload is for a field that present fieldIdentifier for the
	 * domainObjectModel ,then return the objectInstance that have as value for
	 * the fieldIdentifer the value of the payload passed as parameter. return
	 * null otherwise.
	 * 
	 * @param payload
	 * @return
	 */
	private DomainObjectInstance getObjectInstanceByFieldIdentifierValue(Payload payload) {
		DomainObjectModel objectModel = payload.getField().getObjectModel();
		String objectModelName = objectModel.getName();
		String fieldIdentifier = objectModel.getIdentifierFieldName();
		if (!payload.getName().equals(fieldIdentifier))
			return null;
		Website website = payload.getField().getPattern().getWebsite();
		List<DomainObjectInstance> objectInstances = objectInstanceDao.findAllByWebsiteAndModelNameAndPayloadNameAndValue(website, objectModelName,
				fieldIdentifier, payload.getValue());
		if (objectInstances.size() > 0) {
			return objectInstances.get(0);
		} else
			return null;

	}

	private DomainObjectInstance joinPayloadToDomainInstance(DomainObjectInstance newObjectInstance, Payload payload) {
		payload.setObjectInsance(newObjectInstance);
		savePayload(payload);
		return newObjectInstance;

	}
}
