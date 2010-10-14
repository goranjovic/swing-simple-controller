package org.goranjovic.scon.binding.proxy;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.goranjovic.scon.binding.DummyPropertyChangeListener;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class BoundBeanWrapperProxyFactory {

	public void adjustProxyClass(CtClass proxyClass) throws NotFoundException,
			CannotCompileException, InstantiationException,
			IllegalAccessException {

		ClassPool pool = ClassPool.getDefault();

		CtClass pcs = pool.get("java.beans.PropertyChangeSupport");

		CtField changes = new CtField(pcs, "changes", proxyClass);
		proxyClass.addField(changes,
				" new java.beans.PropertyChangeSupport(this)");

		CtMethod pcsGetter = CtNewMethod.getter("getPropertyChangeSupport", changes);
		proxyClass.addMethod(pcsGetter);
		
		CtClass boundBeanWrapperProxyIface = pool.get(BoundBeanWrapperProxy.class.getCanonicalName());
		proxyClass.addInterface(boundBeanWrapperProxyIface);

		List<CtMethod> setters = getAllSetters(proxyClass);

		for (CtMethod setter : setters) {
			alterSetter(setter);
		}
	}

	public void adjustProxyObject(Object proxy) {
		try {
			PropertyChangeSupport pcs = ((BoundBeanWrapperProxy)proxy).getPropertyChangeSupport();
			pcs.addPropertyChangeListener(new DummyPropertyChangeListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void alterSetter(CtMethod setter) throws CannotCompileException,
			NotFoundException {
		String property = getPropertyName(setter);
		setter.addLocalVariable("oldValue", setter.getParameterTypes()[0]);
		setter.insertBefore("oldValue=this." + getGetterName(setter) + ";");
		setter.insertAfter("changes.firePropertyChange(\"" + property
				+ "\", oldValue, $1);");
	}

	private String getPropertyName(CtMethod setter) {

		String methodName = setter.getName();
		String propertyName = methodName.substring(3, 4).toLowerCase()
				+ methodName.substring(4);

		return propertyName;
	}

	private String getGetterName(CtMethod setter) throws NotFoundException {
		String prefix = "get";
		if (setter.getParameterTypes()[0].equals(CtClass.booleanType)) {
			prefix = "is";
		}
		return prefix + setter.getName().substring(3) + "()";
	}

	private List<CtMethod> getAllSetters(CtClass ct) {

		List<CtMethod> setters = new ArrayList<CtMethod>();
		for (CtMethod method : ct.getMethods()) {
			if (method.getName().startsWith("set")) {
				setters.add(method);
			}
		}
		return setters;
	}

}
