package es.esy.playdotv.document;

import java.io.File;

import es.esy.playdotv.gui.terminal.TermUtils;
import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Table {
	
	public static void createTable() throws Exception{
		
		Workbook originalWorkbook = Workbook.getWorkbook(new File("Zoznam.xls"));
		WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"), originalWorkbook);
		WritableSheet sheet = workbook.createSheet("Zoznam", 0);
		
		Cell A1 = sheet.getCell(0, 0);
		TermUtils.println(A1.toString());
		
		Label label = new Label(0, 0, "TRIEDA: KVINTA");
		sheet.addCell(label);
		
		workbook.write();
		workbook.close();
		
	}
	
}
