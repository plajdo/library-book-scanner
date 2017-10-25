package es.esy.playdotv.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;
	public int t = 0;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if(!table.isRowSelected(row)){
			if(column == 3 && t == 1)
				c.setBackground(Color.RED);
			else
				c.setBackground(table.getBackground());
		}
		return c; 
	}
	
}
