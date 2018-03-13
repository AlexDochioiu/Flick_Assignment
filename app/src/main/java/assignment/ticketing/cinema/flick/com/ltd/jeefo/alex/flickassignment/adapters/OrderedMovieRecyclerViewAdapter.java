package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.R;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.Movie;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.models.MovieOrder;
import assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils.IItemLongClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Alex on 13/03/18.
 */

public class OrderedMovieRecyclerViewAdapter extends RecyclerView.Adapter<OrderedMovieRecyclerViewAdapter.ViewHolder>{
    private final List<MovieOrder> movieOrders;
    private final Context context;
    private final IItemLongClickListener itemLongClickListener;

    public OrderedMovieRecyclerViewAdapter(Context context, IItemLongClickListener itemLongClickListener) {
        this.movieOrders = new LinkedList<>();
        this.context = context;
        this.itemLongClickListener = itemLongClickListener;
        Picasso.with(context).setLoggingEnabled(true);
    }

    @Override
    public OrderedMovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordered_movie, parent, false);

        return new OrderedMovieRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderedMovieRecyclerViewAdapter.ViewHolder holder, int position) {
        MovieOrder movieOrder = movieOrders.get(position);
        Movie movie = movieOrder.getMovie();

        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        holder.tvNumberOfTickets.setText(
                String.format(
                        Locale.UK,
                        "Number of Tickets: %d",
                        movieOrder.getNumberOfTicketsAfterOfferIsApplied()
                )
        );
        holder.tvDayOfTickets.setText(movieOrder.getWeekday().toString());

        if (movie.hasPoster()) {
            Log.d("path", movie.getPosterPath());
            Picasso.with(context).load(movie.getPosterPath()).fit().centerCrop().into(holder.ivMovieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movieOrders.size();
    }

    public void addMovieOrder(MovieOrder movieOrder) {
        movieOrders.add(movieOrder);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeItem(int itemPosition) {
        movieOrders.remove(itemPosition);
        notifyItemRemoved(itemPosition);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (MovieOrder movieOrder : movieOrders) {
            totalPrice += movieOrder.getTotalPriceAfterDiscount();
        }
        return totalPrice;
    }

    public int getTotalTickets() {
        int totalTickets = 0;
        for (MovieOrder movieOrder : movieOrders) {
            totalTickets += movieOrder.getNumberOfTicketsAfterOfferIsApplied();
        }
        return totalTickets;
    }

    public double getTotalSaving() {
        double totalSaving = 0;
        for (MovieOrder movieOrder : movieOrders) {
            totalSaving += movieOrder.getTotalSaving();
        }
        return totalSaving;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        @BindView(R.id.ivOrderedMovie)
        CircleImageView ivMovieImage;
        @BindView(R.id.tvOrderedMovieName)
        TextView tvTitle;
        @BindView(R.id.tvOrderedMovieOverview)
        TextView tvOverview;
        @BindView(R.id.tvNumberOfTickets)
        TextView tvNumberOfTickets;
        @BindView(R.id.tvDayOfTickets)
        TextView tvDayOfTickets;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            itemLongClickListener.onLongClick(getAdapterPosition());
            return true;
        }
    }
}