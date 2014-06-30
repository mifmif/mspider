/**
 * 
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

/**
 * Class that present a domain model object in a website,
 * 
 * @author y.mifrah
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "DomainObjectInstance.findByWebsiteAndModelName", query = "SELECT objectInstance FROM DomainObjectInstance objectInstance WHERE objectInstance.model.website = :website and  objectInstance.model.name= :name"),
		@NamedQuery(name = "DomainObjectInstance.findByWebsiteAndModelNameAndPayloadNameAndValue", query = "SELECT objectInstance FROM DomainObjectInstance objectInstance , Payload p WHERE    objectInstance.model.name= :modelName AND objectInstance.model.website = :website AND p.field.objectModel=objectInstance.model   AND p.field.name= :payloadName  AND p.value= :payloadValue ")

})
public class DomainObjectInstance {

	@Id
	@SequenceGenerator(name = "DOMAIN_OBJECT_INSTANCE_SEQ_GEN", sequenceName = "DOMAIN_OBJECT_INSTANCE_SEQ_GEN")
	@GeneratedValue(generator = "DOMAIN_OBJECT_INSTANCE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	private String name;

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
	public DomainObjectInstance(String name, DomainObjectModel model) {
		super();
		this.name = name;
		this.model = model;
		payloads = new ArrayList<Payload>();
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

}
