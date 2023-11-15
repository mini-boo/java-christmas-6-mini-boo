package christmas.Domain.Calendar;

public enum Weekend {
    DAY_1(1),
    DAY_2(2),
    DAY_3(8),
    DAY_4(9),
    DAY_5(15),
    DAY_6(16),
    DAY_7(22),
    DAY_8(23),
    DAY_9(29),
    DAY_10(30);

    private final int day;

    Weekend(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
