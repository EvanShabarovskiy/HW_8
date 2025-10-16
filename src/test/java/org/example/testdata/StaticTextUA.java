package org.example.testdata;

public class StaticText {
    // SignIn page
    private static final String greenCityTitle = "GreenCity — Формуй екологічні звички сьогодні";
    private static final String signInWelcomeText = "З поверненням!";
    private static final String signInDetailsText = "Будь ласка, внесіть свої дані для входу.";
    private static final String signInEmailLabelText = "Електронна пошта";
    private static final String signInPasswordLabelText = "Пароль";
    private static final String signInDontHaveAccText = "Досі немає аккаунту?\nЗареєструватися";
    private static final String signInForgotPassLinkText = "Забули пароль?";
    private static final String signInErrorEmailText = "Перевірте, чи правильно вказано вашу адресу електронної пошти";
    private static final String signInErrorEmptyEmailText = "Введіть пошту.";
    private static final String signInErrorToLongPassText = "Пароль не може містити більше 20 символів.";
    private static final String signInErrorRequiredFildText = "Це поле є обов'язковим";
    private static final String signInErrorShortPassText = "Пароль повинен мати від 8 до 20 символів без пробілів, містити хоча б один символ латинського алфавіту верхнього (A-Z) та нижнього регістру (a-z), число (0-9) та спеціальний символ (~`!@#$%^&*()+=_-{}[]|:;”’?/<>,.)";
    private static final String incorrectEmailOrPassText = "Введено невірний email або пароль";

    private StaticText() {}

    public static String getGreenCityTitle() {
        return greenCityTitle;
    }

    public static String getSignInWelcomeText() {
        return signInWelcomeText;
    }

    public static String getSignInDetailsText() {
        return signInDetailsText;
    }

    public static String getSignInEmailLabelText() {
        return signInEmailLabelText;
    }

    public static String getSignInPasswordLabelText() {
        return signInPasswordLabelText;
    }

    public static String getSignInDontHaveAccText() {
        return signInDontHaveAccText;
    }

    public static String getSignInForgotPassLinkText() {
        return signInForgotPassLinkText;
    }

    public static String getSignInErrorEmailText() {
        return signInErrorEmailText;
    }

    public static String getSignInErrorEmptyEmailText() {
        return signInErrorEmptyEmailText;
    }

    public static String getSignInErrorToLongPassText() {
        return signInErrorToLongPassText;
    }

    public static String getSignInErrorRequiredFildText() {
        return signInErrorRequiredFildText;
    }

    public static String getSignInErrorShortPassText() {
        return signInErrorShortPassText;
    }

    public static String getIncorrectEmailOrPassText() {
        return incorrectEmailOrPassText;
    }
}
