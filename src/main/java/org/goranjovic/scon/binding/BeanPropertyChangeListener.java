package org.goranjovic.scon.binding;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import org.goranjovic.guibuilder.core.ElementDescription;
import org.goranjovic.guibuilder.core.SwingView;
import org.goranjovic.scon.util.ComponentValueManager;

public class BeanPropertyChangeListener implements PropertyChangeListener {
	
	private SwingView view;
	
	private Map<String, List<String>> reverseMapping;

	public BeanPropertyChangeListener(SwingView view, 
			Map<String, List<String>> reverseMapping) {
		this.view = view;
		this.reverseMapping = reverseMapping;
	}
	
	

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println(event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue());
		Object value = event.getNewValue();
		List<String> componentIds = reverseMapping.get(event.getPropertyName());
		for(String componentId : componentIds){
			ElementDescription description = view.findElementDescription(componentId);
			Component component = description.getComponent();
			ComponentValueManager.setValue(component, description.getTagName(), value);
		}
		//

	}

}
