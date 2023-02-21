package com.connectfour.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.connectfour.app.R;
import com.connectfour.app.Settings;
import com.connectfour.app.controller.ControllerInterface;
import com.connectfour.app.controller.GameController;
import com.connectfour.app.model.GameModel;
import com.connectfour.app.views.GameView;

/**
 * The {@code MainActivity} class is the main activity of the application.
 * It sets the layout of the activity, which is initially empty.
 * When the activity is created, it initializes a {@link GameController} object to control the game and starts a new game.
 * The {@code GameController} creates a {@link GameView} class, which creates all views in the game layout, including the game board.
 * When the user clicks the settings button, the class opens the {@link SettingsActivity}.
 * The class also listens for changes in the game {@link Settings} and updates the game accordingly.
 *
 * @see GameController
 * @see GameView
 */
public class MainActivity extends AppCompatActivity {

    private Settings settings;
    private ControllerInterface controller;

    /**
     * Initializes the activity when it is created.
     * Sets the layout of the activity and starts a new game.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout gameLayout = findViewById(R.id.gameLayout);
        Button settingsButton = findViewById(R.id.settingsButton);

        settings = new Settings(getApplicationContext());
        controller = new GameController(new GameModel(settings), gameLayout);
        controller.restart();

        settingsButton.setOnClickListener(view -> openSettings());
    }

    /**
     * Called when the activity is resumed from a paused state.
     * If the settings have changed, updates the settings and restarts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (settings.hasChanged()) {
            settings.update();
            controller.restart();
        }
    }

    /**
     * Starts the {@link SettingsActivity}.
     */
    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}