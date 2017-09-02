package com.example.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;

import org.apache.log4j.Logger;

import com.example.data.Vehicle;
import com.example.reader.data.CSVReader;
import com.example.reader.data.XLReader;
import com.example.reader.utils.FileUtils;

public class ServiceLayer {

	private static String FOLDER_LOCATION = null;
	private static String PROJECT_PROPERTIES = "project.properties";
	private final static Logger logger = Logger.getLogger(ServiceLayer.class);
	private FileUtils utils = null;
	private MimetypesFileTypeMap mimeTypesMap = null;

	static{
		 Properties properties = new Properties();
		 ClassLoader classLoader = ServiceLayer.class.getClassLoader();
		 URL resource = classLoader.getResource(PROJECT_PROPERTIES);
		 try {
			properties.load(new FileReader(new File(resource.getFile())));
			FOLDER_LOCATION = properties.getProperty("FOLDER_LOCATION");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public ServiceLayer(FileUtils fileUtils,MimetypesFileTypeMap mimeTypesMap_p) {
		utils = fileUtils;
		mimeTypesMap = mimeTypesMap_p;
	}

	public static void main(String[] args) throws IOException {
	 ServiceLayer serviceLayer = new ServiceLayer(new FileUtils(new CSVReader(), new XLReader()), new MimetypesFileTypeMap());
	 List<Vehicle> list = serviceLayer.getAllVehicleInformation();
	}

	public List<Vehicle> getAllVehicleInformation(){
		File sourceDir = new File(FOLDER_LOCATION);
		List<File> fileList  = getUtils().findAllFiles(sourceDir);
		List<File> readableFiles = utils.filterReadableFiles(fileList,getMimeTypesMap());
		List<Vehicle> list = utils.readFiles(readableFiles);
		logger.debug("Vehicles found "+list);
		return list;
	}	
	

	
	/**
	 * @return the utils
	 */
	public FileUtils getUtils() {
		return utils;
	}

 
	/**
	 * @return the fOLDER_LOCATION
	 */
	public static String getFolderLocation() {
		return FOLDER_LOCATION;
	}

	/**
	 * @return the mimeTypesMap
	 */
	public MimetypesFileTypeMap getMimeTypesMap() {
		return mimeTypesMap;
	}



}
 