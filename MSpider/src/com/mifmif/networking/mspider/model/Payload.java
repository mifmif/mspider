package com.mifmif.networking.mspider.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

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
	@JoinColumn(name = "OBJECT_INSTANCE_ID")
	private DomainObjectInstance objectInsance;

	@Column(name = "VALUE", length = 65000)
	private String value;

	@ManyToOne
	@JoinColumn(name = "FIELD_ID")
	private Field field;

	public Payload() {
	}

	public Payload(String value, Field field, URL url) {
		this.field = field;
		this.url = url;
		this.value = value;
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
		return objectInsance;
	}

	/**
	 * @param objectInsance
	 *            the objectInsance to set
	 */
	public void setObjectInsance(DomainObjectInstance objectInsance) {
		this.objectInsance = objectInsance;
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
}
