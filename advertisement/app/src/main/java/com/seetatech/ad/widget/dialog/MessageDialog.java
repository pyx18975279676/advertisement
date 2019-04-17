package com.seetatech.ad.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.seetatech.ad.R;
import com.seetatech.ad.util.UiUtil;


/**
 * 消息对话框
 * Created by xjh on 2018/3/8.
 */
public class MessageDialog extends BaseDialog {

    /**
     * 根布局
     */
    private ViewGroup mRootContainer;
    /**
     * 标题
     */
    private TextView mTvTitle;
    /**
     * 内容
     */
    private TextView mTvContent;
    /**
     * 确定按钮
     */
    private TextView mBtnPositive;
    /**
     * 取消按钮
     */
    private TextView mBtnNegative;
    /**
     * 按钮的容器
     */
    private ViewGroup mBtnContainer, mBtnPositiveContainer, mBtnNegativeContainer;
    /**
     * 构造器
     */
    private Builder mBuilder;

    public MessageDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public MessageDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 对话框构建工具
     */
    public static class Builder {

        /**
         * 对话框
         */
        private MessageDialog dialog;
        /**
         * 上下文
         */
        private Context mContext;
        /**
         * 标题
         */
        private String mTitleStr;
        /**
         * 内容
         */
        private String mContentStr;
        /**
         * 最小高度
         */
        private int mMinHeight;
        /**
         * 确定按钮文本
         */
        private String mBtnPositiveStr;
        /**
         * 取消按钮文本
         */
        private String mBtnNegativeStr;
        /**
         * 确定按钮点击事件
         */
        private OnClickListener mBtnPositiveClickListener;
        /**
         * 取消按钮点击事件
         */
        private OnClickListener mBtnNegativeClickListener;
        /**
         * 进入动画资源id
         */
        private int mInAnimationId;
        /**
         * 消失动画资源id
         */
        private int mOutAnimationId;
        /**
         * 对话框对齐方式
         */
        private int mGravity;
        /**
         * 宽度百分比
         */
        private float mWidthPercent;
        /**
         * 是否禁止圆角
         */
        private boolean mAvoidCorner;
        /**
         * 是否允许取消
         */
        private boolean mCancelable = true;

        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 设置标题
         *
         * @param titleStr
         * @return
         */
        public Builder setTitle(String titleStr) {
            mTitleStr = titleStr;
            return this;
        }

        /**
         * 设置内容
         *
         * @param contentStr
         * @return
         */
        public Builder setContent(String contentStr) {
            mContentStr = contentStr;
            return this;
        }

        /**
         * 设置最小高度
         *
         * @param minHeight
         * @return
         */
        public Builder setMinHeight(int minHeight) {
            mMinHeight = minHeight;
            return this;
        }

        /**
         * 设置确定按钮
         *
         * @param btnStr
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String btnStr, final OnClickListener listener) {
            mBtnPositiveStr = btnStr;
            mBtnPositiveClickListener = listener;
            return this;
        }

        /**
         * 设置取消按钮
         *
         * @param btnStr
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String btnStr, final OnClickListener listener) {
            mBtnNegativeStr = btnStr;
            mBtnNegativeClickListener = listener;
            return this;
        }

        /**
         * 设置进入时动画
         *
         * @param animationId
         * @return
         */
        public Builder setInAnimation(int animationId) {
            mInAnimationId = animationId;
            return this;
        }

        /**
         * 设置消失时动画
         *
         * @param animationId
         * @return
         */
        public Builder setOutAnimation(int animationId) {
            mOutAnimationId = animationId;
            return this;
        }

        /**
         * 设置对齐方式
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        /**
         * 设置宽度百分比
         *
         * @param percent
         * @return
         */
        public Builder setWidthPercent(float percent) {
            mWidthPercent = percent;
            return this;
        }

        /**
         * 设置是否需要圆角
         *
         * @param avoidCorner
         * @return
         */
        public Builder setAvoidCorner(boolean avoidCorner) {
            mAvoidCorner = avoidCorner;
            return this;
        }

        /**
         * 设置是否可以取消
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        /**
         * 生成对话框
         *
         * @return
         */
        public MessageDialog build() {
            dialog = new MessageDialog(mContext);
            dialog.setBuilder(this);
            return dialog;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);

        mRootContainer = (ViewGroup) findViewById(R.id.root_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mBtnContainer = (ViewGroup) findViewById(R.id.btn_container);
        mBtnPositiveContainer = (ViewGroup) findViewById(R.id.btn_positive_container);
        mBtnNegativeContainer = (ViewGroup) findViewById(R.id.btn_negative_container);
        mBtnPositive = (TextView) findViewById(R.id.btn_positive);
        mBtnNegative = (TextView) findViewById(R.id.btn_negative);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setParams(mBuilder);
    }

    /**
     * 设置构造器
     */
    private void setBuilder(Builder builder) {
        this.mBuilder = builder;
    }

    /**
     * 设置参数
     *
     * @param builder
     */
    private void setParams(final Builder builder) {
        if (mBuilder != null) {
            //设置标题文字
            if (!TextUtils.isEmpty(builder.mTitleStr)) {
                mTvTitle.setText(builder.mTitleStr);
            } else {
                mTvTitle.setVisibility(View.GONE);
            }
            //设置最小高度
            if (builder.mMinHeight > 0) {
                mTvContent.setMinHeight(UiUtil.dp2px(getContext(), builder.mMinHeight));
            }
            //设置内容文字
            if (!TextUtils.isEmpty(builder.mContentStr)) {
                mTvContent.setText(builder.mContentStr);
            }
            //设置按钮一,按钮二文字
            if (TextUtils.isEmpty(builder.mBtnPositiveStr) &&
                    TextUtils.isEmpty(builder.mBtnNegativeStr)) {
                mBtnContainer.setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(builder.mBtnPositiveStr) &&
                    TextUtils.isEmpty(builder.mBtnNegativeStr)) {
                mBtnPositive.setText(builder.mBtnPositiveStr);
                mBtnNegativeContainer.setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(builder.mBtnNegativeStr) &&
                    TextUtils.isEmpty(builder.mBtnPositiveStr)) {
                mBtnNegative.setText(builder.mBtnNegativeStr);
                mBtnPositiveContainer.setVisibility(View.GONE);
            } else {
                mBtnPositive.setText(builder.mBtnPositiveStr);
                mBtnNegative.setText(builder.mBtnNegativeStr);
            }
            //设置按钮一点击事件
            if (builder.mBtnPositiveClickListener != null) {
                mBtnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.mBtnPositiveClickListener.onClick(MessageDialog.this);
                    }
                });
            }
            //设置按钮二点击事件
            if (builder.mBtnNegativeClickListener != null) {
                mBtnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.mBtnNegativeClickListener.onClick(MessageDialog.this);
                    }
                });
            }

            //设置进入动画资源id
            if (builder.mInAnimationId != 0) {
                setInAnimation(AnimationUtils.loadAnimation(getContext(), builder.mInAnimationId));
            }

            //设置消失动画资源id
            if (builder.mOutAnimationId != 0) {
                setOutAnimation(AnimationUtils.loadAnimation(getContext(), builder.mOutAnimationId));
            }

            //设置圆角
            if (builder.mAvoidCorner) {
                mRootContainer.setBackgroundResource(0);
                mRootContainer.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }

            //设置对齐方式
            if (builder.mGravity != 0) {
                getWindow().setGravity(builder.mGravity);
            }

            setCancelable(builder.mCancelable);

            if (false == builder.mCancelable) {
                findViewById(R.id.close).setVisibility(View.INVISIBLE);
            }

            //设置宽度，默认为屏幕宽度的80%
            WindowManager m = getWindow().getWindowManager();
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p = getWindow().getAttributes();
            if (builder.mWidthPercent != 0) {
                p.width = (int) (d.getWidth() * builder.mWidthPercent);
            } else {
                p.width = (int) (d.getWidth() * 0.6);
            }
            getWindow().setAttributes(p);
        }
    }

    /**
     * 按钮点击的回调
     */
    public interface OnClickListener {
        void onClick(MessageDialog dialog);
    }
}
