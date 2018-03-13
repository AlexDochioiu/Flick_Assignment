package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.R;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.Movie;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.MovieOrder;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.TicketExtra;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.req.Weekday;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by Alex on 13/03/18.
 */

/**
 * A simple {@link Fragment} subclass used for finishing a movie order. We get here after a
 * movie is selected in {@link SelectMovieFragment}. Here we select the day for the movie and
 * the number of tickets we desire to purchase
 */
public class SelectMovieDetailsFragment extends Fragment {

    public static final String TAG = "SelectMovieDetails";
    public static final String FRAGMENT_NAME = "SELECT_MOVIE_DETAILS_FRAGMENT";

    public static final String MOVIE_BUNDLE = "assignment.ticketing.movie";
    public static final String MOVIE_ORDER_BUNDLE = "assignment.ticketing.movie.order";

    @BindView(R.id.number_of_tickets_field)
    TextInputEditText numberOfTicketsField;
    @BindView(R.id.weekday_spinner)
    Spinner weekdaySpinner;
    @BindView(R.id.checkBoxReal3D)
    CheckBox checkBoxReal3D;
    @BindView(R.id.llReal3D)
    LinearLayout llReal3D;
    @BindView(R.id.checkBoxImax)
    CheckBox checkBoxImax;
    @BindView(R.id.llImax)
    LinearLayout llImax;
    @BindView(R.id.tvTotalPriceNewTickets)
    TextView tvTotalPriceNewTickets;
    @BindView(R.id.tvSavingsNewTickets)
    TextView tvSavingsNewTickets;
    @BindView(R.id.svAddMovieDetails)
    ScrollView svAddMovieDetails;
    @BindView(R.id.close_new_tickets)
    FrameLayout closeNewTickets;
    @BindView(R.id.accept_new_tickets)
    FrameLayout acceptNewTickets;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    Unbinder unbinder;

    private Movie movie;
    private int selectedDay = 0;
    private int numberOfTickets = 0;
    private int flagsApplied = 0;

    private MovieOrder movieOrder;

    public SelectMovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_movie_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        OverScrollDecoratorHelper.setUpOverScroll(svAddMovieDetails);

        Bundle bundle = getArguments();

        if (bundle != null) {
            movie = (Movie) bundle.getSerializable(MOVIE_BUNDLE);
            if (movie != null) {
                // If there's a movie poster, set it to the image view
                if (movie.hasPoster()) {
                    Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop().into(profileImage);
                }
            } else {
                Log.e(TAG, "Did not receive movie through bundle!");
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        } else {
            Log.e(TAG, "Did not receive bundle!");
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }

        // When a new day is selected, check again for discounts and offers.
        // TODO: This should actually use dates, not weekdays; However, I think this is good enough for this assignment
        weekdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = position;
                if (numberOfTicketsField.getText() != null &&
                        !numberOfTicketsField.getText().toString().equals("")) {
                    updatePriceAndSaving();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // I could actually add the checkboxes programmatically instead of just showing and hiding
        // the ones added in the xml layout
        setUpCheckBox(TicketExtra.IMAX.getFlag(), checkBoxImax, llImax);
        setUpCheckBox(TicketExtra.Real3D.getFlag(), checkBoxReal3D, llReal3D);
        numberOfTicketsField.setText("1");

        updatePriceAndSaving();

        return view;
    }

    /**
     * Method used for displaying the available checkbox for the extras that apply to our movie.
     * It also sets the listener for the checkbox to update the price when it is pressed.
     * @param flag the flag that is being changed when this checkbox is pressed
     * @param checkBox the checkbox we are registering the listener to
     * @param llCheckBox the linear layout that we would hide if the checkbox does not apply to
     *                   the movie we are dealing with
     */
    private void setUpCheckBox(final int flag, CheckBox checkBox, LinearLayout llCheckBox) {
        if ((movie.getAvailableExtras() & flag) != 0) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    flagsApplied = flagsApplied ^ flag;
                    updatePriceAndSaving();
                }
            });
        } else {
            llCheckBox.setVisibility(View.GONE);
        }
    }

    /**
     * Method used for updating the TextViews with the final price and total saving
     */
    private void updatePriceAndSaving() {
        movieOrder = new MovieOrder(movie, Weekday.weekdayForValue(selectedDay), flagsApplied, numberOfTickets);

        tvTotalPriceNewTickets.setText(getResources().getString(
                R.string.total_price_new_tickets,
                movieOrder.getTotalPriceAfterDiscount(),
                movieOrder.getNumberOfTicketsAfterOfferIsApplied())
        );

        tvSavingsNewTickets.setText(getResources().getString(
                R.string.today_saving_new_tickets,
                movieOrder.getTotalSaving()
        ));
    }

    /**
     * A listener for when the number of tickets is changed by the user
     * @param text the new entry for the number of tickets field
     */
    @OnTextChanged(R.id.number_of_tickets_field)
    public void onTicketsNumberChanged(@Nullable CharSequence text) {
        if (text == null || text.toString().equals("")) {
            numberOfTickets = 0;
        } else {
            try {
                numberOfTickets = Integer.parseInt(text.toString());
            } catch (NumberFormatException e) {
                // theoretically we should never end up here cause I limited the input for this
                // field to be only digits
                Log.e(TAG, "Failed to parse string to int: " + text.toString());
                Toast.makeText(getContext(), "Please enter a valid number of tickets", Toast.LENGTH_SHORT).show();
                numberOfTickets = 0;
            }
        }
        updatePriceAndSaving();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Listener for when the close button is pressed. It returns us to the main activity.
     */
    @OnClick(R.id.close_new_tickets)
    public void onCloseNewTickets() {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    /**
     * Listener for when we press the accept button (top right). If all entries are valid, we
     * put the order into a bundle and return it to the main activity
     */
    @OnClick(R.id.accept_new_tickets)
    public void onAcceptNewTickets() {
        if (numberOfTicketsField.getText() == null || numberOfTicketsField.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please enter a valid number of tickets", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(MOVIE_ORDER_BUNDLE, movieOrder);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }
}
