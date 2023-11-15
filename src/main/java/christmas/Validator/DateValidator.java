package christmas.Validator;

import christmas.Exception.DateErrorMessage;

public class DateValidator {
    private static final int MINIMUM_DATE = 1;
    private static final int MAXIMUM_DATE = 31;

    public static int validateInput(String input) {
        validateNotEmpty(input);
        return parseAndValidateRange(input);
    }

    private static void validateNotEmpty(String input) {
        if (isNullOrEmptyOrBlank(input)) {
            throw new IllegalArgumentException(DateErrorMessage.INVALID_DATE_MESSAGE.getMessage());
        }
    }

    private static int parseAndValidateRange(String input) {
        try {
            int intValue = Integer.parseInt(input);
            validateIsInRange(intValue, MINIMUM_DATE, MAXIMUM_DATE);
            return intValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(DateErrorMessage.INVALID_DATE_MESSAGE.getMessage());
        }
    }

    private static boolean isNullOrEmptyOrBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    private static void validateIsInRange(int value, int min, int max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(DateErrorMessage.INVALID_DATE_MESSAGE.getMessage());
        }
    }
}
