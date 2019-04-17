package com.seetatech.ad.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seetatech.ad.R;
import com.seetatech.ad.data.bean.Goods;

import java.util.List;


/**
 * 消息对话框
 * Created by xjh on 2018/3/8.
 */
public class GoodsListDialog extends BaseDialog {

    /**
     * 根布局
     */
    private ViewGroup mRootContainer;
    /**
     * 标题
     */
    private TextView mTvTitle;
    /**
     * 商品列表
     */
    private RecyclerView mRecyclerView;
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

    public GoodsListDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public GoodsListDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 对话框构建工具
     */
    public static class Builder {

        /**
         * 对话框
         */
        private GoodsListDialog dialog;
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
        private List<Goods> mGoodsList;
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
         * 设置商品列表
         *
         * @param goodsList
         * @return
         */
        public Builder setGoodsList(List<Goods> goodsList) {
            mGoodsList = goodsList;
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
        public GoodsListDialog build() {
            dialog = new GoodsListDialog(mContext);
            dialog.setBuilder(this);
            return dialog;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goods_list);

        mRootContainer = (ViewGroup) findViewById(R.id.root_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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

    public static class GoodsViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ImageView imgGoods;
        private TextView tvGoodsName, tvGoodsPrice, tvGoodsNumber;

        public GoodsViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            imgGoods = itemView.findViewById(R.id.img_goods);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
            tvGoodsNumber = itemView.findViewById(R.id.tv_goods_number);
        }

        public void setGoodInfo(Goods goods) {
            Glide.with(context).load(goods.getGoodsPicture()).into(imgGoods);
            tvGoodsName.setText(goods.getGoodsName());
            tvGoodsPrice.setText(goods.getGoodsPrice());
            tvGoodsNumber.setText(goods.getGoodsNumber() + "");
        }
    }

    public static class GoodAdapter extends RecyclerView.Adapter<GoodsViewHolder> {

        private Context context;
        private List<Goods> goodsList;

        public GoodAdapter(Context context, List<Goods> goodsList) {
            this.context = context;
            this.goodsList = goodsList;
        }

        @Override
        public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GoodsViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false));
        }

        @Override
        public void onBindViewHolder(GoodsViewHolder holder, int position) {
            holder.setGoodInfo(goodsList.get(position));
        }

        @Override
        public int getItemCount() {
            return goodsList == null ? 0 : goodsList.size();
        }
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
            //设置商品列表
            if (builder.mGoodsList != null) {
                GoodAdapter adapter = new GoodAdapter(getContext(), builder.mGoodsList);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                        builder.mBtnPositiveClickListener.onClick(GoodsListDialog.this);
                    }
                });
            }
            //设置按钮二点击事件
            if (builder.mBtnNegativeClickListener != null) {
                mBtnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.mBtnNegativeClickListener.onClick(GoodsListDialog.this);
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

            p.height = p.height < (int) (d.getHeight() * 0.6) ? p.height : (int) (d.getHeight() * 0.6);

            getWindow().setAttributes(p);
        }
    }

    /**
     * 按钮点击的回调
     */
    public interface OnClickListener {
        void onClick(GoodsListDialog dialog);
    }
}
