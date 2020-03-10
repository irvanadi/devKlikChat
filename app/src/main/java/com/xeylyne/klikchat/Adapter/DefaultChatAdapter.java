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

import com.xeylyne.klikchat.Main.Ticket.TicketActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestInsertDefaultChat;
import com.xeylyne.klikchat.Request.RequestSuccess;
import com.xeylyne.klikchat.Response.ResponseDefaultChat;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DefaultChatAdapter extends RecyclerView.Adapter<DefaultChatAdapter.ViewHolder> {

    Context context;
    List<ResponseDefaultChat> results;
    private EditText edDefault;

    public DefaultChatAdapter(Context context, List<ResponseDefaultChat> results) {
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
        final ResponseDefaultChat result = results.get(position);
        holder.txtDefaultChat.setText(result.getText());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Popup(view, result.getId(), position);

                return false;
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

    private void updateItem(final int id, final int position) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Form Default Chat");
        LayoutInflater layoutInflater = ((TicketActivity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_default_chat, null);

        edDefault = view.findViewById(R.id.edDefault);

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                Call<RequestInsertDefaultChat> call = RetrofitInstance.getInstance().updateDefaultChat(Token, edDefault.getText().toString(), "method/ticket/default/update/" + id);
                call.enqueue(new Callback<RequestInsertDefaultChat>() {
                    @Override
                    public void onResponse(Call<RequestInsertDefaultChat> call, Response<RequestInsertDefaultChat> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {
                                default:
                                    Toast.makeText(context, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    results.set(position, response.body().getChatDefault());
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    break;
                            }
                        } else {
                            Log.d("DefaultChat", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestInsertDefaultChat> call, Throwable t) {
                        Log.d("DefaultChat", "onFailure: " + t.getMessage());
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

    private void deleteItem(int id, final int position) {
        SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

            Call<RequestSuccess> call = RetrofitInstance.getInstance().deleteDefaultChat(Token, "method/ticket/default/destroy/" + id);
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


    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDefaultChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDefaultChat = itemView.findViewById(R.id.txtdivisionItem);
        }
    }
}
