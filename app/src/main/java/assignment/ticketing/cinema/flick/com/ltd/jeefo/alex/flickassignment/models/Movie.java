package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.TicketType;

/**
 * Created by Alex on 13/03/18.
 */

/**
 * Class used for managing each Movie
 */
public class Movie implements Serializable {
    private final String id;
    private final String title;
    private final String overview;
    private final String posterPath;

    private final TicketType ticketType;
    private final int availableExtras;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500%s", posterPath);
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public int getAvailableExtras() {
        return availableExtras;
    }

    public boolean hasPoster() {
        return posterPath != null;
    }

    /**
     * Constructor
     * @param id unique id to identify the movie
     * @param title the title of the movie
     * @param overview the overview of the movie
     * @param posterPath the path for obtaining the poster online
     * @param ticketType the type of the ticket available for it (assumes only 1 type is available at once)
     * @param availableExtras the combination of flags for the available extras
     */
    public Movie(
            @NonNull String id,
            @NonNull String title,
            @NonNull String overview,
            @Nullable String posterPath,
            @NonNull TicketType ticketType,
            int availableExtras
    ) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.ticketType = ticketType;
        this.availableExtras = availableExtras;
    }
}
