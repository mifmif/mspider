package com.mifmif.networking.mspider.database.dao.impl;

import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.FieldDao;
import com.mifmif.networking.mspider.model.Field;

/**
 * @author y.mifrah
 * 
 */
public class JpaFieldDao extends JpaDao<Long, Field> implements FieldDao {

	@Override
	public List<Field> findByUrlPattern(String urlPattern) {
		List<Field> found = entityManager.createNamedQuery("Field.findByUrlPattern", Field.class).setParameter("urlPattern", urlPattern).getResultList();
		return found;
	}

}
