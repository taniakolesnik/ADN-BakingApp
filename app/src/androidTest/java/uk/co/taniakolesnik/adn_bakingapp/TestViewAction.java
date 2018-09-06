package uk.co.taniakolesnik.adn_bakingapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by tetianakolesnik on 06/09/2018.
 */

public class TestViewAction {

    public static ViewAction clickRecycletItemWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click the first item in recipe list";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.findViewById(id);
                view.performClick();
            }
        };
    }

}
