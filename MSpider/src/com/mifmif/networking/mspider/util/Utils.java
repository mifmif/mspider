/**
 * 
 */
package com.mifmif.networking.mspider.util;

import com.mifmif.networking.mspider.model.Field;

/**
 * @author y.mifrah
 *
 */
public class Utils {

	/**
	 * The expression that will present a field inside a view page ,ex :
	 * #{beanName.fieldName}
	 * 
	 * @param field
	 * @return
	 */
	public static String getElFieldExpression(Field field) {
		String elBeanName = getELBeanName(field);
		String elFieldName = getElFieldName(field);
		String elFieldExpression = "#{" + elBeanName + "." + elFieldName + "}";
		return elFieldExpression;
	}

	/**
	 * Name that present the field passed as parameter, parent field name are
	 * also added to the name of the field ,ex : parentFieldName.fieldName
	 * 
	 * @param field
	 * @return
	 */
	private static String getElFieldName(Field field) {
		String elFieldName = toFirstLower(field.getName());
		if (field.getParentField() != null)
			elFieldName = getElFieldName(field.getParentField()) + "." + elFieldName;
		return elFieldName;
	}

	/**
	 * @return
	 */
	private static String getELBeanName(Field field) {
		String elBeanName = toFirstLower(field.getObjectModel().getName());
		return elBeanName;

	}

	public static String toFirstLower(String str) {
		String result = str.substring(1);
		String firstLower = ("" + str.charAt(0)).toLowerCase();
		return firstLower + result;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}
}
