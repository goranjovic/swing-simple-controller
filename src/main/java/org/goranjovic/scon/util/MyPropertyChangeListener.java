package org.goranjovic.scon.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyPropertyChangeListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		System.out.println(event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue());

	}

}
