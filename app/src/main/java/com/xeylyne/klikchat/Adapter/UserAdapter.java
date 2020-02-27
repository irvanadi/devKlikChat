package com.xeylyne.klikchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Response.ResponseUserData;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    ArrayList<ResponseUserData> responseUserData;

    public UserAdapter(Context context, ArrayList<ResponseUserData> responseUserData) {
        this.context = context;
        this.responseUserData = responseUserData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseUserData result = responseUserData.get(position);
        holder.txtName.setText(result.getName());
        holder.txtDivision.setText(result.getDivision().getName());
        holder.txtRole.setText(result.getUserType().getName());
    }

    @Override
    public int getItemCount() {
        return responseUserData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDivision, txtRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtDetailName);
            txtDivision = itemView.findViewById(R.id.txtDetailDivision);
            txtRole = itemView.findViewById(R.id.txtDetailRole);
        }
    }
}
