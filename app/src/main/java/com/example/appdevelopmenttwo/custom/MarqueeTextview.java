package com.example.appdevelopmenttwo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextview extends androidx.appcompat.widget.AppCompatTextView {

    public MarqueeTextview(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MarqueeTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MarqueeTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isFocused() {
        return true; //返回值为true,使得所有的TextView都能够获取到焦点.
    }

}
