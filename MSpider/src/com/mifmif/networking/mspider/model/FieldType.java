/**
 * 
 */
package com.mifmif.networking.mspider.model;

/**
 * The content type of a field, it could be used to decide the datastore
 * destination that will be used to save payload of the field (ex: whether we
 * save binary content in the database or in a folder and record it's path in
 * the database)
 * 
 * @author y.mifrah
 *
 */
public enum FieldType {
	LONG, DOUBLE, TEXT, IMAGE, BINARY;
}
