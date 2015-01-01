package com.pascalwelsch.apkmirror.detail;

import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTestUtils;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.*;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.assertThat;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    public void testAppDataVisible() throws Exception {
        final AppUpdate app = new AppUpdate();
        final AppUpdate mock = mock(AppUpdate.class);
        when(mock.getName()).thenReturn("blubb");

        app.setName("Sample App");
        app.setPublisher("Publisher");

        startDetailActivity(app);

        onView(withId(R.id.detail_app_name))
                .check(matches(withText(app.getName())));
        onView(withId(R.id.detail_app_publisher))
                .check(matches(withText(app.getPublisher())));
        onView(withId(R.id.details_app_icon)).check(matches(isDisplayed()));
        final Picasso picasso = PicassoTestUtils.mockPicasso();
        picasso.
    }

    public void testAppIcon() throws Exception {
        final AppUpdate app = new AppUpdate();
        final String iconUrl
                = "http://www.apkmirror.com/wp-content/uploads/2014/11/54655c2d1d190.png";
        app.setIconUrl(iconUrl);
        final AppDetailActivity activity = startDetailActivity(app);

        final ImageView name = (ImageView) activity.findViewById(R.id.details_app_blurry_image);
        assertNotNull(name);
    }

    private AppDetailActivity startDetailActivity(final AppUpdate app) {
        final Intent intent = AppDetailActivity.newInstance(getInstrumentation().getContext(), app);
        final AppDetailActivity appDetailActivity = launchActivity("com.pascalwelsch.apkmirror",
                AppDetailActivity.class, intent.getExtras());
        setActivity(appDetailActivity);
        return getActivity();
    }

}
