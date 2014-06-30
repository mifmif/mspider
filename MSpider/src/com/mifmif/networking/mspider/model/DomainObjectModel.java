/**
 * 
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

/**
 * Class that present a domain model object in a website,
 * 
 * @author y.mifrah
 *
 */
@Entity
public class DomainObjectModel {

	@Id
	@SequenceGenerator(name = "DOMAIN_OBJECT_MODEL_SEQ_GEN", sequenceName = "DOMAIN_OBJECT_MODEL_SEQ_GEN")
	@GeneratedValue(generator = "DOMAIN_OBJECT_MODEL_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "objectModel", orphanRemoval = true)
	private List<Field> fields;

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

}
