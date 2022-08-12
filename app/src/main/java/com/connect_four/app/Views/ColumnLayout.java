package com.connect_four.app.Views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.connect_four.app.R;

import static com.connect_four.app.Board.EMPTY;
import static com.connect_four.app.Board.PLAYER_1;
import static com.connect_four.app.Board.PLAYER_2;

public class ColumnLayout extends LinearLayout {

    private final ImageView[] disks;
    private byte[] values;
    private final int index;

    public ColumnLayout(@NonNull Context context, @NonNull byte[] values, int index) {
        super(context);
        this.disks = new ImageView[values.length];
        this.values = values;
        this.index = index;
        configure();
        createAndAddDisks(context);
    }

    public int getIndex() {
        return index;
    }

    public void refresh(@NonNull byte[] newValues) {
        assert newValues.length == values.length : "Received invalid values array";
        for (int i = 0; i < values.length; i++) {
            if (values[i] != newValues[i]) {
                animateDisk(newValues[i], i);
            }
            setDiskImageResource(disks[i], newValues[i]);
        }
        values = newValues;
    }

    private void animateDisk(byte value, int index) {
        final int diskHeight = disks[index].getHeight();
        if (value == EMPTY) {
            disks[index].setScaleX(0);
            disks[index].setScaleY(0);
            disks[index].animate().scaleX(1).scaleY(1).setDuration(400).start();
        } else {
            disks[index].setTranslationY(diskHeight * (index - values.length));
            disks[index].animate().translationY(0).setDuration(100L * (values.length - index)).start();
        }
    }

    private void setDiskImageResource(ImageView disk, byte value) {
        assert disk != null : "Disk ImageView is null";
        switch (value) {
            case PLAYER_1:
                disk.setImageResource(R.drawable.disk_player1);
                break;
            case PLAYER_2:
                disk.setImageResource(R.drawable.disk_player2);
                break;
            default:
                disk.setImageResource(R.drawable.disk_empty);
        }
    }

    private void configure() {
        setOrientation(VERTICAL);
        setClickable(true);
        setFocusable(true);
    }

    private void createAndAddDisks(Context context) {
        for (int i = 0; i < disks.length; i++) {
            int size = 140; // TODO: size should dynamically fit the screen
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            disks[i] = new ImageView(context);
            disks[i].setLayoutParams(layoutParams);
            setDiskImageResource(disks[i], values[i]);

            this.addView(disks[i], 0);
        }
    }
}
