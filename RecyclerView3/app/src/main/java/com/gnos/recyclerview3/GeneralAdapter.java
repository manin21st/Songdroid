package com.gnos.recyclerview3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.GeneralViewHolder> {
    private Context mContext;
    private Class<?> classType;

    @NonNull
    @Override
    public GeneralViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GeneralView cellView;
        try {
            Constructor<?> con = classType.getDeclaredConstructor(Context.class);
            cellView = con.newInstance(parent.getContext());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new GeneralViewHolder<>(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralViewHolder holder, int position) {
        holder.onBindData(position, getItem(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GeneralViewHolder extends RecyclerView.ViewHolder{
        GeneralView itemView;
        public GeneralViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void onBindData(int position, T data) {
            itemView.onBindData(position, data);
        }
    }
}
