package org.example.tests;
import org.example.extensions.LoggingExtension;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.testdata.UserRepository;
import org.example.utils.WindowsUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@ExtendWith(LoggingExtension.class)
public class LoginTest extends TestRunner {
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static WindowsUtils windowsUtils;

    //String TEST_EMAIL = System.getenv("TEST_EMAIL");
    //String TEST_PASSWORD = System.getenv("TEST_PASSWORD");

    @BeforeEach
    public void initPageElements() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        windowsUtils = new WindowsUtils(driver);
    }

    @Test
    public void closeFormBtn(){
        homePage.openLoginForm()
                 .closeLoginForm();
    }

    @Test
    public void verifyTitle() {
        assertThat(loginPage.getPageTitle(), is("GreenCity — Формуй екологічні звички сьогодні"));
    }

    @Test
    public void verifyStaticElements() {
        homePage.openLoginForm();
        assertThat(loginPage.isLeftSidePictureVisible(), is(true));
        assertThat(loginPage.isGoogleSignInButtonVisible(), is(true));
        assertThat(loginPage.getWelcomeText(), is("З поверненням!"));
        assertThat(loginPage.getSignInDetailsText(), is("Будь ласка, внесіть свої дані для входу."));
        assertThat(loginPage.getEmailLabelText(), is("Електронна пошта"));
        assertThat(loginPage.getPasswordLabelText(), is("Пароль"));
        assertThat(loginPage.getDontHaveAccText(), is("Досі немає аккаунту?\nЗареєструватися"));
        assertThat(loginPage.getForgotPassLinkText(), is("Забули пароль?"));
        assertThat(loginPage.isSignUpBtnVisible(), is(true));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @CsvSource({
            "samplestest@greencity.com",
            "anotheruser@greencity.com"
    })
    public void checkEmailInput(String email) {
        homePage.openLoginForm()
                .fillEmail(email);
        assertThat(loginPage.getEmailValue(), is(email));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @CsvSource({
            "weyt3$Guew^",
            "anotherpassword"
    })
    public void checkPasswordInput(String password) {
        homePage.openLoginForm()
                .fillPassword(password);
        assertThat(loginPage.getPasswordValue(), is(password));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @CsvSource({
            "'samplestesgreencity.com', 'Перевірте, чи правильно вказано вашу адресу електронної пошти'",
            "'', 'Введіть пошту.'"
    })
    public void emailIsNotValid(String email, String message) {
        homePage.openLoginForm()
                .fillEmail(email)
                .blurEmailTrigger();
        assertThat(loginPage.getEmailError(), is(message));
        assertThat(loginPage.isSubmitBtnActive(), is(true));
        loginPage.closeLoginForm();
    }

    @ParameterizedTest
    @CsvSource({
            "'passwordmorethenthwentysymbols', 'Пароль не може містити більше 20 символів.'",
            "'', Це поле є обов'язковим",
            "'shortps', 'Пароль повинен мати від 8 до 20 символів без пробілів, містити хоча б один символ латинського алфавіту верхнього (A-Z) та нижнього регістру (a-z), число (0-9) та спеціальний символ (~`!@#$%^&*()+=_-{}[]|:;”’?/<>,.)'"
    })
    public void passwordIsNotValid(String pass, String message) {
        homePage.openLoginForm()
                .fillPassword(pass)
                .blurPasswordTrigger();
        assertThat(loginPage.getPasswordError(), is(message));
        assertThat(loginPage.isSubmitBtnActive(), is(true));
        loginPage.closeLoginForm();
    }
    @ParameterizedTest
    @CsvSource({
            "someemailone@gmail.com, passwordone",
            "someemailtwo@gmail.com, passwordtwo",
            "someemailthree@gmail.com, passwordthree"
    })
    public void incorrectDataError(String email, String pass){
        homePage.openLoginForm()
                .fillEmail(email)
                .fillPassword(pass)
                .blurEmailTrigger()
                .blurPasswordTrigger()
                .clickSubmit(LoginPage.class);
        assertThat(loginPage.getGeneralError(), is("Введено невірний email або пароль"));
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

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}