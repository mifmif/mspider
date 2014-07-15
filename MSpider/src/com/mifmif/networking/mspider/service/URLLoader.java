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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.PageTemplate;
import com.mifmif.networking.mspider.model.ParameterValue;
import com.mifmif.networking.mspider.model.Payload;
import com.mifmif.networking.mspider.model.URL;
import com.mifmif.networking.mspider.util.Utils;

/**
 * @author y.mifrah
 *
 */
public class URLLoader {
	private URL url;
	private Connection connection;
	private Document document;

	public URLLoader(URL url) {
		this.url = url;
	}

	/**
	 * Load payloads from the url of this urlLoader based on the fields of the
	 * Url Pattern.
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {
		connection = Jsoup.connect(url.getFullUrl());
		setCookies();
		setParameters();
		if (url.getPattern().isPostRequest())
			document = connection.post();
		else
			document = connection.get();
		List<Field> fields = url.getPattern().getFields();
		for (Field field : fields) {
			extractPayload(field, document);
		}
	}

	/**
	 * Build page template from the extracted document based on field of the
	 * urlPattern that match the page url.
	 * 
	 * @return
	 */
	public PageTemplate buildTemplate() {
		Element templateDocument = document.clone();
		String baseContent = templateDocument.toString();
		List<Field> fields = url.getPattern().getFields();
		for (Field field : fields) {
			buildFieldTemplate(field, templateDocument);
		}
		String templateContent = templateDocument.toString();
		PageTemplate template = new PageTemplate(url.getPattern(), baseContent, templateContent);
		return template;
	}

	/**
	 * @param field
	 * @param templateElement
	 */
	private void buildFieldTemplate(Field field, Element templateElement) {
		String selector = field.getSelector();
		Elements elements = templateElement.select(selector);
		if (field.isSelectorForParentElement() && elements.first() != null) {
			elements = elements.first().children();
		}
		if (elements.size() == 0)
			return;

		if (!field.isManyElements()) {
			Element innerElmnt = elements.first();
			String ElFieldExpression = Utils.getElFieldExpression(field);
			innerElmnt.empty();
			innerElmnt.appendText(ElFieldExpression);
			buildsubFieldTemplate(field, innerElmnt);
		} else {
			for (Element innerElmnt : elements) {
				String ElFieldExpression = Utils.getElFieldExpression(field);
				innerElmnt.empty();
				innerElmnt.appendText(ElFieldExpression);
				buildsubFieldTemplate(field, innerElmnt);
			}
		}
	}

	/**
	 * @param field
	 * @param innerElmnt
	 */
	private void buildsubFieldTemplate(Field field, Element innerElmnt) {
		for (Field subField : field.getSubFields()) {
			buildFieldTemplate(subField, innerElmnt);
		}

	}

	private void setCookies() {
		Map<String, String> cookies = url.getPattern().getWebsite().getCookies();
		connection.cookies(cookies);

	}

	private void setHeaders() {
		// TODO get headers from the website instance and set them to the
		// connection
	}

	/**
	 * Set parameters of the url in the request that will be sent .
	 */
	private void setParameters() {
		boolean hasParameters = url.getParameters().size() != 0;
		if (hasParameters)
			for (ParameterValue parameter : url.getParameters())
				connection.data(parameter.getName(), parameter.getValue());

	}

	/**
	 * Extract payloads from the <code>element</code> that match the
	 * <code>field</code> passed as parameter
	 * 
	 * @param field
	 * @param element
	 */
	private void extractPayload(Field field, Element element) {
		String selector = field.getSelector();
		Elements elements = element.select(selector);
		/**
		 * check if the selector specified in the Field is to handle the parent
		 * element that will contain the list of payloads in the document
		 */
		if (field.isSelectorForParentElement() && elements.first() != null) {
			elements = elements.first().children();
		}
		if (elements.size() == 0)
			return;

		if (!field.isManyElements()) {
			Element innerElmnt = elements.first();
			Payload payload = preparePayload(innerElmnt, field, url);
			field.getPayloads().add(payload);
			url.getPayloads().add(payload);
			extractSubPayloads(field, innerElmnt);
		} else {
			for (Element innerElmnt : elements) {
				Payload payload = preparePayload(innerElmnt, field, url);
				field.getPayloads().add(payload);
				url.getPayloads().add(payload);
				extractSubPayloads(field, innerElmnt);
			}
		}

	}

	/**
	 * Extract payloads from the <code>element</code> that match the subFields
	 * of the <code>field</code> passed as parameter
	 * 
	 * @param field
	 * @param element
	 */
	private void extractSubPayloads(Field field, Element element) {
		for (Field subField : field.getSubFields()) {
			extractPayload(subField, element);
		}
	}

	private Payload preparePayload(Element element, Field field, URL url) {
		String payloadValue;
		if (field.getContentSelector() != null) {
			String contentSelector = field.getContentSelector();
			payloadValue = element.select(contentSelector).text();
		} else
			payloadValue = element.text();
		Payload payload = new Payload(payloadValue, field, url);
		return payload;
	}

	/**
	 * prepare and return the list of urls contained in the page loaded
	 * 
	 * @return
	 */
	public List<String> getUrlsInPage() {
		Elements elements = document.select("a");
		List<String> urls = new ArrayList<String>();
		for (Element element : elements) {
			String url = element.attr("href");
			urls.add(url);
		}
		return urls;
	}

}
