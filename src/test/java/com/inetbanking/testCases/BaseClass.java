package com.inetbanking.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.lang3.RandomStringUtils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetbanking.utilities.ReadConfig;

public class BaseClass {

    ReadConfig readconfig = new ReadConfig();

    public String baseURL = readconfig.getApplicationURL();
    public String username = readconfig.getUsername();
    public String password = readconfig.getpassword();
    public static WebDriver driver;
    public static Logger logger;

    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {

        // ✅ Paths
        String projectPath = System.getProperty("user.dir");
        String log4jPath = projectPath + "/src/main/resources/log4j.properties";

        // ✅ Configure logger
        logger = Logger.getLogger("inetBankingV1");
        PropertyConfigurator.configure(log4jPath);

        logger.info("------------------------------------------------");
        logger.info("Starting Test Setup...");
        logger.info("Browser parameter received from XML: " + browser);

        // ✅ Launch browser based on XML parameter
        if ("chrome".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.chrome.driver", projectPath + "/Drivers/chromedriver.exe");
            driver = new ChromeDriver();
            logger.info("Launching Chrome browser...");
        } 
        else if ("firefox".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.gecko.driver", projectPath + "/Drivers/geckodriver.exe");
            driver = new FirefoxDriver();
            logger.info("Launching Firefox browser...");
        } 
        else if ("edge".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.edge.driver", projectPath + "/Drivers/msedgedriver.exe");
            driver = new EdgeDriver();
            logger.info("Launching Edge browser...");
        } 
        else if ("ie".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.ie.driver", projectPath + "/Drivers/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
            logger.info("Launching Internet Explorer browser...");
        } 
        else {
            throw new RuntimeException("❌ Unsupported browser: " + browser);
        }

        // ✅ Browser configuration
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().window().maximize();

        // ✅ Open the application
        driver.get(baseURL);
        logger.info("URL opened: " + baseURL);
        logger.info("------------------------------------------------");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("=== Browser closed successfully ===");
        } else {
            logger.warn("⚠️ Driver was null. Nothing to close.");
        }
    }

    // ✅ Screenshot utility
    public void captureScreen(WebDriver driver, String tname) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
        FileUtils.copyFile(sourceFile, targetFile);
        System.out.println("📸 Screenshot taken: " + targetFile.getAbsolutePath());
    }
    public String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}
}