/*
package com.example.appdevelopmenttwo.adaptor;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Decoration extends RecyclerView.ItemDecoration {
    private int mDividerHeight = 10;
    private int mOrientation;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private int mLeftOffset;
    private int mRightOffset;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDivider(c, parent);
        } else {
//            drawHorizontalDivider(c, parent);
        }
    }

    //Draw item dividing line
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {
        // mLeftOffset 为自己设置的左边偏移量
        final int left = parent.getPaddingLeft() + mLeftOffset;
        // mRightOffset 为设置的右边偏移量
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() + mRightOffset;
        final int childSize = parent.getChildCount();

        if (childSize <= 0) {
            return;
        }

        // 从第一个 item 开始绘制
        int first = mStart;
        // 到第几个 item 绘制结束
        int last = childSize - mEnd - (mIsShowLastDivider ? 0 : 1);
//        Log.d(TAG, " last = " + last + " childSize =" + childSize + "left = " + left);

        if (last <= 0) {
            return;
        }

        for (int i = first; i < last; i++) {
            drawableVerticalDivider(canvas, parent, left, right, i, mDividerHeight);
        }

    }

    private void drawableVerticalDivider(Canvas canvas, RecyclerView parent, int left, int right, int i, int dividerHeight) {
        final View child = parent.getChildAt(i);

        if (child == null) {
            return;
        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int top = child.getBottom() + layoutParams.bottomMargin;
        final int bottom = top + dividerHeight;

        // 适配 drawable
        if (mDivider != null) {
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }

        // 适配分割线
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
*/
