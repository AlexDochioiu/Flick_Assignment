package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.AddMovieActivity;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.R;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.adapters.MovieRecyclerViewAdapter;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.Movie;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.TicketExtra;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.TicketType;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils.IItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * A simple {@link Fragment} subclass used for displaying a RecyclerView Adapter containing all the
 * movies and the base price for them. It allows us to select a movie and continue with the order.
 */
public class SelectMovieFragment extends Fragment implements IItemClickListener {

    public static final String FRAGMENT_NAME = "SELECT_MOVIE_FRAGMENT";
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    Unbinder unbinder;

    private MovieRecyclerViewAdapter movieAdapter;

    public SelectMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_movie, container, false);
        unbinder = ButterKnife.bind(this, view);

        movieAdapter = new MovieRecyclerViewAdapter(getContext(), this, getMockMoviesList());

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(movieAdapter);

        OverScrollDecoratorHelper.setUpOverScroll(rvMovies, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        return view;
    }

    /**
     * This is just for demonstration purposes
     * @return a mock list of movies to be added to adapter
     */
    private List<Movie> getMockMoviesList() {
        List<Movie> movies = new ArrayList<>();
        int withNoExtra = 0;
        int with3DExtra = TicketExtra.Real3D.getFlag();
        int withIMAXExtra = TicketExtra.IMAX.getFlag();
        int withBothExtra = TicketExtra.Real3D.getFlag() | TicketExtra.IMAX.getFlag();

        movies.add(new Movie("277834", "Moana", "In Ancient Polynesia, when a terrible curse incurred by Maui reaches an impetuous Chieftain's daughter's island, she answers the Ocean's call to seek out the demigod to set things right.", "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", TicketType.STANDARD, withIMAXExtra));
        movies.add(new Movie("121856", "Passengers", "A spacecraft traveling to a distant colony planet and transporting thousands of people has a malfunction in its sleep chambers. As a result, two passengers are awakened 90 years early.", "/5gJkVIVU7FDp7AfRAbPSvvdbre2.jpg", TicketType.STANDARD, withBothExtra));
        movies.add(new Movie("330459", "Assassin's Creed", "Lynch discovers he is a descendant of the secret Assassins society through unlocked genetic memories that allow him to relive the adventures of his ancestor, Aguilar, in 15th Century Spain. After gaining incredible knowledge and skills he’s poised to take on the oppressive Knights Templar in the present day.", "/tIKFBxBZhSXpIITiiB5Ws8VGXjt.jpg", TicketType.STANDARD, withIMAXExtra));
        movies.add(new Movie("283366", "Rogue One: A Star Wars Story", "A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.", "/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg", TicketType.CONCESSION, withNoExtra));
        movies.add(new Movie("313369", "La La Land", "Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.", "/ylXCdC106IKiarftHkcacasaAcb.jpg", TicketType.STANDARD, with3DExtra));
        return movies;
    }

    /**
     * This is called by the RecyclerView Adapter once a movie is selected. We then pass the
     * selected movie to the Activity and continue with the order from there.
     * @param itemPosition the position in the RV Adapter for the selected movie
     */
    @Override
    public void onItemSelected(int itemPosition) {
        ((AddMovieActivity) getActivity()).OnMovieSelected(movieAdapter.getMovie(itemPosition));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
