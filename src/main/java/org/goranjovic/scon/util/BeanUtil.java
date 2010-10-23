package org.goranjovic.scon.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtil {
	
	public static Object getPropertyValue(Object bean, 
			String propertyName){
		
		try{			
			return PropertyUtils.getSimpleProperty(bean, propertyName);
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
		
		Class<?> propertyType = setter.getParameterTypes()[0];
		Class<?> valueType = value.getClass();
		if(!propertyType.equals(valueType) && valueType.equals(String.class)){
			//raw text received, parsing needed
			value = parseValue((String)value, propertyType);
		}
		
		setter.invoke(bean, value);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
@SuppressWarnings("unchecked")
public static <T> T parseValue(String value, Class<T> propertyType) {
		
		if(propertyType.equals(int.class) || propertyType.equals(Integer.class)){
			return (T) new Integer(value);
		}
		
		if(propertyType.equals(long.class) || propertyType.equals(Long.class)){
			return (T) new Long(value);
		}
		
		if(propertyType.equals(short.class) || propertyType.equals(Short.class)){
			return (T) new Short(value);
		}
		
		if(propertyType.equals(byte.class) || propertyType.equals(Byte.class)){
			return (T) new Byte(value);
		}
		
		if(propertyType.equals(char.class) || propertyType.equals(Character.class)){
			return (T) new Character(value.charAt(0));
		}
		
		if(propertyType.equals(boolean.class) || propertyType.equals(Boolean.class)){
			return (T) Boolean.valueOf(value);
		}
		
		if(propertyType.equals(float.class) || propertyType.equals(Float.class)){
			return (T) new Float(value);
		}
		
		if(propertyType.equals(double.class) || propertyType.equals(Double.class)){
			return (T) new Double(value);
		}
		
		if(propertyType.isArray()){
			//implement later..
		}
		
		return null;
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
