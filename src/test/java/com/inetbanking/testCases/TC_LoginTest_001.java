package com.inetbanking.testCases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.inetbanking.pageObjects.LoginPage;

public class TC_LoginTest_001 extends BaseClass {

    @Test
    public void loginTest() throws IOException {
        logger.info("=== Starting Login Test ===");
        driver.get(baseURL);
        logger.info("URL is opened: " + baseURL);

        // ✅ Initialize Page Object
        LoginPage lp = new LoginPage(driver);

        lp.SetUserName(username);
        logger.info("Entered username: " + username);

        lp.SetPassword(password);
        logger.info("Entered password");

        lp.ClickSubmit();
        logger.info("Clicked on login button");

        // ✅ Capture actual title
        String actualTitle = driver.getTitle().trim();
        String expectedTitle = "Guru99 Bank Manager HomePage";

        logger.info("Actual Page Title: " + actualTitle);
        logger.info("Expected Page Title: " + expectedTitle);

        // ✅ Verify title
        try {
            Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match after login.");
            logger.info("✅ Login Test Passed");
        } 
        catch (AssertionError e) {
            logger.error("❌ Login Test Failed — Title Mismatch");
            logger.error("Expected: " + expectedTitle);
            logger.error("Found: " + actualTitle);

            // Capture screenshot on failure
            captureScreen(driver, "loginTest");
            logger.info("📸 Screenshot captured on failure.");

            // Rethrow to mark test failed
            throw e;
        }

        logger.info("=== Login Test Completed ===");
    }
}
