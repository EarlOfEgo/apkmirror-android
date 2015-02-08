package com.pascalwelsch.apkmirror.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * ApkMirror
 *
 * @author stephan 09.11.14
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    private static final String TAG = CustomSwipeRefreshLayout.class.getSimpleName();

    private RecyclerView mRecyclerView;

    public CustomSwipeRefreshLayout(final Context context) {
        this(context, null);
    }

    public CustomSwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mRecyclerView != null) {
            final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                final int pos = ((LinearLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition();
                //Log.v(TAG, "pos: " + pos);
                return pos > 0;
            }
        }
        return super.canChildScrollUp();
    }

    public void setRecyclerView(final RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }
}
