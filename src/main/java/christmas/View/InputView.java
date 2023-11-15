package christmas.View;

import camp.nextstep.edu.missionutils.Console;
import christmas.Exception.DateErrorMessage;
import christmas.Validator.DateValidator;
import christmas.Validator.MenuValidator;
import java.util.ArrayList;
import java.util.List;

public class InputView {
    public int readDate() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");

        String input = Console.readLine();

        try {
            int expectedDate = DateValidator.validateInput(input);
            return expectedDate;
        } catch (IllegalArgumentException e) {
            System.out.println(DateErrorMessage.INVALID_DATE_MESSAGE.getMessage());
            return 0;
        }
    }

    public List<String> readMenu() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String input = Console.readLine();
        List<String> menuOrders = new ArrayList<>();
        try {
            MenuValidator.validateMenuInput(input);
            String[] orders = input.split(",");
            for (String order : orders) {
                menuOrders.add(order.trim());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return menuOrders;
    }
}
