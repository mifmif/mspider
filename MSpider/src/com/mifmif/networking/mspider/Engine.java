package com.mifmif.networking.mspider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mifmif.networking.mspider.database.dao.api.WebsiteDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.LoadingStrategy;
import com.mifmif.networking.mspider.model.Payload;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.Website;
import com.mifmif.networking.mspider.service.URLMapperService;
import com.mifmif.networking.mspider.service.UrlLoader;

/**
 * @author y.mifrah
 *
 */
public class Engine {
	private Website website;
	private URLMapperService urlService = URLMapperService.getInstance();
	private List<String> visitedUrls;
	private Queue<String> queueUrls;
	private WebsiteDao websiteDao = JpaDaoFactory.getJpaWebsiteDao();

	public Engine(Website website) {
		visitedUrls = new ArrayList<String>();
		queueUrls = new LinkedList<String>();
		this.website = website;
	}

	public Engine(String host) {
		visitedUrls = new ArrayList<String>();
		queueUrls = new LinkedList<String>();
		this.website = websiteDao.finbByHost(host);
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
		// TODO start a list of thread, each one for a url of the website, each
		// thread will generate parameters and use in the request .
	}

	private void crawl() {
		queueUrls.add(website.getStartPage());
		while (!queueUrls.isEmpty() && customizedConstraint()) {
			String currUrlValue = queueUrls.poll();
			if (isVisitedUrl(currUrlValue))
				continue;
			System.out.println(currUrlValue);
			if (!isValidUrl(currUrlValue))
				continue;
			URL currentUrl = prepareUrl(currUrlValue);
			UrlLoader loader = new UrlLoader(currentUrl);
			try {
				loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				sleep(250);
				continue;
			}
			for (Payload payload : currentUrl.getPayloads())
				urlService.savePayload(payload);
			if (currentUrl.getPayloads().size() != 0)
				urlService.updateUrl(currentUrl);

			else
				urlService.removeUrl(currentUrl);
			addTovisitedUrls(currUrlValue);
			List<String> urlsInPages = loader.getUrlsInPage();
			List<String> nextUrls = urlService.filterUrls(website, currentUrl.getUrl(), urlsInPages);
			nextUrls.removeAll(visitedUrls);
			queueUrls.addAll(nextUrls);
		}
	}

	private boolean isValidUrl(String currUrlValue) {
		boolean isValid = urlService.isValidUrl(website, currUrlValue);
		if (!isValid)
			System.out.println("The url " + currUrlValue + " is already saved or no pattern match it");
		return isValid;
	}

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
		URL url = urlService.prepareUrl(website, urlValue);
		if (url == null) {
			System.out.println("Cannot prepare url " + urlValue);
		}
		return url;
	}

	void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}
}
