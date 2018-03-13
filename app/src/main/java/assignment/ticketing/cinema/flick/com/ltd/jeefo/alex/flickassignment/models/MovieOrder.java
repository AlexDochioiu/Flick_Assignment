package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models;

import java.io.Serializable;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.TicketExtra;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.Weekday;

/**
 * Created by Alex on 13/03/18.
 */

public class MovieOrder implements Serializable {
    private final Movie movie;
    private final Weekday weekday;
    private final int flagsApplied;
    private final int numberOfTickets;

    public double getTotalSaving() {
        return getPriceWithNoDiscounts() * getNumberOfTicketsAfterOfferIsApplied() -
                getTotalPriceAfterDiscount();
    }

    public int getNumberOfTicketsAfterOfferIsApplied() {
        return numberOfTickets + (numberOfTickets * weekday.getFreeExtraTickets());
    }

    public double getTotalPriceAfterDiscount() {
        return getPriceWithNoDiscounts() * (1 - weekday.getPercentageDiscount()) * numberOfTickets;
    }

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

    public MovieOrder(Movie movie, Weekday weekday, int flagsApplied, int numberOfTickets) {
        this.movie = movie;
        this.weekday = weekday;
        this.flagsApplied = flagsApplied;
        this.numberOfTickets = numberOfTickets;
    }

    public Movie getMovie() {
        return movie;
    }

    public Weekday getWeekday() {
        return weekday;
    }
}
