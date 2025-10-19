package org.example.testdata;

import java.util.stream.Stream;

public class InvalidData {
    private InvalidData(){}

    private static final String[] INVALID_EMAILS = {
            "",
            "plainaddress",
            "user@",
            "@domain.com",
            "user@domain",
            "user@domain,com",
            "user@domain..com",
            "user@domain.c",
            "user@@domain.com",
            " user@domain.com",
            "user@domain.com "
    };

    private static final String[] INVALID_PASSWORDS = {
            "",
            "123",
            "password",
            "PASSWORD123",
            "pass word1!",
            "qwertyqwertyqwertyqwerty",
            "pa$$word",
            "12345678",
            "пароль123!",
            "Password_"
    };

    public static String[] invalidPasswordsArray() { return INVALID_PASSWORDS; }
    public static String[] invalidEmailsArray() { return INVALID_EMAILS; }

    public static Stream<String> invalidPasswordsStream() {
        return Stream.of(INVALID_PASSWORDS);
    }
    public static Stream<String> invalidEmailsStream() {
        return Stream.of(INVALID_EMAILS);
    }
}
