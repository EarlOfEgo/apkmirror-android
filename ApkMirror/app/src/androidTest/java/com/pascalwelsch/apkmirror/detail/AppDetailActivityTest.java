package com.pascalwelsch.apkmirror.detail;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pascalwelsch on 11/14/14.
 */
public class AppDetailActivityTest extends ActivityInstrumentationTestCase2<AppDetailActivity> {

    public AppDetailActivityTest() {
        super(AppDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testAppIcon() throws Exception {
        final AppUpdate app = new AppUpdate();
        app.setIconUrl("http://www.apkmirror.com/wp-content/uploads/2014/11/54655c2d1d190.png");
        final AppDetailActivity activity = startDetailActivity(app);

        final ImageView name = (ImageView) activity.findViewById(R.id.details_app_blurry_image);
        assertNotNull(name);
        //https://engineering.aweber.com/unit-testing-remote-images-on-android-with-picasso-and-robolectric/
        // TODO use MockPicasso
    }

    public void testAppName() throws Exception {
        final AppUpdate app = new AppUpdate();
        app.setName("TestApp");
        final AppDetailActivity activity = startDetailActivity(app);

        final TextView name = (TextView) activity.findViewById(R.id.detail_app_name);
        assertNotNull(name);
        assertEquals(app.getName(), name.getText());
    }

    private void expectAppIcon(final AppDetailActivity activity) {

    }

    private AppDetailActivity startDetailActivity(final AppUpdate app) {
        final Intent intent = AppDetailActivity.newInstance(getInstrumentation().getContext(), app);
        final AppDetailActivity appDetailActivity = launchActivity("com.pascalwelsch.apkmirror",
                AppDetailActivity.class, intent.getExtras());
        setActivity(appDetailActivity);
        return getActivity();
    }

}