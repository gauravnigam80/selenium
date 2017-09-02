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

	public List<File> filterReadableFiles(List<File> fileList){
		List<File> list = new ArrayList<>();
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

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
	
	public List<File> findAllFiles(String folderPath) throws IOException {
		List<File> list = new ArrayList<>();
		 File sourceDir = new File(folderPath);
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

	public List<Vehicle> readFiles(List<File> fileList) throws IOException {
		List<Vehicle> list = new ArrayList<>();
		for(File file : fileList){
			String fileName = file.getName();
			String extension = FilenameUtils.getExtension(fileName);
			list.addAll(readFile(file,extension.toUpperCase()));
		}
		return list;
	}

	private List<Vehicle> readFile(File file, String extension) throws IOException {
		List<Vehicle> list = null;
		switch(FileExtensionType.valueOf(extension)){
			case CSV : list = CSVReader.getCSVReader().readCSV(file);break;
			case XLS : list = XLReader.getXLReader().readXLS(file); 
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
