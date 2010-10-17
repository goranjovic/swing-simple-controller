package org.goranjovic.scon.binding.proxy;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.goranjovic.scon.binding.listeners.DummyPropertyChangeListener;
import org.goranjovic.scon.util.BeanUtil;
import org.goranjovic.scon.util.proxy.WrapperProxyFactory;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class BoundBeanWrapperProxyFactory extends WrapperProxyFactory {
	
	private PropertyChangeListener propertyChangeListener;

	public PropertyChangeListener getPropertyChangeListener() {
		return propertyChangeListener;
	}

	public void setPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		this.propertyChangeListener = propertyChangeListener;
	}
	
	private Collection<String> boundProperties;

	public Collection<String> getBoundProperties() {
		return boundProperties;
	}

	public void setBoundProperties(Collection<String> boundProperties) {
		this.boundProperties = boundProperties;
	}

	public void adjustProxyClass(CtClass proxyClass) throws Exception {

		ClassPool pool = ClassPool.getDefault();

		CtClass pcs = pool.get("java.beans.PropertyChangeSupport");

		CtField changes = new CtField(pcs, "changes", proxyClass);
		proxyClass.addField(changes,
				" new java.beans.PropertyChangeSupport(this)");

		CtMethod pcsGetter = CtNewMethod.getter("getPropertyChangeSupport", changes);
		proxyClass.addMethod(pcsGetter);
		
		CtClass boundBeanWrapperProxyIface = pool.get(BoundBeanWrapperProxy.class.getCanonicalName());
		proxyClass.addInterface(boundBeanWrapperProxyIface);

		List<CtMethod> setters = getSetters(proxyClass);

		for (CtMethod setter : setters) {
			alterSetter(setter);
		}
	}

	public void adjustProxyObject(Object proxy) {
		try {
			PropertyChangeSupport pcs = ((BoundBeanWrapperProxy)proxy).getPropertyChangeSupport();
			pcs.addPropertyChangeListener(getPropertyChangeListener());
			
			//must fire events for all bound properties
			//for initial syncing
			for(String boundProperty : boundProperties){
				Object value = BeanUtil.getPropertyValue(proxy, boundProperty);
				if(value != null){
					pcs.firePropertyChange(boundProperty, null, value);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void alterSetter(CtMethod setter) throws Exception {
		String property = getPropertyName(setter);
		setter.addLocalVariable("oldValue", setter.getParameterTypes()[0]);
		setter.insertBefore("oldValue=this." + getGetterName(setter) + ";");
		setter.insertAfter("changes.firePropertyChange(\"" + property
				+ "\", oldValue, $1);");
	}

	private String getPropertyName(CtMethod setter) {

		String methodName = setter.getName();
		String propertyName = BeanUtil.getPropertyName(methodName);

		return propertyName;
	}


	private String getGetterName(CtMethod setter) throws NotFoundException {
		String prefix = "get";
		if (setter.getParameterTypes()[0].equals(CtClass.booleanType)) {
			prefix = "is";
		}
		return prefix + setter.getName().substring(3) + "()";
	}

	private List<CtMethod> getSetters(CtClass ct) {

		List<CtMethod> setters = new ArrayList<CtMethod>();
		for (CtMethod method : ct.getMethods()) {
			String methodName = method.getName();
			String propertyName = BeanUtil.getPropertyName(methodName);
			if (methodName.startsWith("set") && boundProperties.contains(propertyName)) {
				setters.add(method);
			}
		}
		return setters;
	}

}
