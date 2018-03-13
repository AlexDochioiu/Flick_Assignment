package assignment.ticketing.cinema.flick.com.ltd.jeefo.alex.flickassignment.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;


/**
 * Created by Alex on 13/03/18.
 */

public class FragmentCustomUtils {
    private static final String LOG_TAG = "FragmentCustomUtils";
    @Nullable
    public static Fragment createFragment(
            @NonNull FragmentManager fragmentManager,
            @NonNull Class fragmentClass,
            @Nullable String fragmentTag,
            int containerViewId,
            @Nullable Bundle data,
            boolean addToBackStack
    ) {
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if (data != null) {
                fragment.setArguments(data);
            }

            if (addToBackStack) {
                fragmentManager.beginTransaction()
                        .replace(containerViewId, fragment, fragmentTag)
                        .addToBackStack(null)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(containerViewId, fragment, fragmentTag)
                        .commit();
            }
            Log.i(LOG_TAG, String.format("Created and replaced to fragment '%s'", fragmentTag));

        } catch (InstantiationException e) {
            Log.e(LOG_TAG, String.format("Failed to create fragment '%s' : %s", fragmentTag, e.getMessage()));
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, String.format("Failed to create fragment '%s' : %s", fragmentTag, e.getMessage()));
            e.printStackTrace();
        }

        return fragment;
    }
}