package com.gnos.recyclerview3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListViewParts extends Activity {
    
    private class ListModel {
        private String itnbr;
        private String itdsc;
        private String ispec;

        public String getItnbr() {
            return itnbr;
        }

        public void setItnbr(String itnbr) {
            this.itnbr = itnbr;
        }

        public String getItdsc() {
            return itdsc;
        }

        public void setItdsc(String itdsc) {
            this.itdsc = itdsc;
        }

        public String getIspec() {
            return ispec;
        }

        public void setIspec(String ispec) {
            this.ispec = ispec;
        }
    }

    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListViewParts(RecyclerView recyclerView, Context context) {
        recyclerView.setHasFixedSize(true);

        // LayoutManager 생성 및 지정
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter 생성 및 지정
        adapter = new ListAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void addRow(String itnbr, String itdsc, String ispec) {
        ListModel list = new ListModel();

        list.setItnbr(itnbr);
        list.setItdsc(itdsc);
        list.setIspec(ispec);

        adapter.addItem(list);
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private ArrayList<ListModel> mList = new ArrayList<>();

        // 리스트 한행 추가
        public void addItem(ListModel list) {
//        this.vitem.add(0, list);
            this.mList.add(list);
            notifyDataSetChanged();
        }

        // 리스트 여러행 추가
        public void addItemList(ArrayList<ListModel> list) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView vitnbr;
            TextView vitdsc;
            TextView vispec;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                vitnbr = itemView.findViewById(R.id.itnbr);
                vitdsc = itemView.findViewById(R.id.itdsc);
                vispec = itemView.findViewById(R.id.ispec);
            }
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);

            ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
            ListModel list = mList.get(position);

            holder.vitnbr.setText(list.getItnbr());
            holder.vitdsc.setText(list.getItdsc());
            holder.vispec.setText(list.getIspec());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

}
