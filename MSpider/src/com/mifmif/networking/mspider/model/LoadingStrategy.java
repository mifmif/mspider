/**
 * 
 */
package com.mifmif.networking.mspider.model;

/**
 * 
 * Strategy applied when loading information from the website
 * 
 * @author y.mifrah
 *
 */
public enum LoadingStrategy {
	/**
	 * Follow links in pages ,and load thiere contents recursively based on
	 * UrlPattern link
	 */
	CRAWLING,
	/**
	 * Generate values for parameters and use it to load informations from the
	 * website
	 */
	PARAMETERS_GENERATION;
}
