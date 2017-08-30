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
package com.mifmif.networking.mspider.model;

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

import com.mifmif.networking.mspider.model.metamodel.Field;

/**
 * The content of a field in a concrete url.
 * 
 * @author y.mifrah
 *
 */
@Entity
public class Payload {
	@Id
	@SequenceGenerator(name = "PAYLOAD_SEQ_GEN", sequenceName = "PAYLOAD_SEQ_GEN")
	@GeneratedValue(generator = "PAYLOAD_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "URL_ID")
	private URL url;

	@ManyToOne
	@JoinColumn(name = "DOMAIN_OBJECT_INSTANCE_ID")
	private DomainObjectInstance domainObjectInstance;

	@Column(name = "VALUE", length = 65000)
	private String value;

	@ManyToOne
	@JoinColumn(name = "FIELD_ID")
	private Field field;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Payload> subPayloads;
	@ManyToOne
	@JoinColumn(name = "PARENT_PAYLOAD_ID")
	private Payload parentPayload;

	public Payload() {
	}

	public Payload(String value, Field field, URL url, Payload parentPayload) {
		this.field = field;
		this.url = url;
		this.value = value;
		this.parentPayload = parentPayload;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getName() {
		return getField().getName();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @return the objectInsance
	 */
	public DomainObjectInstance getObjectInsance() {
		return getDomainObjectInstance();
	}

	/**
	 * @param objectInsance
	 *            the objectInsance to set
	 */
	public void setObjectInsance(DomainObjectInstance objectInsance) {
		this.setDomainObjectInstance(objectInsance);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public List<Payload> getSubPayloads() {
		return subPayloads;
	}

	public void setSubPayloads(List<Payload> subPayloads) {
		this.subPayloads = subPayloads;
	}

	public Payload getParentPayload() {
		return parentPayload;
	}

	public void setParentPayload(Payload parentPayload) {
		this.parentPayload = parentPayload;
	}

	public DomainObjectInstance getDomainObjectInstance() {
		return domainObjectInstance;
	}

	public void setDomainObjectInstance(DomainObjectInstance domainObjectInstance) {
		this.domainObjectInstance = domainObjectInstance;
	}
}
