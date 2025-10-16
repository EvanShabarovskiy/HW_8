package org.example.testdata;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.example.testdata.InvalidData.*;

public class DataProvider {
    private DataProvider(){}

    // Dynamically matching incorrect data with error messages

    public static Stream<Arguments> invalidPasswordsWithErrors() {
        return Stream.of(invalidPasswordsArray()).map(password -> {
                    String error;

                    if (password.isEmpty()) {
                        error = StaticTextUA.SignInPage.REQUIRED_FIELD_ERROR;
                    } else if (password.length() > 20) {
                        error = StaticTextUA.SignInPage.TOLONG_PASS_ERROR;
                    } else if (password.length() < 8
                            || password.contains(" ")
                            || !password.matches(".*[A-Z].*")
                            || !password.matches(".*[a-z].*")
                            || !password.matches(".*\\d.*")
                            || !password.matches(".*[~`!@#$%^&*()+=\\-{}\\[\\]|:;\"’?/<>,.].*")) {
                        error = StaticTextUA.SignInPage.SHORT_PASS_ERROR;
                    } else {
                        error = StaticTextUA.SignInPage.INCORRECT_EMAIL_PASS_ERROR;
                    }
                    return Arguments.of(password, error);
                });
    }

    public static Stream<Arguments> invalidEmailsWithErrors() {
        return Stream.of(invalidEmailsArray()).map(email -> {
            String error;
            if(email.isEmpty()) {
                error = StaticTextUA.SignInPage.EMPTY_EMAIL_ERROR;
            } else {
                error = StaticTextUA.SignInPage.ERROR_EMAIL_TEXT;
            }
            return Arguments.of(email, error);
        });
    }
}
