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
