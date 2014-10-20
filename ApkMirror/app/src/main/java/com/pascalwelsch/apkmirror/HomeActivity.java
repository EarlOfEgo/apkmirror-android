package com.pascalwelsch.apkmirror;

import com.pascalwelsch.apkmirror.adapter.RecentAppUpdateAdapter;
import com.pascalwelsch.apkmirror.model.AppUpdate;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class HomeActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);



        final RecentAppUpdateAdapter appAdapter = new RecentAppUpdateAdapter(this, getAppUpdate());
        mRecyclerView.setAdapter(appAdapter);
    }

    public List<AppUpdate> getAppUpdate() {
        final List<AppUpdate> updates = new ArrayList<AppUpdate>();
        updates.addAll(Arrays.asList(
                new AppUpdate("test"),
                new AppUpdate("blabla")
        ));
        return updates;
    }
}
