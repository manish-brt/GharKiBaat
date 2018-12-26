package com.manish.gharkibaat.customView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

/**********************************
 * Created by Manish on 19-Nov-18
 ***********************************/
public class CurvedBottomNavigationView extends BottomNavigationView {

    Path mPath;
    Paint mPaint;

    public CurvedBottomNavigationView(Context context) {
        super(context);
        init();
    }

    public CurvedBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurvedBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        setBackgroundColor(Color.TRANSPARENT);
    }

    float mNavigationBarWidth, mNavigationBarHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();

    }
}
