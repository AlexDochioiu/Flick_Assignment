package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models;

import java.io.Serializable;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.TicketExtra;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.Weekday;

/**
 * Created by Alex on 13/03/18.
 */

/**
 * Class used for managing the order for a movie
 */
public class MovieOrder implements Serializable {
    private final Movie movie;
    private final Weekday weekday;
    private final int flagsApplied;
    private final int numberOfTickets;

    /**
     * Calculates the total saving based on how much the cost would have been to pay the normal
     * price for all that's being offered with no discounts and free tickets
     * @return the total saving (absolute value)
     */
    public double getTotalSaving() {
        return getPriceWithNoDiscounts() * getNumberOfTicketsAfterOfferIsApplied() -
                getTotalPriceAfterDiscount();
    }

    /**
     * Calculates the total number of tickets that are being offered after the free tickets (if any)
     * are being considered
     * @return the total number of tickets obtained by the user
     */
    public int getNumberOfTicketsAfterOfferIsApplied() {
        return numberOfTickets + (numberOfTickets * weekday.getFreeExtraTickets());
    }

    /**
     * Calculates the total price after offers and discounts are being accounted for
     * @return the total price to be paid
     */
    public double getTotalPriceAfterDiscount() {
        return getPriceWithNoDiscounts() * (1 - weekday.getPercentageDiscount()) * numberOfTickets;
    }

    /**
     * Calculates the total value for what's being offered (assuming no discounts apply and all
     * tickets offered are being paid for). Used for computing the total savings
     * @return the total value of what's being offered
     */
    private double getPriceWithNoDiscounts() {
        double pricePerTicketWithNoDiscount = movie.getTicketType().getPrice();

        // apply the extras as well
        for (TicketExtra extra : TicketExtra.extras) {
            if ((flagsApplied & extra.getFlag()) != 0) {
                pricePerTicketWithNoDiscount += extra.getPrice();
            }
        }

        return pricePerTicketWithNoDiscount;
    }

    /**
     * Constructor
     * @param movie the movie we are creating the order for
     * @param weekday the day in which we are getting the tickets (normally this would be an actual date)
     * @param flagsApplied the extras that are being applied for this order
     * @param numberOfTickets the number of tickets entered by the user (excludes free tickets)
     */
    public MovieOrder(Movie movie, Weekday weekday, int flagsApplied, int numberOfTickets) {
        this.movie = movie;
        this.weekday = weekday;
        this.flagsApplied = flagsApplied;
        this.numberOfTickets = numberOfTickets;
    }

    /**
     * @return the movie for this order
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return the weekday for this order
     */
    public Weekday getWeekday() {
        return weekday;
    }
}
