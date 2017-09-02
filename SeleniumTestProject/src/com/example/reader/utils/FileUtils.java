package com.example.reader.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.example.data.FileExtensionType;
import com.example.data.Vehicle;
import com.example.reader.data.CSVReader;
import com.example.reader.data.XLReader;

public class FileUtils {

	private final static Logger logger = Logger.getLogger(FileUtils.class);
	CSVReader csvReader = null;
	XLReader xlReader = null;
	
	

	/**
	 * @param csvReader
	 * @param xlReader
	 */
	public FileUtils(CSVReader csvReader, XLReader xlReader) {
		super();
		this.csvReader = csvReader;
		this.xlReader = xlReader;
	}

	public List<File> filterReadableFiles(List<File> fileList,MimetypesFileTypeMap mimeTypesMap){
		List<File> list = new ArrayList<>();

		for(File file : fileList){
			 if(file.isDirectory()== false){
				 String fileName = file.getName();
				 String mimeType = mimeTypesMap.getContentType(file);
				 String extension = FilenameUtils.getExtension(fileName);
				 
				 if(isFileReadable(extension)){
					 logger.debug("Reading Name : " + fileName + " Size "+file.length() + " Bytes " + "mimeType "+mimeType + "extension "+extension);
					 list.add(file);
				 }
				 else{
					 logger.debug("Ignored Name : " + fileName + " Size "+file.length() + " Bytes " + "mimeType "+mimeType + "extension "+extension);
				 }
			 }
		 }
		 return list;
	}
	
	public List<File> findAllFiles(File sourceDir) {
		List<File> list = new ArrayList<>();
		 if(!sourceDir.isDirectory()){
			 return list;
		 }	 
		 else{
			 for(File file : sourceDir.listFiles()){
				 if(!file.isDirectory()){
					 list.add(file);
				 }
			 }
		 }
		 return list;
	}

	public List<Vehicle> readFiles(List<File> fileList){
		List<Vehicle> list = new ArrayList<>();
		for(File file : fileList){
			String fileName = file.getName();
			String extension = FilenameUtils.getExtension(fileName);
			try {
				list.addAll(readFile(file,extension.toUpperCase()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private List<Vehicle> readFile(File file, String extension) throws IOException {
		List<Vehicle> list = null;
		switch(FileExtensionType.valueOf(extension)){
			case CSV : list = csvReader.readCSV(file);break;
			case XLS : list = xlReader.readXLS(file); 
		}
		return list;
	}

	private boolean isFileReadable(String extension) {
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
