package org.goranjovic.scon.binding.listeners;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import org.goranjovic.guibuilder.components.STable;
import org.goranjovic.guibuilder.components.STableColumn;
import org.goranjovic.guibuilder.components.support.VariableTableModel;
import org.goranjovic.guibuilder.core.ElementDescription;
import org.goranjovic.guibuilder.core.SwingView;
import org.goranjovic.scon.util.ComponentValueManager;
import org.goranjovic.scon.util.proxy.WrapperProxy;

public class CollectionItemPropertyChangeListener implements PropertyChangeListener {
	
	private SwingView view;
	
	private Map<String, List<String>> reverseMapping;
	
	private List<?> collection;
	
	private STable table;

	public CollectionItemPropertyChangeListener(SwingView view, 
			Map<String, List<String>> reverseMapping, List<?> collection, STable table) {
		this.view = view;
		this.reverseMapping = reverseMapping;
		this.collection = collection;
		this.table = table;
	}
	
	

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		Object bean = event.getSource();
		
		Object original = ((WrapperProxy)bean).retrieveOriginal();
		
		int index = collection.indexOf(original);
		
		System.out.println(bean.getClass().getSimpleName() + "[" + index + "]."+event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue());
		Object value = event.getNewValue();
		List<String> columnIds = reverseMapping.get(event.getPropertyName());
		
		VariableTableModel model = (VariableTableModel) table.getModel();
		
		int difference = collection.size() - model.getRowCount();
		
		if(difference > 0){
			model.addEmptyRows(difference);
		}
		
		for(String columnId : columnIds){
			model.setValueAt(value, index, columnId);
		}

	}

}
