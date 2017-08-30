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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mifmif.networking.mspider.model.Website;

/**
 * Class that present a domain model object in a website,
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "DOMAIN_OBJECT_MODEL")
public class DomainObjectModel {

	@Id
	@SequenceGenerator(name = "DOMAIN_OBJECT_MODEL_SEQ_GEN", sequenceName = "DOMAIN_OBJECT_MODEL_SEQ_GEN")
	@GeneratedValue(generator = "DOMAIN_OBJECT_MODEL_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@Column(name = "NAME")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "domainObjectModel", orphanRemoval = true)
	private List<Field> fields;

	/**
	 * Name of the field used to identify the domainObjectModel, the list of
	 * DomainObjectInstance that are attached to this domainObjectModel will be
	 * identified by the value of the field <code>identifierFieldName</code>.
	 */
	@Column(name = "IDENTIFIER_FIELD_NAME")
	private String identifierFieldName;
	@ManyToOne
	@JoinColumn(name = "WEBSITE_ID")
	private Website website;

	/**
 * 
 */
	public DomainObjectModel() {
		// TODO Auto-generated constructor stub
	}

	/**
 * 
 */
	public DomainObjectModel(Website website, String name) {
		this.website = website;
		this.name = name;
		fields = new ArrayList<Field>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 * @return the website
	 */
	public Website getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(Website website) {
		this.website = website;
	}

	public String getIdentifierFieldName() {
		return identifierFieldName;
	}

	public void setIdentifierFieldName(String identifierFieldName) {
		this.identifierFieldName = identifierFieldName;
	}

}
