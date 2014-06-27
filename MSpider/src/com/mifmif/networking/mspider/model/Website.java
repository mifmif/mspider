package com.mifmif.networking.mspider.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "WEBSITE", uniqueConstraints = { @UniqueConstraint(columnNames = { "HOST", "START_PAGE" }) })
@NamedQueries({ @NamedQuery(name = "Website.find", query = "SELECT w FROM Website w WHERE w.host = :host ") })
public class Website {
	@Id
	@SequenceGenerator(name = "WEB_SITE_SEQ_GEN", sequenceName = "WEB_SITE_SEQ_GEN")
	@GeneratedValue(generator = "WEB_SITE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@Column(name = "HOST")
	private String host;
	/**
	 * <code>/index.php</code> or just / or any page that would be used as the
	 * starting point
	 */
	@Column(name = "START_PAGE")
	private String startPage = "/";
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "website")
	private List<URLPattern> patterns;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "websiteToExcludeFrom")
	private List<URLPattern> excludedPatterns;

	public String getStartPage() {
		return startPage;
	}

	public Website() {
		// TODO Auto-generated constructor stub
	}

	public Website(String host) {
		this.host = host;
		patterns = new ArrayList<URLPattern>();
		excludedPatterns = new ArrayList<URLPattern>();
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}

	public List<URLPattern> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<URLPattern> patterns) {
		this.patterns = patterns;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<URLPattern> getExcludedPatterns() {
		return excludedPatterns;
	}

	public void setExcludedPatterns(List<URLPattern> excludedPatterns) {
		this.excludedPatterns = excludedPatterns;
	}
}
