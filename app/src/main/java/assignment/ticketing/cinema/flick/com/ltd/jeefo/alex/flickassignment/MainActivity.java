package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.adapters.OrderedMovieRecyclerViewAdapter;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.fragments.SelectMovieDetailsFragment;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.MovieOrder;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils.IItemLongClickListener;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils.ReturnRequestCodes;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends AppCompatActivity implements IItemLongClickListener {

    private static final String TAG = "MainActivity";
    OrderedMovieRecyclerViewAdapter orderedMovieAdapter;
    @BindView(R.id.rvOrderedMovies)
    RecyclerView rvOrderedMovies;
    @BindView(R.id.tvOrderPrice)
    TextView tvOrderPrice;
    @BindView(R.id.tvOrderSaving)
    TextView tvOrderSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        orderedMovieAdapter = new OrderedMovieRecyclerViewAdapter(this, this);

        rvOrderedMovies.setHasFixedSize(true);
        rvOrderedMovies.setLayoutManager(new LinearLayoutManager(this));
        rvOrderedMovies.setAdapter(orderedMovieAdapter);

        OverScrollDecoratorHelper.setUpOverScroll(rvOrderedMovies, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        updateTotalPriceAndDiscount();
    }

    /**
     * Called when the floating action button is pressed. Used for adding new tickets.
     */
    @OnClick(R.id.fabAddTicket)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddMovieActivity.class);
        startActivityForResult(intent, ReturnRequestCodes.ADD_NEW_TICKET_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ReturnRequestCodes.ADD_NEW_TICKET_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        MovieOrder movieOrder = (MovieOrder) bundle.getSerializable(SelectMovieDetailsFragment.MOVIE_ORDER_BUNDLE);
                        if (movieOrder != null) {
                            addMovieOrderToAdapter(movieOrder);
                        } else {
                            Log.e(TAG, "Nu movie order returned when one was expected");
                        }
                    } else {
                        Log.e(TAG, "Nu bundle was returned when one was expected");
                    }
                }
                break;
            default:
                Log.e(TAG, "Unknown request code for on activity result: " + Integer.toString(requestCode));
                break;
        }
    }

    /**
     * Once we receive the order for a movie, we add it to our recycler view and update the
     * the total price and discount
     * @param movieOrder the new movie order to be added
     */
    private void addMovieOrderToAdapter(MovieOrder movieOrder) {
        orderedMovieAdapter.addMovieOrder(movieOrder);
        updateTotalPriceAndDiscount();
    }

    /**
     * Updates the total price and discount
     */
    private void updateTotalPriceAndDiscount() {
        tvOrderPrice.setText(getResources().getString(
                R.string.total_price_new_tickets,
                orderedMovieAdapter.getTotalPrice(),
                orderedMovieAdapter.getTotalTickets()));

        tvOrderSaving.setText(getResources().getString(
                R.string.today_saving_new_tickets,
                orderedMovieAdapter.getTotalSaving()
        ));
    }

    /**
     * Method used for allowing the deletion of a movie order
     * @param itemPosition the position in list of the movie order we want to remove
     */
    @Override
    public void onLongClick(final int itemPosition) {
        final CharSequence options[] = new CharSequence[]{"Remove Entry", "Return"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick the desired action");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        orderedMovieAdapter.removeItem(itemPosition);
                        updateTotalPriceAndDiscount();
                        break;
                    case 1:
                        break;
                }
            }
        });
        builder.show();
    }
}
