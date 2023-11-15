package christmas.Domain;

import christmas.Domain.Menu.Appetizer;
import christmas.Domain.Menu.Beverage;
import christmas.Domain.Menu.Dessert;
import christmas.Domain.Menu.Main;
import christmas.Exception.MenuErrorMessage;
import java.util.Arrays;

public class OrderMenu {
    private String menuName;
    private int menuCount;
    private int menuPrice;

    public OrderMenu(String menuName, int menuCount, int menuPrice) {
        validateMenuName(menuName);
        this.menuName = menuName;
        this.menuCount = menuCount;
        this.menuPrice = menuPrice;
    }

    private void validateMenuName(String menuName) {
        boolean isValidMenu = Arrays.stream(Appetizer.values())
                .anyMatch(appetizer -> appetizer.getName().equals(menuName))
                || Arrays.stream(Beverage.values())
                .anyMatch(beverage -> beverage.getName().equals(menuName))
                || Arrays.stream(Dessert.values())
                .anyMatch(dessert -> dessert.getName().equals(menuName))
                || Arrays.stream(Main.values())
                .anyMatch(main -> main.getName().equals(menuName));

        if (!isValidMenu) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuCount() {
        return menuCount;
    }

    public int getMenuPrice() {
        return menuPrice;
    }
}
