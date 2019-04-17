package com.seetatech.ad.ui.main;

import com.seetatech.ad.data.http.ScanRequest;
import com.seetatech.ad.data.http.ScanResponse;

import java.util.List;

/**
 * Created by xjh on 18-6-21.
 */

public class MainContract {

    interface View {

        void showMessage(String msg);

        void scanSuccess(List<ScanResponse.ScanResult> scanResultList);
    }

    interface Presenter {

        void detach();

        void scan(List<ScanRequest.CameraImage> cameraImageList);
    }
}
