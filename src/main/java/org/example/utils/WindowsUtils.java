package org.example.utils;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowsUtils {
    private final WebDriver driver;
    public WindowsUtils(WebDriver driver) {
        this.driver = driver;
    }

    public <T> T switchToNewWindow(Class<T> pageClass) {
        String mainWindow = driver.getWindowHandle();
        WaitUtils.waitForNumberOfWindowsToBe(driver, 2);

        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                try {
                    return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot create page object: " + pageClass.getName(), e);
                }
            }
        }
        throw new RuntimeException("No new window found to switch to");
    }

    public <T> T switchBackToMainWindow(Class<T> pageClass) {
        String mainWindow = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainWindow);
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create page object: " + pageClass.getName(), e);
        }
    }

    public <T> T closeCurrentWindow(Class<T> pageClass) {
        driver.close();
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create page object: " + pageClass.getName(), e);
        }
    }
}
