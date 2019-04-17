package com.seetatech.ad.device;

import com.seetatech.ad.util.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

/**
 * 串口设备
 * Created by xjh on 18-3-15.
 */

public class SerialPortDevice {

    private SerialPort mSerialPort = null;
    private ReadThread mReadThread;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private BufferedReader mBufferedReader;
    private OnReceivedDataCallback mCallback;
    private boolean open;

    public boolean isOpen() {
        return open;
    }

    /**
     * 打开串口设备
     *
     * @param path     路径
     * @param baudrate 波特率
     */
    public SerialPortDevice(String path, int baudrate) {
        try {
            //改变权限
            changeDevicePermission(path);
            //打开串口
            mSerialPort = getSerialPort(path, baudrate);
            open = true;
        } catch (Exception e) {
            open = false;
        }
    }

    /**
     * 改变设备权限
     *
     * @param path
     */
    public static void changeDevicePermission(String path) {
        File device = new File(path);

        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            Process su = null;
            DataOutputStream os = null;
            try {
                su = Runtime.getRuntime().exec("su");
                os = new DataOutputStream(su.getOutputStream());
                String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
                        + "exit\n";
                os.write(cmd.getBytes());
                os.flush();
                su.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    su.destroy();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 关闭设备
     */
    public void close() {
        open = false;

        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mInputStream = null;
        }

        if (mBufferedReader != null) {
            try {
                mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mBufferedReader = null;
        }

        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mOutputStream = null;
        }

        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 写入数据
     *
     * @param data
     */
    public void write(byte[] data) {
        if (mSerialPort == null) return;
        if (mOutputStream == null) mOutputStream = mSerialPort.getOutputStream();

        try {
            mOutputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据
     *
     * @param buffer
     * @param index
     * @param length
     */
    public void read(byte[] buffer, int index, int length) {
        if (mSerialPort == null) return;
        if (mInputStream == null) mInputStream = mSerialPort.getInputStream();

        try {
            mInputStream.read(buffer, index, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始读取
     *
     * @param callback
     */
    public void startRead(OnReceivedDataCallback callback) {
        if (mSerialPort == null) return;
        //获得输入输出流
        mInputStream = mSerialPort.getInputStream();
        mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));

        mReadThread = new ReadThread();
        mReadThread.start();
        mCallback = callback;
    }

    public SerialPort getSerialPort(String path, int baudrate) throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {

            /* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

            /* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (open) {
                try {
                    if (mInputStream != null && mInputStream.available() > 0) {
                        String lineStr = mBufferedReader.readLine();
                        Logger.d("SerialPortDevice", lineStr + "");

                        if (mCallback != null && lineStr != null) {
                            mCallback.onReceivedData(lineStr);
                        }
                    } else {
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnReceivedDataCallback {
        void onReceivedData(String data);
    }
}
