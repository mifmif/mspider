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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;
import com.mifmif.networking.mspider.database.dao.api.ParameterValueDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.ParameterValue;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.model.URLParameter;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;
import com.mifmif.networking.mspider.util.Utils;

/**
 * @author y.mifrah
 *
 */
public class ParameterizedURLLoadProccess implements Runnable {
	private URLPattern urlPattern;
	private boolean isStarted;
	Map<Long, Iterator> paramsGenerators;
	private URLService urlService = URLService.getInstance();
	private PayloadService payloadService = PayloadService.getInstance();
	private ParameterValueDao parameterValueDao = JpaDaoFactory.getJpaParameterValueDao();

	public ParameterizedURLLoadProccess(URLPattern urlPattern) {
		this.urlPattern = urlPattern;
		initParameterGenerators();
	}

	@Override
	public void run() {
		load();
	}

	/**
	 * Loop over generated parametervalue from the parameters expression , and
	 * request the website using the generated parameter, then persist data
	 * loaded from the website.
	 */
	private void load() {
		while (hasNextUrlToGenerate()) {
			URL url = nextGeneratedUrl();
			URLLoader loader = new URLLoader(url);
			try {
				loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				Utils.sleep(250);
			}
			payloadService.persistPayloads(url.getPayloads());
		}
	}

	/**
	 * Build a mapping between urlParameters that contains the pattern
	 * expression of the parameter and an iterator that generate string from the
	 * pattern expression
	 */
	private void initParameterGenerators() {
		List<URLParameter> parameters = urlPattern.getParameters();
		paramsGenerators = new HashMap<Long, Iterator>();
		for (URLParameter urlParameter : parameters) {
			String patternExpression = urlParameter.getExpression();
			Long paramKey = urlParameter.getId();
			Generex generex = new Generex(patternExpression);
			Iterator paramGenerator = generex.iterator();
			paramsGenerators.put(paramKey, paramGenerator);
		}
	}

	private URL nextGeneratedUrl() {
		String pattern = urlPattern.getUrlPattern();// in the case of loading
													// website by generating
													// parameters values ,
													// the value of the
													// urlPattern define the
													// url to use when
													// requesting website
		String urlValue = preparegeneratedUrlValue();
		URL url = urlService.prepareUrlByPattern(urlPattern, urlValue, false);
		for (URLParameter urlParameter : urlPattern.getParameters()) {
			ParameterValue parameterValue = nextGeneratedParameter(url, urlParameter);
			url.getParameters().add(parameterValue);
		}
		return url;
	}

	private String preparegeneratedUrlValue() {

		return urlPattern.getUrlPattern();// if the value of the urlPattern is
											// not really a pattern then we can
											// extend this method by generating
											// url value from this pattern
	}

	private boolean hasNextUrlToGenerate() {
		for (URLParameter urlParameter : urlPattern.getParameters()) {
			Long paramKey = urlParameter.getId();
			Iterator paramGenerator = paramsGenerators.get(paramKey);
			if (!paramGenerator.hasNext())
				return false;
		}
		return true;
	}

	private ParameterValue nextGeneratedParameter(URL url, URLParameter urlParameter) {
		Long paramKey = urlParameter.getId();
		Iterator paramGenerator = paramsGenerators.get(paramKey);
		String generatedValue = paramGenerator.next();
		ParameterValue nextGenParam = new ParameterValue(url, urlParameter);
		nextGenParam.setValue(generatedValue);
		parameterValueDao.persist(nextGenParam);
		return nextGenParam;
	}

	synchronized public void start() {
		if (isStarted) {
			System.err.println("The proccess already started for " + urlPattern.getUrlPattern());
			return;
		}
		new Thread(this).start();
		isStarted = true;

	}
}
