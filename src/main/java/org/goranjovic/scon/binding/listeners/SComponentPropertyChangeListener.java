package org.goranjovic.scon.binding.listeners;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.goranjovic.scon.binding.validators.Validator;
import org.goranjovic.scon.binding.validators.impl.CompoundValidator;
import org.goranjovic.scon.util.BeanUtil;

public class SComponentPropertyChangeListener implements PropertyChangeListener {

	private static final Validator validator = new CompoundValidator();
	
	private Object bean;
	
	private String propertyName;
	
	private String validationRule;
	
	public SComponentPropertyChangeListener(Object bean, String propertyName, String validationRule) {
		this.bean = bean;
		this.propertyName = propertyName;
		this.validationRule = validationRule;
	}

	public String getValidationRule() {
		return validationRule;
	}

	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
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
		if(validator.validate(event.getNewValue().toString(), validationRule)){
			Component component = (Component) event.getSource();
			System.out.println(component.getName()+"."+event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue());
			BeanUtil.setPropertyValue(bean, propertyName, event.getNewValue());
		}else{
			System.out.println(event.getNewValue() + " not valid " + validationRule);
		}
	}

}
