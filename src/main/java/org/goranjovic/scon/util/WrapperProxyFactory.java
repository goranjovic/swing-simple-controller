package org.goranjovic.scon.util;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

public abstract class WrapperProxyFactory {

	public abstract void adjustProxyClass(CtClass proxyClass);

	public abstract void adjustProxyObject(Object proxy);

	@SuppressWarnings("unchecked")
	public <T> T createWrapperProxy(Object originalObject,
			Class<T> iface) {

		try {

			Class<? extends Object> originalClass = originalObject.getClass();
			String originalClassName = originalClass.getCanonicalName();
			String ifaceName = iface.getCanonicalName();

			ClassPool pool = ClassPool.getDefault();

			CtClass ctOriginalClass = pool.get(originalClassName);
			CtClass ctIface = pool.get(ifaceName);

			CtClass proxyCc = pool.makeClass(originalClassName + "Proxy");
			proxyCc.addInterface(ctIface);

			CtField origField = new CtField(ctOriginalClass, "original",
					proxyCc);
			proxyCc.addField(origField);

			CtClass[] parameters = new CtClass[] { ctOriginalClass };
			CtClass[] exceptions = new CtClass[] {};

			CtMethod putOriginalMethod = CtNewMethod.make(CtClass.voidType,
					"putOriginal", parameters, exceptions, "$0.original=$1;",
					proxyCc);
			proxyCc.addMethod(putOriginalMethod);

			CtConstructor defaultConstructor = CtNewConstructor
					.defaultConstructor(proxyCc);
			proxyCc.addConstructor(defaultConstructor);

			for (CtMethod operation : ctIface.getDeclaredMethods()) {
				String delegatingCode = "";
				if (!operation.getReturnType().equals(CtClass.voidType)) {
					delegatingCode += "return ";
				}
				delegatingCode += " original." + operation.getName() + "($$);";
				CtMethod delegate = CtNewMethod.make(operation.getReturnType(),
						operation.getName(), operation.getParameterTypes(),
						operation.getExceptionTypes(), delegatingCode, proxyCc);
				proxyCc.addMethod(delegate);
			}

			adjustProxyClass(proxyCc);

			Class<?> proxyClass = proxyCc.toClass();
			Object proxyObject = proxyClass.newInstance();

			Method actualPutMethod = proxyClass.getMethod("putOriginal", originalClass);
			actualPutMethod.invoke(proxyObject, originalObject);
			adjustProxyObject(proxyObject);
			return (T)proxyObject;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
