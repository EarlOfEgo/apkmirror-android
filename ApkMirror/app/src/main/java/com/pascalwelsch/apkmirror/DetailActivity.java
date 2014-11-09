package com.pascalwelsch.apkmirror;

import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.pascalwelsch.apkmirror.services.DownloadService;
import com.pascalwelsch.apkmirror.utils.Formater;
import com.squareup.picasso.Picasso;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.transition.Explode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pascalwelsch on 10/25/14.
 */
public class DetailActivity extends BaseActivity {

    public static class TestFragment extends Fragment implements View.OnClickListener {

        private AppUpdate mApp;

        private TextView mAppName;

        private ImageView mBlurryImage;

        private Button mDownloadButton;

        private Button mGetItOnPlayButton;

        private View mHeaderView;

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
                    download();
                    break;
//                case R.id.detail_app_play_store:
//                    openPlayStore();
//                    break;
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
            mHeaderView = rootView.findViewById(R.id.details_app_header);

            mBlurryImage = (ImageView) rootView.findViewById(R.id.details_app_blurry_image);

            mAppName = (TextView) rootView.findViewById(R.id.detail_app_name);
            mAppName.setText("" + mApp.getName());

            ((TextView) rootView.findViewById(R.id.detail_app_file_name)).setText(
                    "" + mApp.getFilename());
            ((TextView) rootView.findViewById(R.id.detail_app_version)).setText(
                    "" + mApp.getVersion());
            ((TextView) rootView.findViewById(R.id.detail_app_md5)).setText(
                    "" + mApp.getMd5());
            ((TextView) rootView.findViewById(R.id.detail_app_sha1)).setText(
                    "" + mApp.getSha1());
            ((TextView) rootView.findViewById(R.id.detail_app_uploader)).setText(
                    "" + mApp.getUploader());
            ((TextView) rootView.findViewById(R.id.detail_app_minimal_version)).setText(
                    "" + mApp.getMinSdk());
            ((TextView) rootView.findViewById(R.id.detail_app_downloads)).setText(
                    "" + mApp.getDownloads());
            ((TextView) rootView.findViewById(R.id.detail_app_file_size)).setText(
                    Formater.humanReadableByteCount(0 + mApp.getFileSize(), false));
            ((TextView) rootView.findViewById(R.id.detail_app_publisher)).setText(
                    "" + mApp.getPublisher());

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date date = null;
                date = format.parse(mApp.getUploaded());
                String formatedDate = DateFormat.getDateInstance().format(date);
                String formatedTime = DateFormat.getTimeInstance().format(date);

                ((TextView) rootView.findViewById(R.id.detail_app_uploaded_day))
                        .setText("" + formatedDate);
                ((TextView) rootView.findViewById(R.id.detail_app_uploaded_time))
                        .setText("" + formatedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            mGetItOnPlayButton = (Button) rootView.findViewById(R.id.detail_app_download_button);
            if (mApp.getListingUrl() != null) {
//                mGetItOnPlayButton.setVisibility(View.VISIBLE);
//                mGetItOnPlayButton.setOnClickListener(this);
            }

            mDownloadButton = (Button) rootView.findViewById(R.id.detail_app_download_button);
            mDownloadButton.setOnClickListener(this);
            Formater.setButtonText(getActivity(), mDownloadButton, mApp);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setExitTransition(new Explode());
            }

            return rootView;
        }

        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Picasso.with(getActivity()).load(mApp.getIconUrl()).into(mIcon);
            Picasso.with(getActivity()).load(mApp.getIconUrl()).into(mBlurryImage);
            makeSexyMaterialAnimations();
        }

        private void download() {
            final DownloadService downloadService = new DownloadService(getActivity());

            downloadService.startDownload(mApp);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void makeSexyMaterialAnimations() {
            final Bitmap bitmap = ((BitmapDrawable) mIcon.getDrawable()).getBitmap();

            Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int rgb = palette.getDarkVibrantColor(R.color.background_material_light);
                    mHeaderView.setBackgroundColor(rgb);

                    mAppName.setTextColor(
                            palette.getLightMutedColor(R.color.bright_foreground_material_light));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mHeaderView
                            .isAttachedToWindow()) {

                        final Display mdisp = getActivity().getWindowManager().getDefaultDisplay();
                        final Point mdispSize = new Point();
                        mdisp.getSize(mdispSize);
                        final int maxX = mdispSize.x;
                        final int maxY = mdispSize.y;
                        final int endAnim = Math.max(maxX, maxY);
                        ViewAnimationUtils.createCircularReveal(mHeaderView,
                                mXPos,
                                mYPos,
                                0,
                                endAnim
                        ).setDuration(500)
                                .start();
//                        mIcon.setBackgroundColor(rgb);
                    }
                }
            });
        }

        private void openPlayStore() {
            final String appPackageName = mApp.getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mApp.getListingUrl())));
            }
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
