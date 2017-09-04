package com.example.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.example.data.Vehicle;
import com.example.reader.data.CSVReader;
import com.example.reader.data.XLReader;
import com.example.reader.utils.FileUtils;

public class FileUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testFilterReadableFiles() {
		CSVReader mockCSVReader = Mockito.mock(CSVReader.class);
		XLReader mockXLReader = Mockito.mock(XLReader.class);
		File mockFile1 = Mockito.mock(File.class);
		File mockFile2 = Mockito.mock(File.class);
		List<File> expected_list = new ArrayList<>();
		expected_list.add(mockFile1);
		expected_list.add(mockFile2);
		
		MimetypesFileTypeMap mimetypesFileTypeMap = Mockito.mock(MimetypesFileTypeMap.class);
		Mockito.when(mimetypesFileTypeMap.getContentType(Mockito.any(File.class))).thenReturn(""); 
		Mockito.when(mockFile1.getName()).thenReturn("Test.csv");
		Mockito.when(mockFile2.getName()).thenReturn("Test.xls");
		FileUtils  utils = new FileUtils(mockCSVReader,mockXLReader);
		List<File> actual_list = utils.filterReadableFiles(expected_list, mimetypesFileTypeMap);
		assertEquals(expected_list, actual_list);
		assertEquals(expected_list.get(0), actual_list.get(0));
	}

	@Test
	public final void testFindAllFiles() {
		CSVReader mockCSVReader = Mockito.mock(CSVReader.class);
		XLReader mockXLReader = Mockito.mock(XLReader.class);
		File mockFile1 = Mockito.mock(File.class);
		File mockFile2 = Mockito.mock(File.class);
		File mockSourceDir = Mockito.mock(File.class);
		List<File> expected_list = new ArrayList<>();
		expected_list.add(mockFile1);
		expected_list.add(mockFile2);
		
		Mockito.when(mockSourceDir.isDirectory()).thenReturn(Boolean.TRUE);
		Mockito.when(mockSourceDir.listFiles()).thenReturn(expected_list.toArray(new File[expected_list.size()]));
		Mockito.when(mockFile1.isDirectory()).thenReturn(Boolean.FALSE);
		Mockito.when(mockFile2.isDirectory()).thenReturn(Boolean.FALSE);
		
		FileUtils  utils = new FileUtils(mockCSVReader,mockXLReader);
		List<File> actual_list = utils.findAllFiles(mockSourceDir);

		assertEquals(expected_list, actual_list);
		assertEquals(expected_list.get(0), actual_list.get(0));
	}

	@Test
	public final void testReadFiles() throws IOException {
		CSVReader mockCSVReader = Mockito.mock(CSVReader.class);
		XLReader mockXLReader = Mockito.mock(XLReader.class);
		
		File mockFile1 = Mockito.mock(File.class);

		List<Vehicle> expected_list = new ArrayList<>();
		expected_list.add(new Vehicle("EK55 BXX","HONDA","SILVER"));
		List<File> filelist = new ArrayList<>();
		filelist.add(mockFile1);
		
		Mockito.when(mockCSVReader.readCSV(mockFile1)).thenReturn(expected_list);
		Mockito.when(mockFile1.getName()).thenReturn("Test.csv");
		FileUtils  utils = new FileUtils(mockCSVReader,mockXLReader);
		List<Vehicle> actual_list = utils.readFiles(filelist);
	
		assertEquals(expected_list, actual_list);
		assertEquals(expected_list.get(0), actual_list.get(0));
	}

}
