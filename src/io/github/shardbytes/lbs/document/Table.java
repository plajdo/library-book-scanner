package io.github.shardbytes.lbs.document;

import java.io.File;
import java.text.SimpleDateFormat;

import io.github.shardbytes.lbs.Load;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Table{
	
	private static int row = 2;
	
	public static void createBorrowingsTable(String group, String folder) throws Exception{
		
		Workbook originalWorkbook = Workbook.getWorkbook(new File("zoznam.xls"));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(folder + "output_" + group + ".xls"), originalWorkbook);
		WritableSheet sheet = workbook.getSheet(0);
		
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next Medium"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align = VerticalAlignment.CENTRE;
		Alignment align = Alignment.LEFT;
		
		cellFormat.setAlignment(align);
		cellFormat.setVerticalAlignment(v_align);
		
		Label label = new Label(0, 0, "Evidencia absen\u010Dn\u00FDch v\u00FDpo\u017Ei\u010Diek: " + group, cellFormat);
		sheet.addCell(label);
		
		WritableCellFormat normalCellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align_normal = VerticalAlignment.CENTRE;
		Alignment align_normal = Alignment.CENTRE;
		
		normalCellFormat.setAlignment(align_normal);
		normalCellFormat.setVerticalAlignment(v_align_normal);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		/* TODO
		
		BorrowingsDatabase bd = new BorrowingsDatabase(Load.B_DATABASE_PATH, group);
		
		bd.forEachBorrowing((id, entry) -> {
			Label cellEntryA = new Label(0, getRow(), dateFormat.format(entry.getBorrowDate()), normalCellFormat);
			Label cellEntryB = new Label(1, getRow(), entry.getUsername(), normalCellFormat);
			Label cellEntryC = new Label(2, getRow(), entry.getBookname(), normalCellFormat);
			Label cellEntryD = new Label(3, getRow(), entry.getBookID(), normalCellFormat);
			Label cellEntryE = new Label(4, getRow(), (entry.getReturnDate() == null ? "" : dateFormat.format(entry.getReturnDate())), normalCellFormat);
			
			TermUtils.println("foreaching");
			
			try {
				sheet.addCell(cellEntryA);
				sheet.addCell(cellEntryB);
				sheet.addCell(cellEntryC);
				sheet.addCell(cellEntryD);
				sheet.addCell(cellEntryE);
			} catch (WriteException e) {
				e.printStackTrace();
			}
			increaseRow();
		});
		*/
		resetRow();
		
		workbook.write();
		workbook.close();
		
	}
	
	private static void increaseRow(){
		row++;
	}
	
	private static int getRow(){
		return row;
	}
	
	private static void resetRow(){
		row = 2;
	}
	
	public static void createBooksTable(String folder) throws Exception{
		/*
		 * TODO: Export zoznamu kníh do xls tabu¾ky (ready to print)
		 */
	}
	
	public static void createPersonTable(String folder) throws Exception{
		/*
		 * TODO: Export zoznamu osôb do xls tabu¾ky (ready to print)
		 */
	}
	
}
