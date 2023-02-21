package com.connectfour.app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.connectfour.app.model.Disk;

import java.util.Objects;

import static com.connectfour.app.model.Disk.PLAYER_1;
import static com.connectfour.app.model.Disk.PLAYER_2;

/**
 * The {@code Settings} class is responsible for storing and retrieving the preferences of the application.
 */
public class Settings {

    private static final String SINGLE_PLAYER_KEY = "single_player";
    private static final String DIFFICULTY_KEY = "difficulty";
    private static final String FIRST_TURN_KEY = "first_turn";
    private final SharedPreferences sharedPreferences;
    private boolean singlePlayer;
    private int difficulty;
    private String firstTurn;

    /**
     * This constructor initializes a new {@code Settings} object by getting the default shared preferences of the given context.
     * It also calls the {@link #update} method to set the initial values of the game settings.
     *
     * @param context the context of the application
     */
    public Settings(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        update();
    }

    /**
     * This method updates all the settings by retrieving the values of the preferences from the shared preferences.
     */
    public void update() {
        singlePlayer = sharedPreferences.getBoolean(SINGLE_PLAYER_KEY, true);
        difficulty = sharedPreferences.getInt(DIFFICULTY_KEY, 6);
        firstTurn = sharedPreferences.getString(FIRST_TURN_KEY, "0");
    }

    /**
     * This method checks whether the settings have changed since the last update.
     *
     * @return true if the game settings have changed
     */
    public boolean hasChanged() {
        return singlePlayer != sharedPreferences.getBoolean(SINGLE_PLAYER_KEY, true) ||
                difficulty != sharedPreferences.getInt(DIFFICULTY_KEY, 6) ||
                !Objects.equals(firstTurn, sharedPreferences.getString(FIRST_TURN_KEY, "0"));
    }

    /**
     * @return true if the game is in single-player mode, false if it is in multiplayer
     */
    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    /**
     * Sets the value of the {@link #singlePlayer} preference to the given value.
     * If the given value is true, method sets the game mode to single-player, otherwise it sets it to multiplayer.
     *
     * @param value the new value of the {@link #singlePlayer} preference
     */
    public void setSinglePlayer(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SINGLE_PLAYER_KEY, value);
        editor.apply();
    }

    /**
     * @return the difficulty level of the AI player
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * This method returns the {@link Disk} of the AI player according to the {@link #firstTurn} value.
     *
     * @return the disk of the AI player
     */
    public Disk getAIPlayerDisk() {
        if (Objects.equals(firstTurn, "0")) {
            return PLAYER_2;
        }
        return PLAYER_1;
    }

}
