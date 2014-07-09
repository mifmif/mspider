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
import javax.persistence.Table;

/**
 * Class that present a domain model object in a website,
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "DOMAIN_OBJECT_INSTANCE")
@NamedQueries({
		@NamedQuery(name = "DomainObjectInstance.findAllByWebsiteAndModelName", query = "SELECT objectInstance FROM DomainObjectInstance objectInstance WHERE objectInstance.model.website = :website and  objectInstance.model.name= :modelName"),
		@NamedQuery(name = "DomainObjectInstance.findAllByWebsiteAndModelNameAndPayloadName", query = "SELECT objectInstance FROM DomainObjectInstance objectInstance , Payload p WHERE    objectInstance.model.name= :modelName AND objectInstance.model.website = :website AND p.field.objectModel=objectInstance.model   AND p.field.name= :payloadName   "),
		@NamedQuery(name = "DomainObjectInstance.findAllByWebsiteAndModelNameAndPayloadNameAndValue", query = "SELECT objectInstance FROM DomainObjectInstance objectInstance , Payload p WHERE    objectInstance.model.name= :modelName AND objectInstance.model.website = :website AND p.field.objectModel=objectInstance.model   AND p.field.name= :payloadName  AND p.value= :payloadValue ")

})
public class DomainObjectInstance {

	@Id
	@SequenceGenerator(name = "DOMAIN_OBJECT_INSTANCE_SEQ_GEN", sequenceName = "DOMAIN_OBJECT_INSTANCE_SEQ_GEN")
	@GeneratedValue(generator = "DOMAIN_OBJECT_INSTANCE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "objectInsance", orphanRemoval = true)
	private List<Payload> payloads;

	@ManyToOne
	@JoinColumn(name = "MODEL_ID")
	private DomainObjectModel model;

	/**
 * 
 */
	public DomainObjectInstance() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param model
	 */
	public DomainObjectInstance(DomainObjectModel model) {
		super();
		this.model = model;
		payloads = new ArrayList<Payload>();
	}

	/**
	 * @return the payloads
	 */
	public List<Payload> getPayloads() {
		return payloads;
	}

	/**
	 * @param payloads
	 *            the payloads to set
	 */
	public void setPayloads(List<Payload> payloads) {
		this.payloads = payloads;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
