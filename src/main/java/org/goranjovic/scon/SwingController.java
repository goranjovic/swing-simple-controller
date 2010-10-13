package org.goranjovic.scon;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JList;

import org.goranjovic.guibuilder.core.SwingView;

public class SwingController {
	
	private SwingView view;
	
	public SwingView getView() {
		return view;
	}

	public void setView(SwingView view) {
		this.view = view;
	}

	public void fillCombo(String comboId, Collection<?> data, boolean append){
		JComboBox combo = (JComboBox) getView().findComponent(comboId);
		if(!append){
			combo.removeAllItems();
		}
		for(Object item : data){
			combo.addItem(item);
		}
	}
	
	public void bind(Object bean, Map<String, String> mapping){
		
	}
	
	

}
