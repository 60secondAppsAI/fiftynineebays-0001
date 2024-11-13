package com.fiftynineebays.dto.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fiftynineebays.converter.Converter;


public class FilterDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String logic;
	private String field;
	private String operator;
	private String value;
	private Optional<Converter> converter;
	private Optional<String> joinType;
	private List <FilterDTO> filters;
	
	public FilterDTO() {
		super();
		this.filters = new ArrayList<FilterDTO>();
	}
	
	public FilterDTO(String logic, String field, String operator, String value, List<FilterDTO> filters) {
		super();
		this.logic = logic;
		this.field = field;
		this.operator = operator;
		this.value = value;
		
		if(filters == null)
			this.filters = new ArrayList<FilterDTO>();
		else
			this.filters = filters;
		
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}
	
	public String getLogic() {
		return logic;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getField() {
		return field;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return operator;
	}	
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {

		if (Optional.ofNullable(converter).isPresent()) 
			return this.converter.get().convert(value, operator);
		
		return value;
	}

	public String getOriginalValue() {
		return value;
	}

	public Optional<Converter> getConverter() {
		return converter;
	}

	public void setConverter(Optional<Converter> converter) {
		this.converter = converter;
	}
	
	public Optional<String> getJoinType() {
		return joinType;
	}

	public void setJoinType(Optional<String> joinType) {
		this.joinType = joinType;
	}

	public List<FilterDTO> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterDTO> filters) {
		this.filters = filters;
	}

	@Override
	public String toString() {
		return "FilterDTO [logic=" + logic + ", field=" + field + ", operator=" + operator + ", value=" + value
				+ ", filters=" + filters + "]";
	}
	
	public void copy(FilterDTO targetFilter)
	{
		logic = targetFilter.getLogic();
		field = targetFilter.getField();
		operator = targetFilter.getOperator();
		value = targetFilter.getValue();
		converter = targetFilter.getConverter();
		joinType = targetFilter.getJoinType();
		filters = targetFilter.getFilters();
	}

}