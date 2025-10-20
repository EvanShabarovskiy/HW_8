package org.example.runners;

import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.utils.DriverFactory;
import org.example.utils.WindowsUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestRunner {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected WindowsUtils windowsUtils;

    private final String BASE_URL = "https://www.greencity.cx.ua/#/greenCity";

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get(BASE_URL);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        windowsUtils = new WindowsUtils(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().setSize(new Dimension(1264, 798));
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
