package org.goranjovic.scon.util;

import java.awt.Component;

import org.goranjovic.guibuilder.components.ValueHolder;

public class ComponentValueManager {
	
public static void setValue(Component component, String type, Object value){
		
		((ValueHolder)component).setValue(value);
		
	}
	
	public static Object getValue(Component component, String type){
		
		return ((ValueHolder)component).getValue();
		
	}


}
