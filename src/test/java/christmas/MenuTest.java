package christmas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import christmas.Domain.OrderMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuTest {
    
    @Test
    void createOrderMenu() {
        String validMenuName = "시저샐러드";
        int menuCount = 2;
        int menuPrice = 8000;

        OrderMenu orderMenu = assertDoesNotThrow(() -> new OrderMenu(validMenuName, menuCount, menuPrice));

        assertEquals(validMenuName, orderMenu.getMenuName());
        assertEquals(menuCount, orderMenu.getMenuCount());
        assertEquals(menuPrice, orderMenu.getMenuPrice());
    }

    @DisplayName("메뉴에 없는 주문을 하면 예외가 발생한다.")
    @Test
    void createInvalidOrderMenu() {
        String menuName = "뱅쇼";
        int menuCount = 1;
        int menuPrice = 10;

        assertThrows(IllegalArgumentException.class, () -> {
            new OrderMenu(menuName, menuCount, menuPrice);
        });
    }
}
