package es.esy.playdotv.document;

import java.io.File;

import es.esy.playdotv.gui.terminal.TermUtils;
import jxl.Cell;
import jxl.Workbook;
import jxl.biff.FontRecord;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Table {
	
	public static void createTable() throws Exception{
		
		Workbook originalWorkbook = Workbook.getWorkbook(new File("zoznam.xls"));
		WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"), originalWorkbook);
		WritableSheet sheet = workbook.getSheet(0);
		
		Cell A1 = sheet.getCell(0, 0);
		TermUtils.println(A1.toString());
		
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setFont(new WritableFont(WritableFont.createFont("Avenir Next"), 12, WritableFont.NO_BOLD));
		
		VerticalAlignment v_align = VerticalAlignment.CENTRE;
		Alignment align = Alignment.LEFT;
		
		cellFormat.setAlignment(align);
		cellFormat.setVerticalAlignment(v_align);
		
		Label label = new Label(0, 0, "Evidencia absen\u010Dn\u00FDch v\u00FDpo\u017Ei\u010Diek: Kvinta", cellFormat);
		sheet.addCell(label);
		
		workbook.write();
		workbook.close();
		
	}
	
}
