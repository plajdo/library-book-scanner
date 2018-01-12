package es.esy.playdotv.document;

import java.io.File;

import es.esy.playdotv.gui.terminal.TermUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Table {
	
	public static void createTable() throws Exception{
		
		Workbook workbook = Workbook.getWorkbook(new File("wb.xls"));
		Sheet sheet = workbook.getSheet(0);
		
		Cell A2 = sheet.getCell(0, 1);
		Cell B8 = sheet.getCell(1, 7);
		
		TermUtils.println(A2.getContents());
		TermUtils.println(B8.getContents());
		
		workbook.close();
		
	}
	
}
