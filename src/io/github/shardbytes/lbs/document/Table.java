package io.github.shardbytes.lbs.document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.shardbytes.lbs.database.BorrowDatabase;
import io.github.shardbytes.lbs.database.LBSDatabase;
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
		
		Label label = new Label(0, 0, "EVIDENCIA ABSEN\u010CN\u00DDCH V\u00DDPO\u017DI\u010CIEK: " + group.toUpperCase(), cellFormat);
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
		
		TermUtils.println("Exporting borrow entries database");
		
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
		Workbook originalWorkbook = Workbook.getWorkbook(new File("books.xls"));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(folder + "output_Books.xls"), originalWorkbook);
		WritableSheet sheet = workbook.getSheet(0);
		
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next Medium"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align = VerticalAlignment.CENTRE;
		Alignment align = Alignment.LEFT;
		
		cellFormat.setAlignment(align);
		cellFormat.setVerticalAlignment(v_align);
		
		cellFormat.setBorder(Border.NONE, BorderLineStyle.NONE, Colour.WHITE);
		
		Label label = new Label(0, 0, "ZOZNAM V\u0160ETK\u00DDCH KN\u00CDH", cellFormat);
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

		LBSDatabase db = LBSDatabase.getInstance();
		
		TermUtils.println("Exporting book database");
		
		Label tableLabelA = new Label(0, 1, "Pr\u00EDrastkov\u00E9 \u010D\u00EDslo", thiccCellFormat);
		Label tableLabelB = new Label(1, 1, "Signat\u00FAra", thiccCellFormat);
		Label tableLabelC = new Label(2, 1, "Autor knihy", thiccCellFormat);
		Label tableLabelD = new Label(3, 1, "N\u00E1zov knihy", thiccCellFormat);
		Label tableLabelE = new Label(4, 1, "Vypo\u017Ei\u010Dan\u00E1?", thiccCellFormat);
		
		sheet.addCell(tableLabelA);
		sheet.addCell(tableLabelB);
		sheet.addCell(tableLabelC);
		sheet.addCell(tableLabelD);
		sheet.addCell(tableLabelE);
		
		db.books.forEach((id, book) -> {
			Label cellEntryA;
			Label cellEntryB;
			Label cellEntryC;
			Label cellEntryD;
			Label cellEntryE;
			if((row % 2) == 1){
				cellEntryA = new Label(0, getRow(), book.getID().split("/")[0], greenCellFormat);
				cellEntryB = new Label(1, getRow(), book.getID().split("/")[1], greenCellFormat);
				cellEntryC = new Label(2, getRow(), book.getAuthor(), greenCellFormat);
				cellEntryD = new Label(3, getRow(), book.getName(), greenCellFormat);
				cellEntryE = new Label(4, getRow(), book.getTakerID().isEmpty() ? "Nie" : "Do " + dateFormat.format(new Date(book.getBorrowedUntilTime())), greenCellFormat);
			}else{
				cellEntryA = new Label(0, getRow(), book.getID().split("/")[0], normalCellFormat);
				cellEntryB = new Label(1, getRow(), book.getID().split("/")[1], normalCellFormat);
				cellEntryC = new Label(2, getRow(), book.getAuthor(), normalCellFormat);
				cellEntryD = new Label(3, getRow(), book.getName(), normalCellFormat);
				cellEntryE = new Label(4, getRow(), book.getTakerID().isEmpty() ? "Nie" : "Do " + dateFormat.format(new Date(book.getBorrowedUntilTime())), normalCellFormat);				
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
	
	public static void createPersonTable(String folder) throws Exception{
		Workbook originalWorkbook = Workbook.getWorkbook(new File("persons.xls"));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(folder + "output_People.xls"), originalWorkbook);
		WritableSheet sheet = workbook.getSheet(0);
		
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next Medium"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align = VerticalAlignment.CENTRE;
		Alignment align = Alignment.LEFT;
		
		cellFormat.setAlignment(align);
		cellFormat.setVerticalAlignment(v_align);
		
		cellFormat.setBorder(Border.NONE, BorderLineStyle.NONE, Colour.WHITE);
		
		Label label = new Label(0, 0, "ZOZNAM POU\u017D\u00CDVATE\u013DOV KNI\u017DNICE", cellFormat);
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

		LBSDatabase db = LBSDatabase.getInstance();
		
		TermUtils.println("Exporting person database");
		
		Label tableLabelA = new Label(0, 1, "ID pou\u017E\u00EDvate\u013Ea", thiccCellFormat);
		Label tableLabelB = new Label(1, 1, "Meno a priezvisko pou\u017E\u00EDvate\u013Ea", thiccCellFormat);
		Label tableLabelC = new Label(2, 1, "Trieda pou\u017E\u00EDvate\u013Ea", thiccCellFormat);
		Label tableLabelD = new Label(3, 1, "Po\u010Det vypo\u017Ei\u010Dan\u00FDch kn\u00EDh pou\u017E\u00EDvate\u013Ea", thiccCellFormat);
		
		sheet.addCell(tableLabelA);
		sheet.addCell(tableLabelB);
		sheet.addCell(tableLabelC);
		sheet.addCell(tableLabelD);
		
		db.persons.forEach((id, person) -> {
			Label cellEntryA;
			Label cellEntryB;
			Label cellEntryC;
			Label cellEntryD;
			if((row % 2) == 1){
				cellEntryA = new Label(0, getRow(), person.getID(), greenCellFormat);
				cellEntryB = new Label(1, getRow(), person.getName(), greenCellFormat);
				cellEntryC = new Label(2, getRow(), person.getGroup(), greenCellFormat);
				cellEntryD = new Label(3, getRow(), person.getBookCount(), greenCellFormat);
			}else{
				cellEntryA = new Label(0, getRow(), person.getID(), normalCellFormat);
				cellEntryB = new Label(1, getRow(), person.getName(), normalCellFormat);
				cellEntryC = new Label(2, getRow(), person.getGroup(), normalCellFormat);
				cellEntryD = new Label(3, getRow(), person.getBookCount(), normalCellFormat);				
			}
			
			try{
				sheet.addCell(cellEntryA);
				sheet.addCell(cellEntryB);
				sheet.addCell(cellEntryC);
				sheet.addCell(cellEntryD);
			}catch(WriteException e){
				e.printStackTrace();
			}
			increaseRow();
		});
		
		resetRow();
		
		workbook.write();
		workbook.close();
		
	}
	
}
