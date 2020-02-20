package com.xeylyne.klikchat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xeylyne.klikchat.R;


public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_division, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    TextView txtdivisionitem,txtdivisionitem1;
    ImageButton btndelete,btndelete1,btnedit,btnedit1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtdivisionitem = itemView.findViewById(R.id.txtdivisionItem);
            txtdivisionitem1 = itemView.findViewById(R.id.txtdivisionItem1);
            btndelete = itemView.findViewById(R.id.btndelete);
            btndelete1 = itemView.findViewById(R.id.btndelete1);
            btnedit = itemView.findViewById(R.id.btnedit);
            btnedit1 = itemView.findViewById(R.id.btnedit1);
        }
    }
}
