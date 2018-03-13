package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils;

/**
 * Created by Alex on 13/03/18.
 */

/**
 * Simple interface for a long click listener when an item is being long clicked in a list of any kind
 */
public interface IItemLongClickListener {

    /**
     * The method to be called when a long click event happens
     * @param itemPosition the position of the long clicked item
     */
    void onLongClick(int itemPosition);
}
