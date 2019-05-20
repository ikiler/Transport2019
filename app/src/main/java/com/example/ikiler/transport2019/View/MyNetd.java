package com.example.ikiler.transport2019.View;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.ikiler.transport2019.R;

/**
 * Created by ikiler on 19-5-18.
 */

public class MyNetd extends NestedScrollView {

    private int heder = 397;
    private TextView textView;
    public OnRefershListener listener;

    public MyNetd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textView = getChildAt(0).findViewById(R.id.header);
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                heder = textView.getHeight();
                scrollTo(0, heder);
            }
        });
    }

    public void setListener(OnRefershListener listener) {
        this.listener = listener;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        if (getScrollY() < (heder / 3)) {
            textView.setText("松开刷新");
        } else {
            textView.setText("下拉刷新");
        }
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
        if (getScrollY() < (heder / 3)) {
            if (null != listener)
                listener.onRefersh();
            setRefersh(true);
        } else if (getScrollY() < heder) {
            scrollTo(0, heder);
        }
    }

    public void setRefersh(boolean refersh) {
        if (refersh) {
            scrollTo(0, heder / 4);
            textView.setText("正在刷新........");
        } else {
            scrollTo(0, heder);
        }
    }

    public interface OnRefershListener {
        public void onRefersh();
    }

}
