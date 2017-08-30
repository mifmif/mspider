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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mifmif.networking.mspider.model.metamodel.URLPattern;

/**
 * PageTemplate are constructed from one or more concrete page loaded from url
 * that match the associated urlPattern.
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "PAGE_TEMPLATE")
public class PageTemplate {

	@Id
	@SequenceGenerator(name = "PAGE_TEMPLATE_SEQ_GEN", sequenceName = "PAGE_TEMPLATE_SEQ_GEN")
	@GeneratedValue(generator = "PAGE_TEMPLATE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@Lob
	@Column(name = "CONTENT")
	private String content;
	@OneToOne
	private URLPattern pattern;
	@Lob
	@Column(name = "BASE_PAGE_CONTENT")
	private String basePageContent;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pageTemplate")
	private List<Resource> resources;

	/**
	 * @param pattern
	 * @param basePageContent
	 * @param templateContent
	 */
	public PageTemplate(URLPattern pattern, String basePageContent, String templateContent) {
		this.pattern = pattern;
		this.basePageContent = basePageContent;
		this.content = templateContent;
		resources = new ArrayList<Resource>();
	}

	/**
	 * 
	 */
	public PageTemplate() {
		// TODO Auto-generated constructor stub
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

	public URLPattern getPattern() {
		return pattern;
	}

	public void setPattern(URLPattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the basePageContent
	 */
	public String getBasePageContent() {
		return basePageContent;
	}

	/**
	 * @param basePageContent
	 *            the basePageContent to set
	 */
	public void setBasePageContent(String basePageContent) {
		this.basePageContent = basePageContent;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}
