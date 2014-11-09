package com.pascalwelsch.apkmirror.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * ApkMirror
 *
 * @author stephan 09.11.14
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollView;

    public CustomSwipeRefreshLayout(final Context context) {
        this(context, null);
    }

    public CustomSwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollView != null) {
            if (mScrollView.getScrollY() == 0.0) {
                return false;
            } else {
                return true;
            }
        }
        return super.canChildScrollUp();
    }

    public void setScrollView(final View scrollView) {
        mScrollView = scrollView;
    }
}
