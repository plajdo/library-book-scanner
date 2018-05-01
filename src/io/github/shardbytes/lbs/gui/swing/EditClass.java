package io.github.shardbytes.lbs.gui.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Group;
import net.miginfocom.swing.MigLayout;

public class EditClass extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	
	private ClassDatabase cdb = ClassDatabase.getInstance();
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tblModel;
	private JTextField textField;
	private JButton btnNovyRad;
	
	private int num = 0;
	
	public EditClass() {
		setMaximizable(true);
		setIconifiable(true);
		setResizable(true);
		setTitle("Upravi\u0165 triedy");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[][][grow]"));
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		getContentPane().add(scrollPane, "cell 0 2,grow");
		
		textField = new JTextField();
		getContentPane().add(textField, "flowx,cell 0 0,growx");
		textField.setColumns(10);
		
		btnNovyRad = new JButton("Nov\u00FD rad");
		btnNovyRad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().isEmpty()){
					cdb.getClassList().put(textField.getText(), new ArrayList<Group>());
					textField.setText("");
					refreshTable();
					
				}else{
					JOptionPane.showMessageDialog(null, "Nezadali ste \u017Eiadny n\u00E1zov.", "Chyba", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
			
		});
		getContentPane().add(btnNovyRad, "cell 0 0");
		
		addStuffToTable();
		
		setVisible(true);

	}
	
	private void addStuffToTable(){
		String[] groupList = new String[cdb.getClassList().size()];
		cdb.getClassList().keySet().toArray(groupList);
		
		tblModel = new DefaultTableModel(null, (String[])null){
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column){
				return true;
			}
		};
		table.setModel(tblModel);
		
		cdb.getClassList().forEach((columnName, columnData) -> {
			String[] data = new String[columnData.size()];
			columnData.forEach((group) -> {
				data[num] = group.getName();
				num++;
			});
			num = 0;
			
			tblModel.addColumn(columnName, data);
			
		});
		
		tblModel.addRow(new Object[]{
				"dsadsa"
		});
		
		tblModel.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e){
				tblModel.get
				
			}
			
		});
		
	}
	
	private void clearStuffFromTable(){
		tblModel.getDataVector().removeAllElements();
		tblModel.fireTableDataChanged();
	}
	
	private void refreshTable(){
		clearStuffFromTable();
		addStuffToTable();
	}

}
