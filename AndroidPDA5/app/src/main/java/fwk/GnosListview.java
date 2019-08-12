package fwk;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class GnosListview extends Activity {

    private GnosAdapter adapter;
    private RecyclerView recyclerView;
    private int[] r_id;
    private int r_layout;
    private String[] j_tag;

    public GnosListview(Context Ctx, RecyclerView Rcv, int R_layout, int[] R_id, String[] J_tag) {
        recyclerView = Rcv;

        recyclerView.setHasFixedSize(true);

        // RecyclerView 바인딩 정보
        r_layout = R_layout;    // activity_user_list  <-  activity_user_list,xml
        r_id = R_id;            // {R.id.id, R.id.pwd, R.id.name, R.id.dept}
        j_tag = J_tag;          // {"L_USERID", "L_PASSWORD", "EMPNAME", "DEPTNAME"}

        // LayoutManager 생성 및 지정
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Ctx);
        recyclerView.setLayoutManager(layoutManager);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Adapter 생성 및 지정
        adapter = new GnosAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void addRow(HashMap<String, String> row) {
        adapter.add(row);
    }

    public void setRows(ArrayList<HashMap<String, String>> list) {
        adapter.removeAll();
        adapter.addAll(list);
    }

    public class GnosAdapter extends RecyclerView.Adapter<GnosAdapter.ViewHolder> {
        private ArrayList<HashMap<String, String>> mList = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(r_layout, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HashMap<String, String> list = mList.get(position);
            for (int i=0;i<holder.txt_id.length;i++) {
                holder.txt_id[i].setText(list.get(j_tag[i]));
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView[] txt_id = new TextView[r_id.length];

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                for (int i=0;i<r_id.length;i++) {
                    txt_id[i] = itemView.findViewById(r_id[i]);
                }

                // 클릭 이벤트 처리
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("[GnosListView]", "position = " + getAdapterPosition());
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Log.d("[GnosListView]", "position = " + getAdapterPosition());
                        return false;
                    }
                });
            }
        }

        // 리스트 한행 추가
        public void add(HashMap<String, String> list) {
//        this.vitem.add(0, list);
            this.mList.add(list);
            notifyDataSetChanged();
        }
        // 리스트 여러행 추가
        public void addAll(ArrayList<HashMap<String, String>> list) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
        // 리스트 clear
        public void removeAll() {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }
}
