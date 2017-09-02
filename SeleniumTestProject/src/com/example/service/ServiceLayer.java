package com.example.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.example.data.Vehicle;
import com.example.reader.utils.FileUtils;

public class ServiceLayer {

	private static String FOLDER_LOCATION = null;
	private static String PROJECT_PROPERTIES = "project.properties";
	private final static Logger logger = Logger.getLogger(ServiceLayer.class);
	
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
	
	
	public static void main(String[] args) throws IOException {
	 ServiceLayer serviceLayer = new ServiceLayer();
	 List<Vehicle> list = serviceLayer.getAllVehicleInformation();
	}

	public List<Vehicle> getAllVehicleInformation() throws IOException{
		FileUtils utils = new FileUtils();
		List<File> fileList  = utils.findAllFiles(FOLDER_LOCATION);
		List<File> readableFiles = utils.filterReadableFiles(fileList);
		List<Vehicle> list = utils.readFiles(readableFiles);
		logger.debug("Vehicles found "+list);
		return list;
	}	
	

	
 
}
 