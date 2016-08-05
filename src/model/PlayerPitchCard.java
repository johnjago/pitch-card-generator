package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javafx.scene.text.Font;

public class PlayerPitchCard extends PitchCard {
	
	private static final int NUMBER_OF_ROWS = 13;
	private static final int  NUMBER_OF_COLUMNS = 16;
	
	private static int sheetCount = 0;
	private static final File PLAYER_EXCEL_FILE = new File("player.xls");

	private Sheet sheet;
	private static HSSFWorkbook workBook = new HSSFWorkbook(); 
	private List<PitchData> pitchList;
	
	public PlayerPitchCard(List<PitchData> pitchList) {
		super(PLAYER_EXCEL_FILE);
		this.pitchList = pitchList;
		sheet = workBook.createSheet("PlayerSheet #" + (sheetCount + 1));
		sheetCount++;
		initializeCellArray();
	}

	public void enterCellValues() {
		
		for (int r=1; r<6; r++) {
			array[r][0].setCellValue(r);
		}
		
		for (int r=8; r<NUMBER_OF_ROWS; r++) {
			array[r][0].setCellValue(r-7);
		}
	
		array[0][0].setCellValue("#" + sheetCount);
		
		array[0][1].setCellValue(1);
		array[0][2].setCellValue(2);
		array[0][3].setCellValue(3);
		array[0][4].setCellValue(4);
		array[0][5].setCellValue(5);
		array[0][6].setCellValue(11);
		array[0][7].setCellValue(12);
		array[0][8].setCellValue(13);
		array[0][9].setCellValue(14);
		array[0][10].setCellValue(15);
		array[0][11].setCellValue(21);
		array[0][12].setCellValue(22);
		array[0][13].setCellValue(23);
		array[0][14].setCellValue(24);
		array[0][15].setCellValue(25);
		
		array[7][1].setCellValue(31);	
		array[7][2].setCellValue(32);
		array[7][3].setCellValue(33);
		array[7][4].setCellValue(34);
		array[7][5].setCellValue(35);
		array[7][6].setCellValue(41);
		array[7][7].setCellValue(42);
		array[7][8].setCellValue(43);
		array[7][9].setCellValue(44);
		array[7][10].setCellValue(45);
		array[7][11].setCellValue(51);
		array[7][12].setCellValue(52);
		array[7][13].setCellValue(53);
		array[7][14].setCellValue(54);
		array[7][15].setCellValue(55);

		Stack<Cell> availableCells = getAvailableCells();
		for (PitchData p: pitchList) {
			List<String> dataSet = p.getDataSet();
			Collections.shuffle(availableCells);
		
			for (int i=0; i<(int)(p.getPercentage()*1.5); i++) {
				Cell cell = availableCells.pop();
				cell.setCellValue(p.getAbbrev());
				
				if (cell.getRowIndex() > 6) {
					dataSet.add((int)array[0][cell.getColumnIndex()].getNumericCellValue()+30 + "" + (int)array[cell.getRowIndex()][0].getNumericCellValue());
				} else if (cell.getColumnIndex() < 6) {
					dataSet.add("0" + (int)(array[0][cell.getColumnIndex()].getNumericCellValue()) + "" + (int)array[cell.getRowIndex()][0].getNumericCellValue());
				} else {
					dataSet.add((int)array[0][cell.getColumnIndex()].getNumericCellValue() + "" + (int)array[cell.getRowIndex()][0].getNumericCellValue());
				}
				
				sheet.autoSizeColumn(i);
			}
		}
	}

	private Stack<Cell> getAvailableCells() {
		Stack<Cell> availableCells = new Stack<>();
		
		for (int row=1; row<6; row++) {
			for (int col=1; col<NUMBER_OF_COLUMNS; col++) {
				if (array[row][col].getStringCellValue().equals(""));
					availableCells.push(array[row][col]);
			}
		}
		
		for (int row=8; row<NUMBER_OF_ROWS; row++) {
			for (int col=1; col<NUMBER_OF_COLUMNS; col++) {
				if (array[row][col].getStringCellValue().equals(""));
					availableCells.push(array[row][col]);
			}
		}
		return availableCells;
	}

	protected void initializeCellArray() {
		array = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		
		for(int row=0; row<NUMBER_OF_ROWS; row++) {
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

	public void createPitchCardSheet() {
		enterCellValues();
		formatCells();
		
		array[0][1].setCellValue("01");
		array[0][2].setCellValue("02");
		array[0][3].setCellValue("03");
		array[0][4].setCellValue("04");
		array[0][5].setCellValue("05");
		
		HSSFPrintSetup printSetup = (HSSFPrintSetup)sheet.getPrintSetup();
		printSetup.setScale((short)85);
		sheet.setMargin(Sheet.LeftMargin, 1.0);
	}

	private void formatCells() {
		CellStyle cellBorders = workBook.createCellStyle();
		cellBorders.setBorderTop((short)1);
		cellBorders.setBorderRight((short)1);
		cellBorders.setBorderBottom((short)1);
		cellBorders.setBorderLeft((short)1);
		cellBorders.setAlignment(CellStyle.ALIGN_CENTER);
		
		for (int row=0; row<NUMBER_OF_ROWS; row++) {
			for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
				array[row][col].setCellStyle(cellBorders);
			}
		}
		
		CellStyle greyForeground = workBook.createCellStyle();
		greyForeground.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		greyForeground.setFillPattern(CellStyle.SOLID_FOREGROUND);
		greyForeground.setBorderTop((short)1);
		greyForeground.setBorderRight((short)1);
		greyForeground.setBorderBottom((short)1);
		greyForeground.setBorderLeft((short)1);
		greyForeground.setAlignment(CellStyle.ALIGN_CENTER);
		greyForeground.getShrinkToFit();
		
		for (int row=0; row<NUMBER_OF_ROWS; row++) {
			for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
				if (row % 2 == 1)
					array[row][col].setCellStyle(greyForeground);
			}
		}
		
		HSSFFont bold = workBook.createFont();
		bold.setBold(true);
		
		CellStyle greyForegroundBold = workBook.createCellStyle();
		greyForegroundBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		greyForegroundBold.setFillPattern(CellStyle.SOLID_FOREGROUND);
		greyForegroundBold.setBorderTop((short)1);
		greyForegroundBold.setBorderRight((short)1);
		greyForegroundBold.setBorderBottom((short)1);
		greyForegroundBold.setBorderLeft((short)1);
		greyForegroundBold.setAlignment(CellStyle.ALIGN_CENTER);
		greyForegroundBold.getShrinkToFit();
		greyForegroundBold.setFont(bold);
		
		for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
			array[7][col].setCellStyle(greyForegroundBold);
		}
		
		CellStyle boldNumbers = workBook.createCellStyle();
		boldNumbers.setBorderTop((short)1);
		boldNumbers.setBorderRight((short)1);
		boldNumbers.setBorderBottom((short)1);
		boldNumbers.setBorderLeft((short)1);
		boldNumbers.setAlignment(CellStyle.ALIGN_CENTER);
		boldNumbers.getShrinkToFit();
		boldNumbers.setFont(bold);
		
		for (int col=0; col<NUMBER_OF_COLUMNS; col++) {
			array[0][col].setCellStyle(boldNumbers);
		}
		
		CellStyle noGreyForegroundBold = workBook.createCellStyle();
		noGreyForegroundBold.setBorderTop((short)1);
		noGreyForegroundBold.setBorderRight((short)1);
		noGreyForegroundBold.setBorderBottom((short)1);
		noGreyForegroundBold.setBorderLeft((short)1);
		noGreyForegroundBold.setAlignment(CellStyle.ALIGN_CENTER);
		noGreyForegroundBold.getShrinkToFit();
		noGreyForegroundBold.setFont(bold);
		
		for (int row=0; row<NUMBER_OF_ROWS; row++) {
			if (row % 2 == 0)
				array[row][0].setCellStyle(noGreyForegroundBold);
			if (row % 2 == 1)
				array[row][0].setCellStyle(greyForegroundBold);
		}
	}
}
