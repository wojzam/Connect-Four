package com.connect_four.app.views;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.connect_four.app.R;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        throw new UnsupportedOperationException("This constructor is not supported");
    }

    public CustomButton(@NonNull Context context, int textResId) {
        super(context);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        setId(View.generateViewId());
        setBackgroundResource(R.drawable.button_bg);
        setLayoutParams(params);
        setText(textResId);
    }
}
