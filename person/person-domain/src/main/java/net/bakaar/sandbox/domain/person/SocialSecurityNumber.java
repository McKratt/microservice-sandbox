package net.bakaar.sandbox.domain.person;

import java.util.regex.Pattern;

public class SocialSecurityNumber {

    private final long number;

    private SocialSecurityNumber(long number) {
        Pattern pattern = Pattern.compile("756[\\d]{10}");
        if (!pattern.matcher(number + "").matches()) {
            throw new IllegalArgumentException("The number should begin by 756 and contains 13 digits (756 + 10 digits)");
        }
        this.number = number;
    }

    public static SocialSecurityNumber of(long number) {
        return new SocialSecurityNumber(number);
    }

    public String format() {
        return new StringBuilder(number + "")
                .insert(3, '.')
                .insert(8, '.')
                .insert(13, '.')
                .toString();
    }
}
