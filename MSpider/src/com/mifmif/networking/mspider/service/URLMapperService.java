package com.mifmif.networking.mspider.service;

import java.util.ArrayList;
import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.PayloadDao;
import com.mifmif.networking.mspider.database.dao.api.UrlDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.Payload;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class URLMapperService {
	private UrlDao urlDao = JpaDaoFactory.getJpaUrlDao();
	private PayloadDao payloadDao = JpaDaoFactory.getJpaPayloadDao();

	private String hostUrl;

	private URLMapperService() {
	}

	private static URLMapperService instance;

	public static URLMapperService getInstance() {
		if (instance == null)
			instance = new URLMapperService();
		return instance;
	}

	/**
	 * filter <code>urls</code> and retain just the <code>url</code> that are
	 * allowed to use in the page loaded from the <code>fromUrl</code>
	 * 
	 * @param fromUrl
	 * @param urls
	 * @return
	 */
	public List<String> filterUrls(Website website, String fromUrl, List<String> urls) {
		URLPattern fromPattern = findUrlPatternByUrl(website, fromUrl);
		List<URLPattern> excludedPatterns = website.getExcludedPatterns();
		List<URLPattern> toUrlsPattern = fromPattern.getNextUrls();
		List<String> filteredUrls = new ArrayList<String>();
		for (URLPattern toUrlPattern : toUrlsPattern) {
			loopOverUrls: for (String url : urls) {
				if (url.matches(toUrlPattern.getUrlPattern())) {
					for (URLPattern excludedPattern : excludedPatterns) {
						if (url.matches(excludedPattern.getUrlPattern()))
							continue loopOverUrls;
					}
					filteredUrls.add(url);
				}
			}
		}
		return filteredUrls;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public URLPattern findUrlPatternByUrl(Website website, String urlValue) {
		List<URLPattern> patterns = website.getPatterns();
		if (patterns == null)
			return null;
		for (URLPattern pattern : patterns) {
			if (urlValue.matches(pattern.getUrlPattern()))
				return pattern;
		}
		return null;
	}

	public URL findUrlByWebsiteAndValue(Website website, String urlValue) {
		URL url = urlDao.findByWebsiteAndValue(website, urlValue);
		return url;
	}

	public URL prepareUrl(Website website, String urlValue) {
		URLPattern pattern = findUrlPatternByUrl(website, urlValue);
		if (pattern == null)
			return null;
		URL url = new URL(pattern, urlValue);
		urlDao.persist(url);
		return url;
	}

	public URL updateUrl(URL nextUrl) {
		return urlDao.merge(nextUrl);
	}

	public void savePayload(Payload payload) {
		payloadDao.persist(payload);

	}
}
