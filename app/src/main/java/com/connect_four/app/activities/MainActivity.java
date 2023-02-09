package com.connect_four.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.connect_four.app.R;
import com.connect_four.app.Settings;
import com.connect_four.app.controller.GameController;
import com.connect_four.app.model.GameModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        Button newGameButton = findViewById(R.id.newGameButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        Settings settings = new Settings(getApplicationContext());

        GameController controller = new GameController(new GameModel(settings), gameLayout);
        controller.restart();

        newGameButton.setOnClickListener(view -> controller.restart());
        settingsButton.setOnClickListener(view -> openSettings());
    }

    private void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}