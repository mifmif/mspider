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
package com.mifmif.networking.mspider.model.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.mifmif.networking.mspider.model.Payload;

/**
 * Model of an element component of a page. defined by the css selector path in
 * a specific UrlPattern, it could have a list of Field childs .
 * 
 * @author y.mifrah
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Field.findAllByUrlPattern", query = "SELECT f FROM Field f WHERE f.pattern = :pattern "),
		@NamedQuery(name = "Field.findByUrlPatternAndName", query = "SELECT f FROM Field f WHERE   f.pattern = :pattern AND f.name= :fieldName ") })
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
	@Enumerated(EnumType.STRING)
	private FieldType type = FieldType.TEXT;

	@ManyToOne
	@JoinColumn(name = "OBJECT_MODEL_ID")
	private DomainObjectModel objectModel;

	public Field() {
	}

	public Field(DomainObjectModel objectModel, URLPattern pattern, String selector, String name) {
		super();
		this.objectModel = objectModel;
		this.pattern = pattern;
		this.selector = selector;
		this.name = name;
		payloads = new ArrayList<Payload>();
		subFields = new ArrayList<Field>();

	}

	public Field(DomainObjectModel objectModel, URLPattern pattern, String selector, String tagContentSelector, String name) {
		this(objectModel, pattern, selector, name);
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

	/**
	 * Return the <code>objectModel</code> of this field if
	 * <code>parentField</code> is null ,else return the result of
	 * <code>parentField.getObjectModel()</code>. <br>
	 * This way, subFields of a field will always return the objectModel of the
	 * parent field.
	 * 
	 * @return the objectModel
	 */
	public DomainObjectModel getObjectModel() {
		if (parentField == null)
			return objectModel;
		return parentField.getObjectModel();
	}

	/**
	 * @param objectModel
	 *            the objectModel to set
	 */
	public void setObjectModel(DomainObjectModel objectModel) {
		this.objectModel = objectModel;
	}

}
