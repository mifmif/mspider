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
package com.mifmif.networking.mspider.model.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mifmif.networking.mspider.model.PageTemplate;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.Website;

/**
 * Class that present a <code>URLPattern</code> expression in a website. one
 * <code>URLPattern</code> point to other <code>URLPattern</code> to constitute
 * the sitemap of a web site.
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "URL_PATTERN", uniqueConstraints = { @UniqueConstraint(columnNames = { "URL_PATTERN_VALUE", "ASSOCIATED_WEBSITE_ID" }) })
@NamedQueries({ @NamedQuery(name = "UrlPattern.findByPatternValue", query = "SELECT u FROM URLPattern u WHERE u.urlPattern = :urlPattern "),
		@NamedQuery(name = "UrlPattern.findAllByWebsite", query = "SELECT u FROM URLPattern u WHERE u.website = :website ") })
public class URLPattern {

	@Id
	@SequenceGenerator(name = "URL_PATTERN_SEQ_GEN", sequenceName = "URL_PATTERN_SEQ_GEN")
	@GeneratedValue(generator = "URL_PATTERN_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@Column(name = "URL_PATTERN_VALUE")
	private String urlPattern;
	@Column(name = "URL_NAME")
	private String urlName;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pattern")
	private List<URL> urls;
	@OneToMany(mappedBy = "pattern")
	private List<Field> fields;
	/**
	 * List of urlPattern used for matching urls in the content of the current
	 * url page (which match this url pattern).
	 */
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<URLPattern> nextUrls;
	@OneToOne(mappedBy = "pattern")
	private PageTemplate template;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pattern")
	private List<URLParameter> parameters;

	/**
	 * if true then the method to request the url that match this pattern is
	 * POST, else , GET is used.
	 */
	@Column(name = "POST_REQUEST")
	private boolean postRequest;

	@ManyToOne
	@JoinColumn(name = "ASSOCIATED_WEBSITE_ID", nullable = true)
	private Website website;
	private boolean contentStatic;
	@ManyToOne
	@JoinColumn(name = "EXCLUDED_FROM_WEBSITE_ID", nullable = true)
	private Website websiteToExcludeFrom;

	public URLPattern() {
		this.nextUrls = new ArrayList<URLPattern>();
		this.fields = new ArrayList<Field>();
		this.urls = new ArrayList<URL>();
		this.parameters = new ArrayList<URLParameter>();
	}

	public URLPattern(Website website, String urlPattern, String urlName, boolean contentStatic) {
		this();
		this.website = website;
		this.urlPattern = urlPattern;
		this.urlName = urlName;
		this.contentStatic = contentStatic;
	}

	public boolean hasParameters() {
		return getParameters().size() != 0;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public List<URL> getUrls() {
		return urls;
	}

	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public List<URLPattern> getNextUrls() {
		return nextUrls;
	}

	public void setNextUrls(List<URLPattern> nextUrls) {
		this.nextUrls = nextUrls;
	}

	public Website getWebsiteToExcludeFrom() {
		return websiteToExcludeFrom;
	}

	public void setWebsiteToExcludeFrom(Website websiteToExcludeFrom) {
		this.websiteToExcludeFrom = websiteToExcludeFrom;
	}

	public static URLPatternBuilder builder() {
		return new URLPatternBuilder();
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	/**
	 * True if the page contain a payload that will be extracted and saved ,
	 * false if the page just link to other page and do not contain payload that
	 * will be persisted
	 */
	public boolean hasPayloadContent() {
		return getFields().size() != 0;
	}

	public boolean isPostRequest() {
		return postRequest;
	}

	public boolean isGetRequest() {
		return !postRequest;
	}

	public void setPostRequest(boolean postRequest) {
		this.postRequest = postRequest;
	}

	public List<URLParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<URLParameter> parameters) {
		this.parameters = parameters;
	}

	public PageTemplate getTemplate() {
		return template;
	}

	public void setTemplate(PageTemplate template) {
		this.template = template;
	}

	public boolean isContentStatic() {
		return contentStatic;
	}

	public void setContentStatic(boolean contentStatic) {
		this.contentStatic = contentStatic;
	}

	public static class URLPatternBuilder {
		URLPattern pattern;

		public URLPatternBuilder withWebsite(Website website) {
			pattern.setWebsite(website);
			return this;
		}

		public URLPatternBuilder withUrlPattern(String urlPattern) {
			pattern.setUrlPattern(urlPattern);
			return this;
		}

		public URLPatternBuilder withPostRequest() {
			pattern.setPostRequest(true);
			return this;
		}

		public URLPatternBuilder withGetRequest() {
			pattern.setPostRequest(false);
			return this;
		}

		public URLPatternBuilder withUrlName(String urlName) {
			pattern.setUrlName(urlName);
			return this;
		}

		public URLPattern build() {
			return pattern;
		}

		public URLPatternBuilder withExcludeFrom(Website website) {
			pattern.setWebsiteToExcludeFrom(website);
			return this;
		}

		public URLPatternBuilder withContentStatic(boolean contentStatic) {
			pattern.setContentStatic(contentStatic);
			return this;
		}

		public URLPatternBuilder() {
			pattern = new URLPattern();
			pattern.setNextUrls(new ArrayList<URLPattern>());
			pattern.setFields(new ArrayList<Field>());
			pattern.setUrls(new ArrayList<URL>());
		}
	}

	@Override
	public String toString() {
		return urlPattern;
	}
}
