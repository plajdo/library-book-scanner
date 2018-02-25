package io.github.shardbytes.lbs.gui.swing;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Date d = new Date();
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
		
		try{
			d = (Date)value;
			if(d == null){
				throw new NullPointerException();
			}
		}catch(Exception e){
			l.setText("Nie");
			l.setBackground(table.getBackground());
			return l;
		}
		
		if(now.compareTo(d) < 0){
			l.setBackground(table.getBackground());
			l.setText("Do " + sdf.format(d));
		}else if(now.compareTo(d) >= 0){
			l.setBackground(Color.RED);
			l.setText("Do " + sdf.format(d));
		}
		
		return l; 
	}

}
