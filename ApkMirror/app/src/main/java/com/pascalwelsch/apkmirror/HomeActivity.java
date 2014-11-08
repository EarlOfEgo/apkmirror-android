package com.pascalwelsch.apkmirror;

import com.google.gson.Gson;

import com.linearlistview.LinearListView;
import com.pascalwelsch.apkmirror.adapter.RecentAppUpdateAdapter;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.pascalwelsch.apkmirror.model.Recents;
import com.pascalwelsch.apkmirror.services.ApiService;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class HomeActivity extends BaseActivity implements LinearListView.OnItemClickListener {


    private RecentAppUpdateAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.recents_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecentAppUpdateAdapter(HomeActivity.this, new ArrayList<AppUpdate>());
        mRecyclerView.setAdapter(mAdapter);

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
                        mAdapter.updateList(recents.getApps());
                    }
                });
            }
        });
    }


    @Override
    public void onItemClick(final LinearListView linearListView, final View view,
            final int position, final long l) {
        final AppUpdate appUpdate = mAdapter.getItem(position);
        final View icon = view.findViewById(R.id.icon);
        final View name = view.findViewById(R.id.recents_app_name);
        final View publisher = view.findViewById(R.id.recents_app_publisher);
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
                            Pair.create(publisher, "publisherTransition"),
                            Pair.create(name, "nameTransition"));

            bundle = options.toBundle();
        }
        startActivity(intent, bundle);
    }
}
