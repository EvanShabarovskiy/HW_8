package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestRunner {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        driver.manage().window().setSize(new Dimension(1264, 798));
    }
}
