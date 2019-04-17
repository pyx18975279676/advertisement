package com.seetatech.ad.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.seetatech.ad.R;


/**
 * 进度条对话框
 * Created by xjh on 2016/12/28.
 */
public class ProcessDialog extends BaseDialog {

    private String mMsg;

    public ProcessDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public ProcessDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_process);
        TextView tvMsg = findViewById(R.id.tv_msg);
        tvMsg.setText(mMsg);
        setCancelable(false);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            mMsg = msg;
        }
    }
}
