package com.abdelhakim.prosoundeq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;


@SuppressLint("AppCompatCustomView")
public class ProSoundEQVerticalSeekbar extends SeekBar {

    private boolean shouldChange = false;

    public ProSoundEQVerticalSeekbar(Context context) {
        super(context);
    }

    public ProSoundEQVerticalSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProSoundEQVerticalSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);}

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(height, width, oldHeight, oldWidth);
    }



    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        super.onDraw(canvas);
    }

    public void updateThumb(){
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {return false;}
        shouldChange = true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP: {
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                break;
            }
        }

        shouldChange = true;
        return true;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        shouldChange = false;
    }


    public boolean getShouldChange() {
        return shouldChange;
    }

    public void setShouldChange(boolean shouldChange) {
        this.shouldChange = shouldChange;
    }
}
