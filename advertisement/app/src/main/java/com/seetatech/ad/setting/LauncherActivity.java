package com.seetatech.ad.setting;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seetatech.ad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJH on 2017/4/29.
 */

public class LauncherActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private AppInfoAdapter mAdapter;

    private List<ApplicationInfo> mAllApplicationInfoList;

    private List<ApplicationInfo> mShowApplicationInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 8));

        mAdapter = new AppInfoAdapter();
        mRecyclerView.setAdapter(mAdapter);

        loadApp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //android.R.id.home对应应用程序图标的id
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void loadApp() {
        mAllApplicationInfoList = getPackageManager().getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);

        mShowApplicationInfoList = new ArrayList<>();
        for (ApplicationInfo applicationInfo : mAllApplicationInfoList) {
            if (appCanLaunchTheMainActivity(applicationInfo.packageName) && !applicationInfo.packageName.equals(getPackageName())) {
                mShowApplicationInfoList.add(applicationInfo);
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * whether app can Launch the main activity.
     * Return true when can Launch,otherwise return false.
     *
     * @param packageName
     * @return
     */
    private boolean appCanLaunchTheMainActivity(String packageName) {
        boolean canLaunchTheMainActivity = false;
        do {
            if (TextUtils.isEmpty(packageName)) {
                break;
            }

            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            canLaunchTheMainActivity = (null == intent) ? (false) : (true);
        } while (false);

        return canLaunchTheMainActivity;
    }

    private class AppInfoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAppIcon;

        private TextView tvAppName;

        public AppInfoViewHolder(View itemView) {
            super(itemView);
            imgAppIcon = (ImageView) itemView.findViewById(R.id.img_app_icon);
            tvAppName = (TextView) itemView.findViewById(R.id.tv_app_name);
        }

        public void bindView(final ApplicationInfo applicationInfo) {
            imgAppIcon.setImageDrawable(applicationInfo.loadIcon(getPackageManager()));
            tvAppName.setText(applicationInfo.loadLabel(getPackageManager()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName);
                    startActivity(intent);
                }
            });
        }
    }

    private class AppInfoAdapter extends RecyclerView.Adapter<AppInfoViewHolder> {

        @Override
        public AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AppInfoViewHolder(LayoutInflater.from(LauncherActivity.this).inflate(R.layout.item_app_info, parent, false));
        }

        @Override
        public void onBindViewHolder(AppInfoViewHolder holder, int position) {
            holder.bindView(mShowApplicationInfoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mShowApplicationInfoList == null ? 0 : mShowApplicationInfoList.size();
        }
    }
}
