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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.Nullable;

/**
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "URL_PATTERN", uniqueConstraints = { @UniqueConstraint(columnNames = { "URL_PATTERN_VALUE",
		"ASSOCIATED_WEBSITE_ID" }) })
@NamedQueries({
		@NamedQuery(name = "UrlPattern.find", query = "SELECT u FROM URLPattern u WHERE u.urlPattern = :urlPattern "),
		@NamedQuery(name = "UrlPattern.findByWebsite", query = "SELECT u FROM URLPattern u WHERE u.website = :website ") })
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pattern")
	private List<Field> fields;
	/**
	 * List of urlPattern used for matching urls in the content of the current
	 * url page (which match this url pattern).
	 */
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<URLPattern> nextUrls;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "pattern")
	private List<URLParameter> parameters;
	/**
	 * if true then the method to request the url that match this pattern is
	 * POST, else , GET is used.
	 */
	@Column(name = "POST_REQUEST")
	private boolean postRequest;
	@Nullable
	@ManyToOne
	@JoinColumn(name = "ASSOCIATED_WEBSITE_ID")
	private Website website;

	@Nullable
	@ManyToOne
	@JoinColumn(name = "EXCLUDED_FROM_WEBSITE_ID")
	private Website websiteToExcludeFrom;

	public URLPattern() {
	}

	public URLPattern(Website website, String urlPattern, String urlName) {
		this.website = website;
		this.urlPattern = urlPattern;
		this.urlName = urlName;
		nextUrls = new ArrayList<URLPattern>();
		fields = new ArrayList<Field>();
		urls = new ArrayList<URL>();
		setParameters(new ArrayList<URLParameter>());
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

	public void setPostRequest(boolean postRequest) {
		this.postRequest = postRequest;
	}

	public List<URLParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<URLParameter> parameters) {
		this.parameters = parameters;
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

		public URLPattern build() {
			return pattern;
		}

		public URLPatternBuilder withExcludeFrom(Website website) {
			pattern.setWebsiteToExcludeFrom(website);
			return this;
		}

		public URLPatternBuilder() {
			pattern = new URLPattern();
			pattern.setNextUrls(new ArrayList<URLPattern>());
			pattern.setFields(new ArrayList<Field>());
			pattern.setUrls(new ArrayList<URL>());
		}
	}
}
