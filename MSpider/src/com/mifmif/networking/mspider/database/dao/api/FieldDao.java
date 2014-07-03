package com.mifmif.networking.mspider.database.dao.api;

import java.util.List;

import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.URLPattern;

/**
 * @author y.mifrah
 *
 */
public interface FieldDao extends Dao<Long, Field> {

	List<Field> findAllByUrlPattern(URLPattern pattern);

	Field findByUrlPatternAndName(URLPattern pattern, String fieldName);
}
