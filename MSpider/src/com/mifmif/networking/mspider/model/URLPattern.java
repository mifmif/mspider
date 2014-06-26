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

@Entity
@Table(name = "URL_PATTERN", uniqueConstraints = { @UniqueConstraint(columnNames = { "URL_PATTERN_VALUE", "ASSOCIATED_WEBSITE_ID" }) })
@NamedQueries({ @NamedQuery(name = "UrlPattern.find", query = "SELECT u FROM URLPattern u WHERE u.urlPattern = :urlPattern "),
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
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<URLPattern> nextUrls;
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
