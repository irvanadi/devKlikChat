package com.xeylyne.klikchat.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xeylyne.klikchat.Main.FAQ.FAQActivity;
import com.xeylyne.klikchat.Main.Ticket.ChatBotActivity;
import com.xeylyne.klikchat.Main.Ticket.ChatBotFragment;
import com.xeylyne.klikchat.Main.Ticket.TicketActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestChatBot;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestInsertChatBot;
import com.xeylyne.klikchat.Request.RequestInsertFaq;
import com.xeylyne.klikchat.Request.RequestSuccess;
import com.xeylyne.klikchat.Response.ListSpinner;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChatBotAdapter extends RecyclerView.Adapter<ChatBotAdapter.ViewHolder> {

    Context context;
    List<RequestChatBot> results;
    private EditText edName, edResponse;
    private Spinner spinnerDivision;
    private ArrayList<ResponseDivision> resultsDivision = new ArrayList<>();
    private List<ListSpinner> listSpinner = new ArrayList<>();
    private int number;

    public ChatBotAdapter(Context context, List<RequestChatBot> results) {
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
        final RequestChatBot result = results.get(position);
        holder.txtfaqparent.setText(result.getName());
        holder.txtfaqchild.setText(result.getResponse());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle i = new Bundle();
//                i.putString("parent", result.getUuid());
//                i.putString("name", result.getName());
//
//                ChatBotFragment frag = new ChatBotFragment();
//                frag.setArguments(i);
//                FragmentManager fragmentManager = ((TicketActivity)context).getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .add(R.id.fragmentContainer, new ChatBotFragment())
//                        .addToBackStack(null)
//                        .commit();

                Intent intent = new Intent(context, ChatBotActivity.class);
                intent.putExtra("parent", result.getUuid());
                intent.putExtra("name", result.getName());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Popup(view, result.getId(), position);
                return true;
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
            Call<RequestSuccess> call = RetrofitInstance.getInstance().deleteChatBot(Token, "method/ticket/chatbot/destroy/" + id);
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

        alertDialog.setTitle("Form Chat Bot");
        LayoutInflater layoutInflater = ((TicketActivity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_chatbot, null);
        edName = view.findViewById(R.id.edName);
        edResponse = view.findViewById(R.id.edResponse);
        spinnerDivision = view.findViewById(R.id.spinnerDivision);

        edName.setText(results.get(position).getName());
        edResponse.setText(results.get(position).getResponse());

        SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestDivision> call = RetrofitInstance.getInstance().getDivision(Token,"100","ASC");
        call.enqueue(new Callback<RequestDivision>() {
            @Override
            public void onResponse(Call<RequestDivision> call, Response<RequestDivision> response) {
                if (response.isSuccessful()) {
                    ProgressDialogInstance.dissmisProgress();
                    switch (response.body().getSuccess().getCode()) {
                        case 200:
                            resultsDivision = response.body().getDivision().getData();
                            listSpinner = new ArrayList<>();
                            for (int i = 0; i < resultsDivision.size(); i ++){
                                listSpinner.add(new ListSpinner(resultsDivision.get(i).getId(), resultsDivision.get(i).getName()));
                                Log.d("Testing", "onResponse1: " + results.get(position).getDivisionId());
                                Log.d("Testing", "onResponse2: " + resultsDivision.get(i).getId());
                                if (results.get(position).getDivisionId() == Integer.parseInt(listSpinner.get(i).getId())){
                                    number = i;
                                }
                            }
                            ArrayAdapter<ListSpinner> adapter = new ArrayAdapter<ListSpinner>(context,  android.R.layout.simple_spinner_dropdown_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDivision.setAdapter(adapter);
                            spinnerDivision.setSelection(number,true);
                            Log.d("Number", "onResponse: " + number);
                            break;

                        default:
                            List<String> listSpinner2 = new ArrayList<>();
                            listSpinner2.add("There Is No Division Data");
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,  android.R.layout.simple_spinner_dropdown_item, listSpinner2);
                            adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                            spinnerDivision.setAdapter(adapter2);
                            break;

                    }
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Log.d("DivisionKLIKCHAT", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestDivision> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = context.getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                String division_id = listSpinner.get((int) spinnerDivision.getSelectedItemId()).getId();
                Call<RequestInsertChatBot> call = RetrofitInstance.getInstance().updateChatBot(Token, edName.getText().toString(), edResponse.getText().toString(),
                        division_id, "method/ticket/chatbot/update/" + id);
                call.enqueue(new Callback<RequestInsertChatBot>() {
                    @Override
                    public void onResponse(Call<RequestInsertChatBot> call, Response<RequestInsertChatBot> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {

                                case 200:
                                    Toast.makeText(context, "Division has been Updated", Toast.LENGTH_SHORT).show();
                                    results.get(position).setName(response.body().getChatbot().getName());
                                    results.get(position).setResponse(response.body().getChatbot().getResponse());
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
                    public void onFailure(Call<RequestInsertChatBot> call, Throwable t) {
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
