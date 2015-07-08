package org.scheming.note.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Scheming on 2015/5/30.
 */
public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private ListView mListView;
    private OnLoadMoreListener mListener;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setListViewListener();
    }

    private void setListViewListener() {
        int child_count = getChildCount();
        if (child_count > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                mListView.setOnScrollListener(this);
            }

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private void loadData() {
        if (mListener!=null){
            mListener.OnLoadMore();
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mListener) {
        this.mListener = mListener;
    }

    public interface OnLoadMoreListener {
        public void OnLoadMore();
    }
}
