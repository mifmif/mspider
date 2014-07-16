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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author y.mifrah
 * 
 */
@Entity
@Table(name = "URL")
// , uniqueConstraints = { @UniqueConstraint(columnNames = { "URL_VALUE",
// "URL_PATTERN_ID" }) }
@NamedQueries({ @NamedQuery(name = "Url.findByUrlPatternAndValue", query = "SELECT u FROM URL u WHERE u.url = :url and u.pattern.urlPattern = :urlPattern "),
		@NamedQuery(name = "Url.findByWebsiteAndValue", query = "SELECT u FROM URL u WHERE u.url = :url and u.pattern.website = :website ") })
public class URL {

	@Id
	@SequenceGenerator(name = "URL_SEQ_GEN", sequenceName = "URL_SEQ_GEN")
	@GeneratedValue(generator = "URL_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@Column(name = "URL_VALUE")
	private String url;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "url")
	private List<Payload> payloads;

	@ManyToOne
	@JoinColumn(name = "URL_PATTERN_ID")
	private URLPattern pattern;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "url")
	private List<ParameterValue> parameters;

	public String getUrl() {
		return url;
	}

	public URL() {
		// TODO Auto-generated constructor stub
	}

	public URL(URLPattern pattern, String urlValue) {
		this.pattern = pattern;
		this.url = urlValue;
		setPayloads(new ArrayList<Payload>());
		pattern.getUrls().add(this);
		parameters = new ArrayList<ParameterValue>();
	}

	public String getFullUrl() {
		return pattern.getWebsite().getHost() + url;
	}

	public String getUrlDirectory() {
		StringBuilder fullUrl = new StringBuilder(pattern.getWebsite().getHost() + url);
		fullUrl = fullUrl.reverse();
		int indexOfSlash = fullUrl.indexOf("/");

		String urlDir = fullUrl.reverse().substring(0, fullUrl.length() - indexOfSlash);
		return urlDir;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public List<Payload> getPayloads() {
		return payloads;
	}

	public void setPayloads(List<Payload> payloads) {
		this.payloads = payloads;
	}

	public List<ParameterValue> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterValue> parameters) {
		this.parameters = parameters;
	}

}
