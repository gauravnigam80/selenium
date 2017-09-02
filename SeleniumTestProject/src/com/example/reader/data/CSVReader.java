package com.example.reader.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.example.data.Vehicle;

public class CSVReader {

	private final static Logger logger = Logger.getLogger(CSVReader.class);

	public List<Vehicle> readCSV(File file) throws IOException {
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
}
