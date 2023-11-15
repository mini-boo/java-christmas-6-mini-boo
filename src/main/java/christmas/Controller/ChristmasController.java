package christmas.Controller;

import christmas.Constant.ControllerConstant;
import christmas.Domain.Benefit;
import christmas.Domain.EventBadge;
import christmas.Domain.Menu.Appetizer;
import christmas.Domain.Menu.Beverage;
import christmas.Domain.Menu.Dessert;
import christmas.Domain.Menu.Main;
import christmas.Domain.OrderMenu;
import christmas.Exception.MenuErrorMessage;
import christmas.Service.ChristmasService;
import christmas.Validator.MenuValidator;
import christmas.View.InputView;
import christmas.View.OutputView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChristmasController implements ControllerConstant {
    private final ChristmasService christmasService = new ChristmasService();
    private final InputView inputView = new InputView();

    public void run() {
        try {
            play();
        } catch (IllegalArgumentException e) {
            System.out.println(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }
    }

    public void play() {
        int expectedDate = inputView.readDate();
        List<OrderMenu> orderMenus = processMenuInput(inputView.readMenu());
        printMenuMessageAndOrderMenus(orderMenus);

        int totalOrderAmount = christmasService.calculateTotalPrice(orderMenus);
        OutputView.printTotalOrderAmount(totalOrderAmount);

        String giveawayMenu = christmasService.getGiveawayMenu(totalOrderAmount);
        OutputView.printGiveawayMenu(giveawayMenu);

        int totalBenefitAmount = calculateBenefit(expectedDate, orderMenus, giveawayMenu, totalOrderAmount);
        OutputView.printTotalBenefitAmount(totalBenefitAmount);
        OutputView.printExpectedPaymentAmount(totalOrderAmount - totalBenefitAmount);

        String eventBadge = calculateEventBadge(totalBenefitAmount).getDisplayName();
        OutputView.printEventBadge(eventBadge);
    }

    private List<OrderMenu> processMenuInput(List<String> menuInput) {
        return menuInput.stream()
                .map(menuOrder -> menuOrder.split(HYPHEN_SEPARATOR))
                .filter(menuParts -> menuParts.length == SEPARATE_COUNT)
                .map(this::parseMenuParts)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private OrderMenu parseMenuParts(String[] menuParts) {
        String menuName = menuParts[0].trim();
        try {
            int menuCount = Integer.parseInt(menuParts[1].trim());
            int menuPrice = getMenuPriceFromEnum(menuName);
            return new OrderMenu(menuName, menuCount, menuPrice);
        } catch (NumberFormatException e) {
            System.out.println(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
            return null;
        }
    }

    private int getMenuPriceFromEnum(String menuName) {
        Map<String, Integer> menuPriceMap = new HashMap<>();
        for (Appetizer appetizer : Appetizer.values()) {
            menuPriceMap.put(appetizer.getName(), appetizer.getPrice());
        }
        for (Beverage beverage : Beverage.values()) {
            menuPriceMap.put(beverage.getName(), beverage.getPrice());
        }
        for (Dessert dessert : Dessert.values()) {
            menuPriceMap.put(dessert.getName(), dessert.getPrice());
        }
        for (Main main : Main.values()) {
            menuPriceMap.put(main.getName(), main.getPrice());
        }
        return menuPriceMap.getOrDefault(menuName, DEFAULT_VALUE);
    }

    private void printMenuMessageAndOrderMenus(List<OrderMenu> orderMenus) {
        OutputView.printMenuMessage();
        validateOrderMenus(orderMenus);
        validateTotalMenuCount(orderMenus);
        printOrderMenus(orderMenus);
    }

    private void validateOrderMenus(List<OrderMenu> orderMenus) {
        boolean containsNonBeverage = orderMenus.stream()
                .anyMatch(orderMenu -> !MenuValidator.isBeverage(orderMenu.getMenuName()));

        if (!containsNonBeverage) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }
    }

    private void validateTotalMenuCount(List<OrderMenu> orderMenus) {
        int totalMenuCount = orderMenus.stream()
                .mapToInt(OrderMenu::getMenuCount)
                .sum();

        if (totalMenuCount > TOTAL_COUNT_MENU) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }
    }

    private void printOrderMenus(List<OrderMenu> orderMenus) {
        for (OrderMenu orderMenu : orderMenus) {
            validateMenuCount(orderMenu);
            OutputView.printMenu(orderMenu.getMenuName(), orderMenu.getMenuCount());
        }
    }

    private void validateMenuCount(OrderMenu orderMenu) {
        int menuCount = orderMenu.getMenuCount();
        if (menuCount < DEFAULT_VALUE) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }

        if (menuCount == DEFAULT_VALUE) {
            throw new IllegalArgumentException(MenuErrorMessage.INVALID_MENU_MESSAGE.getMessage());
        }
    }

    private int calculateGiveawayDiscount(String giveawayMenu) {
        int giveawayDiscount = DEFAULT_VALUE;
        if (Objects.equals(giveawayMenu, CHAMPAGNE_MESSAGE)) {
            giveawayDiscount = CHAMPAGNE_COST;
        }
        return giveawayDiscount;
    }

    private int calculateTotalBenefitAmount(int christmasDiscount, int weekdayDiscount, int weekendDiscount,
                                            int specialDayDiscount, int giveawayDiscount) {
        return christmasDiscount + weekdayDiscount + weekendDiscount + specialDayDiscount + giveawayDiscount;
    }

    private int calculateBenefit(int expectedDate, List<OrderMenu> orderMenus, String giveawayMenu, int totalPrice) {
        int christmasDiscount = christmasService.calculateChristmasDiscount(expectedDate, totalPrice);
        int weekdayDiscount = christmasService.calculateWeekdayDiscount(expectedDate, orderMenus, totalPrice);
        int weekendDiscount = christmasService.calculateWeekendDiscount(expectedDate, orderMenus, totalPrice);
        int specialDayDiscount = christmasService.calculateSpecialDayDiscount(expectedDate, totalPrice);
        int giveawayDiscount = calculateGiveawayDiscount(giveawayMenu);

        OutputView.printBenefitDetailMessage();

        int[] discounts = {christmasDiscount, weekdayDiscount, weekendDiscount, specialDayDiscount, giveawayDiscount};
        printBenefitDetails(discounts);

        return calculateTotalBenefitAmount(christmasDiscount, weekdayDiscount, weekendDiscount, specialDayDiscount,
                giveawayDiscount);
    }

    private void printBenefitDetails(int[] discounts) {
        Benefit[] benefits = Benefit.values();
        int totalDiscount = DEFAULT_VALUE;

        for (int i = 0; i < discounts.length; i++) {
            if (discounts[i] > 0) {
                OutputView.printBenefitDetails(benefits[i].getDisplayName(), discounts[i]);
                totalDiscount += discounts[i];
            }
        }

        if (totalDiscount == DEFAULT_VALUE) {
            OutputView.printNoBenefitMessage();
        }
    }

    private EventBadge calculateEventBadge(int totalBenefitAmount) {
        if (totalBenefitAmount >= SANTA_STANDARD) {
            return EventBadge.SANTA;
        }
        if (totalBenefitAmount >= TREE_STANDARD) {
            return EventBadge.TREE;
        }
        if (totalBenefitAmount >= STAR_STANDARD) {
            return EventBadge.STAR;
        }
        return EventBadge.NONE;
    }
}
