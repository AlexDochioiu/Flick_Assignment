package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils;

/**
 * Created by Alex on 13/03/18.
 */

/**
 * Simple interface for a click listener when an item is being selected in a list of any kind
 */
public interface IItemClickListener {

    /**
     * Method called when an item is being selected in the list
     * @param itemPosition the position of the selected item
     */
    void onItemSelected(int itemPosition);
}
