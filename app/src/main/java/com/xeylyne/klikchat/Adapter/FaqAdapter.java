package com.xeylyne.klikchat.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.xeylyne.klikchat.Main.FAQ.FAQActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestFAQ;
import com.xeylyne.klikchat.Request.RequestInsertDivision;
import com.xeylyne.klikchat.Request.RequestInsertFaq;
import com.xeylyne.klikchat.Request.RequestSuccess;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    Context context;
    List<RequestFAQ> results;
    private EditText edCommand, edFeedback;

    public FaqAdapter(Context context, List<RequestFAQ> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final RequestFAQ result = results.get(position);
        holder.txtfaqparent.setText(result.getHeader());
        holder.txtfaqchild.setText(result.getBody());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Popup(view, result.getId(), position);
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FAQActivity.class);
                intent.putExtra("parent", result.getUuid());
                intent.putExtra("header", result.getHeader());
                context.startActivity(intent);
            }
        });
    }

    private void Popup(View view, final int id, final int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.actionUpdate:
                        updateItem(id, position);
                        break;

                    case R.id.actionDelete:
                        OpenDialog(id, position);
                        break;
                }
                return false;
            }
        });
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, popupMenu.getMenu());
        popupMenu.show();
    }

    private void deleteItem(int id, final int position) {
        SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");
        if (results.get(position).getChild() != 0){
            ProgressDialogInstance.dissmisProgress();
            Toast.makeText(context, "Delete Child First", Toast.LENGTH_SHORT).show();
        } else {
            Call<RequestSuccess> call = RetrofitInstance.getInstance().deleteFAQ(Token, "method/faq/destroy/" + id);
            call.enqueue(new Callback<RequestSuccess>() {
                @Override
                public void onResponse(Call<RequestSuccess> call, Response<RequestSuccess> response) {
                    if (response.isSuccessful()){
                        ProgressDialogInstance.dissmisProgress();
                        Toast.makeText(context, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                        results.remove(position);
                        notifyDataSetChanged();
                    } else {
                        ProgressDialogInstance.dissmisProgress();
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RequestSuccess> call, Throwable t) {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateItem(final int id, final int position) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Form FAQ");
        LayoutInflater layoutInflater = ((FAQActivity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_faq, null);
        edCommand = view.findViewById(R.id.edCommand);
        edFeedback = view.findViewById(R.id.edFeedback);

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                Call<RequestInsertFaq> call = RetrofitInstance.getInstance().updateFAQ(Token, edCommand.getText().toString(), edFeedback.getText().toString(),
                        "method/faq/update/" + id);
                call.enqueue(new Callback<RequestInsertFaq>() {
                    @Override
                    public void onResponse(Call<RequestInsertFaq> call, Response<RequestInsertFaq> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {

                                case 200:
                                    Toast.makeText(context, "Division has been Updated", Toast.LENGTH_SHORT).show();
                                    results.get(position).setBody(response.body().getFaq().getBody());
                                    results.get(position).setHeader(response.body().getFaq().getHeader());
                                    results.set(position, results.get(position));
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
                    public void onFailure(Call<RequestInsertFaq> call, Throwable t) {
                        Log.d("Division", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }


    private void OpenDialog(final int id, final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Delete User");
        alertDialog.setMessage("Are you sure ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialogInstance.createProgressDialoge(context, "Deleting Data");
                        ProgressDialogInstance.showProgress();
                        deleteItem(id, position);
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

        TextView txtfaqparent,txtfaqchild;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtfaqparent = itemView.findViewById(R.id.txtfaqparent);
            txtfaqchild = itemView.findViewById(R.id.txtfaqchild);
        }
    }
}
