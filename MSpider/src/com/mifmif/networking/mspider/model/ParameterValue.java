/**
 * 
 */
package com.mifmif.networking.mspider.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * It present the parameter value used to query one <code>URL</code> , it's
 * attached to a <code>URLParameter</code> to define the name of parameter to
 * use when preparing request, and <code>URL</code> that was invoked using this
 * ParameterValue
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "PARAMETER_VALUE")
public class ParameterValue {

	@Id
	@SequenceGenerator(name = "PARAMETER_VALUE_SEQ_GEN", sequenceName = "PARAMETER_VALUE_SEQ_GEN")
	@GeneratedValue(generator = "PARAMETER_VALUE_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	private String value;

	@ManyToOne
	@JoinColumn(name = "URLPARAMETER_ID")
	private URLParameter urlParameter;

	@ManyToOne
	@JoinColumn(name = "URL_ID")
	private URL url;

	public URLParameter getUrlParameter() {
		return urlParameter;
	}

	public void setUrlParameter(URLParameter urlParameter) {
		this.urlParameter = urlParameter;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return urlParameter.getName();
	}

	public void setValue(String value) {
		this.value = value;
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

}
