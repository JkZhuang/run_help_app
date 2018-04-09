package com.zjk.common.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by thomas on 16/6/1.
 */
public class ListItemDividerDecoration extends RecyclerView.ItemDecoration {

    protected int mColor;
    protected int mDividerSpace;
    private int mOrientation;
    //是否显示最后一个item的标线
    private boolean isHideLastDivider = false;
    protected int mMarginL, mMarginT, mMarginR, mMarginB;

    public ListItemDividerDecoration(int space, int orientation) {
        this(space, orientation, Color.TRANSPARENT);
    }

    public ListItemDividerDecoration(int space, int orientation, int color) {
        this(space, orientation, color, 0);
    }

    public ListItemDividerDecoration(int space, int orientation, int color, int margin) {
        this(space, orientation, color, margin, margin, margin, margin);
    }

    public ListItemDividerDecoration(int space, int orientation, int color, int mL, int mT, int mR, int mB) {
        this(space, orientation, color, false, mL, mT, mR, mB);
    }

    public ListItemDividerDecoration(int space, int orientation, int color, boolean isLastDivider, int mL, int mT, int mR, int mB) {
        mDividerSpace = space;
        this.isHideLastDivider = isLastDivider;
        setOrientation(orientation);
        setColor(color);
        setMargin(mL, mT, mR, mB);
    }

    public void setOrientation(int orientation) {
        if (orientation != RecyclerView.VERTICAL && orientation != RecyclerView.HORIZONTAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setColor(int c) {
        mColor = c;
    }

    public void setMargin(int l, int t, int r, int b) {
        mMarginL = l;
        mMarginT = t;
        mMarginR = r;
        mMarginB = b;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == RecyclerView.VERTICAL) {
            outRect.set(0, 0, 0, mDividerSpace);
        } else if (mOrientation == RecyclerView.HORIZONTAL) {
            outRect.set(0, 0, mDividerSpace, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mColor == 0) {
            return;
        }
        if (mOrientation == RecyclerView.VERTICAL) {
            drawVertical(c, parent, state);
        } else if (mOrientation == RecyclerView.HORIZONTAL) {
            drawHorizontal(c, parent, state);
        }
    }

    protected void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + mMarginL;
        int right = parent.getWidth() - parent.getPaddingRight() - mMarginR;
        int childCount = parent.getChildCount();
        int lastIndex = childCount - 1;
        for (int i = 0; i < childCount; i++) {

            if (isHideLastDivider && i >= lastIndex) {
                continue;
            }
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            int bottom = top + mDividerSpace;
            Rect rect = new Rect(left, top, right, bottom);
            drawDivider(c, rect);
        }
    }

    protected void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getTop() + mMarginT;
        int bottom = parent.getBottom() - parent.getPaddingBottom() - mMarginB;
        int childCount = parent.getChildCount();
        int lastIndex = childCount - 1;
        for (int i = 0; i < childCount; i++) {
            if (isHideLastDivider && i >= lastIndex) {
                continue;
            }
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() + params.leftMargin + Math.round(ViewCompat.getTranslationX(child)) - mDividerSpace;
            int right = left + mDividerSpace;
            Rect rect = new Rect(left, top, right, bottom);
            drawDivider(c, rect);
        }
    }

    private void drawDivider(Canvas c, Rect rect) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mColor);
        c.drawRect(rect, paint);
    }
}
