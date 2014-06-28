package com.mifmif.networking.mspider.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * Model of an element component of a page. defined by the css selector path in
 * a specific UrlPattern, it could have a list of Field childs .
 * 
 * @author y.mifrah
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Field.findByUrlPattern", query = "SELECT f FROM Field f WHERE f.pattern.urlPattern = :urlPattern ") })
public class Field {
	@Id
	@SequenceGenerator(name = "FIELD_SEQ_GEN", sequenceName = "FIELD_SEQ_GEN")
	@GeneratedValue(generator = "FIELD_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	/**
	 * Selector to handle the field from an element
	 */
	private String selector;
	/**
	 * the selector to handle the content to be associated to the field
	 */
	private String contentSelector;
	private String name;
	private boolean manyElements = false;
	private boolean selectorForParentElement = false;
	@ManyToOne
	@JoinColumn(name = "URL_PATTERN_ID")
	private URLPattern pattern;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "field")
	private List<Payload> payloads;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Field> subFields;
	@ManyToOne
	private Field parentField;
	private FieldType type = FieldType.TEXT;

	public Field() {
	}

	public Field(URLPattern pattern, String selector, String name) {
		super();
		this.pattern = pattern;
		this.selector = selector;
		this.name = name;
		payloads = new ArrayList<Payload>();
		subFields = new ArrayList<Field>();

	}

	public Field(URLPattern pattern, String selector, String tagContentSelector, String name) {
		this(pattern, selector, name);
		this.setContentSelector(tagContentSelector);
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isManyElements() {
		return manyElements;
	}

	public void setManyElements(boolean manyElements) {
		this.manyElements = manyElements;
	}

	public List<Payload> getPayloads() {
		return payloads;
	}

	public void setPayloads(List<Payload> payloads) {
		this.payloads = payloads;
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

	public boolean isSelectorForParentElement() {
		return selectorForParentElement;
	}

	public void setSelectorForParentElement(boolean selectorForParentElement) {
		this.selectorForParentElement = selectorForParentElement;
	}

	public List<Field> getSubFields() {
		return subFields;
	}

	public void setSubFields(List<Field> subFields) {
		this.subFields = subFields;
	}

	public Field getParentField() {
		return parentField;
	}

	public void setParentField(Field parentField) {
		this.parentField = parentField;
	}

	public String getContentSelector() {
		return contentSelector;
	}

	public void setContentSelector(String contentSelector) {
		this.contentSelector = contentSelector;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

}
