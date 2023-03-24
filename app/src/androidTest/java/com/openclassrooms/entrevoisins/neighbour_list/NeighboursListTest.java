
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
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
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    /**
     * We ensure that our recyclerviews is displaying at least on item each
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        // for the fragment Neighbours
        onView(withIndex(withId(R.id.list_neighbours),0))
                .check(matches(hasMinimumChildCount(1)));

        //for the fragment Favorite
        onView(withIndex(withId(R.id.list_neighbours),1))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {

        // Given : We remove the element at position 2 -- Fragment Neighbours
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEM_COUNT_NEIGHBOURS_LIST));
        // When perform a click on a delete icon -- Fragment Neighbours
        onView(withIndex(withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11 - Fragment Neighbours
        onView(withIndex(withId(R.id.list_neighbours),0)).check(withItemCount(ITEM_COUNT_NEIGHBOURS_LIST-1));

        // On fragment Favorite -- remove the element at position 1
        onView(withIndex(withId(R.id.list_neighbours),1)).check(withItemCount(ITEM_COUNT_FAVORITE_LIST));
        onView(withIndex(withId(R.id.list_neighbours), 1)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(ITEM_COUNT_FAVORITE_LIST-1));
    }
}