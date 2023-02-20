package com.connectfour.app.views;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.connectfour.app.R;
import com.connectfour.app.model.Board;
import com.connectfour.app.model.Disk;

import static com.connectfour.app.model.Disk.EMPTY;

public class ColumnLayout extends LinearLayout {

    private final ImageView[] disksImages;
    private final int index;
    private Disk[] disks;

    public ColumnLayout(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    public ColumnLayout(@NonNull Context context, @NonNull Disk[] disks, int index) {
        super(context);
        this.disksImages = new ImageView[disks.length];
        this.disks = disks;
        this.index = index;
        configure();
        createAndAddDisks(context);
    }

    public void update(@NonNull Disk[] newDisks) {
        assert newDisks.length == disks.length : "Received invalid disks array";
        for (int i = 0; i < disks.length; i++) {
            if (disks[i] != newDisks[i]) {
                animateDisk(newDisks[i], i);
                setDiskImageResource(disksImages[i], newDisks[i]);
            }
        }
        disks = newDisks;
    }

    private void animateDisk(Disk disk, int index) {
        if (disk == EMPTY) {
            animateEmptyDisk(index);
        } else {
            animatePlayerDisk(index);
        }
    }

    private void animateEmptyDisk(int index) {
        disksImages[index].setScaleX(0);
        disksImages[index].setScaleY(0);
        disksImages[index].animate().scaleX(1).scaleY(1).setDuration(400).start();
    }

    private void animatePlayerDisk(int index) {
        int diskHeight = disksImages[index].getHeight();
        disksImages[index].setTranslationY(diskHeight * (index - disks.length));
        disksImages[index].animate().translationY(0)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(100L * (disks.length - index))
                .start();
    }

    private void configure() {
        setOrientation(VERTICAL);
        setClickable(true);
        setFocusable(true);
        setTag(index);
    }

    private void createAndAddDisks(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int margin = (int) context.getResources().getDimension(R.dimen.board_margin);
        int availableWidth = displayMetrics.widthPixels - 2 * margin;
        int diskSize = availableWidth / (Board.WIDTH_DEFAULT);

        for (int i = 0; i < disksImages.length; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(diskSize, diskSize);
            disksImages[i] = new ImageView(context);
            disksImages[i].setLayoutParams(layoutParams);
            setDiskImageResource(disksImages[i], disks[i]);

            addView(disksImages[i], 0);
        }
    }

    private void setDiskImageResource(ImageView diskImage, Disk disk) {
        switch (disk) {
            case PLAYER_1:
                diskImage.setImageResource(R.drawable.disk_player1);
                break;
            case PLAYER_2:
                diskImage.setImageResource(R.drawable.disk_player2);
                break;
            default:
                diskImage.setImageResource(R.drawable.disk_empty);
        }
    }

    public int getIndex() {
        return index;
    }
}
