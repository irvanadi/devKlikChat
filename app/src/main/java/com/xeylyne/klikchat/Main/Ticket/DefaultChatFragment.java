package com.xeylyne.klikchat.Main.Ticket;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Adapter.DefaultChatAdapter;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestDefaultChat;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestInsertChatBot;
import com.xeylyne.klikchat.Request.RequestInsertDefaultChat;
import com.xeylyne.klikchat.Response.ListSpinner;
import com.xeylyne.klikchat.Response.ResponseDefaultChat;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultChatFragment extends Fragment {

    public static DefaultChatFragment newInstance() {
        return new DefaultChatFragment();
    }

    RecyclerView recyclerView;
    List<ResponseDefaultChat> results = new ArrayList<>();
    DefaultChatAdapter defaultChatAdapter;
    FloatingActionButton fabDefaultChat;
    private EditText edDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerDefaultChat);
        fabDefaultChat = view.findViewById(R.id.fabDefaultChat);

        initRecyclerView();

        LoadData();

        fabDefaultChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });

        return view;
    }

    private void OpenDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Form Default Chat");
        LayoutInflater layoutInflater = ((TicketActivity)getContext()).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_default_chat, null);

        edDefault = view.findViewById(R.id.edDefault);

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                Call<RequestInsertDefaultChat> call = RetrofitInstance.getInstance().insertDefaultChat(Token, edDefault.getText().toString());
                call.enqueue(new Callback<RequestInsertDefaultChat>() {
                    @Override
                    public void onResponse(Call<RequestInsertDefaultChat> call, Response<RequestInsertDefaultChat> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {
                                default:
                                    Toast.makeText(getContext(), response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    LoadData();
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

    private void initRecyclerView() {
        RecyclerViewInstance.getRecyclerViewVertical(recyclerView, getContext());
    }

    private void LoadData() {

        SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestDefaultChat> call = RetrofitInstance.getInstance().getDefaultChat(Token, "100", "ASC");
        call.enqueue(new Callback<RequestDefaultChat>() {
            @Override
            public void onResponse(Call<RequestDefaultChat> call, Response<RequestDefaultChat> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getSuccess().getCode()) {

                        case 200:
                            results = response.body().getChatDefault().getData();
                            defaultChatAdapter = new DefaultChatAdapter(getContext(), results);
                            recyclerView.setAdapter(defaultChatAdapter);
                            defaultChatAdapter.notifyDataSetChanged();

                        default:
                            Toast.makeText(getContext(), response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestDefaultChat> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
