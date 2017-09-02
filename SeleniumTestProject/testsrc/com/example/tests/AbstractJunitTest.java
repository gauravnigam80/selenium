package com.example.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.runners.Parameterized.Parameter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AbstractJunitTest {

	protected static WebDriver driver;
	//@Parameter(value = 0)
	private String browser ;

	private SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SS");
	
	
	
	/**
	 * @param browser
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public AbstractJunitTest(String browser) throws IOException {
		super();
		this.browser = browser;
		loadWebDriver(browser); 
	}

	protected WebDriver loadWebDriver(String browser) throws IOException{
	
	    DesiredCapabilities capabilities = new DesiredCapabilities();
    	capabilities = DesiredCapabilities.chrome();
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("test-type", "start-maximized","no-default-browser-check");    
    	options.addArguments("--disable-extensions");
    	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS , true);
		
		if(browser.equalsIgnoreCase("firefox")) {	// If the browser is Firefox, then do this
			System.setProperty("webdriver.gecko.driver","drivers\\geckodriver.exe"); // Here I am setting up the path for my firefox driver
			driver = new FirefoxDriver(capabilities);// Here I am setting up the path for my FireFoxDriver	
		}
		else if (browser.equalsIgnoreCase("ie")) { // If browser is IE, then do this
			System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");	// Here I am setting up the path for my IEDriver
			driver = new InternetExplorerDriver(capabilities);
		}
		else if (browser.equalsIgnoreCase("chrome")){	// If the browser is Firefox, then do this
			System.setProperty("webdriver.chrome.driver","drivers\\chromedriver.exe");	// Here I am setting up the path for my chromeDriver
			driver= new ChromeDriver(capabilities);
		}
		return driver;
	}
	
	protected void takeScreenShot() throws IOException {
		File screenshotFile = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
		File dir = new File("screenshot");
		String fileName = sd.format(new Date());
        FileUtils.copyFile(screenshotFile, new File(dir,browser+"-"+fileName+".jpg"));
	}

}
