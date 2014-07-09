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
