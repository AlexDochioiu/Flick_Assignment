package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 13/03/18.
 */

public enum Weekday {
    MONDAY(0), TUESDAY(1), WEDNSEDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(6);

    private double percentageDiscount;
    private int freeExtraTickets;

    static {
        MONDAY.percentageDiscount = 0;
        MONDAY.freeExtraTickets = 0;

        TUESDAY.percentageDiscount = 0;
        TUESDAY.freeExtraTickets = 0;

        WEDNSEDAY.percentageDiscount = 0;
        WEDNSEDAY.freeExtraTickets = 0;

        THURSDAY.percentageDiscount = 0;
        THURSDAY.freeExtraTickets = 2;

        FRIDAY.percentageDiscount = 0;
        FRIDAY.freeExtraTickets = 0;

        SATURDAY.percentageDiscount = 0;
        SATURDAY.freeExtraTickets = 0;

        SUNDAY.percentageDiscount = 0;
        SUNDAY.freeExtraTickets = 0;
    }

    private int value;
    private static Map map = new HashMap();

    Weekday(int value) {
        this.value = value;
    }

    static {
        for (Weekday weekday : Weekday.values()) {
            //noinspection unchecked
            map.put(weekday.value, weekday);
        }
    }

    public static Weekday weekdayForValue(int value) {
        return (Weekday) map.get(value);
    }

    public double getPercentageDiscount() {
        return percentageDiscount;
    }

    public int getFreeExtraTickets() {
        return freeExtraTickets;
    }
}
