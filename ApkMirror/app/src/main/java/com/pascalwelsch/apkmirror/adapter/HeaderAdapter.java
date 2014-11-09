package com.pascalwelsch.apkmirror.adapter;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.adapter.stickyheader.StickyHeadersAdapter;
import com.pascalwelsch.apkmirror.model.AppUpdate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pascalwelsch on 11/9/14.
 */
public class HeaderAdapter implements StickyHeadersAdapter<HeaderAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    private final RecentAppUpdateAdapter mItemAdapter;

    public HeaderAdapter(final RecentAppUpdateAdapter adapter) {
        mItemAdapter = adapter;
    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
        headerViewHolder.title.setText(R.string.recent_updates);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_recents_header, parent, false);

        return new ViewHolder(itemView);
    }
}
