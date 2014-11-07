package com.pascalwelsch.apkmirror;

import com.google.gson.Gson;

import com.linearlistview.LinearListView;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.pascalwelsch.apkmirror.model.AppUpdateList;
import com.pascalwelsch.apkmirror.services.DownloadService;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class HomeActivity extends BaseActivity implements LinearListView.OnItemClickListener {


    private class RecentsAdapter extends BaseAdapter {

        private final LayoutInflater mInflater;

        private Context mContext;

        private List<AppUpdate> mUpdates;

        public RecentsAdapter(final Context context, final List<AppUpdate> updates) {
            mContext = context;

            mUpdates = updates;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mUpdates.size();
        }

        @Override
        public Object getItem(final int position) {
            return mUpdates.get(position);
        }

        @Override
        public long getItemId(final int position) {
            return ((Object) mUpdates.get(position)).hashCode();
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final AppUpdate appUpdate = (AppUpdate) getItem(position);

            final View view = mInflater.inflate(R.layout.view_recents, parent, false);
            final TextView appName = (TextView) view.findViewById(android.R.id.text1);
            final ImageView icon = (ImageView) view.findViewById(R.id.icon);
            final Button actionButton = (Button) view.findViewById(R.id.action);
            Picasso.with(mContext).load(appUpdate.getIconUrl()).into(icon);
            appName.setText(appUpdate.getName());

            try {
                PackageInfo pInfo = getPackageManager()
                        .getPackageInfo(appUpdate.getPackageName(), 0);
                int installedVersion = pInfo.versionCode;
                int updateVersion = appUpdate.getVersion();
                if (installedVersion < updateVersion) {
                    actionButton.setText(R.string.app_action_update);
                } else {
                    actionButton.setText(R.string.app_action_open);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                actionButton.setText(R.string.app_action_install);
            }

            return view;
        }
    }

    private RecentsAdapter mAdapter;

    private LinearListView mLinearList;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);



        final RecentAppUpdateAdapter appAdapter = new RecentAppUpdateAdapter(this, getAppUpdate());
        mRecyclerView.setAdapter(appAdapter);*/

        mLinearList = (LinearListView) findViewById(R.id.recents);
        mAdapter = new RecentsAdapter(this, getAppUpdate());
        mLinearList.setAdapter(mAdapter);
        mLinearList.setOnItemClickListener(this);
    }

    public List<AppUpdate> getAppUpdate() {
        final List<AppUpdate> updates = new ArrayList<AppUpdate>();
        /*updates.addAll(Arrays.asList(
                new AppUpdate("Chrome Beta 39.0.2171.37",
                        "http://www.apkmirror.com/wp-content/themes/APKMirror/ap_resize/ap_resize.php?src=http%3A%2F%2Fwww.apkmirror.com%2Fwp-content%2Fuploads%2F2014%2F10%2F54483b41e1242.png",
                        "Google Inc.", "October 24, 2014", "com.chrome.beta", 2171037),
                new AppUpdate("Android Wear 1.0.2.1534065",
                        "http://www.apkmirror.com/wp-content/themes/APKMirror/ap_resize/ap_resize.php?src=http%3A%2F%2Fwww.apkmirror.com%2Fwp-content%2Fuploads%2F2014%2F10%2F5449802075ec4.png",
                        "Google Inc.", "October 24, 2014", "com.google.android.wearable.app",
                        201534065),
                new AppUpdate("Calendar 201404014",
                        "http://www.apkmirror.com/wp-content/themes/APKMirror/ap_resize/ap_resize.php?src=http%3A%2F%2Fwww.apkmirror.com%2Fwp-content%2Fuploads%2F2014%2F10%2F542d25925355a.png",
                        "Amazon Mobile LLC", "October 23, 2014", "com.google.android.calendar",
                        201404015)
        ));*/
        return updates;
    }

    @Override
    public void onItemClick(final LinearListView linearListView, final View view,
            final int position,
            final long l) {
        final AppUpdate appUpdate = (AppUpdate) mAdapter.getItem(position);
        final ImageView icon = (ImageView) view.findViewById(R.id.icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final DownloadService downloadService = new DownloadService(
                        getApplicationContext());

                String url
                        = "http://www.apkmirror.com/wp-content/themes/APKMirror/download.php?id=2052";
                final String filename = "myapp.apk";
                //downloadService.startDownload(url, filename);

                Callback responseCallback = new Callback() {
                    @Override
                    public void onFailure(final Request request, final IOException e) {

                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {
                        ResponseBody body = response.body();
                        Log.v("myTag", body.string());

                        Gson gson = new Gson();
                        gson.fromJson(body.string(), AppUpdateList.class);
                    }
                };

                //ApiService.getRecents(responseCallback);

                final Intent intent = DetailActivity.getInstance(HomeActivity.this, appUpdate);

                Bundle bundle = Bundle.EMPTY;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(HomeActivity.this, icon,
                                    "testTransition");
                    bundle = options.toBundle();
                }
                startActivity(intent, bundle);
            }
        });
    }
}
