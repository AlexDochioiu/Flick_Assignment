package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 13/03/18.
 */

public enum TicketType {
    STANDARD(7.9) {
        @Override
        public String toString() { return "Standard"; }
    },
    CONCESSION(5.4) {
        @Override
        public String toString() { return "Concession"; }
    };

    private double value;
    private static Map map = new HashMap();

    TicketType(double value) {
        this.value = value;
    }

    static {
        for (TicketType type : TicketType.values()) {
            //noinspection unchecked
            map.put(type.value, type);
        }
    }

    public static TicketType typeOfPrice(double value) {
        return (TicketType) map.get(value);
    }

    public double getPrice() {
        return value;
    }
}
