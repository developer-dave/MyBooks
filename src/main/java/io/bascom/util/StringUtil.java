package io.bascom.util;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern numberPattern = Pattern.compile("^[0-9]+$");

    /**
     * Validates that the input is not null, not empty and not juts a bunch of white space
     *
     * @param input
     * @return {{true}} if input has at least one non-whitespace character, else {{false}}
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    public static boolean isNumber(String input) {
        return isNotEmpty(input) && numberPattern.matcher(input).matches();
    }
}
