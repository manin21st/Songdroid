package com.gnos.recyclerview3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ItemView extends GeneralView {
    @BindView(R.id.title)
    TextView title;
    public ItemView (Context context) {
        super(context);
    }

    @Override
    public View createView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void onBindData(int position, String data) {
        title.setText(data);
    }
}
