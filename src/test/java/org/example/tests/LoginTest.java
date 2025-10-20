package org.example.tests;
import org.example.extensions.LoggingExtension;
import org.example.pages.LoginPage;
import org.example.runners.TestRunner;
import org.example.testdata.StaticTextUA;
import org.example.testdata.UserRepository;
import org.example.utils.WindowsUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(LoggingExtension.class)
public class LoginTest extends TestRunner {

    //String TEST_EMAIL = System.getenv("TEST_EMAIL");
    //String TEST_PASSWORD = System.getenv("TEST_PASSWORD");

    @Test
    public void closeFormBtn(){
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        homePage.openLoginForm()
                 .closeLoginForm();
        System.out.println(">>> Running on thread: " + Thread.currentThread().getId());
    }

    @Test
    public void verifyTitle() {
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        assertThat(loginPage.getPageTitle(), is(StaticTextUA.SignInPage.GREEN_CITY_TITLE));
        System.out.println(">>> Running on thread: " + Thread.currentThread().getId());
    }

    @Test
    public void verifyStaticElements() {
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        homePage.openLoginForm();
        assertThat(loginPage.isLeftSidePictureVisible(), is(true));
        assertThat(loginPage.isGoogleSignInButtonVisible(), is(true));
        assertThat(loginPage.getWelcomeText(), is(StaticTextUA.SignInPage.WELCOME_TEXT));
        assertThat(loginPage.getSignInDetailsText(), is(StaticTextUA.SignInPage.DETAILS_TEXT));
        assertThat(loginPage.getEmailLabelText(), is(StaticTextUA.SignInPage.EMAIL_LABEL_TEXT));
        assertThat(loginPage.getPasswordLabelText(), is(StaticTextUA.SignInPage.PASSWORD_LABEL_TEXT));
        assertThat(loginPage.getDontHaveAccText(), is(StaticTextUA.SignInPage.DONT_HAVE_ACC_TEXT));
        assertThat(loginPage.getForgotPassLinkText(), is(StaticTextUA.SignInPage.FORGOT_PASS_LINK_TEXT));
        assertThat(loginPage.isSignUpBtnVisible(), is(true));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @MethodSource("org.example.testdata.ValidData#validEmailsStream")
    public void checkEmailInput(String email) {
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        homePage.openLoginForm()
                .fillEmail(email);
        assertThat(loginPage.getEmailValue(), is(email));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @MethodSource("org.example.testdata.ValidData#validPasswordsStream")
    public void checkPasswordInput(String password) {
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        homePage.openLoginForm()
                .fillPassword(password);
        assertThat(loginPage.getPasswordValue(), is(password));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @MethodSource("org.example.testdata.InvalidData#invalidEmailsStream")
    public void emailIsNotValid(String email) {
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        if(!Objects.equals(email, "")) {
            homePage.openLoginForm()
                    .fillEmail(email)
                    .blurEmailTrigger();
            assertThat(loginPage.getEmailError(), is(StaticTextUA.SignInPage.ERROR_EMAIL_TEXT));
            assertThat(loginPage.isSubmitBtnActive(), is(false));
        } else { assertThat(loginPage.getEmailError(), is(StaticTextUA.SignInPage.EMPTY_EMAIL_ERROR)); }
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @MethodSource("org.example.testdata.InvalidDataProvider#invalidPasswordsWithErrors")
    public void passwordIsNotValid(String pass, String message) {
        System.out.println(">>>>>>>>>>> Running on thread: " + Thread.currentThread().getId());
        homePage.openLoginForm()
                .fillPassword(pass)
                .blurPasswordTrigger();
        assertThat(loginPage.getPasswordError(), is(message));
        assertThat(loginPage.isSubmitBtnActive(), is(true));
        loginPage.closeLoginForm();
    }

    @Test
    public void UnregisteredUserCheck(){
        homePage.openLoginForm()
                .fillEmail(UserRepository.getUnregistered().getEmail())
                .fillPassword(UserRepository.getUnregistered().getPassword())
                .blurEmailTrigger()
                .blurPasswordTrigger()
                .clickSubmit(LoginPage.class);
        assertThat(loginPage.getGeneralError(), is (StaticTextUA.SignInPage.INCORRECT_EMAIL_PASS_ERROR));
        loginPage.closeLoginForm();
    }

    @Test
    public void checkForgotPassBtn(){
        homePage.openLoginForm()
                .openForgotPassword();
        assertThat(loginPage.isForgotPassWindow(), is(true));
        loginPage.closeLoginForm();
    }

    @Test
    public void testShowHidePassBtn(){
        homePage.openLoginForm()
                .fillPassword("somepassword");
        assertThat(loginPage.getPasswordType(), is("password"));
        assertThat(loginPage.getPassAltValue(), is("show-hide-password"));

        loginPage.clickShowHideBtn();
        assertThat(loginPage.getPasswordType(), is("text"));
        assertThat(loginPage.getPassAltValue(), is("hide password"));

        loginPage.clickShowHideBtn();
        assertThat(loginPage.getPasswordType(), is("password"));
        assertThat(loginPage.getPassAltValue(), is("show password"));
        loginPage.closeLoginForm();
    }

    // TODO upgrade
    @Test
    public void loginWithGoogleTest() {
        homePage.openLoginForm()
                .clickGoogleSignIn();
        windowsUtils.switchToNewWindow(LoginPage.class);
        assertThat(loginPage.getCurrentUrl(), containsString("accounts.google.com"));
        windowsUtils.closeCurrentWindow(WindowsUtils.class)
                    .switchBackToMainWindow(LoginPage.class)
                    .closeLoginForm();
    }

    @Test
    public void signUpBtnTest(){
        homePage.openLoginForm()
                .clickSignUp();
        assertThat(loginPage.isSignUpWindowVisible(), is(true));
        loginPage.closeLoginForm();
    }

    @Test
    public void loginWithValidData() {
        homePage.openLoginForm()
                .loginAs(UserRepository.getValid());
        assertThat(homePage.isLoggedIn(), is(true));
        homePage.signOut();
    }


}