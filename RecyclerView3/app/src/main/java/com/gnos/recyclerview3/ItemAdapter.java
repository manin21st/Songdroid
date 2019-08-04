package com.gnos.recyclerview3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<ItemModel> vitem;

    // 생성자에서 데이터리스트 객체를 전달받음
    public ItemAdapter(ArrayList<ItemModel> item) {
        this.vitem = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView vitnbr;
        public TextView vitdsc;
        public TextView vispec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vitnbr = itemView.findViewById(R.id.itnbr);
            vitdsc = itemView.findViewById(R.id.itdsc);
            vispec = itemView.findViewById(R.id.ispec);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = vitem.get(position);

        holder.vitnbr.setText(item.getItnbr());
        holder.vitdsc.setText(item.getItdsc());
        holder.vispec.setText(item.getIspec());
    }

    @Override
    public int getItemCount() {
        return vitem.size();
    }
}
