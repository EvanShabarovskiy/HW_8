package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // -------- SETTINGS --------
    private static final BrowserType BROWSER = BrowserType.FIREFOX;
    private static final boolean HEADLESS_MODE = true;
    // ---------------------------

    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver(BROWSER));
        }
        return driver.get();
    }

    private static WebDriver createDriver(BrowserType browserType) {
        return switch (browserType) {
            case FIREFOX -> createFirefoxDriver();
            case EDGE -> createEdgeDriver();
            default -> createChromeDriver();
        };
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--window-size=1920,1080",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-extensions",
                "--disable-popup-blocking",
                "--disable-infobars",
                "--remote-allow-origins=*"
        );

        if (HEADLESS_MODE) {
            options.addArguments("--headless=new");
            System.out.println("🧠 Running Chrome in headless mode");
        } else {
            System.out.println("🪟 Running Chrome in UI mode");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (HEADLESS_MODE) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
            System.out.println("🧠 Running Firefox in headless mode");
        } else {
            System.out.println("🦊 Running Firefox in UI mode");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (HEADLESS_MODE) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            System.out.println("🧠 Running Edge in headless mode");
        } else {
            System.out.println("🪟 Running Edge in UI mode");
        }

        return new EdgeDriver(options);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
