package com.app.utils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    public static int VERTICAL_VIEW = 0;
    public static int GRID_VIEW = 1;

    private boolean userScrolled = false;
    private int defaultView = VERTICAL_VIEW;
    private RecyclerView.LayoutManager mLayoutManager;

    public PaginationScrollListener(RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public PaginationScrollListener(RecyclerView.LayoutManager mLayoutManager, int defaultView) {
        this.defaultView = defaultView;
        this.mLayoutManager = mLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        userScrolled = newState != SCROLL_STATE_IDLE;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();

        int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        if (defaultView == GRID_VIEW) {
            firstVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        }

        if (!isLoading()/* && userScrolled */ && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
        int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ?
                0 : recyclerView.getChildAt(0).getTop();
        int firstVisibleItem;
        if (defaultView == GRID_VIEW) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

        } else {
            LinearLayoutManager linearLayoutManager1 = (LinearLayoutManager) mLayoutManager;
            firstVisibleItem = linearLayoutManager1.findFirstVisibleItemPosition();
        }
        setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
    }

    protected abstract void setEnabled(boolean b);

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}