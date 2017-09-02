/**
 * 
 */
package com.example.reader.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.example.data.Vehicle;

/**
 * @author Gaurav Nigam
 *
 */
public class XLReader {

	private final static Logger logger = Logger.getLogger(XLReader.class);
	private final static XLReader xlReader = new XLReader();
	
	public static XLReader getXLReader(){
		return xlReader;
	}
	
	
	public List<Vehicle> readXLS(File file) throws IOException{
		List<Vehicle> list = new ArrayList<>();		
		Vehicle vehicle = null;
		FileInputStream excelFile = new FileInputStream(file);
		Workbook workbook = new HSSFWorkbook(excelFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		while (iterator.hasNext()) {
			
			Row currentRow = iterator.next();
			if (true) {
				vehicle = new Vehicle(currentRow.getCell(0).getStringCellValue(), currentRow.getCell(1).getStringCellValue(), currentRow.getCell(2).getStringCellValue());
				logger.debug(vehicle);
				list.add(vehicle);
			} 
		}
		workbook.close();
		return list;
	}

}
