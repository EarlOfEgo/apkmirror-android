package com.pascalwelsch.apkmirror.detail;

import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import static com.pascalwelsch.apkmirror.utils.ViewHelper.setBackgroundAlpha;
import static com.pascalwelsch.apkmirror.utils.ViewHelper.showText;

/**
 * Created by pascalwelsch on 11/14/14.
 */
public class AppDetailFragment extends Fragment
        implements ViewTreeObserver.OnScrollChangedListener {

    protected static final String INTENT_APP = "intent::app";

    protected static final String INTENT_TOUCH_X = "intent::x_pos";

    protected static final String INTENT_TOUCH_Y = "intent::y_pos";

    private static final String TAG = AppDetailFragment.class.getSimpleName();

    private AppInfo mApp;

    private ImageView mAppIcon;

    private ImageView mBlurryImage;

    private Picasso mPicasso;

    private int mHeaderHeight;

    private ParallaxScrollView mParallaxContainer;

    private Toolbar mToolbar;

    public static AppDetailFragment newInstance(final AppInfo appInfo, final int x,
            final int y) {
        AppDetailFragment fragment = new AppDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_APP, appInfo);
        if (x > 0 && y > 0) {
            bundle.putInt(INTENT_TOUCH_X, x);
            bundle.putInt(INTENT_TOUCH_Y, y);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AppDetailFragment newInstance(final AppInfo app) {
        return newInstance(app, -1, -1);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = getArguments().getParcelable(INTENT_APP);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        mPicasso = Picasso.with(activity);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_app_detail, null);
        mBlurryImage = (ImageView) rootView.findViewById(R.id.details_app_blurry_image);
        mAppIcon = (ImageView) rootView.findViewById(R.id.details_app_icon);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mParallaxContainer = (ParallaxScrollView) rootView
                .findViewById(R.id.parallax_scrollview);
        mParallaxContainer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewTreeObserver observer = mParallaxContainer.getViewTreeObserver();
                observer.addOnScrollChangedListener(AppDetailFragment.this);
                return false;
            }
        });
        final View headerView = ((ViewGroup) mParallaxContainer.getChildAt(0)).getChildAt(0);
        mHeaderHeight = headerView.getLayoutParams().height;
        mToolbar.setTitle(mApp.getName());

        return rootView;
    }

    @Override
    public void onScrollChanged() {

        // change the color of the toolbar
        int baseColor = getResources().getColor(R.color.apkmirror_primary);
        float alpha = 1 - (float) Math.max(0, mHeaderHeight - mParallaxContainer.getScrollY())
                / mHeaderHeight;
        setBackgroundAlpha(mToolbar, alpha, baseColor);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillViewWithData();

        // load the app icon and the blurry header icon
        final RequestCreator requestCreator = mPicasso
                .load(mApp.getIconUrl());
        requestCreator.into(mAppIcon);
        requestCreator.transform(new BlurTransform(getActivity(), 8)).into(mBlurryImage);
    }

    private void fillViewWithData() {
        final View view = getView();
        if (view == null) {
            // view not available. skip
            return;
        }
        if (mApp == null) {
            Log.w(TAG, "try to display app content but mApp is null");
            return;
        }

        showText((TextView) view.findViewById(R.id.detail_app_name), mApp.getName());
        showText((TextView) view.findViewById(R.id.detail_app_publisher), mApp.getPublisher());
    }


}
