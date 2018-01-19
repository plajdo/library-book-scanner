package es.esy.playdotv.document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import es.esy.playdotv.gui.terminal.TermUtils;
import jxl.Cell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Table {
	
	public static void createTable(List<BorrowingEntry> entries, String group) throws Exception{
		
		Workbook originalWorkbook = Workbook.getWorkbook(new File("zoznam.xls"));
		WritableWorkbook workbook = Workbook.createWorkbook(new File("output_" + group + ".xls"), originalWorkbook);
		WritableSheet sheet = workbook.getSheet(0);
		
		Cell A1 = sheet.getCell(0, 0);
		TermUtils.println(A1.toString());
		
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next Medium"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align = VerticalAlignment.CENTRE;
		Alignment align = Alignment.LEFT;
		
		cellFormat.setAlignment(align);
		cellFormat.setVerticalAlignment(v_align);
		
		Label label = new Label(0, 0, "Evidencia absen\u010Dn\u00FDch v\u00FDpo\u017Ei\u010Diek: " + group, cellFormat);
		sheet.addCell(label);
		
		int cellRow = 2;
		
		WritableCellFormat normalCellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align_normal = VerticalAlignment.CENTRE;
		Alignment align_normal = Alignment.CENTRE;
		
		normalCellFormat.setAlignment(align_normal);
		normalCellFormat.setVerticalAlignment(v_align_normal);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		for(BorrowingEntry entry : entries){
			Label cellEntryA = new Label(0, cellRow, dateFormat.format(entry.getBorrowDate()));
			Label cellEntryB = new Label(1, cellRow, entry.getUsername());
			Label cellEntryC = new Label(2, cellRow, entry.getBookname());
			Label cellEntryD = new Label(3, cellRow, entry.getBookID());
			Label cellEntryE = new Label(4, cellRow, (entry.getReturnDate() == null ? "" : dateFormat.format(entry.getReturnDate())));
			sheet.addCell(cellEntryA);
			sheet.addCell(cellEntryB);
			sheet.addCell(cellEntryC);
			sheet.addCell(cellEntryD);
			sheet.addCell(cellEntryE);
			cellRow++;
		}
		
		workbook.write();
		workbook.close();
		
	}
	
}
