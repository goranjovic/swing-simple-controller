package org.goranjovic.scon;

import java.util.Collection;
import java.util.List;

import javax.swing.JComboBox;

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
	
	

}
