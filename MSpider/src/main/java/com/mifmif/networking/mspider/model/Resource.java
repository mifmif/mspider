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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PageTemplate are constructed from one or more concrete page loaded from url
 * that match the associated urlPattern.
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "RESOURCE", uniqueConstraints = { @UniqueConstraint(columnNames = { "RESOURCE_DIRECTORY", "RESOURCE_RELATIVE_PATH", "PAGE_TEMPLATE_ID" }) })
public class Resource {

	@Id
	@SequenceGenerator(name = "RESOURCE_SEQ_GEN", sequenceName = "RESOURCE_SEQ_GEN")
	@GeneratedValue(generator = "RESOURCE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@Lob
	@Column(name = "CONTENT")
	private String content;
	@OneToOne
	@JoinColumn(name = "PAGE_TEMPLATE_ID")
	private PageTemplate pageTemplate;
	@Column(name = "RESOURCE_DIRECTORY")
	private String resourceDirectory;
	@Column(name = "RESOURCE_RELATIVE_PATH")
	private String resourceRelativePath;

	/**
	 * @param pattern
	 * @param basePageContent
	 * @param templateContent
	 */
	public Resource(PageTemplate pageTemplate, String resourceDirectory, String resourceRelativeUrl, String resourceContent) {
		this.setPageTemplate(pageTemplate);
		this.resourceDirectory = resourceDirectory;
		this.setResourceRelativePath(resourceRelativeUrl);
		this.content = resourceContent;
	}

	/**
	 * 
	 */
	public Resource() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PageTemplate getPageTemplate() {
		return pageTemplate;
	}

	public void setPageTemplate(PageTemplate pageTemplate) {
		this.pageTemplate = pageTemplate;
	}

	public String getResourceDirectory() {
		return resourceDirectory;
	}

	public void setResourceDirectory(String resourceDirectory) {
		this.resourceDirectory = resourceDirectory;
	}

	public String getResourceRelativePath() {
		return resourceRelativePath;
	}

	public void setResourceRelativePath(String resourceRelativePath) {
		this.resourceRelativePath = resourceRelativePath;
	}

}
