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
package com.mifmif.networking.mspider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mifmif.networking.mspider.model.PageTemplate;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.Website;
import com.mifmif.networking.mspider.model.metamodel.URLPattern;
import com.mifmif.networking.mspider.service.ParameterizedURLLoadProccess;
import com.mifmif.networking.mspider.service.PayloadService;
import com.mifmif.networking.mspider.service.URLLoader;
import com.mifmif.networking.mspider.service.URLService;
import com.mifmif.networking.mspider.service.WebsiteService;
import com.mifmif.networking.mspider.util.Utils;

/**
 * @author y.mifrah
 *
 */
public class Engine {
	private Website website;
	private URLService urlService = URLService.getInstance();
	private WebsiteService websiteService = WebsiteService.getInstance();
	private PayloadService payloadService = PayloadService.getInstance();
	private List<String> visitedUrls;
	private Queue<String> queueUrls;

	public Engine(Website website) {
		visitedUrls = new ArrayList<String>();
		queueUrls = new LinkedList<String>();
		this.website = website;
	}

	public Engine(String host) {
		visitedUrls = new ArrayList<String>();
		queueUrls = new LinkedList<String>();
		this.website = websiteService.finbByHost(host);
	}

	public void start() {
		switch (website.getStrategy()) {
		case CRAWLING:
			crawl();
			break;

		case PARAMETERS_GENERATION:
			loadByParamsGeneration();
			break;
		}
	}

	private void loadByParamsGeneration() {

		for (URLPattern urlPattern : website.getPatterns()) {
			ParameterizedURLLoadProccess urlPatternLoadProccess = new ParameterizedURLLoadProccess(urlPattern);
			urlPatternLoadProccess.start();
		}
		// TODO start a list of thread, each one for a url of the website, each
		// thread will generate parameters and use them in the request .
	}

	private void crawl() {
		queueUrls.add(website.getStartPage());
		while (!queueUrls.isEmpty() && customizedConstraint()) {
			String currUrlValue = queueUrls.poll();
			if (isVisitedUrl(currUrlValue))
				continue;
			System.out.println(currUrlValue);
			boolean isURLExistAndContentStatic = urlService.isURLExistAndContentStatic(website, currUrlValue);
			if (isURLExistAndContentStatic)
				continue;
			URL currentUrl = prepareUrl(currUrlValue);
			URLLoader loader = new URLLoader(currentUrl);
			try {
				loader.load();
				if (website.isBuildTemplate() && currentUrl.getPattern().getTemplate() == null) {
					buildPageTemplate(loader);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Utils.sleep(250);
				continue;
			}
			persistPayloads(currentUrl);
			addTovisitedUrls(currUrlValue);
			List<String> urlsInPages = loader.getUrlsInPage();
			List<String> nextUrls = urlService.filterUrls(website, currentUrl.getUrl(), urlsInPages);
			nextUrls.removeAll(visitedUrls);
			queueUrls.addAll(nextUrls);
		}
	}

	private void persistPayloads(URL currentUrl) {
		payloadService.persistPayloads(currentUrl.getPayloads());
		urlService.updateUrl(currentUrl);

	}

	private void buildPageTemplate(URLLoader loader) {
		PageTemplate template = loader.buildTemplate();
		urlService.persistTemplate(template);
	}

	/**
	 * Check if the url is already exist in the database and if it's content
	 * would be changed
	 * 
	 * 
	 * @return
	 */

	private boolean isVisitedUrl(String url) {
		return visitedUrls.contains(url);
	}

	private void addTovisitedUrls(String url) {
		visitedUrls.add(url);

	}

	private boolean customizedConstraint() {
		return visitedUrls.size() < 100;
	}

	private URL prepareUrl(String urlValue) {
		URL url = urlService.prepareUrl(website, urlValue, true);
		if (url == null) {
			System.out.println("Cannot prepare url " + urlValue);
		}
		return url;
	}

}
