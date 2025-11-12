package com.inetbanking.pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    WebDriver ldriver;

    public LoginPage(WebDriver rdriver) {
        ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    @FindBy(name = "uid")
    WebElement txtUserName;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(name = "btnLogin")
    WebElement btnLogin;

    @FindBy(xpath = "//a[contains(text(),'Log out')]")
    WebElement lnkLogout;

    // ✅ Set username
    public void SetUserName(String uname) {
        txtUserName.sendKeys(uname);
    }

    // ✅ Set password
    public void SetPassword(String pwd) {
        txtPassword.sendKeys(pwd);
    }

    // ✅ Click login
    public void ClickSubmit() {
        btnLogin.click();
    }

    // ✅ Safe logout click with JSExecutor (prevents ElementClickInterceptedException)
    public void clickLogout() {
        try {
            WebDriverWait wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(lnkLogout));
            wait.until(ExpectedConditions.elementToBeClickable(lnkLogout));

            ((JavascriptExecutor) ldriver).executeScript("arguments[0].scrollIntoView(true);", lnkLogout);
            Thread.sleep(500); // small delay to stabilize UI
            ((JavascriptExecutor) ldriver).executeScript("arguments[0].click();", lnkLogout);

            System.out.println("✅ Logout link clicked successfully");
        } catch (Exception e) {
            System.out.println("❌ Logout click failed: " + e.getMessage());
        }
    }

    // ✅ Getter method (if needed in test class)
    public WebElement getLogoutLink() {
        return lnkLogout;
    }
}
