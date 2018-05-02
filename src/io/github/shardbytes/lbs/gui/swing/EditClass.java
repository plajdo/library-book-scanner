package io.github.shardbytes.lbs.gui.swing;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.objects.Group;
import net.miginfocom.swing.MigLayout;

class EditClass extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	
	private ClassDatabase cdb = ClassDatabase.getInstance();
	private DefaultTableModel tblModel;
	private JTextField textField;

	private int num = 0;
	private TableModelListener tml;

	EditClass(){
		setMaximizable(true);
		setIconifiable(true);
		setResizable(true);
		setTitle("Upravi\u0165 triedy");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[][][grow]"));

		JTable table = new JTable();
		table.setRowSelectionAllowed(false);
		tblModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column){
				return true;
				
			}
			
		};
		table.setModel(tblModel);

		tml = (e) -> {
			for(int i = 0; i < tblModel.getColumnCount(); i++){
				ArrayList<Group> arls = new ArrayList<>();
				for(int j = 0; j < tblModel.getRowCount(); j++){
					arls.add(new Group((String) tblModel.getValueAt(j, i)));

				}
				cdb.getClassList().put(tblModel.getColumnName(i), arls);

			}
			cdb.save();
			refreshTable();

		};
		tblModel.addTableModelListener(tml);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		getContentPane().add(scrollPane, "cell 0 2,grow");
		
		textField = new JTextField();
		getContentPane().add(textField, "flowx,cell 0 0,growx");
		textField.setColumns(10);

		JButton btnNovyRad = new JButton("Nov\u00FD rad");
		btnNovyRad.addActionListener((event) -> {
			if(!textField.getText().isEmpty()){
				cdb.getClassList().put(textField.getText(), new ArrayList<>());
				textField.setText("");
				refreshTable();
				
			}else{
				JOptionPane.showMessageDialog(null, "Nezadali ste \u017Eiadny n\u00E1zov.", "Chyba", JOptionPane.ERROR_MESSAGE);
				
			}
			
		});
		getContentPane().add(btnNovyRad, "cell 0 0");
		
		addStuffToTable();
		
		setVisible(true);

	}
	
	private void addStuffToTable(){
		cdb.getClassList().forEach((columnName, columnData) -> {
			String[] data = new String[columnData.size()];
			columnData.forEach((group) -> {
				data[num] = group.getName();
				num++;
			});
			num = 0;
			
			tblModel.addColumn(columnName, data);
			
		});
		
		ArrayList<Object> arlo = new ArrayList<>();
		try{
			for(int i = 0; i < tblModel.getColumnCount(); i++){
				arlo.add(tblModel.getValueAt(tblModel.getRowCount() - 1, i));

			}

			if(!arlo.stream().noneMatch(Objects::nonNull)){
				tblModel.addRow((Object[])null);
			}
			
		}catch(Exception ignored){
			tblModel.addRow((Object[])null);
		}
		
	}
	
	private void clearStuffFromTable(){
		tblModel.setRowCount(0);
		tblModel.setColumnIdentifiers((Object[])null);
	}
	
	private void refreshTable(){
		tblModel.removeTableModelListener(tml);
		clearStuffFromTable();
		addStuffToTable();
		tblModel.addTableModelListener(tml);
	}

}
