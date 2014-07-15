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
package com.mifmif.networking.mspider.service;

import java.util.ArrayList;
import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.PageTemplateDao;
import com.mifmif.networking.mspider.database.dao.api.UrlDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.PageTemplate;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 * 
 */
public class URLService {
	private UrlDao urlDao = JpaDaoFactory.getJpaUrlDao();
	private PageTemplateDao templateDao = JpaDaoFactory.getJpaPageTemplateDao();

	private URLService() {
	}

	private static URLService instance;

	public static URLService getInstance() {
		if (instance == null)
			instance = new URLService();
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
		urls = removeHostFromUrl(website.getHost(), urls);
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

	private List<String> removeHostFromUrl(String host, List<String> urls) {
		List<String> resultUrls = new ArrayList<String>();
		for (String url : urls) {
			if (url.startsWith(host)) {
				url = url.replace(host, "");
			}
			resultUrls.add(url);
		}
		return resultUrls;
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

	/**
	 * Create a new instance of <code>URL</code> , attach it to the matched
	 * <code>URLPattern</code> and persist it.
	 * 
	 * @param website
	 * @param urlValue
	 * @return
	 */
	public URL prepareUrl(Website website, String urlValue, boolean useExsitingInstance) {
		URL url = findUrlByWebsiteAndValue(website, urlValue);
		if (useExsitingInstance && url != null && !url.getPattern().isContentStatic())
			return url;
		URLPattern pattern = findUrlPatternByUrl(website, urlValue);
		url = new URL(pattern, urlValue);
		urlDao.persist(url);
		return url;
	}

	public URL prepareUrlByPattern(URLPattern urlPattern, String urlValue, boolean useExsitingInstance) {
		URL url = findUrlByWebsiteAndValue(urlPattern.getWebsite(), urlValue);
		if (useExsitingInstance && url != null && !url.getPattern().isContentStatic())
			return url;
		url = new URL(urlPattern, urlValue);
		urlDao.persist(url);
		return url;
	}

	public boolean isURLExistAndContentStatic(Website website, String urlValue) {
		URL url = findUrlByWebsiteAndValue(website, urlValue);
		if (url == null)
			return false;
		return url.getPattern().isContentStatic();

	}

	/**
	 * Update the <code>URL</code> passed as parameter
	 * 
	 * @param nextUrl
	 * @return
	 */
	public URL updateUrl(URL nextUrl) {
		return urlDao.merge(nextUrl);
	}

	/**
	 * Remove the <code>URL</code> passed as parameter from the data store
	 * 
	 * @param currentUrl
	 */
	public void removeUrl(URL currentUrl) {
		System.out.println("DELETE " + currentUrl);
		urlDao.remove(currentUrl);
	}

	public void persistTemplate(PageTemplate template) {
		URLPattern pattern = template.getPattern();
		pattern.setTemplate(template);
		templateDao.persist(template);
	}
}
