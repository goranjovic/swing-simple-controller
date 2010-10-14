package org.goranjovic.scon.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DummyPropertyChangeListener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		System.out.println(event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue());

	}

}
