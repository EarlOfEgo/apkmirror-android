package com.pascalwelsch.apkmirror.detail;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pascalwelsch on 11/14/14.
 */
public class AppDetailFragment extends Fragment {

    protected static final String INTENT_APP = "intent::app";

    protected static final String INTENT_TOUCH_X = "intent::x_pos";

    protected static final String INTENT_TOUCH_Y = "intent::y_pos";

    private AppUpdate mApp;

    private ImageView mBlurryImage;

    public static AppDetailFragment newInstance(final AppUpdate appUpdate, final int x,
            final int y) {
        AppDetailFragment fragment = new AppDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_APP, appUpdate);
        if (x > 0 && y > 0) {
            bundle.putInt(INTENT_TOUCH_X, x);
            bundle.putInt(INTENT_TOUCH_Y, y);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AppDetailFragment newInstance(final AppUpdate app) {
        return newInstance(app, -1, -1);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = getArguments().getParcelable(INTENT_APP);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_app_detail, null);
        mBlurryImage = (ImageView) rootView.findViewById(R.id.details_app_blurry_image);
        TextView appName = (TextView) rootView.findViewById(R.id.detail_app_name);
        appName.setText(mApp.getName());

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getActivity())
                .load(mApp.getIconUrl())
                .transform(new BlurTransform(getActivity(), 10))
                .into(mBlurryImage);
    }
}
