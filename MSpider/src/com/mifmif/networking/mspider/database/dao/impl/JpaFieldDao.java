package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.FieldDao;
import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class JpaFieldDao extends JpaDao<Long, Field> implements FieldDao {

	@Override
	public List<Field> findAllByUrlPattern(URLPattern pattern) {
		List<Field> found = entityManager
				.createNamedQuery("Field.findAllByUrlPattern", Field.class)
				.setParameter("pattern", pattern).getResultList();
		return found;
	}

	@Override
	public Field findByUrlPatternAndName(URLPattern pattern, String fieldName) {
		List<Field> found = entityManager
				.createNamedQuery("Field.findAllByUrlPattern", Field.class)
				.setParameter("pattern", pattern).getResultList();
		return found.isEmpty() ? null : found.get(0);
	}

}
