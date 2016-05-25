package com.phillipcalvin.iconbutton;

import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconDecorator {

    private final TextView textView;

    protected DrawablePositions drawablePosition;
    protected int drawableWidth;
    protected int iconPadding;

    Rect bounds; // Cached to prevent allocation during onLayout

    IconDecorator(TextView textView) {
        this.textView = textView;
        bounds = new Rect();
    }

    IconDecorator(TextView textView, AttributeSet attrs) {
        this.textView = textView;
        bounds = new Rect();
        applyAttributes(attrs);
    }

    public void setIconPadding(int padding) {
        iconPadding = padding;
        textView.requestLayout();
    }

    protected void onLayout() {
        Paint textPaint = textView.getPaint();
        String text = textView.getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int textWidth = bounds.width();
        int factor = (drawablePosition == DrawablePositions.LEFT_AND_RIGHT) ? 2 : 1;
        int contentWidth = drawableWidth + iconPadding * factor + textWidth;
        int horizontalPadding = (int) ((textView.getWidth() / 2.0) - (contentWidth / 2.0));

        textView.setCompoundDrawablePadding(-horizontalPadding + iconPadding);

        switch (drawablePosition) {
            case LEFT:
                textView.setPadding(horizontalPadding,
                                    textView.getPaddingTop(),
                                    0,
                                    textView.getPaddingBottom());
                break;

            case RIGHT:
                textView.setPadding(0,
                                    textView.getPaddingTop(),
                                    horizontalPadding,
                                    textView.getPaddingBottom());
                break;

            case LEFT_AND_RIGHT:
                textView.setPadding(horizontalPadding,
                                    textView.getPaddingTop(),
                                    horizontalPadding,
                                    textView.getPaddingBottom());
                break;

            default:
                textView.setPadding(0, textView.getPaddingTop(), 0, textView.getPaddingBottom());
        }
    }

    protected void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                           Drawable top,
                                                           Drawable right,
                                                           Drawable bottom) {
        if (left != null && right != null) {
            drawableWidth = left.getIntrinsicWidth() + right.getIntrinsicWidth();
            drawablePosition = DrawablePositions.LEFT_AND_RIGHT;
        }
        else if (left != null) {
            drawableWidth = left.getIntrinsicWidth();
            drawablePosition = DrawablePositions.LEFT;
        }
        else if (right != null) {
            drawableWidth = right.getIntrinsicWidth();
            drawablePosition = DrawablePositions.RIGHT;
        }
        else {
            drawablePosition = DrawablePositions.NONE;
        }

        textView.requestLayout();
    }

    protected void applyAttributes(AttributeSet attrs) {
        // Slight contortion to prevent allocating in onLayout
        if (null == bounds) {
            bounds = new Rect();
        }

        TypedArray typedArray = textView.getContext()
                                        .obtainStyledAttributes(attrs, R.styleable.IconButton);
        int paddingId = typedArray.getDimensionPixelSize(R.styleable.IconButton_iconPadding, 0);
        setIconPadding(paddingId);
        typedArray.recycle();
    }

    private enum DrawablePositions {
        NONE,
        LEFT_AND_RIGHT,
        LEFT,
        RIGHT
    }
}