package com.seetatech.ad.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;

import com.seetatech.ad.setting.Constants;

/**
 * Created by xjh on 18-7-19.
 */

public class CameraImageView extends android.support.v7.widget.AppCompatImageView {
    public CameraImageView(Context context) {
        super(context);
    }

    public CameraImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int specSize = MeasureSpec.getSize(widthMeasureSpec);
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (specSize * (Constants.CAMERA_HEIGHT * 1.0f / Constants.CAMERA_WIDTH)), MeasureSpec.EXACTLY));
        } else if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int specSize = MeasureSpec.getSize(heightMeasureSpec);
            super.onMeasure(MeasureSpec.makeMeasureSpec((int) (specSize * (Constants.CAMERA_WIDTH * 1.0f / Constants.CAMERA_HEIGHT)), MeasureSpec.EXACTLY), heightMeasureSpec);
        }
    }
}
