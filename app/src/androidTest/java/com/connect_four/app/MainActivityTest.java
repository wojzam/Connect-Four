package com.connect_four.app;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.connect_four.app.activities.MainActivity;
import com.connect_four.app.model.Board;
import com.connect_four.app.views.ColumnLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ActivityScenario.launch;
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

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> scenario = launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onActivity(activity -> {
            Settings settings = new Settings(activity);
            settings.setSinglePlayer(false);
        });
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
    public void shouldUpdateText_whenPlayerTurnChanged() {
        onView(allOf(withTagValue(equalTo(0)), instanceOf(ColumnLayout.class))).perform(click());
        waitForUpdate();
        onView(allOf(withText(R.string.player_2_turn), instanceOf(TextView.class))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldUpdateText_whenPlayerTurnChangedTwice() {
        onView(allOf(withTagValue(equalTo(0)), instanceOf(ColumnLayout.class))).perform(click());
        waitForUpdate();
        onView(allOf(withTagValue(equalTo(0)), instanceOf(ColumnLayout.class))).perform(click());
        waitForUpdate();
        onView(allOf(withText(R.string.player_1_turn), instanceOf(TextView.class))).check(matches(isDisplayed()));
    }

    private void waitForUpdate() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

