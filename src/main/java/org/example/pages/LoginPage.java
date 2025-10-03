package org.example.pages;

import org.example.utils.JsUtils;
import org.example.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;
    private WaitUtils waitUtils;
    private final JsUtils jsUtils;

        @FindBy(css = ".close-modal-window")
        private WebElement closeFormBtn;

        @FindBy(css = ".container h1")
        private WebElement welcomeText;

        @FindBy(css = ".container h2")
        private WebElement signInDetailsText;

        @FindBy(css = "form > label[for='email']")
        private WebElement emailLabel;

        @FindBy(id = "email")
        private WebElement emailInput;

        @FindBy(css = "form > label[for='password']")
        private WebElement passwordLabel;

        @FindBy(id = "password")
        private WebElement passwordInput;

        @FindBy(css = ".image-show-hide-password")
        private WebElement showHidePassBtn;

        @FindBy(css = ".forgot-wrapper a")
        private WebElement forgotPassLink;

        @FindBy(css = ".restore-password-container h1")
        private WebElement checkIsForgotPassWindow;

        @FindBy(css = ".google-sign-in")
        private WebElement googleSignInBtn;

        @FindBy(css = ".greenStyle")
        private WebElement signInSubmitButton;

        @FindBy(css = "#pass-err-msg div")
        private WebElement errorPassword;

        @FindBy(css = "#email-err-msg div")
        private WebElement errorEmail;

        @FindBy(css = ".missing-account p")
        private WebElement dontHaveAccText;

        @FindBy(css = ".missing-account p a")
        private WebElement signUpBtn;

        @FindBy(css = ".main-picture")
        private WebElement leftSidePicture;

        @FindBy(css = ".main")
        private WebElement logInWindow;

        @FindBy(css = ".alert-general-error")
        private WebElement generalError;

        @FindBy(css = ".title-text")
        private WebElement isSignUpWindow;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.waitUtils = new WaitUtils(driver);
        this.jsUtils = new JsUtils(driver);
    }

    public LoginPage closeLoginForm() {
        waitUtils.clickWhenReady(closeFormBtn);
        waitUtils.waitForInvisibility(logInWindow);
        return this;
    }

    public LoginPage fillEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public LoginPage fillPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public LoginPage blurEmailTrigger() {
        jsUtils.blur(emailInput);
        return this;
    }

    public LoginPage blurPasswordTrigger() {
        jsUtils.blur(passwordInput);
        return this;
    }

    public <T> T clickSubmit(Class<T> pageClass) {
        waitUtils.clickWhenReady(signInSubmitButton);
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create page object: " + pageClass.getName(), e);
        }
    }

    public void clickShowHideBtn() {
        waitUtils.clickWhenReady(showHidePassBtn);
    }

    public String getEmailError() {
        return errorEmail.getText();
    }

    public String getPasswordError() {
        return errorPassword.getText();
    }

    public String getGeneralError() {
        waitUtils.waitForVisibility(generalError);
        return generalError.getText();
    }

    public boolean isForgotPassWindow() { return checkIsForgotPassWindow.isDisplayed(); }

    public String getPasswordType() {
        return passwordInput.getAttribute("type");
    }

    public String getPassAltValue() {
        return showHidePassBtn.getAttribute("alt");
    }

    public String getEmailValue() { return emailInput.getAttribute("value"); }

    public String getPasswordValue(){
        return passwordInput.getAttribute("value");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSubmitBtnActive() { return signInSubmitButton.getAttribute("disabled") != null; }

    public void loginAs(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        blurEmailTrigger();
        blurPasswordTrigger();
        clickSubmit(LoginPage.class);
    }

    public LoginPage openForgotPassword() {
        waitUtils.clickWhenReady(forgotPassLink);
        return this;
    }

    public LoginPage clickGoogleSignIn() {
        waitUtils.clickWhenReady(googleSignInBtn);
        return this;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void clickSignUp() {
        moveToElement(signUpBtn);
        waitUtils.clickWhenReady(signUpBtn);
    }

    public boolean isSignUpWindowVisible() { return isSignUpWindow.isDisplayed(); }

    public boolean isElementVisible(WebElement locator) { return locator.isDisplayed(); }

    public String getText(WebElement locator) { return locator.getText(); }

    public boolean isLeftSidePictureVisible() { return isElementVisible(leftSidePicture); }

    public boolean isGoogleSignInButtonVisible() { return isElementVisible(googleSignInBtn); }

    public String getWelcomeText() { return getText(welcomeText); }

    public String getSignInDetailsText() { return getText(signInDetailsText); }

    public String getEmailLabelText() { return getText(emailLabel); }

    public String getPasswordLabelText() { return getText(passwordLabel); }

    public String getDontHaveAccText() { return getText(dontHaveAccText); }

    public String getForgotPassLinkText() { return getText(forgotPassLink); }

    public boolean isSignUpBtnVisible() { return signUpBtn.isDisplayed(); }
}