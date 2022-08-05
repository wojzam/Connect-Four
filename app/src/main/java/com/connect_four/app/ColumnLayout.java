package com.connect_four.app;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class ColumnLayout extends LinearLayout {

    private final ArrayList<ImageView> disks = new ArrayList<>();
    private final int index;

    public ColumnLayout(Context context, int height, int index) {
        super(context);
        this.index = index;
        setOrientation(VERTICAL);
        setClickable(true);
        setFocusable(true);
        createAndAddDisks(context, height);
    }

    public int getIndex() {
        return index;
    }

    public void refresh(@NonNull byte[] values) {
        assert values.length == disks.size() : "Received invalid values array";
        for (int i = 0; i < disks.size(); i++) {
            switch (values[i]) {
                case PLAYER_1:
                    disks.get(i).setImageResource(R.drawable.disk_player1);
                    break;
                case PLAYER_2:
                    disks.get(i).setImageResource(R.drawable.disk_player2);
                    break;
                default:
                    disks.get(i).setImageResource(R.drawable.disk_empty);
            }
        }
    }

    private void createAndAddDisks(Context context, int count) {
        for (int j = 0; j < count; j++) {
            int size = 130; // TODO: size should dynamically fit the screen
            ImageView disk = new ImageView(context);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            disk.setLayoutParams(layoutParams);
            disk.setImageResource(R.drawable.disk_empty);

            this.addView(disk, 0);
            disks.add(disk);
        }
    }
}
