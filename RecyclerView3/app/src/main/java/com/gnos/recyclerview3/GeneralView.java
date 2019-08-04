package com.gnos.recyclerview3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public abstract class GeneralView extends FrameLayout {
    public GeneralView(Context context) {
        super(context);
        View childView = createView(this);
        //recyclerview에 등록시 generalview 가 wrap / wrap으로 적용되기 때문에 원하는 childview의 layoutparam으로 맞춰준다
        setLayoutParams(childView.getLayoutParams());
        addView(childView);
        ButterKnife.bind(this);
    }

    public abstract void onBindData(int position, T data);

    public abstract View createView(ViewGroup parent);
}
