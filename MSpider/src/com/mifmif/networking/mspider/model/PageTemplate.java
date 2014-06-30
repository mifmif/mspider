/**
 * 
 */
package com.mifmif.networking.mspider.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 * PageTemplate are constructed from one or more concrete page loaded from url
 * that match the associated urlPattern.
 * 
 * @author y.mifrah
 *
 */
@Entity
public class PageTemplate {

	@Id
	@SequenceGenerator(name = "PAGE_TEMPLATE_SEQ_GEN", sequenceName = "PAGE_TEMPLATE_SEQ_GEN")
	@GeneratedValue(generator = "PAGE_TEMPLATE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	@Column(columnDefinition = "TEXT", name = "CONTENT")
	private String content;
	@OneToOne
	private URLPattern pattern;
	@Column(columnDefinition = "TEXT", name = "BASE_PAGE_CONTENT")
	private String basePageContent;

	/**
	 * @param pattern
	 * @param basePageContent
	 * @param templateContent
	 */
	public PageTemplate(URLPattern pattern, String basePageContent, String templateContent) {
		this.pattern = pattern;
		this.basePageContent = basePageContent;
		this.content = templateContent;
	}

	/**
	 * 
	 */
	public PageTemplate() {
		// TODO Auto-generated constructor stub
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public URLPattern getPattern() {
		return pattern;
	}

	public void setPattern(URLPattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the basePageContent
	 */
	public String getBasePageContent() {
		return basePageContent;
	}

	/**
	 * @param basePageContent
	 *            the basePageContent to set
	 */
	public void setBasePageContent(String basePageContent) {
		this.basePageContent = basePageContent;
	}
}
