package com.seetatech.ad.util;

import android.graphics.Bitmap;
import android.util.Base64;

import com.seetatech.ad.widget.CameraInitView;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {

    /**
     * bitmap转base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bytes = bos.toByteArray();
            return Base64.encodeToString(bytes, 0, bytes.length, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap clip(Bitmap bitmap, CameraInitView.Point[] points) {
        if (points == null || points.length < 4 || bitmap == null) return bitmap;

        try {
            return Bitmap.createBitmap(bitmap, ((int) points[0].getX()), ((int) points[0].getY()),
                    (int) (points[2].getX() - points[0].getX()), (int) (points[2].getY() - points[0].getY()));
        } catch (Exception e) {
            return bitmap;
        }
    }
}
