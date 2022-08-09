package com.connect_four.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.connect_four.app.Game;
import com.connect_four.app.R;
import com.connect_four.app.Settings;

public class MainActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        Button newGameButton = findViewById(R.id.newGameButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        newGameButton.setOnClickListener(view -> game.restart());
        settingsButton.setOnClickListener(view -> openSettings());

        Settings settings = new Settings(getApplicationContext());
        game = new Game(gameLayout, settings);
        game.restart();
    }

    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}