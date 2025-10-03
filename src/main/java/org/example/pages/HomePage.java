package org.example.pages;

import org.example.utils.JsUtils;
import org.example.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(css = ".dropdown-list > :last-child a")
    private WebElement signOutBtn;

    @FindBy(css = ".user-name")
    private WebElement isLoggedIn;

    @FindBy(css = ".ubs-header-sing-in-img")
    private WebElement signInButton;

    private final WebDriver driver;
    private WaitUtils waitUtils;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.waitUtils = new WaitUtils(driver);
    }

    public LoginPage openLoginForm() {
        waitUtils.clickWhenReady(signInButton);
        return new LoginPage(driver);
    }

    public HomePage signOut() {
        waitUtils.clickWhenReady(isLoggedIn);
        waitUtils.clickWhenReady(signOutBtn);
        return this;
    }

    public boolean isLoggedIn() {
        waitUtils.waitForVisibility(isLoggedIn);
        return isLoggedIn.isDisplayed();
    }
}
