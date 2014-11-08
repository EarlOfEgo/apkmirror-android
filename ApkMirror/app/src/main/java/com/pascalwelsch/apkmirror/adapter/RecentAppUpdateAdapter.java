package com.pascalwelsch.apkmirror.adapter;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pascalwelsch on 10/19/14.
 */
public class RecentAppUpdateAdapter
        extends ArrayAdapter<AppUpdate, RecentAppUpdateAdapter.RecentAppsViewHolder> {

    public class RecentAppsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mAppIcon;

        private final TextView mAppName;

        private final TextView mPublisherName;

        public RecentAppsViewHolder(final View itemView) {
            super(itemView);
            mAppName = (TextView) itemView.findViewById(R.id.recents_app_name);
            mAppIcon = (ImageView) itemView.findViewById(R.id.recents_app_icon);
            mPublisherName = (TextView) itemView.findViewById(R.id.recents_app_publisher);
        }
    }

    private Context mContext;

    private LayoutInflater mInflater;

    public RecentAppUpdateAdapter(final Context context, final List<AppUpdate> appUpdates) {
        super(appUpdates);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final RecentAppsViewHolder viewHolder, final int position) {
        final AppUpdate appUpdate = getItem(position);
        viewHolder.mAppName.setText("" + appUpdate.getName());
        viewHolder.mPublisherName.setText("" + appUpdate.getName());
        Picasso.with(mContext).load(appUpdate.getIconUrl()).into(viewHolder.mAppIcon);
    }

    @Override
    public RecentAppsViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        @SuppressLint("InflateParams")
        // don't use mInflater.inflate(R.layout.view_recents, null); or the items get a wrap_content width
        // don't attachToRoot (3rd argument). The RecyclerView will attach it later
        final View view = mInflater.inflate(R.layout.view_recents, viewGroup, false);
        return new RecentAppsViewHolder(view);
    }


}

