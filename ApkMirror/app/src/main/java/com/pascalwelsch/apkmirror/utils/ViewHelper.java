package com.pascalwelsch.apkmirror.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pascalwelsch on 12/7/14.
 */
public class ViewHelper {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Convert value of dip to pixel
     *
     * @param dp      The dp value to be converted
     * @param context to get the DisyplayMetrics to calculate the DP values
     * @return the calculated pixel value
     */
    public static int dpToPx(Context context, int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return (int) px;
    }

    /**
     * Generate a value suitable for use in {@link #setId(int)}. This value will not collide with ID
     * values generated at build time by aapt for R.id.
     * <p/>
     * Taken from the Android Source Code as it's only available for Devices with API >=17. See
     * http://stackoverflow.com/a/15442898/1384756
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) {
                newValue = 1; // Roll over to 1, not 0.
            }
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * hides a view depending on {@param hide}.
     * <p/>
     * analog to {@link #showView(android.view.View, boolean)}
     * <p/>
     * If you want the View to be {@link View#GONE} instead of {@link View#INVISIBLE} use {@link
     * #setVisiblity(android.view.View, boolean)}
     *
     * @param view the view to hide
     * @param hide true means {@link View#INVISIBLE}, false {@link View#VISIBLE}
     */
    public static void hideView(final View view, final boolean hide) {
        view.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * Convert value of dip to pixel
     *
     * @param displayWidth the displaywidth of device
     * @param percentage   the percentage you want to be shown
     * @return the calculated pixel value
     */
    public static int percentageOfScreen(int displayWidth, int percentage) {

        int percentageOfScreen = 0;

        percentageOfScreen = displayWidth / 100;
        percentageOfScreen = percentageOfScreen * percentage;

        return percentageOfScreen;
    }

    /**
     * changes the alpha of the background for the given view
     *
     * @param view      to apply the background with {@link View#setBackground(android.graphics.drawable.Drawable)}
     * @param alpha     between 0 and 1
     * @param baseColor the 100% visible color
     */
    public static void setBackgroundAlpha(final View view, final float alpha, final int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        view.setBackgroundColor(a + rgb);
    }

    /**
     * Crate an onclick-listeners for multiple views
     *
     * @param onClickListener onclickListener for views
     * @param views           views
     */
    public static void setOnClickListenerForViews(View.OnClickListener onClickListener,
            View... views) {
        for (View v : views) {
            v.setOnClickListener(onClickListener);
        }
    }

    /**
     * shows or hides the view
     * <p/>
     * If you want the View to be {@link View#INVISIBLE} instead of {@link View#INVISIBLE} use
     * {@link #hideView(android.view.View, boolean)} or {@link #showView(android.view.View,
     * boolean)}
     *
     * @param view    the view to apply the visiblity
     * @param visible true means {@link View#VISIBLE}, false {@link View#GONE}
     */
    public static void setVisiblity(final View view, final boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Crate an onclick-listeners for multiple views
     *
     * @param rootView        Root view containing the layout
     * @param onClickListener onclickListener for views
     * @param viewsIds        view IDs
     */
    public static void setonClickListenerForViews(View rootView,
            View.OnClickListener onClickListener, int... viewsIds) {
        for (int viewsId : viewsIds) {
            rootView.findViewById(viewsId).setOnClickListener(onClickListener);
        }
    }

    /**
     * Crate an onclick-listeners for multiple views by ID
     *
     * @param activity        Activity containing the layout
     * @param onClickListener onclickListener for views
     * @param viewsIds        view IDs
     */
    public static void setonClickListenerForViews(Activity activity,
            View.OnClickListener onClickListener, int... viewsIds) {
        for (int viewsId : viewsIds) {
            activity.findViewById(viewsId).setOnClickListener(onClickListener);
        }
    }

    public static void showText(final TextView textView, final String text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * Display a text or hide view if value is null or empty
     *
     * @param textView TextView which should be filled
     * @param text     the value for the TextView
     */
    public static void showTextOrHide(final TextView textView, final String text) {
        showText(textView, text);
        showView(textView, !TextUtils.isEmpty(text));
    }

    /**
     * hides a view depending on {@param show}.
     * <p/>
     * analog to {@link #showView(android.view.View, boolean)}
     * <p/>
     * If you want the View to be {@link View#GONE} instead of {@link View#INVISIBLE} use {@link
     * #setVisiblity(android.view.View, boolean)}
     *
     * @param view the view to hide
     * @param show true means {@link View#INVISIBLE}, false {@link View#VISIBLE}
     */
    public static void showView(final View view, final boolean show) {
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
