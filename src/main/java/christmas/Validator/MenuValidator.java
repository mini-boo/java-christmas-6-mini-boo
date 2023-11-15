package christmas.Validator;


import christmas.Domain.Menu.Beverage;
import christmas.Exception.MenuErrorMessage;
import java.util.HashSet;
import java.util.Set;

public class MenuValidator {
    private static final String COMMA_SEPERATOR = ",";
    private static final String HYPHEN_SEPERATOR = "-";

    public static void validateMenuInput(String input) {
        if (isNullOrEmpty(input)) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }

        if (containsBothCommaAndDash(input)) {
            validateUniqueOrders(input);
        }
        if (!containsDash(input)) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }
    }

    private static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    private static boolean containsBothCommaAndDash(String input) {
        return input.contains(COMMA_SEPERATOR) && containsDash(input.trim());
    }

    private static boolean containsDash(String input) {
        return input.contains(HYPHEN_SEPERATOR);
    }

    private static void validateUniqueOrders(String input) {
        String[] menuOrders = input.split(COMMA_SEPERATOR);
        Set<String> uniqueOrders = new HashSet<>();

        for (String order : menuOrders) {
            if (!uniqueOrders.add(order.trim())) {
                throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
            }
        }
    }

    public static boolean isBeverage(String menuName) {
        for (Beverage beverage : Beverage.values()) {
            if (beverage.getName().equals(menuName)) {
                return true;
            }
        }
        return false;
    }
}
