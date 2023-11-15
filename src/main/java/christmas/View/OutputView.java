package christmas.View;

public class OutputView {
    public static void printMenuMessage() {
        System.out.println("<주문 메뉴>");
    }

    public static void printMenu(String menuName, int menuCount) {
        System.out.println(menuName + " " + menuCount + "개");
    }

    public static void printTotalOrderAmount(int orderAmount) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(orderAmount + "원");
    }

    public static void printGiveawayMenu(String giveawayMenu) {
        System.out.println("<증정 메뉴>");
        System.out.println(giveawayMenu);
    }

    public static void printBenefitDetailMessage() {
        System.out.println("<혜택 내역>");
    }

    public static void printBenefitDetails(String benefitInfo, int benefitAmount) {
        System.out.println(benefitInfo + ": -" + benefitAmount + "원");

    }

    public static void printNoBenefitMessage() {
        System.out.println("없음");
    }

    public static void printTotalBenefitAmount(int benefitAmount) {
        System.out.println("<총혜택 금액>");
        System.out.println("-" + benefitAmount + "원");
    }

    public static void printExpectedPaymentAmount(int expectPayment) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(expectPayment + "원");
    }

    public static void printEventBadge(String badge) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(badge);
    }
}
