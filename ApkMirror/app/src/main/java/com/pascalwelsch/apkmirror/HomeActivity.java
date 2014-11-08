package com.pascalwelsch.apkmirror;

import com.google.gson.Gson;

import com.linearlistview.LinearListView;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.pascalwelsch.apkmirror.model.Recents;
import com.pascalwelsch.apkmirror.services.ApiService;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
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
            final TextView appName = (TextView) view.findViewById(R.id.recents_app_name);
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
//        mAdapter = new RecentsAdapter(this, getAppUpdate());
//        mLinearList.setAdapter(mAdapter);
        mLinearList.setOnItemClickListener(this);

        ApiService.getRecents(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final Recents recents = new Gson()
                        .fromJson(response.body().string(), Recents.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecentsAdapter(HomeActivity.this, recents.getApps());
                        mLinearList.setAdapter(mAdapter);
                    }
                });
            }
        });


    }


    @Override
    public void onItemClick(final LinearListView linearListView, final View view,
            final int position, final long l) {
        final AppUpdate appUpdate = (AppUpdate) mAdapter.getItem(position);
        final View icon = view.findViewById(R.id.icon);
        final View name = view.findViewById(R.id.recents_app_name);
        final View card = findViewById(R.id.card_view_animation_hack);

        final int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        final Intent intent = DetailActivity
                .getInstance(HomeActivity.this, appUpdate, xy[0], xy[1]);

        Bundle bundle = Bundle.EMPTY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(HomeActivity.this,
                            Pair.create(icon, "iconTransition"),
                            Pair.create(card, "cardTransition"),
                            Pair.create(name, "nameTransition"));

            bundle = options.toBundle();
        }
        startActivity(intent, bundle);
    }
}
