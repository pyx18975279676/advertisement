package com.seetatech.ad.ui.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.seetatech.ad.R;
import com.seetatech.ad.data.AppData;
import com.seetatech.ad.util.ToastUtil;
import com.seetatech.ad.widget.CameraInitView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 18-6-20.
 */

public class CameraActivity extends AppCompatActivity {

    public static CameraActivity cameraActivity;
    private static final String TAG = "CameraActivity";

    private ImageView imgCamera1, imgCamera2, imgCamera3, imgCamera4;

    private CameraInitView initView1;
    private CameraInitView initView2;
    private CameraInitView initView3;
    private CameraInitView initView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraActivity = this;
        setContentView(R.layout.activity_camera);
        findView();
        init();
    }

    private void findView() {
        imgCamera1 = findViewById(R.id.img_camera_1);
        imgCamera2 = findViewById(R.id.img_camera_2);
        imgCamera3 = findViewById(R.id.img_camera_3);
        imgCamera4 = findViewById(R.id.img_camera_4);

        initView1 = findViewById(R.id.init_view_1);
        initView2 = findViewById(R.id.init_view_2);
        initView3 = findViewById(R.id.init_view_3);
        initView4 = findViewById(R.id.init_view_4);
    }

    private void init() {
        List<Bitmap> initBitmaps = AppData.getInitBitmap(this);
        if (initBitmaps != null) {
            if (initBitmaps.size() > 0) {
                imgCamera1.setImageBitmap(initBitmaps.get(0));
            }

            if (initBitmaps.size() > 1) {
                imgCamera2.setImageBitmap(initBitmaps.get(1));
            }

            if (initBitmaps.size() > 2) {
                imgCamera3.setImageBitmap(initBitmaps.get(2));
            }

            if (initBitmaps.size() > 3) {
                imgCamera4.setImageBitmap(initBitmaps.get(3));
            }
        }
    }

    public void onBackBtnClick(View view) {
        finish();
    }

    public void startInit(View view) {
        CameraInitView.Point[] points1 = initView1.getPoints();
        CameraInitView.Point[] points2 = initView2.getPoints();
        CameraInitView.Point[] points3 = initView3.getPoints();
        CameraInitView.Point[] points4 = initView4.getPoints();

        final List<CameraInitView.Point[]> pointList = new ArrayList<>();
        if (checkInitParams(points1)) {
            pointList.add(points1);
        } else {
            pointList.add(null);
        }

        if (checkInitParams(points2)) {
            pointList.add(points2);
        } else {
            pointList.add(null);
        }

        if (checkInitParams(points3)) {
            pointList.add(points3);
        } else {
            pointList.add(null);
        }

        if (checkInitParams(points4)) {
            pointList.add(points4);
        } else {
            pointList.add(null);
        }

        AppData.putInitPoints(this, pointList);

        ToastUtil.show(CameraActivity.this, "初始化成功");
        finish();
    }

    private boolean checkInitParams(CameraInitView.Point[] points) {
        if (points == null || points.length != 4) return false;

        for (CameraInitView.Point point : points) {
            if (point == null || (point.getX() <= 0 || point.getY() <= 0)) {
                return false;
            }
        }
        return true;
    }
}
