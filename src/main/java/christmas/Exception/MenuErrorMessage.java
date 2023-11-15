package christmas.Exception;

public enum MenuErrorMessage {
    INVALID_MENU_MESSAGE("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String message;

    MenuErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
