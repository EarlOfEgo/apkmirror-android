package com.pascalwelsch.apkmirror.detail;

import com.pascalwelsch.apkmirror.BaseActivity;
import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by pascalwelsch on 11/14/14.
 */
public class AppDetailActivity extends BaseActivity {

    private static final String INTENT_APP = AppDetailFragment.INTENT_APP;

    private static final String INTENT_TOUCH_X = AppDetailFragment.INTENT_TOUCH_X;

    private static final String INTENT_TOUCH_Y = AppDetailFragment.INTENT_TOUCH_Y;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_detail);

        if (savedInstanceState == null) {
            try {
                final Bundle extras = getIntent().getExtras();
                AppInfo app = extras.getParcelable(INTENT_APP);
                AppDetailFragment fragment;
                if (extras.containsKey(INTENT_TOUCH_X) && extras.containsKey(INTENT_TOUCH_Y)) {
                    final int touchX = extras.getInt(INTENT_TOUCH_X, -1);
                    final int touchY = extras.getInt(INTENT_TOUCH_Y, -1);
                    fragment = AppDetailFragment.newInstance(app, touchX, touchY);
                } else {
                    fragment = AppDetailFragment.newInstance(app);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.app_detail_fragment, fragment).commit();
            } catch (NullPointerException e) {
                throw new IllegalStateException(
                        "Use the static newInstance method to start this Activity", e);
            }
        }
    }

    /**
     * @param app the app to display
     * @param x   the x position the user was interacting with the app to start this detail view
     * @param y   the y touch point
     * @return an intent.
     */
    public static Intent newInstance(final Context context, final AppInfo app, final int x,
            final int y) {
        Intent intent = new Intent(context, AppDetailActivity.class);
        intent.putExtra(INTENT_APP, app);
        if (x > 0 && y > 0) {
            intent.putExtra(INTENT_TOUCH_X, x);
            intent.putExtra(INTENT_TOUCH_Y, y);
        }
        return intent;
    }

    public static Intent newInstance(final Context context, final AppInfo app) {
        return newInstance(context, app, -1, -1);
    }

}
