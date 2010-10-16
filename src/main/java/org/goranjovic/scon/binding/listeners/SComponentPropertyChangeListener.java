package org.goranjovic.scon.binding.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import org.goranjovic.scon.util.BeanUtil;

public class SComponentPropertyChangeListener implements PropertyChangeListener {

	private Object bean;
	
	private String propertyName;
	
	public SComponentPropertyChangeListener(Object bean, String propertyName) {
		this.bean = bean;
		this.propertyName = propertyName;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		System.out.println(event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue());
		BeanUtil.setPropertyValue(bean, propertyName, event.getNewValue());
	}

}
