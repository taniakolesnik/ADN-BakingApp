package uk.co.taniakolesnik.adn_bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

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

        // click on Ingredients tab
        onView(allOf(withText("Ingredients"),
                isDescendantOfA(withId(R.id.tabLayout)))).perform(click());

        // check Ingredients first item is not empty
        onData(anything())
                .inAdapterView(allOf(withId(R.id.ingredients_listView), isCompletelyDisplayed()))
                .atPosition(0).check(matches(not(withText(""))));

        // back to Instructions tab
        onView(allOf(withText("Instructions"),
                isDescendantOfA(withId(R.id.tabLayout)))).perform(click());

        // click on the first Instructions step
        onView(withId(R.id.steps_recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, TestViewAction.clickRecycletItemWithId(R.id.step_short_description_textView)));

        // check step full description is loaded
        onView(withId(R.id.step_description_textView))
                .check(matches(not(withText(""))));

    }
}
