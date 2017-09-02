package com.example.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.data.Vehicle;
import com.example.reader.data.CSVReader;
import com.example.reader.data.XLReader;
import com.example.reader.utils.FileUtils;
import com.example.service.ServiceLayer;

@RunWith(value = Parameterized.class)
public class SeleniumJunitTest extends AbstractJunitTest{
	
	
	public SeleniumJunitTest(String browser) throws IOException, InterruptedException{
		super(browser);
		
	}

	@Parameterized.Parameters
	public static Collection<String> browserList() {
	     return Arrays.asList(new String[] {
	         "firefox", "chrome"//,"ie"
	     });
	}
	
	@After
	public void shutDown(){
		driver.close();
	}

	@Test
	public void carRegistrationCheck() throws IOException {
		
		ServiceLayer serviceLayer = new ServiceLayer(new FileUtils(new CSVReader(), new XLReader()), new MimetypesFileTypeMap());
		List<Vehicle> vehicles = serviceLayer.getAllVehicleInformation();
		String expected_car_reg_number = null ;  
		String expected_car_make = null ; 
		String expected_car_color = null ; 

		String actual_car_reg_number = null;
		String actual_car_make = null;
		String actual_car_color = null;
		
		for(Vehicle vehicle : vehicles){
			expected_car_reg_number = vehicle.getRegistrationNumber();
			expected_car_make = vehicle.getMake();
			expected_car_color = vehicle.getColour();
		
			
			String baseUrl = "https://www.gov.uk/get-vehicle-information-from-dvla";
	        driver.get(baseUrl);
	        
	        takeScreenShot();
	        WebDriverWait wait= new WebDriverWait(driver, 10);
			WebElement we_get_started_link= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='get-started']")));
	        we_get_started_link.findElement(By.xpath("//a[contains(text(),'Start now')]")).click();
			
	        takeScreenShot();
			WebElement  we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='Vrm']")));
			we.sendKeys(expected_car_reg_number);
	
	        takeScreenShot();
			WebElement we_continue= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@name='Continue']")));
			we_continue.click();
	
			WebElement we_car_reg_number= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//li[contains(@class, 'list-summary-item')][1]//span[contains(@class,'reg-mark')]")));
			actual_car_reg_number = we_car_reg_number.getText();
			
			WebElement we_car_make= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//li[contains(@class, 'list-summary-item')][2]//span[2]")));
			actual_car_make = we_car_make.getText();
			
			WebElement we_car_colour= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//li[contains(@class, 'list-summary-item')][3]//span[2]")));
			actual_car_color = we_car_colour.getText();	
	        takeScreenShot();
			
			assertTrue("Car registration number not matched found "+actual_car_reg_number + " expected "+expected_car_reg_number,expected_car_reg_number.equalsIgnoreCase(actual_car_reg_number));
			assertTrue("Car make not matched Registration Number " + expected_car_reg_number + " found "+actual_car_make + " expected "+expected_car_make,expected_car_make.equalsIgnoreCase(actual_car_make));
			assertTrue("Car color not matched. Registration Number " + expected_car_reg_number + " found "+actual_car_color + " expected "+expected_car_color,expected_car_color.equalsIgnoreCase(actual_car_color));
		}
	}

}
