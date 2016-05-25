package com.phillipcalvin.iconbutton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconButton extends TextView {

    private final IconDecorator iconDecorator;

    public IconButton(Context context) {
        super(context);
        iconDecorator = new IconDecorator(this);
    }

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        iconDecorator = new IconDecorator(this, attrs);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        iconDecorator = new IconDecorator(this, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        iconDecorator.onLayout();
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        iconDecorator.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}