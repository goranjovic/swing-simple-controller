package org.goranjovic.scon.util;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ComponentValueManager {
	
public static void setValue(Component component, String type, Object value){
		
		if (type.equalsIgnoreCase("button")) {
		}
		else if (type.equalsIgnoreCase("panel")){
		}
		else if (type.equalsIgnoreCase("radio")){
			((JRadioButton)component).setSelected((Boolean) value);
		}
		else if (type.equalsIgnoreCase("checkbox")){
			((JCheckBox)component).setSelected((Boolean) value);
		}
		else if(type.equalsIgnoreCase("group")){
			//to-do
		}
		else if (type.equalsIgnoreCase("label")){
		}
		else if(type.equalsIgnoreCase("textfield")){
			((JTextField)component).setText(value.toString());
		}
		else if(type.equalsIgnoreCase("textarea")){
			((JTextArea)component).setText(value.toString());
		}
		else if(type.equalsIgnoreCase("passwordfield")){
			//what to do with this?
		}
		else if(type.equalsIgnoreCase("list")){
			//to-do
		}
		else if(type.equalsIgnoreCase("combo")){
			((JComboBox)component).setSelectedItem(value);
		}
		else if(type.equalsIgnoreCase("table")){
			//to-do
		}
		else if(type.equalsIgnoreCase("form")){
		}
		else if(type.equalsIgnoreCase("menu")){
		}
		else if(type.equalsIgnoreCase("menu-item")){
		}
		else if(type.equalsIgnoreCase("image")){
			//to-do
		}
		else if(type.equalsIgnoreCase("icon")){
			//to-do
		}
		else if(type.equalsIgnoreCase("tab")){
		}else if(type.equalsIgnoreCase("table-column")){
			//to-do			
		}
		
	}
	
	public static Object getValue(Component component, String type){
		if (type.equalsIgnoreCase("button")) {
			JButton button = ((JButton)component);
			return button.getText();
		}
		else if (type.equalsIgnoreCase("panel")){
		}
		else if (type.equalsIgnoreCase("radio")){
			return ((JRadioButton)component).isSelected();
		}
		else if (type.equalsIgnoreCase("checkbox")){
			return ((JCheckBox)component).isSelected();
		}
		else if(type.equalsIgnoreCase("group")){
			//to-do
			return null;
		}
		else if (type.equalsIgnoreCase("label")){
			return ((JLabel)component).getText();
		}
		else if(type.equalsIgnoreCase("textfield")){
			return ((JTextField)component).getText();
		}
		else if(type.equalsIgnoreCase("textarea")){
			return ((JTextArea)component).getText();
		}
		else if(type.equalsIgnoreCase("passwordfield")){
			return ((JPasswordField)component).getPassword();
		}
		else if(type.equalsIgnoreCase("list")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("combo")){
			return ((JComboBox)component).getSelectedItem();
		}
		else if(type.equalsIgnoreCase("table")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("form")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("menu")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("menu-item")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("icon")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("tab")){
			//to-do
			return null;
		}
		else if(type.equalsIgnoreCase("table-column")){
			//to-do
			return null;
		}
		return null;
	}


}
