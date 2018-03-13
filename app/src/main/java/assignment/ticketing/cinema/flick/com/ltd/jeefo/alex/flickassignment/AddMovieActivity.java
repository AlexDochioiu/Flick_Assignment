package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.fragments.SelectMovieDetailsFragment;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.fragments.SelectMovieFragment;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.Movie;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils.FragmentCustomUtils;

public class AddMovieActivity extends AppCompatActivity {

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        showMovieListFragment();
    }

    private void showMovieListFragment() {
        currentFragment = FragmentCustomUtils.createFragment(
                getSupportFragmentManager(),
                SelectMovieFragment.class,
                SelectMovieFragment.FRAGMENT_NAME,
                R.id.flAddMovie,
                null,
                false
        );
    }


    public void OnMovieSelected(Movie movie) {
        Intent intent = new Intent();
        intent.putExtra(SelectMovieDetailsFragment.MOVIE_BUNDLE, movie);

        currentFragment = FragmentCustomUtils.createFragment(
                getSupportFragmentManager(),
                SelectMovieDetailsFragment.class,
                SelectMovieDetailsFragment.FRAGMENT_NAME,
                R.id.flAddMovie,
                intent.getExtras(),
                false
        );
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == null || currentFragment.getTag().equals(SelectMovieFragment.FRAGMENT_NAME)) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            // I can also keep the fragments in the fragment manager and ues the backstack here to get back
            // instead of creating the fragment again
            showMovieListFragment();
        }
    }
}
