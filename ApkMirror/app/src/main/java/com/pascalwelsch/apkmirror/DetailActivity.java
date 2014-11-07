package com.pascalwelsch.apkmirror;

import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by pascalwelsch on 10/25/14.
 */
public class DetailActivity extends BaseActivity {

    public static class TestFragment extends Fragment {

        private AppUpdate mApp;

        private ImageView mIcon;

        public TestFragment() {
            // do nothing
        }

        public static TestFragment newInstance(final AppUpdate appUpdate) {
            TestFragment fragment = new TestFragment();
            final Bundle bundle = new Bundle();
            bundle.putParcelable(INTENT_APP, appUpdate);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mApp = getArguments().getParcelable(INTENT_APP);
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                @Nullable final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_app_detail, null);

            mIcon = (ImageView) rootView.findViewById(R.id.icon);
            return rootView;
        }

        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            Picasso.with(getActivity()).load(mApp.getIconUrl()).into(mIcon);
        }
    }

    private static final String INTENT_APP = "intent::app";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AppUpdate app = getIntent().getExtras().getParcelable(INTENT_APP);
        setContentView(R.layout.activity_app_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.test_fragment, TestFragment.newInstance(app))
                    .commit();
        }
    }

    public static Intent getInstance(final Context context, final AppUpdate appUpdate) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_APP, appUpdate);
        return intent;
    }
}
