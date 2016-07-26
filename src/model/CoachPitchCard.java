package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class CoachPitchCard extends PitchCard {
	
	private final int NUMBER_OF_COLUMNS;
	private final int NUMBER_OF_ROWS;
	
	private static int sheetCount = 0;
	private static final File COACH_EXCEL_FILE = new File("coach.xls");

	private Sheet sheet;
	private static HSSFWorkbook workBook = new HSSFWorkbook(); 
	private List<PitchData> pitchList;
	
	public CoachPitchCard(List<PitchData> pitchList) {
		super(COACH_EXCEL_FILE);
		this.pitchList = pitchList;
		NUMBER_OF_COLUMNS = pitchList.size();
		NUMBER_OF_ROWS = generateNumberOfRows();
		sheet = workBook.createSheet("CoachSheet #" + (sheetCount + 1));
		sheetCount++;
		initializeCellArray();
	}
	
	public int generateNumberOfRows() {
		List<Integer> numberOfPitches = new ArrayList<Integer>();
		
		for (PitchData p: pitchList) {
			List<String> dataSet = p.getDataSet();
			numberOfPitches.add(dataSet.size());
		}
		
		return Collections.max(numberOfPitches) + 2;
	}
	
	public void enterCellValues() {
		array[0][0].setCellValue("#" + sheetCount);
		
		for (int col=0; col<pitchList.size(); col++) {
			array[1][col].setCellValue(pitchList.get(col).getFullName());

			if (array[1][col].getStringCellValue() == "") {
				sheet.setColumnHidden(col, true);
			}
			
			for (int row=2; row<pitchList.get(col).getDataSet().size()+2; row++) {
				array[row][col].setCellValue(pitchList.get(col).getDataSet().get(row-2));
				sheet.setDefaultColumnWidth(7);
			}
		}
	}

	protected void initializeCellArray() {
		array = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		
		for (int row=0; row<NUMBER_OF_ROWS; row++) {
			Row r = sheet.createRow(row);
			for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
				array[row][col] = r.createCell(col);
				array[row][col].setCellValue("");
			}
		}
	}

	@Override
	HSSFWorkbook getWorkBook() {
		return workBook;
	}

	@Override
	public void createPitchCardSheet() {
		enterCellValues();
		formatCells();
		
		HSSFPrintSetup printSetup = (HSSFPrintSetup)sheet.getPrintSetup();
		printSetup.setScale((short)90);
		sheet.setMargin(Sheet.TopMargin, 0.6);
		sheet.setMargin(Sheet.RightMargin, 0.6);
		sheet.setMargin(Sheet.BottomMargin, 0.6);
		sheet.setMargin(Sheet.LeftMargin, 0.6);
	}
	
	public boolean isCellEmpty(final Cell cell) {
	    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
	        return true;
	    }
	    
	    if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty()) {
	        return true;
	    }
	    
	    return false;
	}
	
	private void formatCells() {
		CellStyle cellBorders = workBook.createCellStyle();
		cellBorders.setBorderRight((short)1);
		cellBorders.setBorderLeft((short)1);
		cellBorders.setAlignment(CellStyle.ALIGN_CENTER);
		
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short)12);
		cellBorders.setFont(font);
		
		for (int row=0; row<NUMBER_OF_ROWS; row++) {
			for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
				if (!isCellEmpty(array[row][col])) {
					array[row][col].setCellStyle(cellBorders);
				}
			}
		}
		
		CellStyle topRow = workBook.createCellStyle();
		topRow.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		topRow.setFillPattern(CellStyle.SOLID_FOREGROUND);
		topRow.setBorderTop((short)1);
		topRow.setBorderRight((short)1);
		topRow.setBorderBottom((short)1);
		topRow.setBorderLeft((short)1);
		topRow.setAlignment(CellStyle.ALIGN_CENTER);
		topRow.setWrapText(true);
		topRow.setFont(font);
		
		for (int row=1; row<2; row++) {
			for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
				if (!isCellEmpty(array[row][col])) {
					array[row][col].setCellStyle(topRow);
				}
			}
		}
	}
}
