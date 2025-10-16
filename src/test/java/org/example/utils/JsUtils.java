package org.example.utils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsUtils {
    private final WebDriver driver;

    public JsUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void blur(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
    }

    public void clearBrowserData(WebDriver driver) {
        driver.manage().deleteAllCookies();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");
    }
}
