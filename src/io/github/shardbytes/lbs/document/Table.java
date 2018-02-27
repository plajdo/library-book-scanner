package io.github.shardbytes.lbs.document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.shardbytes.lbs.database.BorrowDatabase;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@SuppressWarnings("deprecation")
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
		
		cellFormat.setBorder(Border.NONE, BorderLineStyle.NONE, Colour.WHITE);
		
		Label label = new Label(0, 0, "Evidencia absen\u010Dn\u00FDch v\u00FDpo\u017Ei\u010Diek: " + group, cellFormat);
		sheet.addCell(label);
		
		WritableCellFormat normalCellFormat = new WritableCellFormat();
		normalCellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next"), 10, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align_normal = VerticalAlignment.CENTRE;
		Alignment align_normal = Alignment.CENTRE;
		
		normalCellFormat.setAlignment(align_normal);
		normalCellFormat.setVerticalAlignment(v_align_normal);
		
		normalCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GREY_40_PERCENT);
		
		WritableCellFormat greenCellFormat = new WritableCellFormat();
		greenCellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next"), 10, WritableFont.NO_BOLD));
		
		greenCellFormat.setAlignment(align_normal);
		greenCellFormat.setVerticalAlignment(v_align_normal);
		
		greenCellFormat.setBackground(Colour.GREY_25_PERCENT);
		greenCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GREY_40_PERCENT);
		
		WritableCellFormat thiccCellFormat = new WritableCellFormat();
		thiccCellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next Demi Bold"), 10, WritableFont.BOLD));
		
		thiccCellFormat.setAlignment(align_normal);
		thiccCellFormat.setVerticalAlignment(v_align_normal);
		
		thiccCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GREY_40_PERCENT);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		BorrowDatabase bd = BorrowDatabase.getInstance();
		
		TermUtils.println("Exporting the database");
		
		Label tableLabelA = new Label(0, 1, "D\u00E1tum v\u00FDpo\u017Ei\u010Dky", thiccCellFormat);
		Label tableLabelB = new Label(1, 1, "Meno, priezvisko a ID pou\u017E\u00EDvate\u013Ea", thiccCellFormat);
		Label tableLabelC = new Label(2, 1, "N\u00E1zov kni\u017Eni\u010Dnej jednotky", thiccCellFormat);
		Label tableLabelD = new Label(3, 1, "ID kni\u017Eni\u010Dnej jednotky", thiccCellFormat);
		Label tableLabelE = new Label(4, 1, "D\u00E1tum vr\u00E1tenia", thiccCellFormat);
		
		sheet.addCell(tableLabelA);
		sheet.addCell(tableLabelB);
		sheet.addCell(tableLabelC);
		sheet.addCell(tableLabelD);
		sheet.addCell(tableLabelE);
		
		bd.borrowings.get(group).forEach((id, entry) -> {
			Label cellEntryA;
			Label cellEntryB;
			Label cellEntryC;
			Label cellEntryD;
			Label cellEntryE;
			if((row % 2) == 1){
				cellEntryA = new Label(0, getRow(), dateFormat.format(new Date(entry.getBorrowDate())), greenCellFormat);
				cellEntryB = new Label(1, getRow(), entry.getBorrowerCompleteName(), greenCellFormat);
				cellEntryC = new Label(2, getRow(), entry.getBookName(), greenCellFormat);
				cellEntryD = new Label(3, getRow(), entry.getBookID(), greenCellFormat);
				cellEntryE = new Label(4, getRow(), (entry.getReturnDate() == 0 ? "" : dateFormat.format(new Date(entry.getReturnDate()))), greenCellFormat);
			}else{
				cellEntryA = new Label(0, getRow(), dateFormat.format(new Date(entry.getBorrowDate())), normalCellFormat);
				cellEntryB = new Label(1, getRow(), entry.getBorrowerCompleteName(), normalCellFormat);
				cellEntryC = new Label(2, getRow(), entry.getBookName(), normalCellFormat);
				cellEntryD = new Label(3, getRow(), entry.getBookID(), normalCellFormat);
				cellEntryE = new Label(4, getRow(), (entry.getReturnDate() == 0 ? "" : dateFormat.format(new Date(entry.getReturnDate()))), normalCellFormat);				
			}
			
			try{
				sheet.addCell(cellEntryA);
				sheet.addCell(cellEntryB);
				sheet.addCell(cellEntryC);
				sheet.addCell(cellEntryD);
				sheet.addCell(cellEntryE);
			}catch(WriteException e){
				e.printStackTrace();
			}
			increaseRow();
		});
		
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
		 * TODO: Export zoznamu kn�h do xls tabu�ky (ready to print)
		 */
	}
	
	public static void createPersonTable(String folder) throws Exception{
		/*
		 * TODO: Export zoznamu os�b do xls tabu�ky (ready to print)
		 */
	}
	
}
