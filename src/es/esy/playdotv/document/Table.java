package es.esy.playdotv.document;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Table {
	
	public static void createTable() throws Exception{
		
		WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"));
		WritableSheet sheet = workbook.createSheet("Zoznam", 0);
		
		Label label = new Label(0, 0, "TRIEDA: KVINTA");
		sheet.addCell(label);
		
	}
	
}
