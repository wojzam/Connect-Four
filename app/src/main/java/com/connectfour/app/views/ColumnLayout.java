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

/**
 * The {@code ColumnLayout} represents a column in the Connect Four game board.
 * It contains an array of {@link ImageView} objects representing the disks in the column.
 *
 * @see BoardLayout
 */
public class ColumnLayout extends LinearLayout {

    private final ImageView[] disksImages;
    private final int index;
    private Disk[] disks;

    /**
     * This constructor is not supported.
     *
     * @param context the context to use
     * @throws UnsupportedOperationException always thrown, as this constructor is not supported
     */
    public ColumnLayout(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    /**
     * Constructs a new instance of {@code ColumnLayout}.
     *
     * @param context the context to use
     * @param disks   the initial array of disks in the column
     * @param index   the index of the column
     */
    public ColumnLayout(@NonNull Context context, @NonNull Disk[] disks, int index) {
        super(context);
        this.disksImages = new ImageView[disks.length];
        this.disks = disks;
        this.index = index;
        configure();
        createAndAddDisks(context);
    }

    /**
     * Updates the disks and animates any changes.
     *
     * @param newDisks the new array of disks in the column
     */
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

    /**
     * Animates disk based on its type.
     *
     * @param disk  the disk to animate
     * @param index the index of the disk in the column
     */
    private void animateDisk(Disk disk, int index) {
        if (disk == EMPTY) {
            animateEmptyDisk(index);
        } else {
            animatePlayerDisk(index);
        }
    }

    /**
     * Animates empty disk, making it appear from nothing to its normal size.
     *
     * @param index the index of the disk in the column
     */
    private void animateEmptyDisk(int index) {
        disksImages[index].setScaleX(0);
        disksImages[index].setScaleY(0);
        disksImages[index].animate().scaleX(1).scaleY(1).setDuration(400).start();
    }

    /**
     * Animates player's disk, making it fall into place in the column.
     *
     * @param index the index of the disk in the column
     */
    private void animatePlayerDisk(int index) {
        int diskHeight = disksImages[index].getHeight();
        disksImages[index].setTranslationY(diskHeight * (index - disks.length));
        disksImages[index].animate().translationY(0)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(100L * (disks.length - index))
                .start();
    }

    /**
     * Configures the layout.
     */
    private void configure() {
        setOrientation(VERTICAL);
        setClickable(true);
        setFocusable(true);
        setTag(index);
    }

    /**
     * Creates and adds the disk images to the layout.
     *
     * @param context the context to use
     */
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

    /**
     * Sets the image resource of a disk image based on the type of disk.
     *
     * @param diskImage the ImageView to set the image resource for
     * @param disk      the type of disk
     */
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

    /**
     * @return index of column
     */
    public int getIndex() {
        return index;
    }
}
