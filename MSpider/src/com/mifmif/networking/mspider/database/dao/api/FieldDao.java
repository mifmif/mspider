package com.mifmif.networking.mspider.database.dao.api;

import java.util.List;

import com.mifmif.networking.mspider.model.Field;

/**
 * @author y.mifrah
 *
 */
public interface FieldDao extends Dao<Long, Field> {

	List<Field> findByUrlPattern(String urlPattern);
}
