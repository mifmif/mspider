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
package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.FieldDao;
import com.mifmif.networking.mspider.model.Website;
import com.mifmif.networking.mspider.model.metamodel.Field;
import com.mifmif.networking.mspider.model.metamodel.URLPattern;

/**
 * @author y.mifrah
 * 
 */
public class JpaFieldDao extends JpaDao<Long, Field> implements FieldDao {

	@Override
	public List<Field> findAllByUrlPattern(URLPattern pattern) {
		List<Field> found = entityManager.createNamedQuery("Field.findAllByUrlPattern", Field.class).setParameter("pattern", pattern).getResultList();
		return found;
	}

	@Override
	public Field findByUrlPatternAndName(URLPattern pattern, String fieldName) {
		List<Field> found = entityManager.createNamedQuery("Field.findAllByUrlPattern", Field.class).setParameter("pattern", pattern).getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

}
