package com.seetatech.ad.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.widget.EditText;

import com.seetatech.ad.R;
import com.seetatech.ad.setting.Constants;
import com.seetatech.ad.ui.camera.CameraActivity;
import com.seetatech.ad.util.ToastUtil;


/**
 * Created by xjh on 17-9-4.
 */

public class CameraInitView extends View {
    /**
     * 描述画点状态
     */
    private enum PointState{
        STATE_INITIAL,//初始 状态
        STATE_DRAW_ONE_POINT,//画了一个点 状态
        STATE_DRAW_TWO_POINT,//画了两个点 状态
        STATE_MODIFY_POINT,//修改点 状态
    }

    /**
     * 下面为画框范围的限制参数
     */
    public static final int X_MIN = 20;
    public static final int X_MAX = 700;
    public static final int Y_MIN = 20;
    public static final int Y_MAX = 390;

    /**
     * 坐标放缩比例
     */
    public static final float ratio_x = 2.4f;
    public static final float ratio_y = 2.4f;

    /**
     * 画阴影的画笔对象
     */
    private Paint mPaintPoint, mPaintLine, mPaintText;

    /**
     * 保存四个点的坐标
     */
    private Point[] mPoints;
    private Path mPath;

    PointState point_state = PointState.STATE_INITIAL;//初始状态
    private int mIndex, mChangeIndex = - 1;//初始化为未选中任何点

    public CameraInitView(Context context) {
        super(context);
        init();
    }

    public CameraInitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraInitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPoints = new Point[4];
        //设置阴影画笔颜色，用与画阴影部分
        mPaintPoint = new Paint();
        mPaintPoint.setColor(Color.GREEN);
        mPaintPoint.setStrokeWidth(5); //线的宽度
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setAntiAlias(true); //抗锯齿

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLUE);
        mPaintLine.setStrokeWidth(2); //线的宽度
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setAntiAlias(true); //抗锯齿

        mPaintText = new Paint();
        mPaintText.setColor(Color.RED);
        mPaintText.setTextSize(22);
        mPaintText.setStrokeWidth(1); //线的宽度
        mPaintText.setAntiAlias(true); //抗锯齿
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath = new Path();
        for (int i = 0; i < 4; i++) {
            Point point = mPoints[i];
            if (point != null) {
                if (i == 0) {
                    canvas.drawCircle(point.getX(), point.getY(), 10, mPaintPoint);
                    mPath.moveTo(point.getX(), point.getY());
                } else if(i == 1) {
                    mPath.lineTo(point.getX(), point.getY());
                } else if(i == 2) {
                    canvas.drawCircle(point.getX(), point.getY(), 10, mPaintPoint);
                    mPath.lineTo(point.getX(), point.getY());
                } else if(i == 3){
                    mPath.lineTo(point.getX(), point.getY());
                    mPath.close();
                }
            }
        }
        canvas.drawPath(mPath, mPaintLine);//画框
        if(mPoints[0] != null) {//显示左上角的坐标
            int x = (int) (mPoints[0].getX() + 5);
            int y = (int) (mPoints[0].getY() + 20);
            String coord_top = String.format("X:%.1f,  Y:%.1f", mPoints[0].getX() * ratio_x, mPoints[0].getY() * ratio_y);
            canvas.drawText(coord_top, x, y, mPaintText);
        }
        if(mPoints[2] != null) {//显示右下角的坐标
            int x = (int) (mPoints[2].getX() - 170);
            int y = (int) (mPoints[2].getY());
            String coord_btm = String.format("X:%.1f,  Y:%.1f", mPoints[2].getX() * ratio_x, mPoints[2].getY() * ratio_y);
            canvas.drawText(coord_btm, x, y, mPaintText);
        }
    }

    public float range_limit(float src, float a, float b){//取值范围限制，超过范围则修改它
        if(src > a)
            src = a;
        else if(src < b)
            src = b;
        return src;
    }

    private static long lastClickTime = 0;//保存上一次的点击时间

    public boolean checkDoubleClick() {
        long cur_time = System.currentTimeMillis();
        long delta_time = cur_time - lastClickTime;
        if(delta_time > 0 && delta_time < 500){
            return true;
        }
        lastClickTime = cur_time;
        return false;
    }

    public int handleDoubleClick(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (mPoints[0] != null) {
            if ((Math.abs(x - mPoints[0].getX()) < 50) && (Math.abs(y - mPoints[0].getY()) < 50)) {
                return 1;
            }
        }
        if (mPoints[2] != null) {
            if ((Math.abs(x - mPoints[2].getX()) < 50) && (Math.abs(y - mPoints[2].getY()) < 50)) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//交互操作，设置或改变框的位置和大小
        float x = event.getX();
        float y = event.getY();
        x = range_limit(x, X_MAX, X_MIN);
        y = range_limit(y, Y_MAX, Y_MIN);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (point_state == PointState.STATE_INITIAL) {
                    x = range_limit(x, X_MAX - 100, X_MIN);
                    y = range_limit(y, Y_MAX - 100, Y_MIN);
                    mPoints[0] = new Point(x, y);
                    point_state = PointState.STATE_DRAW_ONE_POINT;
                    postInvalidate();
                } else if (point_state == PointState.STATE_DRAW_ONE_POINT) {
                    x = range_limit(x, X_MAX, mPoints[0].getX() + 50);
                    y = range_limit(y, Y_MAX, mPoints[0].getY() + 50);
                    mPoints[2] = new Point(x, y);
                    mPoints[1] = new Point(mPoints[2].getX(), mPoints[0].getY());
                    mPoints[3] = new Point(mPoints[0].getX(), mPoints[2].getY());
                    point_state = PointState.STATE_DRAW_TWO_POINT;
                    postInvalidate();
                } else if (point_state == PointState.STATE_MODIFY_POINT) {
                    if (checkDoubleClick()) {
                        if (handleDoubleClick(event) == 1) {
                            ShowInputDialog(1);
                            return true;
                        } else if (handleDoubleClick(event) == 2) {
                            ShowInputDialog(2);
                            return true;
                        }
                    }
                    if ((Math.abs(x - mPoints[0].getX()) < 50) && (Math.abs(y - mPoints[0].getY()) < 50)) {
                        mChangeIndex = 0;
                    } else if ((Math.abs(x - mPoints[2].getX()) < 50) && (Math.abs(y - mPoints[2].getY()) < 50)) {
                        mChangeIndex = 2;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (point_state == PointState.STATE_DRAW_ONE_POINT) {
                    x = range_limit(x, X_MAX, mPoints[0].getX() + 50);
                    y = range_limit(y, Y_MAX, mPoints[0].getY() + 50);
                    mPoints[2] = new Point(x, y);
                    mPoints[1] = new Point(mPoints[2].getX(), mPoints[0].getY());
                    mPoints[3] = new Point(mPoints[0].getX(), mPoints[2].getY());
                    point_state = PointState.STATE_DRAW_TWO_POINT;
                    postInvalidate();
                } else if (point_state == PointState.STATE_DRAW_TWO_POINT) {
                    x = range_limit(x, X_MAX, mPoints[0].getX() + 50);
                    y = range_limit(y, Y_MAX, mPoints[0].getY() + 50);
                    mPoints[2] = new Point(x, y);
                    mPoints[1] = new Point(mPoints[2].getX(), mPoints[0].getY());
                    mPoints[3] = new Point(mPoints[0].getX(), mPoints[2].getY());
                    postInvalidate();
                } else if (point_state == PointState.STATE_MODIFY_POINT) {
                    if(mChangeIndex == 0) {//选中左上角的点，修改它的坐标
                        x = range_limit(x, mPoints[2].getX() - 50, X_MIN);
                        y = range_limit(y, mPoints[2].getY() - 50, Y_MIN);
                        mPoints[mChangeIndex] = new Point(x, y);
                        mPoints[1].setY(mPoints[mChangeIndex].getY());
                        mPoints[3].setX(mPoints[mChangeIndex].getX());
                        postInvalidate();
                    } else if (mChangeIndex == 2) {//选中右下角的点，修改它的坐标
                        x = range_limit(x, X_MAX, mPoints[0].getX() + 50);
                        y = range_limit(y, Y_MAX, mPoints[0].getY() + 50);
                        mPoints[mChangeIndex] = new Point(x, y);
                        mPoints[1].setX(mPoints[mChangeIndex].getX());
                        mPoints[3].setY(mPoints[mChangeIndex].getY());
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (point_state == PointState.STATE_DRAW_TWO_POINT) {
                    point_state = PointState.STATE_MODIFY_POINT;//松开鼠标，状态变为可修改
                } else if (point_state == PointState.STATE_MODIFY_POINT) {
                    mChangeIndex = - 1;//松开鼠标，恢复为未选中
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    //弹出输入对话框，修改矩形框的位置
    public void ShowInputDialog(int id) {
        if (id == 1) {
            LayoutInflater inflater = (LayoutInflater) CameraActivity.cameraActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.dialog_edittext, null);
            final EditText editText_x = (EditText) view.findViewById(R.id.et_input_x);
            final EditText editText_y = (EditText) view.findViewById(R.id.et_input_y);
            editText_x.setText(String.format("%.1f", mPoints[0].getX() * ratio_x));
            editText_y.setText(String.format("%.1f", mPoints[0].getY() * ratio_y));
            new AlertDialog.Builder(CameraActivity.cameraActivity)
                    .setTitle("手动修改左上角坐标")
                    .setView(view)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String inputString_x = editText_x.getText().toString();
                            String inputString_y = editText_y.getText().toString();
                            if ("".equals(inputString_x) || "".equals(inputString_y)) {
                                ToastUtil.show(CameraActivity.cameraActivity, "输入不能为空，且必须为有效数值");
                            } else {
                                float xx = Float.valueOf(inputString_x) / ratio_x;
                                float yy = Float.valueOf(inputString_y) / ratio_y;
                                xx = range_limit(xx, mPoints[2].getX() - 50, X_MIN);
                                yy = range_limit(yy, mPoints[2].getY() - 50, Y_MIN);
                                mPoints[0].setX(xx);
                                mPoints[0].setY(yy);
                                mPoints[1].setY(mPoints[0].getY());
                                mPoints[3].setX(mPoints[0].getX());
                                postInvalidate();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (id == 2) {
            LayoutInflater inflater = (LayoutInflater) CameraActivity.cameraActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.dialog_edittext, null);
            final EditText editText_x = (EditText) view.findViewById(R.id.et_input_x);
            final EditText editText_y = (EditText) view.findViewById(R.id.et_input_y);
            editText_x.setText(String.format("%.1f", mPoints[2].getX() * ratio_x));
            editText_y.setText(String.format("%.1f", mPoints[2].getY() * ratio_y));
            new AlertDialog.Builder(CameraActivity.cameraActivity)
                    .setTitle("手动修改右下角坐标")
                    .setView(view)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String inputString_x = editText_x.getText().toString();
                            String inputString_y = editText_y.getText().toString();
                            if ("".equals(inputString_x) || "".equals(inputString_y)) {
                                ToastUtil.show(CameraActivity.cameraActivity, "输入不能为空，且必须为有效数值");
                            } else {
                                float xx = Float.valueOf(inputString_x) / ratio_x;
                                float yy = Float.valueOf(inputString_y) / ratio_y;
                                xx = range_limit(xx, X_MAX, mPoints[0].getX() + 50);
                                yy = range_limit(yy, Y_MAX, mPoints[0].getY() + 50);
                                mPoints[2].setX(xx);
                                mPoints[2].setY(yy);
                                mPoints[1].setX(mPoints[2].getX());
                                mPoints[3].setY(mPoints[2].getY());
                                postInvalidate();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    /**
     * 获得所有的点
     *
     * @return
     */
    public Point[] getPoints() {
        for (Point point : mPoints) {
            if (point != null) {
                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    point.setX(point.getX() * Constants.CAMERA_WIDTH / getWidth());
                    point.setY(point.getY() * Constants.CAMERA_WIDTH / getWidth());
                } else {
                    point.setX(point.getX() * Constants.CAMERA_HEIGHT / getHeight());
                    point.setY(point.getY() * Constants.CAMERA_HEIGHT / getHeight());
                }
            }

        }
        return mPoints;
    }

    public Bitmap clip(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap, (int) mPoints[0].getX(),
                (int) mPoints[0].getY(), (int) (mPoints[2].getX() - mPoints[0].getX()), (int) (mPoints[2].getY() - mPoints[0].getY()));
    }

    public static class Point {
        private float x;
        private float y;

        public Point() {
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
