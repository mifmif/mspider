package com.mifmif.networking.mspider.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Website entity model that present the structure of the website ,it contain
 * the list of informations that will be used to query the website,it group also
 * the urlpattern that would be used when loading information from it.
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "WEBSITE", uniqueConstraints = { @UniqueConstraint(columnNames = { "HOST", "START_PAGE" }) })
@NamedQueries({ @NamedQuery(name = "Website.findByHost", query = "SELECT w FROM Website w WHERE w.host = :host ") })
public class Website {
	@Id
	@SequenceGenerator(name = "WEB_SITE_SEQ_GEN", sequenceName = "WEB_SITE_SEQ_GEN")
	@GeneratedValue(generator = "WEB_SITE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	/**
	 * Host url of the website (ex: http://www.example.com)
	 */
	@Column(name = "HOST")
	private String host;
	/**
	 * Cookies used to connect to the website
	 */
	@ElementCollection
	@JoinTable(name = "WEBSITE_COOKIES", joinColumns = @JoinColumn(name = "WEBSITE_ID"))
	@MapKeyColumn(name = "COOKIES_KEY")
	@Column(name = "COOKIES_VALUE")
	private Map<String, String> cookies;
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "website")
	private List<DomainObjectModel> objectModels;

	/**
	 * Loading strategy applied to load information from the website , crawling
	 * for link following or parameter generation to generate parameter used to
	 * request pages .
	 */
	@Enumerated(EnumType.STRING)
	private LoadingStrategy strategy = LoadingStrategy.CRAWLING;

	public String getStartPage() {
		return startPage;
	}

	public Website() {
		// TODO Auto-generated constructor stub
	}

	public Website(String host) {
		this.host = host;
		this.patterns = new ArrayList<URLPattern>();
		this.excludedPatterns = new ArrayList<URLPattern>();
		this.cookies = new HashMap<String, String>();
		this.objectModels = new ArrayList<DomainObjectModel>();
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

	public LoadingStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(LoadingStrategy strategy) {
		this.strategy = strategy;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public List<DomainObjectModel> getObjectModels() {
		return objectModels;
	}

	public void setObjectModels(List<DomainObjectModel> objectModels) {
		this.objectModels = objectModels;
	}
}
