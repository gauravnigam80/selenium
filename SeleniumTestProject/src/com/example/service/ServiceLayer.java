package com.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.example.data.FileExtensionType;
import com.example.data.Vehicle;

public class ServiceLayer {

	private static List<Vehicle> list = new ArrayList<>();
	private static String FOLDER_LOCATION = null;
	
	static{
		 Properties properties = new Properties();
		 ClassLoader classLoader = ServiceLayer.class.getClassLoader();
		 URL resource = classLoader.getResource("project.properties");
		 try {
			properties.load(new FileReader(new File(resource.getFile())));
			FOLDER_LOCATION = properties.getProperty("FOLDER_LOCATION");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	final static Logger logger = Logger.getLogger(ServiceLayer.class);
	
	public static void main(String[] args) throws IOException {
	 ServiceLayer serviceLayer = new ServiceLayer();
	 serviceLayer.findDataFromFiles();
	}

	public List<Vehicle> findDataFromFiles() throws IOException {
		List<Vehicle> list = new ArrayList<>();
		 File sourceDir = new File(FOLDER_LOCATION);
		 
		 for(File file : sourceDir.listFiles()){
			 if(file.isDirectory()== false){
				 MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
				 String fileName = file.getName();
				 String mimeType = mimeTypesMap.getContentType(file);
				 String extension = FilenameUtils.getExtension(fileName);
				 
				 if(isFileReadable(extension)){
					 logger.debug("Reading Name : " + fileName + " Size "+file.length() + " Bytes " + "mimeType "+mimeType + "extension "+extension);
					 list.addAll(readFile(file,extension.toUpperCase()));
				 }
				 else{
					 logger.debug("Ignored Name : " + fileName + " Size "+file.length() + " Bytes " + "mimeType "+mimeType + "extension "+extension);
				 }
			 }
		 }
		 return list;
	}

	private List<Vehicle> readFile(File file, String extension) throws IOException {
		switch(FileExtensionType.valueOf(extension)){
			case CSV : list = readCSV(file);break;
			case XLS : list = readXLS(file); 
		}
		return list;
	}

	private List<Vehicle> readXLS(File file) throws IOException {
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
		return list;
	}

	private List<Vehicle> readCSV(File file) throws IOException {
		List<Vehicle> list = new ArrayList<>();
		String line = null;
		Vehicle vehicle = null;
		String cvsSplitBy = ";";
		BufferedReader br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null) {
            // use semicolon as separator
            String[] data = line.split(cvsSplitBy);
            vehicle = new Vehicle(data[0], data[1], data[2]);
            list.add(vehicle);
            logger.debug(vehicle);
        }
        br.close();
        return list;
	}

	private static boolean isFileReadable(String extension) {
		boolean matchFound = false;
		for(FileExtensionType fileType : FileExtensionType.values()){
			if(fileType.name().equalsIgnoreCase(extension)){
				matchFound = true;
				break;
			}
		} 
		return matchFound;
	}
 
}
 