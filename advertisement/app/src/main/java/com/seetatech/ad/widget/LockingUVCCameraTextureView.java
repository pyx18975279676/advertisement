package com.seetatech.ad.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;

import com.seetatech.ad.setting.Constants;
import com.serenegiant.usb.widget.UVCCameraTextureView;

/**
 * Created by xjh on 18-6-20.
 */

public class LockingUVCCameraTextureView extends UVCCameraTextureView {
    public LockingUVCCameraTextureView(Context context) {
        super(context);
    }

    public LockingUVCCameraTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockingUVCCameraTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
