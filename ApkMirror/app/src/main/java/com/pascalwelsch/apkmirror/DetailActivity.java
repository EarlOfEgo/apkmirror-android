package com.pascalwelsch.apkmirror;

import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.squareup.picasso.Picasso;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by pascalwelsch on 10/25/14.
 */
public class DetailActivity extends BaseActivity {

    public static class TestFragment extends Fragment implements View.OnClickListener {

        private AppUpdate mApp;

        private Button mDownloadButton;

        private RelativeLayout mHeaderView;

        private ImageView mIcon;

        private int mXPos;

        private int mYPos;

        public TestFragment() {
            // do nothing
        }

        public static TestFragment newInstance(final AppUpdate appUpdate, final int xPos,
                final int yPos) {
            TestFragment fragment = new TestFragment();
            final Bundle bundle = new Bundle();
            bundle.putParcelable(INTENT_APP, appUpdate);
            bundle.putInt(INTENT_X_POS, xPos);
            bundle.putInt(INTENT_Y_POS, yPos);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.detail_app_download_button:
                    //Download
                    break;
            }
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mXPos = getArguments().getInt(INTENT_X_POS);
            mYPos = getArguments().getInt(INTENT_Y_POS);
            mApp = getArguments().getParcelable(INTENT_APP);
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                @Nullable final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_app_detail, null);

            mIcon = (ImageView) rootView.findViewById(R.id.icon);
            mHeaderView = (RelativeLayout) rootView.findViewById(R.id.header_layout);

            ((TextView) rootView.findViewById(R.id.detail_app_file_name))
                    .setText("" + mApp.getName());
            ((TextView) rootView.findViewById(R.id.detail_app_version))
                    .setText("" + mApp.getVersionCode());
            ((TextView) rootView.findViewById(R.id.detail_app_uploaded))
                    .setText("" + mApp.getUpdateDate());
            ((TextView) rootView.findViewById(R.id.detail_app_file_size)).setText("");
            ((TextView) rootView.findViewById(R.id.detail_app_minimal_version)).setText("");
            ((TextView) rootView.findViewById(R.id.detail_app_downloads)).setText("");
            ((TextView) rootView.findViewById(R.id.detail_app_publisher)).setText(
                    "" + mApp.getPublisher());

            mDownloadButton = (Button) rootView.findViewById(R.id.detail_app_download_button);
            mDownloadButton.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Picasso.with(getActivity()).load(mApp.getIcon()).into(mIcon);
            setPalette();
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void setPalette() {
            final Bitmap bitmap = ((BitmapDrawable) mIcon.getDrawable()).getBitmap();
            Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int rgb = palette.getDarkVibrantColor(R.color.background_material_light);
                    mHeaderView.setBackgroundColor(rgb);

                    Display mdisp = getActivity().getWindowManager().getDefaultDisplay();
                    Point mdispSize = new Point();
                    mdisp.getSize(mdispSize);
                    int maxX = mdispSize.x;
                    int maxY = mdispSize.y;

                    int dx = Math.abs(maxX - mXPos);
                    int dy = Math.abs(maxY - mYPos);

                    final int endAnim = (int) Math.sqrt((dx * dx) + (dy * dy));
                    ViewAnimationUtils.createCircularReveal(mHeaderView,
                            mXPos,
                            mYPos,
                            0,
                            endAnim
                    ).setDuration(500)
                            .start();
                }
            });
        }
    }

    private static final String INTENT_APP = "intent::app";

    private static final String INTENT_X_POS = "intent::x_pos";

    private static final String INTENT_Y_POS = "intent::y_pos";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AppUpdate app = getIntent().getExtras().getParcelable(INTENT_APP);
        final int xPos = getIntent().getExtras().getInt(INTENT_X_POS);
        final int yPos = getIntent().getExtras().getInt(INTENT_Y_POS);
        setContentView(R.layout.activity_app_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.test_fragment, TestFragment.newInstance(app, xPos, yPos))
                    .commit();
        }
    }

    public static Intent getInstance(final Context context, final AppUpdate appUpdate,
            int xPosition, int yPosition) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_APP, appUpdate);
        intent.putExtra(INTENT_X_POS, xPosition);
        intent.putExtra(INTENT_Y_POS, yPosition);
        return intent;
    }
}
