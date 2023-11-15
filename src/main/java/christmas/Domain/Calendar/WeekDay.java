package christmas.Domain.Calendar;

public enum WeekDay {
    WEEK_2(4, 7),
    WEEK_3(11, 14),
    WEEK_4(18, 21),
    WEEK_5(25, 28);

    private final int start;
    private final int end;

    WeekDay(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
