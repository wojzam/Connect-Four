package com.connect_four.app;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.connect_four.app.activities.MainActivity;
import com.connect_four.app.model.Board;
import com.connect_four.app.views.ColumnLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Settings settings = new Settings(activityTestRule.getActivity());
        settings.setSinglePlayerKey(false);
    }

    @Test
    public void shouldDisplayActivityContent() {
        onView(allOf(withText(R.string.player_1_turn), instanceOf(TextView.class))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.new_game), instanceOf(AppCompatButton.class))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.settings), instanceOf(AppCompatButton.class))).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.undo), instanceOf(AppCompatButton.class))).check(matches(isDisplayed()));

        for (int index = 0; index < Board.WIDTH_DEFAULT; index++) {
            onView(allOf(withTagValue(equalTo(index)), instanceOf(ColumnLayout.class))).check(matches(isDisplayed()));
        }
    }

    @Test
    public void shouldChangeTurn_whenTurnFinished() {
        int index = 0;
        onView(allOf(withTagValue(equalTo(index)), instanceOf(ColumnLayout.class))).perform(click());
        onView(allOf(withText(R.string.player_2_turn), instanceOf(TextView.class))).check(matches(isDisplayed()));
    }

}

