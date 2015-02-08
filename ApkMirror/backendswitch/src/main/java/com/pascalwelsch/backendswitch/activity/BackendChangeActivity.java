package com.pascalwelsch.backendswitch.activity;

import com.pascalwelsch.backendswitch.Backend;
import com.pascalwelsch.backendswitch.R;
import com.pascalwelsch.backendswitch.Server;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pascalwelsch on 2/8/15.
 */
public class BackendChangeActivity extends ActionBarActivity {

    private static final String TAG = BackendChangeActivity.class.getSimpleName();

    private List<Map<String, String>> mBackends;

    private Spinner mSpinner;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_change);

        setSupportActionBar(((Toolbar) findViewById(R.id.toolbar_actionbar)));

        mSpinner = (Spinner) findViewById(R.id.spinner);

        mBackends = getBackends();
        final SimpleAdapter adapter = new SimpleAdapter(this, mBackends,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "ip"},
                new int[]{android.R.id.text1, android.R.id.text2});
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public boolean swallowedInflationCall = false;

            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                    final int position, final long id) {

                // swallow the first call. It's from layouting and not a user input
                if (!swallowedInflationCall) {
                    swallowedInflationCall = true;
                    return;
                }

                final String name = mBackends.get(position).get("name");
                Log.v(TAG, name);
                Backend.load(BackendChangeActivity.this, name);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
    }

    private List<Map<String, String>> getBackends() {

        final List<Map<String, String>> backends = new ArrayList<>();
        // first add the current server
        final Server currentBackend = Backend.get();
        backends.add(serverToMap(currentBackend));

        // add all others
        for (Server server : Backend.getsBackends()) {
            if (!server.equals(currentBackend)) {
                backends.add(serverToMap(server));
            }
        }
        return backends;
    }

    private Map<String, String> serverToMap(final Server server) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("name", server.getName());
        map.put("ip", server.getUrl());
        return map;
    }
}
