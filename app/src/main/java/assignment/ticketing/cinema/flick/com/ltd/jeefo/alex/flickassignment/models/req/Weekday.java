package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 13/03/18.
 */

/**
 * An enum used for managing the weekdays and the offer available for each one
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
        THURSDAY.freeExtraTickets = 2; // the only advantage to date

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

    /**
     * Used for getting the weekday based on the equivalent integer
     * @param value the integer value for a given day (0 is Monday ... 6 is Sunday)
     * @return the Weekday for the int value
     */
    public static Weekday weekdayForValue(int value) {
        return (Weekday) map.get(value);
    }

    /**
     * @return The percentage discount for a given Weekday
     */
    public double getPercentageDiscount() {
        return percentageDiscount;
    }

    /**
     * @return The free extra tickets(for each ticket purchased) for a given Weekday
     */
    public int getFreeExtraTickets() {
        return freeExtraTickets;
    }
}
