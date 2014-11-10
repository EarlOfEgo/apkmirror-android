package com.pascalwelsch.apkmirror.adapter;

import com.pascalwelsch.apkmirror.R;
import com.pascalwelsch.apkmirror.model.AppUpdate;
import com.pascalwelsch.apkmirror.utils.Formater;
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

        private final TextView mActionInfo;

        private final ImageView mAppIcon;

        private final TextView mAppName;

        private final TextView mPublisherName;


        public RecentAppsViewHolder(final View itemView) {
            super(itemView);
            mAppName = (TextView) itemView.findViewById(R.id.recents_app_name);
            mAppIcon = (ImageView) itemView.findViewById(R.id.recents_app_icon);
            mPublisherName = (TextView) itemView.findViewById(R.id.recents_app_publisher);
            mActionInfo = (TextView) itemView.findViewById(R.id.action_info);
        }
    }

    private final View.OnClickListener mOnClickListener;

    private final View.OnTouchListener mOnTouchListener;

    private Context mContext;

    private LayoutInflater mInflater;

    public RecentAppUpdateAdapter(final Context context, final List<AppUpdate> appUpdates,
            final View.OnClickListener onClickListener,
            final View.OnTouchListener onTouchListener) {
        super(appUpdates);
        mOnClickListener = onClickListener;
        mOnTouchListener = onTouchListener;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(final RecentAppsViewHolder viewHolder, final int position) {
        final AppUpdate appUpdate = getItem(position);
        viewHolder.mAppName.setText("" + appUpdate.getName());
        viewHolder.mPublisherName.setText("" + appUpdate.getPublisher());
        Picasso.with(mContext).load(appUpdate.getIconUrl()).into(viewHolder.mAppIcon);

        Formater.setButtonText(mContext, viewHolder.mActionInfo, appUpdate);
        Formater.setButtonTextColor(mContext, viewHolder.mActionInfo, appUpdate);
    }

    @Override
    public RecentAppsViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        @SuppressLint("InflateParams")
        // don't use mInflater.inflate(R.layout.view_recents, null); or the items get a wrap_content width
        // don't attachToRoot (3rd argument). The RecyclerView will attach it later
        final View view = mInflater.inflate(R.layout.view_recents, viewGroup, false);
        view.setOnClickListener(mOnClickListener);
        view.setOnTouchListener(mOnTouchListener);
        return new RecentAppsViewHolder(view);
    }
}

