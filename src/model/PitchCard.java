package model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class PitchCard {
	
	Cell array[][];
	File file;
	
	public PitchCard(File file) {
		this.file = file;
	}
	
	public void write() {
		try {
			FileOutputStream out = new FileOutputStream(getFile());
			getWorkBook().write(out);
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	File getFile() {
		return file;
	}
	
	abstract HSSFWorkbook getWorkBook();
	protected abstract void initializeCellArray();
	protected abstract void createPitchCardSheet();
}