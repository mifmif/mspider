package com.mifmif.networking.mspider.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.Payload;
import com.mifmif.networking.mspider.model.URL;

public class UrlLoader {
	private URL url;
	private Document document;

	public UrlLoader(URL url) {
		this.url = url;
	}

	/**
	 * Load payloads from the fields of this urlLoader.
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {

		document = Jsoup.connect(url.getFullUrl()).get();
		List<Field> fields = url.getPattern().getFields();
		for (Field field : fields) {
			extractPayload(field, document);
		}

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
		String content;
		if (field.getContentSelector() != null) {
			String contentSelector = field.getContentSelector();
			content = element.select(contentSelector).text();
		} else
			content = element.text();
		Payload payload = new Payload(content, field, url);
		payload.setField(field);
		payload.setContent(content);
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
