package uk.co.taniakolesnik.adn_bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by tetianakolesnik on 06/09/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainRecipeListLoadedAndClickableCheck {

    @Rule public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test public void checkRecipeNameShownAndClickable(){
        // find the first recipe name text view
        // check if name is Nutella Pie
        onView(withId(R.id.recipes_recyclerView))
                .check(matches(hasDescendant(withText("Nutella Pie"))));

        // check if recipe name is clickable
        onView(withId(R.id.recipes_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, TestViewAction.clickRecycletItemWithId(R.id.recipes_name)));
        // check if recipe click open details activity

    }



}
