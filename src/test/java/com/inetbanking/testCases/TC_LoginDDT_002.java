package com.inetbanking.testCases;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;

public class TC_LoginDDT_002 extends BaseClass {

    @Test(dataProvider = "LoginData")
    public void loginDDT(String user, String pwd) throws InterruptedException {
        LoginPage lp = new LoginPage(driver);

        lp.SetUserName(user);
        logger.info("User name provided");

        lp.SetPassword(pwd);
        logger.info("Password provided");

        lp.ClickSubmit();
        logger.info("Login button clicked");

        Thread.sleep(3000);

        if (isAlertPresent() == true) {
            driver.switchTo().alert().accept(); // close alert
            driver.switchTo().defaultContent();
            Assert.assertTrue(false);
            logger.warn("Login failed");
        } else {
            Assert.assertTrue(true);
            logger.info("Login passed");

            lp.clickLogout(); // ✅ now handles JS click automatically
            Thread.sleep(2000);

            try {
                driver.switchTo().alert().accept(); // close logout alert
                driver.switchTo().defaultContent();
            } catch (Exception e) {
                logger.warn("No logout alert present or already handled");
            }
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @DataProvider(name = "LoginData")
    String[][] getData() throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/java/com/inetbanking/testData/LoginData.xlsx";

        int rownum = XLUtils.getRowCount(path, "Sheet1");
        int colcount = XLUtils.getCellCount(path, "Sheet1", 1);

        String logindata[][] = new String[rownum][colcount];

        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colcount; j++) {
                logindata[i - 1][j] = XLUtils.getCellData(path, "Sheet1", i, j);
            }
        }
        return logindata;
    }
}
