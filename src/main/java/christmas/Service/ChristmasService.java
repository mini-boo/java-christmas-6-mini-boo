package christmas.Service;

import christmas.Constant.ServiceConstant;
import christmas.Domain.Calendar.SpecialDay;
import christmas.Domain.Calendar.WeekDay;
import christmas.Domain.Calendar.Weekend;
import christmas.Domain.Menu.Dessert;
import christmas.Domain.Menu.Main;
import christmas.Domain.OrderMenu;
import java.util.List;

public class ChristmasService implements ServiceConstant {
    public int calculateTotalPrice(List<OrderMenu> orderList) {
        int totalPrice = DEFAULT_VALUE;
        for (OrderMenu orderMenu : orderList) {
            totalPrice += (orderMenu.getMenuPrice() * orderMenu.getMenuCount());
        }

        return totalPrice;
    }

    public String getGiveawayMenu(int totalPrice) {
        if (totalPrice >= GIVEAWAY_STANDARD_PRICE) {
            return CHAMPAGNE_MESSAGE;
        }
        return NONE_MESSAGE;
    }

    public int calculateChristmasDiscount(int expectedDate, int totalPrice) {
        if (totalPrice < MINIMUM_PRICE) {
            return DEFAULT_VALUE;
        }

        int discountAmount = DEFAULT_VALUE;

        if (expectedDate >= MINIMUM_CHRISTMAS_DISCOUNT_DATE && expectedDate <= MAXIMUM_CHRISTMAS_DISCOUNT_DATE) {
            int daysUntilChristmas = expectedDate - 1;

            discountAmount = DISCOUNT_BASE + (DISCOUNT_PER_DAY * daysUntilChristmas);
        }
        return discountAmount;
    }

    public int calculateWeekdayDiscount(int expectedDate, List<OrderMenu> orderMenus, int totalPrice) {
        if (totalPrice < MINIMUM_PRICE) {
            return DEFAULT_VALUE;
        }

        int weekdayDiscount = DEFAULT_VALUE;

        if (isWeekday(expectedDate)) {
            weekdayDiscount = calculateDessertDiscount(orderMenus);
        }

        return weekdayDiscount;
    }

    private int calculateDessertDiscount(List<OrderMenu> orderMenus) {
        int dessertDiscount = DEFAULT_VALUE;

        for (OrderMenu orderMenu : orderMenus) {
            if (isDessertMenu(orderMenu)) {
                dessertDiscount += orderMenu.getMenuCount() * DISCOUNT_COST;
            }
        }

        return dessertDiscount;
    }

    public int calculateWeekendDiscount(int expectedDate, List<OrderMenu> orderMenus, int totalPrice) {
        if (totalPrice < MINIMUM_PRICE) {
            return DEFAULT_VALUE;
        }

        int weekendDiscount = DEFAULT_VALUE;

        if (isWeekend(expectedDate)) {
            weekendDiscount = calculateMainDiscount(orderMenus);
        }

        return weekendDiscount;
    }

    private int calculateMainDiscount(List<OrderMenu> orderMenus) {
        int mainDiscount = DEFAULT_VALUE;

        for (OrderMenu orderMenu : orderMenus) {
            if (isMainMenu(orderMenu)) {
                mainDiscount += orderMenu.getMenuCount() * DISCOUNT_COST;
            }
        }

        return mainDiscount;
    }

    public int calculateSpecialDayDiscount(int expectedDate, int totalPrice) {
        if (totalPrice < MINIMUM_PRICE) {
            return DEFAULT_VALUE;
        }

        if (isSpecialDay(expectedDate)) {
            return SPECIAL_DAY_DISCOUNT;
        }
        return DEFAULT_VALUE;
    }

    private boolean isWeekday(int expectedDate) {
        return isInRange(expectedDate, WeekDay.WEEK_2) ||
                isInRange(expectedDate, WeekDay.WEEK_3) ||
                isInRange(expectedDate, WeekDay.WEEK_4) ||
                isInRange(expectedDate, WeekDay.WEEK_5);
    }

    private boolean isInRange(int expectedDate, WeekDay weekDay) {
        return expectedDate >= weekDay.getStart() && expectedDate <= weekDay.getEnd();
    }

    private boolean isWeekend(int expectedDate) {
        for (Weekend weekendDay : Weekend.values()) {
            if (expectedDate == weekendDay.getDay()) {
                return true;
            }
        }
        return false;
    }

    private boolean isSpecialDay(int expectedDate) {
        for (SpecialDay specialDay : SpecialDay.values()) {
            if (expectedDate == specialDay.getDay()) {
                return true;
            }
        }
        return false;
    }

    private boolean isDessertMenu(OrderMenu orderMenu) {
        return orderMenu.getMenuName().equals(Dessert.CHOCOLATE_CAKE.getName()) ||
                orderMenu.getMenuName().equals(Dessert.ICE_CREAM.getName());
    }

    private boolean isMainMenu(OrderMenu orderMenu) {
        return orderMenu.getMenuName().equals(Main.T_BONE_STEAK.getName()) ||
                orderMenu.getMenuName().equals(Main.BBQ_RIBS.getName()) ||
                orderMenu.getMenuName().equals(Main.SEAFOOD_PASTA.getName()) ||
                orderMenu.getMenuName().equals(Main.CHRISTMAS_PASTA.getName());
    }

}
