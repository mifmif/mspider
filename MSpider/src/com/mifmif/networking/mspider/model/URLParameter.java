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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * It present the parameter model that has a website. values used to query the
 * website are presented by <code>ParameterValue</code>
 * 
 * @author y.mifrah
 *
 */
@Entity
@Table(name = "URL_PARAMETER")
public class URLParameter {

	@Id
	@SequenceGenerator(name = "URL_PARAMETER_SEQ_GEN", sequenceName = "URL_PARAMETER_SEQ_GEN")
	@GeneratedValue(generator = "URL_PARAMETER_SEQ_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	/**
	 * Name of the parameter that will be used when requesting the url
	 */
	private String name;
	/**
	 * Value could have a constant value or a pattern expression that will be
	 * used to generate parameter value to use when requesting the url
	 */
	private String expression;

	/**
	 * description of the parameter (not used when we request the url)
	 */
	private String description;
	/**
	 * Type of the parameter : CONSTANT or PATTERN_EXPRESSION
	 */
	@Enumerated(EnumType.STRING)
	private URLParameterType type = URLParameterType.PATTERN_EXPRESSION;
	@ManyToOne
	@JoinColumn(name = "URL_PATTERN_ID")
	private URLPattern pattern;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "urlParameter")
	private List<ParameterValue> parameterValues;

	/**
	 * 
	 */
	public URLParameter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pattern
	 * @param name
	 * @param value
	 */
	public URLParameter(URLPattern pattern, String name, String expression) {
		super();
		this.pattern = pattern;
		this.name = name;
		this.expression = expression;
		parameterValues = new ArrayList<ParameterValue>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URLParameterType getType() {
		return type;
	}

	public void setType(URLParameterType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URLPattern getPattern() {
		return pattern;
	}

	public void setPattern(URLPattern pattern) {
		this.pattern = pattern;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(List<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
