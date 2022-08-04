package com.connect_four.app;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class ColumnLayout extends LinearLayout {

    private final ArrayList<ImageView> disks = new ArrayList<>();

    public ColumnLayout(Context context, int height) {
        super(context);
        setOrientation(VERTICAL);
        setClickable(true);
        setFocusable(true);
        createDisks(context, height);
    }

    public void refresh(byte[] values){
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

    private void createDisks(Context context, int count){
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