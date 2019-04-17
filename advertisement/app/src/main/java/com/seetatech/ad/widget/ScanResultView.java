package com.seetatech.ad.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.seetatech.ad.setting.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 18-6-25.
 */

public class ScanResultView extends android.support.v7.widget.AppCompatImageView {

    /**
     * 画阴影的画笔对象
     */
    private Paint mPaintLine;
    /**
     * 物体的点
     */
    private List<Float[]> mPoints;

    private int mRealWidth, mRealHeight;

    public ScanResultView(Context context) {
        super(context);
        init();
    }

    public ScanResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPoints = new ArrayList<>();

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLUE);
        mPaintLine.setStrokeWidth(3); //线的宽度
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setAntiAlias(true); //抗锯齿
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRealWidth > 0 && mRealHeight > 0) {
            super.onMeasure(MeasureSpec.makeMeasureSpec((int) (mRealWidth * 1.0f / 4), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec((int) (mRealHeight * 1.0f / 4), MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(MeasureSpec.makeMeasureSpec((int) (Constants.CAMERA_WIDTH * 1.0f / 4), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec((int) (Constants.CAMERA_HEIGHT * 1.0f / 4), MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPoints != null && mPoints.size() > 0) {
            for (Float[] point : mPoints) {
                if (point != null && point.length >= 4) {
                    canvas.drawRect(point[0] * 1.0f / 4, point[1] * 1.0f / 4,
                            point[2] * 1.0f / 4, point[3] * 1.0f / 4, mPaintLine);
                }
            }
        }
    }

    public void addPoints(List<Float[]> points) {
        if (points == null) return;

        mPoints.addAll(points);
    }

    public void setSize(int width, int height) {
        if (width < 0 || height < 0) return;

        mRealWidth = width;
        mRealHeight = height;
        requestLayout();
    }

    public void refresh() {
        postInvalidate();
    }
}
