package org.goranjovic.scon.util;

import java.lang.reflect.Method;

public class BeanUtil {
	
	public static Object getPropertyValue(Object bean, 
			String propertyName, Class<?> propertyType){
		
		try{
			Class<?> clazz = bean.getClass();
			String getterName = getAccessorName(propertyName, "get", propertyType);
			Method getter = clazz.getMethod(getterName, new Class<?>[]{});
			
			return getter.invoke(bean, new Object[]{});
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setPropertyValue(Object bean, String propertyName, Object value){
		
		try{
		
		Class<?> clazz = bean.getClass();
		String setterName = getAccessorName(propertyName, "set", null);
		Method setter = findCompatibleSetter(value, clazz, setterName);
		setter.invoke(bean, value);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static Method findCompatibleSetter(Object value, Class<?> clazz,
			String setterName) throws NoSuchMethodException {
		for(Method m : clazz.getMethods()){
			if(m.getName().equals(setterName)){
					//&& m.getParameterTypes().length == 1
					//&& m.getParameterTypes()[0].isAssignableFrom(value.getClass())){
				return m;
			}
		}
		return null;
	}
	
	public static String getPropertyName(String methodName) {
		String propertyName = methodName.substring(3, 4).toLowerCase()
				+ methodName.substring(4);
		return propertyName;
	}
	
	public static String getAccessorName(String propertyName, 
			String accessorType, Class<?> propertyType){
		
		propertyName = propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
		
		if(accessorType.equals("get") && propertyType.equals(boolean.class)){
			accessorType = "is";
		}
		
		return accessorType + propertyName;
	}
	
	

}
