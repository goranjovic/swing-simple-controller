package org.goranjovic.scon;

import java.util.Collection;
import java.util.Map;

import javax.swing.JComboBox;

import org.goranjovic.guibuilder.core.SwingView;
import org.goranjovic.scon.binding.DummyPropertyChangeListener;
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
		
		BoundBeanWrapperProxyFactory beanProxyFactory = new BoundBeanWrapperProxyFactory();
		beanProxyFactory.setPropertyChangeListener(new DummyPropertyChangeListener());
		beanProxyFactory.setBoundProperties(boundProperties);
		
		T proxy = beanProxyFactory.createWrapperProxy(bean, iface);
		
		for(String componentId : mapping.keySet()){
			
			String propertyName = mapping.get(componentId);
			
		}
		
		return proxy;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T unbind(T boundBean, Class<T> iface){
		
		return (T) ((WrapperProxy)boundBean).retrieveOriginal();
		
	}
	

}
