package org.goranjovic.scon;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JComboBox;

import org.goranjovic.guibuilder.components.SComponent;
import org.goranjovic.guibuilder.components.SRadio;
import org.goranjovic.guibuilder.core.SwingView;
import org.goranjovic.scon.binding.listeners.BeanPropertyChangeListener;
import org.goranjovic.scon.binding.listeners.DummyPropertyChangeListener;
import org.goranjovic.scon.binding.listeners.SComponentPropertyChangeListener;
import org.goranjovic.scon.binding.proxy.BoundBeanWrapperProxyFactory;
import org.goranjovic.scon.util.proxy.WrapperProxy;

public class SwingController {
	
	private SwingView view;
	
	public SwingView getView() {
		return view;
	}

	public void setView(SwingView view) {
		this.view = view;
	}

	public void fillCombo(String comboId, Collection<?> data, boolean append){
		JComboBox combo = (JComboBox) getView().findComponent(comboId);
		if(!append){
			combo.removeAllItems();
		}
		for(Object item : data){
			combo.addItem(item);
		}
	}
	
	public <T> T bind(T bean, Class<T> iface, Map<String, String> mapping){
	
		Collection<String> boundProperties = mapping.values();
		Map<String, List<String>> reverseMapping = createReverseMapping(mapping);
		BoundBeanWrapperProxyFactory beanProxyFactory = new BoundBeanWrapperProxyFactory();
		beanProxyFactory.setPropertyChangeListener(new BeanPropertyChangeListener(view, reverseMapping));
		beanProxyFactory.setBoundProperties(boundProperties);
		
		T proxy = beanProxyFactory.createWrapperProxy(bean, iface);

		for(String componentId : mapping.keySet()){
			
			SComponent scomponent = (SComponent) view.findComponent(componentId);
			PropertyChangeSupport pcs = scomponent.retrievePropertyChangeSupport();
			pcs.addPropertyChangeListener(new SComponentPropertyChangeListener(bean,mapping.get(componentId)));
			
		}
		
		return proxy;
	}
	
	
	private Map<String, List<String>> createReverseMapping(
			Map<String, String> mapping) {
	
		Map<String, List<String>> reverse = new HashMap<String, List<String>>();
		for(String value : mapping.values()){
			List<String> keys = new LinkedList<String>();
			for(Entry<String, String> entry : mapping.entrySet()){
				if(entry.getValue().equals(value)){
					keys.add(entry.getKey());
				}
			}
			reverse.put(value, keys);
		}
		
		return reverse;
	}

	@SuppressWarnings("unchecked")
	public <T> T unbind(T boundBean, Class<T> iface){
		
		return (T) ((WrapperProxy)boundBean).retrieveOriginal();
		
	}
	
	public void setRadioModel(Map<String, Object> fullRadioModel){
		for(String radioId : fullRadioModel.keySet()){
			Object model = fullRadioModel.get(radioId);
			SRadio radio = (SRadio) view.findComponent(radioId);
			radio.setMode("model");
			radio.setRadioModel(model);
		}
	}
	

}
