package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req;

/**
 * Created by Alex on 13/03/18.
 */

public class TicketExtra {
    public final static TicketExtra Real3D = new TicketExtra(0b01, 0.9);
    public final static TicketExtra IMAX = new TicketExtra(0b10, 1.5);

    public final static TicketExtra[] extras = {Real3D, IMAX};


    private int flag;
    private double price;

    public int getFlag() {
        return flag;
    }

    public double getPrice() {
        return price;
    }

    private TicketExtra(int flag, double price) {
        this.flag = flag;
        this.price = price;
    }
}
