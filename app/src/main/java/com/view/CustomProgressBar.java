package com.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by elephant on 2016/11/29.
 * 构建 hierarchy 时使用 ProgressBarDrawable
 */

public class CustomProgressBar extends Drawable {
    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int i) {
        Log.i("CustomProgressBar","setAlpha" + i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    protected boolean onLevelChange(int level) {
        // level is on a scale of 0-10,000
        // where 10,000 means fully downloaded

        // your app's logic to change te drawable's
        // appearance here based on progress
        Log.i("CustomProgressBar", level + "");
        return super.onLevelChange(level);
    }
}
