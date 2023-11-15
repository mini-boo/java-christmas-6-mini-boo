package christmas.Domain.Calendar;

public enum SpecialDay {
    DAY_1(3),
    DAY_2(10),
    DAY_3(17),
    DAY_4(24),
    DAY_5(25),
    DAY_6(31);

    private final int day;

    SpecialDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}

