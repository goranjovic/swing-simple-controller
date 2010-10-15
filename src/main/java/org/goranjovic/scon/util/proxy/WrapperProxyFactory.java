package org.goranjovic.scon.util.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

public abstract class WrapperProxyFactory {

	public abstract void adjustProxyClass(CtClass proxyClass) throws Exception;

	public abstract void adjustProxyObject(Object proxy)  throws Exception;

	@SuppressWarnings("unchecked")
	public <T> T createWrapperProxy(Object originalObject,
			Class<T> iface) {

		try {

			String ifaceName = iface.getCanonicalName();

			ClassPool pool = ClassPool.getDefault();

			CtClass ctIface = pool.get(ifaceName);
			CtClass ctBaseType = pool.get(Object.class.getCanonicalName());

			CtClass proxyCc = pool.makeClass(ifaceName + "Proxy");
			proxyCc.addInterface(ctIface);
			
			CtClass wrapperProxyIface = pool.get(WrapperProxy.class.getCanonicalName());
			proxyCc.addInterface(wrapperProxyIface);
			
			CtField origField = new CtField(ctIface, "original",
					proxyCc);
			proxyCc.addField(origField);

			CtClass[] parameters = new CtClass[] { ctBaseType };
			CtClass[] exceptions = new CtClass[] {};

			CtMethod putOriginalMethod = CtNewMethod.make(CtClass.voidType,
					"putOriginal", parameters, exceptions, "$0.original=$1;",
					proxyCc);
			proxyCc.addMethod(putOriginalMethod);
			
			CtMethod retrieveOriginalMethod = CtNewMethod.make(ctBaseType,
					"retrieveOriginal", new CtClass[] {}, exceptions, "return $0.original;",
					proxyCc);
			proxyCc.addMethod(retrieveOriginalMethod);

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

			((WrapperProxy)proxyObject).putOriginal(originalObject); 
			
			adjustProxyObject(proxyObject);
			return (T)proxyObject;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
