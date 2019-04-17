package com.seetatech.ad.ui.main;

import com.seetatech.ad.BuildConfig;
import com.seetatech.ad.setting.AppSetting;
import com.seetatech.ad.setting.Constants;
import com.seetatech.ad.http.HttpTest;
import com.seetatech.ad.data.Callback;
import com.seetatech.ad.data.http.ScanRequest;
import com.seetatech.ad.data.http.ScanResponse;
import com.seetatech.ad.util.ThreadPool;

import java.util.List;
import java.util.Random;

/**
 * Created by xjh on 18-6-21.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void scan(List<ScanRequest.CameraImage> cameraImageList) {
        if (!BuildConfig.TEST_SCAN_DATA) {
            ScanRequest scanRequest = new ScanRequest();
            scanRequest.setDev_code(Constants.DEVICE_CODE);
            scanRequest.setThreshold(AppSetting.getThreshold());
            scanRequest.setImages(cameraImageList);
            scanRequest.requestMap(new Callback<ScanResponse>() {
                @Override
                public void onResponse(ScanResponse response) {
                    if (mView != null) mView.scanSuccess(response.getResult());
                }

                @Override
                public void onError(Throwable t) {
                    if (mView != null) mView.showMessage(t.getMessage());
                }
            });
        } else {
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    int r = new Random().nextInt(10);
                    if (r > 5) {
                        if (mView != null) mView.scanSuccess(HttpTest.makeTestScanResult());
                    } else {
                        if (mView != null) mView.scanSuccess(HttpTest.makeTestScanResult2());
                    }
                }
            });
        }
    }
}
