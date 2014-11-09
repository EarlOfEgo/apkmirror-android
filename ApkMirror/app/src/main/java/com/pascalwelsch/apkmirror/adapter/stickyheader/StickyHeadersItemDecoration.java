package com.pascalwelsch.apkmirror.adapter.stickyheader;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aurel on 22/09/14.
 */
public class StickyHeadersItemDecoration extends RecyclerView.ItemDecoration {

    private final StickyHeadersAdapter adapter;
    private final RecyclerView parent;
    private final HeaderStore headerStore;
    private final AdapterDataObserver adapterDataObserver;
    private boolean overlay;

    public StickyHeadersItemDecoration(StickyHeadersAdapter adapter, RecyclerView parent) {
        this(adapter, parent, false);
    }

    public StickyHeadersItemDecoration(StickyHeadersAdapter adapter, RecyclerView parent, boolean overlay) {
        this.adapter = adapter;
        this.parent = parent;
        this.overlay = overlay;
        this.headerStore = new HeaderStore();
        this.adapterDataObserver = new AdapterDataObserver();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {


        final int childCount = parent.getChildCount();
        final RecyclerView.LayoutManager lm = parent.getLayoutManager();
        Float lastY = null;

        for (int i = childCount - 1; i >= 0; i--) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)child.getLayoutParams();
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(child);

            if (!lp.isItemRemoved()) {

                float translationY = ViewCompat.getTranslationY(child);

                if (i == 0 && headerStore.isHeader(holder)) {

                        View header = headerStore.getHeaderViewByItem(holder);
                        int headerHeight = headerStore.getHeaderHeight(holder);
                        float y = getHeaderY(child, lm) + translationY;

                        if (lastY != null && lastY < y + headerHeight) {
                            y = lastY - headerHeight;
                        }


                        c.save();
                        c.translate(0, y);
                        header.draw(c);
                        c.restore();

                        lastY = y;
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)view.getLayoutParams();
        RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
        boolean isHeader = lp.isItemRemoved() ? headerStore.wasHeader(holder) : headerStore.isHeader(holder);


        if (overlay || !isHeader) {
            outRect.set(0, 0, 0, 0);
        }
        else {
            //TODO: Handle layout direction
            outRect.set(0, headerStore.getHeaderHeight(holder), 0, 0);
        }
    }

    public void registerAdapterDataObserver(RecyclerView.Adapter adapter) {
        adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    private float getHeaderY(View item, RecyclerView.LayoutManager lm) {
        return lm.getDecoratedTop(item);
    }


    private void layoutHeader(View header) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        header.measure(widthSpec, heightSpec);
        header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
    }


    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {

        public AdapterDataObserver() {
        }
        
        @Override
        public void onChanged() {
            headerStore.clear();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            headerStore.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            headerStore.onItemRangeInserted(positionStart, itemCount);
        }
    }

    private class HeaderStore {

        private final HashMap<Long, View> headersViewByHeadersIds;
        private final HashMap<Long, Boolean> wasHeaderByItemId;
        private final ArrayList<Boolean> isHeaderByItemPosition;
        private final HashMap<Long, Integer> headersHeightsByItemsIds;

        public HeaderStore() {
            this.headersViewByHeadersIds = new HashMap<Long, View>();
            this.wasHeaderByItemId = new HashMap<Long, Boolean>();
            this.isHeaderByItemPosition = new ArrayList<Boolean>();
            this.headersHeightsByItemsIds = new HashMap<Long, Integer>();
        }

        public View getHeaderViewByItem(RecyclerView.ViewHolder itemHolder) {
            int itemPosition = RecyclerViewHelper
                    .convertPreLayoutPositionToPostLayout(parent, itemHolder.getPosition());

            if (itemPosition == -1)
                return null;

            long headerId = adapter.getHeaderId(itemPosition);

            if (!headersViewByHeadersIds.containsKey(headerId)) {
                RecyclerView.ViewHolder headerViewHolder = adapter.onCreateViewHolder(parent);

                adapter.onBindViewHolder(headerViewHolder, itemPosition);
                layoutHeader(headerViewHolder.itemView);

                headersViewByHeadersIds.put(headerId, headerViewHolder.itemView);
            }

            return headersViewByHeadersIds.get(headerId);

        }

        public int getHeaderHeight(RecyclerView.ViewHolder itemHolder) {

            if (!headersHeightsByItemsIds.containsKey(itemHolder.getItemId())) {
                View header = getHeaderViewByItem(itemHolder);
                headersHeightsByItemsIds.put(itemHolder.getItemId(), header.getMeasuredHeight());
            }

            return headersHeightsByItemsIds.get(itemHolder.getItemId());
        }

        public boolean isHeader(RecyclerView.ViewHolder itemHolder) {
            int itemPosition = RecyclerViewHelper.convertPreLayoutPositionToPostLayout(parent, itemHolder.getPosition());
            if (isHeaderByItemPosition.size() <= itemPosition) {
                isHeaderByItemPosition.add(itemPosition, itemPosition == 0 || adapter.getHeaderId(itemPosition) != adapter.getHeaderId(itemPosition - 1));
            }
            else if (isHeaderByItemPosition.get(itemPosition) == null) {
                isHeaderByItemPosition.set(itemPosition, itemPosition == 0 || adapter.getHeaderId(itemPosition) != adapter.getHeaderId(itemPosition - 1));
            }

            return isHeaderByItemPosition.get(itemPosition);
        }

        public boolean wasHeader(RecyclerView.ViewHolder itemHolder) {
            if (!wasHeaderByItemId.containsKey(itemHolder.getItemId())) {
                int itemPosition = RecyclerViewHelper.convertPreLayoutPositionToPostLayout(parent, itemHolder.getPosition());
                wasHeaderByItemId.put(itemHolder.getItemId(), itemPosition == 0 || adapter.getHeaderId(itemPosition) != adapter.getHeaderId(itemPosition - 1));
            }
            return wasHeaderByItemId.get(itemHolder.getItemId());
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {

            if (isHeaderByItemPosition.size() > positionStart + itemCount) {

                for (int i = 0; i < itemCount; i++) {
                    RecyclerView.ViewHolder holder = parent.findViewHolderForPosition(positionStart + i);
                    if (holder != null) {
                        wasHeaderByItemId.put(holder.getItemId(), isHeaderByItemPosition.get(positionStart + i));
                    }
                }

                isHeaderByItemPosition.set(positionStart + itemCount, null);

                for (int i = 0; i < itemCount; i++) {
                    isHeaderByItemPosition.remove(positionStart);
                }
            }
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (isHeaderByItemPosition.size() > positionStart) {
                for (int i = 0; i < itemCount; i++) {
                    isHeaderByItemPosition.add(positionStart, null);
                }
            }


            if (isHeaderByItemPosition.size() > positionStart + itemCount) {
                isHeaderByItemPosition.set(positionStart + itemCount, null);
            }
        }

        public void clear() {
            headersViewByHeadersIds.clear();
            isHeaderByItemPosition.clear();
            wasHeaderByItemId.clear();
        }

    }
}
