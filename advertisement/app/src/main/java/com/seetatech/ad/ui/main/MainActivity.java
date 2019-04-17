package com.seetatech.ad.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.seetatech.ad.R;
import com.seetatech.ad.data.AppData;
import com.seetatech.ad.data.MediaController;
import com.seetatech.ad.data.bean.AdFile;
import com.seetatech.ad.data.http.ScanRequest;
import com.seetatech.ad.data.http.ScanResponse;
import com.seetatech.ad.setting.AppSetting;
import com.seetatech.ad.setting.Constants;
import com.seetatech.ad.setting.SettingsActivity;
import com.seetatech.ad.util.BitmapUtil;
import com.seetatech.ad.util.FileUtil;
import com.seetatech.ad.util.Logger;
import com.seetatech.ad.util.ThreadPool;
import com.seetatech.ad.util.ToastUtil;
import com.seetatech.ad.widget.CameraInitView;
import com.seetatech.ad.widget.dialog.InputDialog;
import com.serenegiant.usb.DeviceFilter;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.UVCCamera;
import com.serenegiant.usb.common.AbstractUVCCameraHandler;
import com.serenegiant.usb.common.UVCCameraHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements USBMonitor.OnDeviceConnectListener, MainContract.View {

    private static final String TAG = "MainActivity_LOG";

    private USBMonitor mUSBMonitor;
    private List<DeviceFilter> mDeviceFilters;

    private ImageView imgCamera1, imgCamera2, imgCamera3, imgCamera4;

    private long camera1Time, camera2Time, camera3Time, camera4Time;

    private byte[] cameraData1, cameraData2, cameraData3, cameraData4;

    private VideoView mVideoView;
    private ImageView mImgView;

    private Button mBtnStart, mBtnShowImage;
    private TextView mTvCameraError;

    private boolean stopPlay = false;//控制是否停止播放

    private Map<UsbDevice, UVCCameraHandler> cameraHandlerMap = new HashMap<>();

    private int reOpenCameraTimes = 0;//重新打开摄像头次数
    private static String msgToken = UUID.randomUUID().toString();//用于验证handler的token

    private MainContract.Presenter mPresenter;

    private static final int MSG_OPEN_CAMERA = 0;
    private static final int MSG_SHOW = 1;
    private static final int MSG_GET_REDUCE_GOODS = 2;
    private static final int MSG_PLAY_MEDIA_FILE = 3;
    private static final int MSG_STOP_PLAY_MEDIA_FILE = 4;
    private static final int MSG_START_SCAN = 5;

    /**
     * 防止内存泄露的handler
     */
    private static class SafeHandler extends Handler {

        private WeakReference<MainActivity> mWeakReference;

        public SafeHandler(MainActivity mainActivity) {
            mWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                MainActivity mainActivity = mWeakReference.get();
                if (mainActivity != null) {
                    switch (msg.what) {
                        case MSG_OPEN_CAMERA:
                            int index = (int) msg.obj;
                            mainActivity.handleOpenCamera(index);
                            break;
                        case MSG_SHOW:
                            String message = (String) msg.obj;
                            mainActivity.handleShowMessage(message);
                            break;
                        case MSG_GET_REDUCE_GOODS:
                            String id = (String) msg.obj;
                            mainActivity.handleGetReduceGoods(id);
                            break;
                        case MSG_PLAY_MEDIA_FILE:
                            String token = msg.getData().getString("token");
                            if (msgToken.equals(token)) {
                                String key = msg.getData().getString("key");
                                mainActivity.playMediaFile(key);
                            }
                            break;
                        case MSG_START_SCAN:
                            mainActivity.handleScan();
                            break;
                        case MSG_STOP_PLAY_MEDIA_FILE:
                            mainActivity.stopPlayMediaFile();
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Handler mHandler = new SafeHandler(this);
    private Handler mMediaHandler = new SafeHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        init();
    }

    private void findView() {
        imgCamera1 = findViewById(R.id.img_camera_1);
        imgCamera2 = findViewById(R.id.img_camera_2);
        imgCamera3 = findViewById(R.id.img_camera_3);
        imgCamera4 = findViewById(R.id.img_camera_4);

        mVideoView = findViewById(R.id.video_view);
        mImgView = findViewById(R.id.img_view);

        mBtnStart = findViewById(R.id.btn_start);
        mBtnShowImage = findViewById(R.id.btn_show_image);
        mTvCameraError = findViewById(R.id.tv_camera_error);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnStart.setVisibility(View.INVISIBLE);
                mBtnShowImage.setVisibility(View.INVISIBLE);
                findViewById(R.id.view_black).setVisibility(View.VISIBLE);

                stopPlay = false;
                if (MediaController.loadMediaConfig()) {
                    initVideo();

//                    sendAdMessage(null, 0);
                } else {
                    showMessage("读取配置文件失败");
                }
            }
        });

        mTvCameraError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay = true;
                mBtnStart.setVisibility(View.VISIBLE);
                mBtnShowImage.setVisibility(View.VISIBLE);
                findViewById(R.id.view_black).setVisibility(View.INVISIBLE);
                mVideoView.setVisibility(View.INVISIBLE);
                mImgView.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.btn_open_camera_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(0);
            }
        });

        findViewById(R.id.btn_open_camera_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(1);
            }
        });

        findViewById(R.id.btn_open_camera_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(2);
            }
        });

        findViewById(R.id.btn_open_camera_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(3);
            }
        });
    }

    private void init() {
        mUSBMonitor = new USBMonitor(this, this);
        mUSBMonitor.register();

        //申请写入外部文件权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                FileUtil.makeMediaDir(this);
            }
        } else {
            FileUtil.makeMediaDir(this);
        }

        mDeviceFilters = DeviceFilter.getDeviceFilters(this, com.jiangdg.libusbcamera.R.xml.device_filter);
        mPresenter = new MainPresenter(this);

        mBtnStart.setVisibility(View.INVISIBLE);
        mBtnShowImage.setVisibility(View.INVISIBLE);
        findViewById(R.id.view_black).setVisibility(View.VISIBLE);
        stopPlay = false;

        if (MediaController.loadMediaConfig()) {
            initVideo();

//            sendAdMessage(null, 0);
        } else {
            showMessage("读取配置文件失败");
        }

        mHandler.sendEmptyMessageDelayed(MSG_START_SCAN, 3000);
    }

    private void initVideo() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(false);
            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopPlaybackVideo();
//                sendAdMessage(null, 0);
                sendStopMessage(0);
                return true;
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                sendAdMessage(null, 0);
                sendStopMessage(0);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        FileUtil.makeMediaDir(this);
        MediaController.loadMediaConfig();
    }

    private synchronized void sendAdMessage(String key, long delayed) {
        Message message = mMediaHandler.obtainMessage(MSG_PLAY_MEDIA_FILE);
        Bundle bundle = new Bundle();
        bundle.putString("token", msgToken);
        bundle.putString("key", key);
        message.setData(bundle);
        mMediaHandler.sendMessageDelayed(message, delayed);
    }

    private synchronized void sendStopMessage(long delayed) {
        Message message = mMediaHandler.obtainMessage(MSG_STOP_PLAY_MEDIA_FILE);
        mMediaHandler.sendMessageDelayed(message, delayed);
    }

    private void playMediaFile(String id) {
        if (stopPlay) return;
        AdFile adFile = null;
//        if (TextUtils.isEmpty(id)) {
//            adFile = MediaController.getNextMediaFile();
//        } else {
//            adFile = MediaController.getNextMediaFile(id);
//        }

        adFile = MediaController.getMediaFile(id);

        if (adFile == null) return;

        msgToken = UUID.randomUUID().toString();

        if (adFile.getPath().endsWith(".mp4")) {
            Uri uri = Uri.parse(adFile.getPath());
            mVideoView.setVideoURI(uri);

            mVideoView.setVisibility(View.VISIBLE);
            mImgView.setVisibility(View.INVISIBLE);
        } else if (adFile.getPath().endsWith(".gif")) {
            Glide.with(this).load(adFile.getPath()).asGif().into(mImgView);
//            sendAdMessage(null, adFile.getTime() * 1000);
            sendStopMessage(adFile.getTime() * 1000);

            mImgView.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.INVISIBLE);
        } else if (adFile.getPath().endsWith(".jpg")) {
            Glide.with(this).load(adFile.getPath()).into(mImgView);
//            sendAdMessage(null, adFile.getTime() * 1000);
            sendStopMessage(adFile.getTime() * 1000);

            mImgView.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.INVISIBLE);
        }
    }

    private void stopPlayMediaFile() {
        mVideoView.setVisibility(View.INVISIBLE);
        mImgView.setVisibility(View.INVISIBLE);
    }

    private void stopPlaybackVideo() {
        try {
            mVideoView.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mVideoView.isPlaying()) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mVideoView.canPause()) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlaybackVideo();
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (UVCCameraHandler handler : cameraHandlerMap.values()) {
                        handler.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mUSBMonitor.unregister();
        mUSBMonitor.destroy();
        mPresenter.detach();
        mHandler.removeCallbacksAndMessages(null);
        mMediaHandler.removeCallbacksAndMessages(null);
    }

    private void handleOpenCamera(int index) {
        try {
            int cameraCount = AppSetting.getCameraCount();
            if (cameraCount > index) openCamera(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera(final int cameraId) {
        List<UsbDevice> usbDeviceList = mUSBMonitor.getDeviceList(mDeviceFilters);
        UsbDevice usbDevice = null;
        AbstractUVCCameraHandler.OnPreViewResultListener onPreViewResultListener = null;
        int cameraCount = AppSetting.getCameraCount();
        switch (cameraId) {
            case 0:
                if (usbDeviceList != null && usbDeviceList.size() > 0 && cameraCount > 0) {
                    usbDevice = usbDeviceList.get(0);
                    onPreViewResultListener = new AbstractUVCCameraHandler.OnPreViewResultListener() {
                        @Override
                        public void onPreviewResult(byte[] data) {
                            cameraData1 = data;
                            camera1Time = System.currentTimeMillis();
                        }
                    };
                }
                break;
            case 1:
                if (usbDeviceList != null && usbDeviceList.size() > 1 && cameraCount > 1) {
                    usbDevice = usbDeviceList.get(1);
                    onPreViewResultListener = new AbstractUVCCameraHandler.OnPreViewResultListener() {
                        @Override
                        public void onPreviewResult(byte[] data) {
                            cameraData2 = data;
                            camera2Time = System.currentTimeMillis();
                        }
                    };
                }
                break;
            case 2:
                if (usbDeviceList != null && usbDeviceList.size() > 2 && cameraCount > 2) {
                    usbDevice = usbDeviceList.get(2);
                    onPreViewResultListener = new AbstractUVCCameraHandler.OnPreViewResultListener() {
                        @Override
                        public void onPreviewResult(byte[] data) {
                            cameraData3 = data;
                            camera3Time = System.currentTimeMillis();
                        }
                    };
                }
                break;
            case 3:
                if (usbDeviceList != null && usbDeviceList.size() > 3 && cameraCount > 3) {
                    usbDevice = usbDeviceList.get(3);
                    onPreViewResultListener = new AbstractUVCCameraHandler.OnPreViewResultListener() {
                        @Override
                        public void onPreviewResult(byte[] data) {
                            cameraData4 = data;
                            camera4Time = System.currentTimeMillis();
                        }
                    };
                }
                break;
        }

        if (usbDevice != null) {
            UVCCameraHandler handler = UVCCameraHandler.createHandler(this,
                    null, 2, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT,
                    UVCCamera.FRAME_FORMAT_MJPEG, AppSetting.getCameraBandWidthFactor(), onPreViewResultListener);
            cameraHandlerMap.put(usbDevice, handler);
            mUSBMonitor.requestPermission(usbDevice);
        }
    }

    @Override
    public void onAttach(UsbDevice device) {
        List<UsbDevice> usbDeviceList = mUSBMonitor.getDeviceList(mDeviceFilters);
        if (usbDeviceList != null && usbDeviceList.contains(device)) {
            int index = usbDeviceList.indexOf(device);
            mHandler.sendMessage(Message.obtain(mHandler, MSG_OPEN_CAMERA, index));
        }
    }

    @Override
    public void onDetach(UsbDevice device) {

    }

    @Override
    public void onConnect(UsbDevice device, USBMonitor.UsbControlBlock ctrlBlock, boolean createNew) {
        final UVCCameraHandler handler = cameraHandlerMap.get(device);
        if (handler != null) {
            handler.open(ctrlBlock);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 休眠500ms，等待Camera创建完毕
                    try {
                        Thread.sleep(500);
                        // 开启预览
                        handler.startPreview(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onDisconnect(UsbDevice device, USBMonitor.UsbControlBlock ctrlBlock) {

    }

    @Override
    public void onCancel(UsbDevice device) {

    }

    public void displayCameraImage(View view) {
        Bitmap bitmap1 = cameraData1 == null ? null : BitmapFactory.decodeByteArray(cameraData1, 0, cameraData1.length);
        Bitmap bitmap2 = cameraData2 == null ? null : BitmapFactory.decodeByteArray(cameraData2, 0, cameraData2.length);
        Bitmap bitmap3 = cameraData3 == null ? null : BitmapFactory.decodeByteArray(cameraData3, 0, cameraData3.length);
        Bitmap bitmap4 = cameraData4 == null ? null : BitmapFactory.decodeByteArray(cameraData4, 0, cameraData4.length);

        List<Bitmap> initBitmaps = new ArrayList<>();
        initBitmaps.add(bitmap1);
        initBitmaps.add(bitmap2);
        initBitmaps.add(bitmap3);
        initBitmaps.add(bitmap4);
        AppData.putInitBitmap(this, initBitmaps);

        imgCamera1.setImageBitmap(bitmap1);
        imgCamera2.setImageBitmap(bitmap2);
        imgCamera3.setImageBitmap(bitmap3);
        imgCamera4.setImageBitmap(bitmap4);
    }

    public void onClickSetting(View view) {
        new InputDialog.Builder(this)
                .setTitle("请输入管理员密码")
                .setPositiveButton("确定", new InputDialog.OnClickPositiveListener() {
                    @Override
                    public void onClick(String inputString, InputDialog dialog) {
                        dialog.dismiss();
                        if ("".equals(inputString) || AppSetting.getAdminPassword().equals(inputString)) {
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        } else {
                            ToastUtil.show(MainActivity.this, "密码错误");
                        }
                    }
                }).setNegativeButton("取消", new InputDialog.OnClickNegativeListener() {
            @Override
            public void onClick(InputDialog dialog) {
                dialog.dismiss();
            }
        }).build().show();
    }

    private void handleScan() {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                scan();
            }
        });
        int fps = AppSetting.getCameraFPS();
        fps = fps > 20 ? 20 : fps;
        fps = fps < 1 ? 1 : fps;
        mHandler.sendEmptyMessageDelayed(MSG_START_SCAN, (long)(1000.0 / fps));
    }

    private void scan() {
        List<ScanRequest.CameraImage> cameraImages = getCameraImages(AppData.getInitPoints(MainActivity.this));

        if (cameraImages == null) {
            showMessage("获取图片失败,请尝试重新初始化");
        } else {
            Logger.d(TAG, "startScan: ");
            mPresenter.scan(cameraImages);
        }
    }

    @Override
    public void scanSuccess(List<ScanResponse.ScanResult> scanResultList) {
        Logger.d(TAG, "scanSuccess: " + JSON.toJSONString(scanResultList));
        if (scanResultList != null && scanResultList.size() > 0) {
            //检查是否有手臂,如果有直接作废
            for (ScanResponse.ScanResult scanResult : scanResultList) {
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                if (scanResultInfoList != null) {
                    for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                        if (scanResultInfo != null && "sb".equals(scanResultInfo.getLabel())) {
                            List<Float[]> rectList = scanResultInfo.getRect_list();
                            if (rectList.size() > 0) return;
                        }
                    }
                }
            }

            //去掉未知物品
            List<ScanResponse.ScanResult> scanResultListWithoutWzwp = new ArrayList<>();
            for (ScanResponse.ScanResult scanResult : scanResultList) {
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoListWithoutWzwp = new ArrayList<>();
                ScanResponse.ScanResult scanResultWithoutWzwp = new ScanResponse.ScanResult();
                if (scanResultInfoList != null) {
                    for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                        if (scanResultInfo != null && !"wzwp".equals(scanResultInfo.getLabel())) {
                            List<Float[]> rectList = scanResultInfo.getRect_list();
                            if (rectList.size() > 0)
                                scanResultInfoListWithoutWzwp.add(scanResultInfo);
                        }
                    }
                    scanResultWithoutWzwp.setCamera_id(scanResult.getCamera_id());
                    scanResultWithoutWzwp.setInfo(scanResultInfoListWithoutWzwp);
                }
                scanResultListWithoutWzwp.add(scanResultWithoutWzwp);
            }

            Logger.d(TAG, "WithoutWzwp: " + JSON.toJSONString(scanResultListWithoutWzwp));

            if (scanResultListWithoutWzwp.size() > 0) {
                //更新本地数据
                AppData.putScanResults(scanResultListWithoutWzwp);
                //获取每个商品的个数
                Map<String, Integer> goodsInfo = AppData.countGoods(scanResultListWithoutWzwp);
                //和上一次的每个商品个数进行比较得到减少的商品code和个数
                Map<String, Integer> reduceGoodsInfo = AppData.getReduceGoodsNumberInfo(this, goodsInfo);
                //更新本地的商品个数信息
                AppData.putGoodsNumberInfo(this, goodsInfo);

                if (reduceGoodsInfo != null && reduceGoodsInfo.size() > 0) {
                    String id = reduceGoodsInfo.keySet().iterator().next();
                    mHandler.sendMessage(mHandler.obtainMessage(MSG_GET_REDUCE_GOODS, id));
                }
            }
        } else {
            showMessage("没有识别到商品");
        }
    }

    private void handleGetReduceGoods(String id) {
        Logger.d(TAG, "playAd: " + id);
        sendAdMessage(id, 0);
    }

    private List<ScanRequest.CameraImage> getCameraImages(List<CameraInitView.Point[]> initPoints) {
        try {
            int cameraCount = AppSetting.getCameraCount();

            if (cameraCount == 0) return null;

            final Bitmap bitmap1 = cameraData1 == null ? null : BitmapFactory.decodeByteArray(cameraData1, 0, cameraData1.length);
            final Bitmap bitmap2 = cameraData2 == null ? null : BitmapFactory.decodeByteArray(cameraData2, 0, cameraData2.length);
            final Bitmap bitmap3 = cameraData3 == null ? null : BitmapFactory.decodeByteArray(cameraData3, 0, cameraData3.length);
            final Bitmap bitmap4 = cameraData4 == null ? null : BitmapFactory.decodeByteArray(cameraData4, 0, cameraData4.length);

            List<Bitmap> initBitmaps = new ArrayList<>();
            initBitmaps.add(bitmap1);
            initBitmaps.add(bitmap2);
            initBitmaps.add(bitmap3);
            initBitmaps.add(bitmap4);
            AppData.putInitBitmap(this, initBitmaps);

            int openCameraCount = 0;
            long currentTime = System.currentTimeMillis();
            if (bitmap1 != null && currentTime - camera1Time < 1000) openCameraCount++;
            if (bitmap2 != null && currentTime - camera2Time < 1000) openCameraCount++;
            if (bitmap3 != null && currentTime - camera3Time < 1000) openCameraCount++;
            if (bitmap4 != null && currentTime - camera4Time < 1000) openCameraCount++;

            final int finalOpenCameraCount = openCameraCount;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (finalOpenCameraCount < AppSetting.getCameraCount()) {
                        //如果图片获取异常,尝试重启摄像头,
                        if (reOpenCameraTimes < 3) {
                            mTvCameraError.setText("正在打开摄像头...");
                            tryReopenCamera();
                            reOpenCameraTimes++;
                        } else {
                            reOpenCameraTimes = 0;
                            mTvCameraError.setText("摄像头打开失败,点击此处进行设置");
                        }
                        mTvCameraError.setVisibility(View.VISIBLE);
                    } else {
                        reOpenCameraTimes = 0;
                        mTvCameraError.setVisibility(View.INVISIBLE);
                    }
                }
            });

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgCamera1.setImageBitmap(bitmap1);
                    imgCamera2.setImageBitmap(bitmap2);
                    imgCamera3.setImageBitmap(bitmap3);
                    imgCamera4.setImageBitmap(bitmap4);
                }
            });

            List<Bitmap> bitmapList = new ArrayList<>();
            List<ScanRequest.CameraImage> cameraImages = new ArrayList<>();

            if (cameraCount > 0) {
                Bitmap bitmapClip = clipBitmap(bitmap1, 0, initPoints);
                bitmapList.add(bitmapClip);
                cameraImages.add(createCameraImage(bitmapClip, 0));
            }

            if (cameraCount > 1) {
                Bitmap bitmapClip2 = clipBitmap(bitmap2, 1, initPoints);
                bitmapList.add(bitmapClip2);
                cameraImages.add(createCameraImage(bitmapClip2, 1));
            }

            if (cameraCount > 2) {
                Bitmap bitmapClip3 = clipBitmap(bitmap3, 2, initPoints);
                bitmapList.add(bitmapClip3);
                cameraImages.add(createCameraImage(bitmapClip3, 2));
            }

            if (cameraCount > 3) {
                Bitmap bitmapClip4 = clipBitmap(bitmap4, 3, initPoints);
                bitmapList.add(bitmapClip4);
                cameraImages.add(createCameraImage(bitmapClip4, 3));
            }

            AppData.putCameraBitmaps(bitmapList);

            return cameraImages;
        } catch (Exception e) {
            return null;
        }
    }

    private void tryReopenCamera() {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    openCamera(0);
                    Thread.sleep(1000);
                    openCamera(1);
                    Thread.sleep(1000);
                    openCamera(2);
                    Thread.sleep(1000);
                    openCamera(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Bitmap clipBitmap(Bitmap bitmap, int index, List<CameraInitView.Point[]> initPoints) {
        Bitmap bitmapClip;
        if (initPoints != null && initPoints.size() > index) {
            bitmapClip = BitmapUtil.clip(bitmap, initPoints.get(index));
        } else {
            bitmapClip = bitmap;
        }
        return bitmapClip;
    }

    private ScanRequest.CameraImage createCameraImage(Bitmap bitmap, int index) {
        ScanRequest.CameraImage cameraImage = new ScanRequest.CameraImage();
        cameraImage.setCamera_id(index);
        cameraImage.setImage(BitmapUtil.bitmapToBase64(bitmap));
        return cameraImage;
    }

    @Override
    public void showMessage(final String msg) {
        mHandler.sendMessage(Message.obtain(mHandler, MSG_SHOW, msg));
    }

    private void handleShowMessage(String msg) {
        ToastUtil.show(MainActivity.this, msg);
    }
}
