/**
 * 
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

/**
 * @author y.mifrah
 *
 */
@Entity
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
}
