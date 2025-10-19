package org.example.testdata;

import java.util.stream.Stream;

public class ValidData {
    private ValidData(){}

    private static final String[] VALID_EMAILS = {
            "samplestest@greencity.com",
            "anotheruser@greencity.com",
            "oaoooaa@okun.ua"
    };

    public static Stream<String> validEmailsStream() {
        return Stream.of(VALID_EMAILS);
    }

    private static final String[] VALID_PASSWORDS = {
            "weyt3$Guew^",
            "!anotherpassword",
            "A1sddda#*oaP"
    };

    public static Stream<String> validPasswordsStream() {
        return Stream.of(VALID_PASSWORDS);
    }
}
