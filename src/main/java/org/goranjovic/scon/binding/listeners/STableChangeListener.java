package org.goranjovic.scon.binding.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import org.goranjovic.scon.util.BeanUtil;

public class STableChangeListener implements PropertyChangeListener {
	
	private List<?> collection;
	private Map<String, String> mapping;

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	public STableChangeListener(List<?> collection, Map<String, String> mapping) {
		this.collection = collection;
		this.mapping = mapping;
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
		
		System.out.println("(" + event.getPropertyName() +"): " + event.getOldValue() + " -> " + event.getNewValue() );
		
		BeanUtil.setPropertyValue(collection.get(row), beanPropertyName, event.getNewValue());
	}

}
