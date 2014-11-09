package com.pascalwelsch.apkmirror;

import com.google.gson.Gson;

import com.pascalwelsch.apkmirror.adapter.RecentAppUpdateAdapter;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.pascalwelsch.apkmirror.model.Recents;
import com.pascalwelsch.apkmirror.services.ApiService;
import com.pascalwelsch.apkmirror.widgets.CustomSwipeRefreshLayout;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,
        View.OnTouchListener {


    private RecentAppUpdateAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private CustomSwipeRefreshLayout mSwipeRefreshLayout;

    private int mXTouchPos = 0;

    private int mYTouchPos = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.recents_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecentAppUpdateAdapter(HomeActivity.this, new ArrayList<AppUpdate>(),
                this, this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        setupSwipeRefreshLayout();

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

                        final int amount = mAdapter.getItemCount();
                        mRecyclerView.animate();
                    }
                });
            }
        });

        refresh();
    }

    @Override
    public void onClick(final View view) {
        int position = mRecyclerView.getChildPosition(view);
        final AppUpdate appUpdate = mAdapter.getItem(position);
        final View icon = view.findViewById(R.id.recents_app_icon);
        final View name = view.findViewById(R.id.recents_app_name);
        final View publisher = view.findViewById(R.id.recents_app_publisher);
//        final View card = findViewById(R.id.card_view_animation_hack);

        final int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        final Intent intent = DetailActivity
                .getInstance(HomeActivity.this, appUpdate, mXTouchPos, xy[1] + mYTouchPos);

        Bundle bundle = Bundle.EMPTY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(HomeActivity.this,
                            Pair.create(icon, "iconTransition"),
//                            Pair.create(card, "cardTransition"),
                            Pair.create(publisher, "publisherTransition"),
                            Pair.create(name, "nameTransition"));

            bundle = options.toBundle();
        }
        startActivity(intent, bundle);
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mXTouchPos = (int) event.getX();
            mYTouchPos = (int) event.getY();
        }
        return false;
    }

    private void refresh() {

        ApiService.getRecents(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final Recents recents = new Gson()
                        .fromJson(response.body().string(), Recents.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateList(recents.getApps());
                        mRecyclerView.animate();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout = (CustomSwipeRefreshLayout) findViewById(
                R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setScrollView(findViewById(R.id.recents_scroll_view));

        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.color_swipetorefresh_1,
                    R.color.color_swipetorefresh_2,
                    R.color.color_swipetorefresh_3);

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
        }

    }
}
