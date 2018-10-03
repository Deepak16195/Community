package com.rdavepatient.soft.Community.TagLayout;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class FlowTagLayout extends ViewGroup {

    private static final String TAG = FlowTagLayout.class.getSimpleName();

    /**
     * FlowLayout not support checked
     */
    public static final int FLOW_TAG_CHECKED_NONE = 0;

    /**
     * FlowLayout support multi-select
     */
    public static final int FLOW_TAG_CHECKED_MULTI = 2;

    /**
     * Should be used by subclasses to listen to changes in the dataset
     */
    AdapterDataSetObserver mDataSetObserver;

    /**
     * The adapter containing the data to be displayed by this view
     */
    ListAdapter mAdapter;

    /**
     * the tag click event callback
     */
    OnTagClickListener mOnTagClickListener;

    /**
     * the tag select event callback
     */
    OnTagSelectListener mOnTagSelectListener;


    private int mTagCheckMode = FLOW_TAG_CHECKED_NONE;


    private SparseBooleanArray mCheckedTagArray = new SparseBooleanArray();

    public FlowTagLayout(Context context) {
        super(context);
    }

    public FlowTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);


        int resultWidth = 0;
        int resultHeight = 0;
        int lineWidth = 0;
        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            measureChild(childView, widthMeasureSpec, heightMeasureSpec);


            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();


            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.leftMargin + mlp.rightMargin;
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;


            if ((lineWidth + realChildWidth) > sizeWidth) {

                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;

                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {

                lineWidth += realChildWidth;
                lineHeight = Math.max(lineHeight, realChildHeight);
            }


            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }

            setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                    modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int flowWidth = getWidth();

        int childLeft = 0;
        int childTop = 0;


        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);


            if (childView.getVisibility() == View.GONE) {
                continue;
            }


            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();


            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            if (childLeft + mlp.leftMargin + childWidth + mlp.rightMargin > flowWidth) {
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
            }

            int left = childLeft + mlp.leftMargin;
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.leftMargin + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;
            childView.layout(left, top, right, bottom);

            childLeft += (mlp.leftMargin + childWidth + mlp.rightMargin);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }



    private void reloadData() {
        removeAllViews();
        boolean isSetted = false;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final int j = i;
            mCheckedTagArray.put(i, false);
            final View childView = mAdapter.getView(i, null, this);
            addView(childView, new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));

            if (mAdapter instanceof OnInitSelectedPosition) {
                boolean isSelected = ((OnInitSelectedPosition) mAdapter).isSelectedPosition(i);
                if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                    if (!isSelected) {
                        mCheckedTagArray.put(i, false);
                        childView.setSelected(false);
                    }
                }
            }

            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTagCheckMode == FLOW_TAG_CHECKED_MULTI) {
                        if (mCheckedTagArray.get(j)) {
                            mCheckedTagArray.put(j, false);
                            childView.setSelected(false);
                        } else {
                            mCheckedTagArray.put(j, true);
                            childView.setSelected(true);
                        }
                        if (mOnTagSelectListener != null) {
                            List<Integer> list = new ArrayList<>();
                            for (int k = 0; k < mAdapter.getCount(); k++) {
                                if (mCheckedTagArray.get(k)) {
                                    list.add(k);
                                }
                            }
                            mOnTagSelectListener.onItemSelect(FlowTagLayout.this, list);
                        }
                    }
                }
            });
        }
    }


    public void clearAllOption(){
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (mCheckedTagArray.get(i)) {
                getChildAt(i).setSelected(false);
            }
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener onTagSelectListener) {
        this.mOnTagSelectListener = onTagSelectListener;
    }


    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        removeAllViews();
        mAdapter = adapter;

        if (mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }


    public int getmTagCheckMode() {
        return mTagCheckMode;
    }


    public void setTagCheckedMode(int tagMode) {
        this.mTagCheckMode = tagMode;
    }
}
