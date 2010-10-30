package org.goranjovic.scon.binding.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import org.goranjovic.scon.binding.validators.Validator;
import org.goranjovic.scon.binding.validators.impl.CompoundValidator;
import org.goranjovic.scon.util.BeanUtil;

public class STableChangeListener implements PropertyChangeListener {

	private static final Validator validator = new CompoundValidator();
	
	private List<?> collection;
	private Map<String, String> mapping;
	private Map<String, String> validationRules;

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	public STableChangeListener(List<?> collection, Map<String, String> mapping, Map<String, String> validationRules) {
		this.collection = collection;
		this.mapping = mapping;
		this.validationRules = validationRules;
	}

	public List<?> getCollection() {
		return collection;
	}

	public void setCollection(List<?> collection) {
		this.collection = collection;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		String[] splitted = event.getPropertyName().split(",");
		int row = Integer.parseInt(splitted[0]);
		String column = splitted[1];
		
		String beanPropertyName = mapping.get(column);
		
		Object value = event.getNewValue();
		String rule = validationRules.get(column);
		
		if(rule == null || validator.validate(value.toString(), rule)){
		
			System.out.println("(" + event.getPropertyName() +"): " + event.getOldValue() + " -> " + value );
			BeanUtil.setPropertyValue(collection.get(row), beanPropertyName, value);
			
		}else{
			System.out.println(value + " not valid " + rule);
		}
	}

}
