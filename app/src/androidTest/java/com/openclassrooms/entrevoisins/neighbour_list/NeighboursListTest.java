
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.MatcherUtils.withIndex;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;

import static org.hamcrest.core.IsNull.notNullValue;


import android.view.View;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEM_COUNT_NEIGHBOURS_LIST = 12;
    private static int ITEM_COUNT_FAVORITE_LIST = 1;


    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        Intents.init();
    }


    /**
     * We ensure that our recyclerviews are displaying at least on item each
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        // for the fragment Neighbours
        onView(withIndex(withId(R.id.list_neighbours),0))
                .check(matches(hasMinimumChildCount(1)));

        //for the fragment Favorites
        onView(withIndex(withId(R.id.list_neighbours),1))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item in neighbours list, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withIndex(withId(R.id.list_neighbours),0))
                .check(withItemCount(ITEM_COUNT_NEIGHBOURS_LIST));
        // When perform a click on a delete icon
        onView(withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withIndex(withId(R.id.list_neighbours),0))
                .check(withItemCount(ITEM_COUNT_NEIGHBOURS_LIST-1));
    }

    /**
     * When we delete an item in FAVORITE neighbours list, the item is no more shown
     */
    @Test
    public void myFavoriteNeighboursList_deleteAction_shouldRemoveItem(){
        // Open the fragment favorite
        onView(withText("Favorites")).perform(click());

        onView(withIndex(withId(R.id.list_neighbours),1))
                .check(withItemCount(ITEM_COUNT_FAVORITE_LIST));
        onView(withIndex(withId(R.id.list_neighbours),1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withIndex(withId(R.id.list_neighbours),1))
                .check(withItemCount(ITEM_COUNT_FAVORITE_LIST-1));
    }

    /**
     * When we delete an item which is favorite, from neighbours list, the item is no more shown in both list
     */
    @Test
    public void neighbourFavorite_deleteFromNeighboursList_shouldBeRemoveFromBothList(){
        onView(withIndex(withId(R.id.list_neighbours),0))
                .check(withItemCount(ITEM_COUNT_NEIGHBOURS_LIST));
        onView((withIndex(withId(R.id.list_neighbours),1)))
                .check(withItemCount(ITEM_COUNT_FAVORITE_LIST));

        // delete from neighbours list, item position 0 which is favorite
        onView(withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        //no more shown in My Neighbours Fragment
        onView(withIndex(withId(R.id.list_neighbours),0)).check(withItemCount(ITEM_COUNT_NEIGHBOURS_LIST -1));
        // no more shown in Favorite Fragment
        onView(withText("Favorites")).perform(click());
        onView(withIndex(withId(R.id.list_neighbours),1)).check(withItemCount(ITEM_COUNT_FAVORITE_LIST -1));
    }

    /**
     * When we click on an item, detail activity is launch
     */
    @Test
    public void onClickItem_launchDetailActivity_isSuccessful(){
        // Click on recycleView item 2
        onView(withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        //Then the DetailActivity is shown
        intended(hasComponent(DetailNeighbourActivity.class.getName()));
    }

    @Test
    public void onDetailActivity_displayNeighbourClicked(){
        // Click on item 3, which is Chloe
        onView(withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        // Display the right name on DetailActivity
        onView(withId(R.id.neighbourSelectedName))
                .check(matches(withText("Chloé")));
    }

    @Test
    public void favoriteFragment_isDisplayingOnlyFavoriteNeighbours(){
        // Click on recycleView item 2 from NeighbourFragment, which is Jack
        onView(withIndex(withId(R.id.list_neighbours), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // Then on Detail Activity, click on star FAB to make jack a favorite
        onView(withId(R.id.favoriteBtn)).perform(click());
        // Go back to ListNeighbour Activity and click on Favorites Tab to see if it contains 1 neighbour in addition
        onView(withId(R.id.backToNeighbourList)).perform(click());
        onView(withText("Favorites")).perform(click());
        onView(withIndex(withId(R.id.list_neighbours),1))
                .check(withItemCount(ITEM_COUNT_FAVORITE_LIST + 1));
    }
}