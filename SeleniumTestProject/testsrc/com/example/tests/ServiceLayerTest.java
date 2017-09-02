/**
 * 
 */
package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.junit.Test;
import org.mockito.Mockito;

import com.example.data.Vehicle;
import com.example.reader.utils.FileUtils;
import com.example.service.ServiceLayer;

/**
 * @author Gaurav Nigam
 *
 */
public class ServiceLayerTest {


	/**
	 * Test method for {@link com.example.service.ServiceLayer#getAllVehicleInformation()}.
	 */
	@Test
	public final void testGetAllVehicleInformation() {
		
		Vehicle vehicle = new Vehicle("EK55BXX","HONDA","SILVER");
		List<Vehicle> expected_list = new ArrayList<>();
		List<File> mocking_list = new ArrayList<>();
		expected_list.add(vehicle);
		//File sourceDir = Mockito.mock(File.class);
		
		FileUtils fileUtils = Mockito.mock(FileUtils.class);
		MimetypesFileTypeMap mimetypesFileTypeMap = Mockito.mock(MimetypesFileTypeMap.class);
		ServiceLayer serviceLayer = new ServiceLayer(fileUtils,mimetypesFileTypeMap);
		Mockito.when(fileUtils.findAllFiles(Mockito.any())).thenReturn(mocking_list);
		Mockito.when(fileUtils.filterReadableFiles(mocking_list,serviceLayer.getMimeTypesMap())).thenReturn(mocking_list);
		Mockito.when(fileUtils.readFiles(mocking_list)).thenReturn(expected_list);
		
		List<Vehicle> actual_list = serviceLayer.getAllVehicleInformation();
		assertNotNull(actual_list);
		assertEquals(expected_list.get(0), actual_list.get(0));
	}

}
