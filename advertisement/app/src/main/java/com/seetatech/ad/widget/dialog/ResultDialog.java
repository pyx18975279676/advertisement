package com.seetatech.ad.widget.dialog;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.seetatech.ad.R;
import com.seetatech.ad.data.AppData;
import com.seetatech.ad.data.http.ScanResponse;
import com.seetatech.ad.widget.CameraInitView;
import com.seetatech.ad.widget.ScanResultView;

import java.util.List;

/**
 * Created by hexuren on 18-6-27.
 */

public class ResultDialog extends BaseDialog {

    private ScanResultView img1, img2, img3, img4;

    public ResultDialog(Context context) {
        super(context);
    }

    public ResultDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_result);

        findView();

        init();
    }

    private void findView() {
        img1 = findViewById(R.id.img_camera_1);
        img2 = findViewById(R.id.img_camera_2);
        img3 = findViewById(R.id.img_camera_3);
        img4 = findViewById(R.id.img_camera_4);
    }

    private void init() {
        List<Bitmap> bitmapList = AppData.getCameraBitmaps();
        if (bitmapList != null) {
            if (bitmapList.size() > 0) img1.setImageBitmap(bitmapList.get(0));
            if (bitmapList.size() > 1) img2.setImageBitmap(bitmapList.get(1));
            if (bitmapList.size() > 2) img3.setImageBitmap(bitmapList.get(2));
            if (bitmapList.size() > 3) img4.setImageBitmap(bitmapList.get(3));
        }

        List<ScanResponse.ScanResult> scanResultList = AppData.getScanResult();
        List<CameraInitView.Point[]> initPoints = AppData.getInitPoints((ContextWrapper) getContext());
        for (ScanResponse.ScanResult scanResult : scanResultList) {
            if (0 == scanResult.getCamera_id()) {
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                    List<Float[]> points = scanResultInfo.getRect_list();
                    img1.addPoints(points);
                }
                if (initPoints != null && initPoints.get(0) != null && initPoints.get(0).length >= 4) {
                    img1.setSize((int) (initPoints.get(0)[2].getX() - initPoints.get(0)[0].getX()),
                            (int) (initPoints.get(0)[2].getY() - initPoints.get(0)[0].getY()));
                }
                img1.refresh();
            } else if (1 == scanResult.getCamera_id()) {
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                    List<Float[]> points = scanResultInfo.getRect_list();
                    img2.addPoints(points);
                }
                if (initPoints != null && initPoints.get(1) != null && initPoints.get(1).length >= 4) {
                    img2.setSize((int) (initPoints.get(1)[2].getX() - initPoints.get(1)[0].getX()),
                            (int) (initPoints.get(1)[2].getY() - initPoints.get(1)[0].getY()));
                }
                img2.refresh();
            } else if (2 == scanResult.getCamera_id()) {
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                    List<Float[]> points = scanResultInfo.getRect_list();
                    img3.addPoints(points);
                }
                if (initPoints != null && initPoints.get(2) != null && initPoints.get(2).length >= 4) {
                    img3.setSize((int) (initPoints.get(2)[2].getX() - initPoints.get(2)[0].getX()),
                            (int) (initPoints.get(2)[2].getY() - initPoints.get(2)[0].getY()));
                }
                img3.refresh();
            } else if (3 == scanResult.getCamera_id()) {
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                    List<Float[]> points = scanResultInfo.getRect_list();
                    img4.addPoints(points);
                }
                if (initPoints != null && initPoints.get(3) != null && initPoints.get(3).length >= 4) {
                    img4.setSize((int) (initPoints.get(3)[2].getX() - initPoints.get(3)[0].getX()),
                            (int) (initPoints.get(3)[2].getY() - initPoints.get(3)[0].getY()));
                }
                img4.refresh();
            }
        }

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //设置宽度，默认为屏幕宽度的80%
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 1);
        p.height = (int) (d.getHeight() * 1);
        getWindow().setAttributes(p);
    }
}
