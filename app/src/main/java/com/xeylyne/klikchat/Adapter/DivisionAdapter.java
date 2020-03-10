package com.xeylyne.klikchat.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xeylyne.klikchat.Main.Division.DivisionActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestInsertDivision;
import com.xeylyne.klikchat.Request.RequestSuccess;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Response.ResponseSuccess;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.ViewHolder> {

    Context context;
    ArrayList<ResponseDivision> results;
    private EditText edDivision;

    public DivisionAdapter(Context context, ArrayList<ResponseDivision> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_division, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ResponseDivision result = results.get(position);
        holder.txtdivisionitem.setText(result.getName());
        Log.d("Division", "onBindViewHolder: " + result.getId());
        Log.d("Division", "onBindViewHolder: " + result.getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Popup(view, result.getId(), position);

                return false;
            }
        });
    }

    private void Popup(View view, final String id, final int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.actionUpdate:
                        updateItem(id, position);
                        break;

                    case R.id.actionDelete:
                        deleteItem(id, position);
                        break;
                }
                return false;
            }
        });
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, popupMenu.getMenu());
        popupMenu.show();
    }

    private void updateItem(final String id, final int position) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context);

        alertDialog.setTitle("Form Division");
        LayoutInflater layoutInflater = ((DivisionActivity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_division, null);
        edDivision = view.findViewById(R.id.edDivision);

        alertDialog.setView(view);
        final androidx.appcompat.app.AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                Call<RequestInsertDivision> call = RetrofitInstance.getInstance().updateDivision(Token, edDivision.getText().toString(),"method/division/update/" + id);
                call.enqueue(new Callback<RequestInsertDivision>() {
                    @Override
                    public void onResponse(Call<RequestInsertDivision> call, Response<RequestInsertDivision> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {

                                case 200:
                                    Toast.makeText(context, "Division has been Updated", Toast.LENGTH_SHORT).show();
                                    results.set(position, response.body().getDivision());
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    break;

                                default:
                                    Toast.makeText(context, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("Division", "onResponse: " + response.body().getSuccess().getMessage());
                                    break;
                            }
                        } else {
                            Log.d("Division", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestInsertDivision> call, Throwable t) {
                        Log.d("Division", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void deleteItem(final String id, final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
        final String Token = retrieveToken.getString("token", "isNull");

        Log.d("DelData", "deleteItem: " + id);
        alertDialog.setTitle("Delete Division");
        alertDialog.setMessage("Are you sure ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<RequestSuccess> call = RetrofitInstance.getInstance().deleteDivision(Token, "method/division/destroy/" + id);
                        call.enqueue(new Callback<RequestSuccess>() {
                            @Override
                            public void onResponse(Call<RequestSuccess> call, Response<RequestSuccess> response) {
                                if (response.isSuccessful()){
                                    switch (response.body().getSuccess().getCode()) {
                                        default:
                                            Toast.makeText(context, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                            results.remove(position);
                                            notifyDataSetChanged();
                                            break;
                                    }
                                } else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RequestSuccess> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        alertDialog.create();
        alertDialog.show();

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtdivisionitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdivisionitem = itemView.findViewById(R.id.txtdivisionItem);
        }
    }
}
