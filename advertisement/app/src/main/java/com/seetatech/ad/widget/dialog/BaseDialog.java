package com.seetatech.ad.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.seetatech.ad.R;


/**
 * 基础对话框
 * Created by xjh on 2017/1/16.
 */
public class BaseDialog extends Dialog {

    /**
     * 对话框的DecorView
     */
    private View mDialogDecorView;
    /**
     * 进入时的动画
     */
    private Animation mInAnimation;
    /**
     * 消失时时的动画
     */
    private Animation mOutAnimation;

    public BaseDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogDecorView = getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mInAnimation != null) {
            mDialogDecorView.startAnimation(mInAnimation);
        }
    }

    @Override
    public void dismiss() {
        try {
            if (mOutAnimation != null) {
                mOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            BaseDialog.super.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mDialogDecorView.startAnimation(mOutAnimation);
            } else {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置对话框进入动画
     *
     * @return
     */
    public void setInAnimation(Animation animation) {
        if (animation != null) {
            mInAnimation = animation;
        }
    }

    /**
     * 设置对话框消失动画
     *
     * @return
     */
    public void setOutAnimation(Animation animation) {
        if (animation != null) {
            mOutAnimation = animation;
        }
    }
}
