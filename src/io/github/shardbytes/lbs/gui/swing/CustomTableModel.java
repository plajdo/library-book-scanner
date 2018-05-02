package io.github.shardbytes.lbs.gui.swing;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class CustomTableModel extends DefaultTableModel{
	
	public void removeColumn(int column){
		for(Object o : dataVector){
			((Vector)o).remove(column);
		}
		columnIdentifiers.remove(column);
		
		fireTableStructureChanged();
		
	}
	
}
