package com.xeylyne.klikchat.Main.Ticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Adapter.ChatBotAdapter;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestChatBot;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestInsertChatBot;
import com.xeylyne.klikchat.Response.ListSpinner;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatBotActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    FloatingActionButton fabChatButton;
    private String name, parent;
    ChatBotAdapter chatBotAdapter;
    private ArrayList<ResponseDivision> resultsDivision = new ArrayList<>();
    private List<ListSpinner> listSpinner = new ArrayList<>();
    private List<RequestChatBot> results = new ArrayList<>();
    private EditText edName, edResponse;
    private Spinner spinnerDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        recyclerView = findViewById(R.id.recyclerChatBot);
        toolbar = findViewById(R.id.toolbarChatBot);
        fabChatButton = findViewById(R.id.fabChatBot);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        parent = intent.getStringExtra("parent");

        getSupportActionBar().setTitle(name);

        fabChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });

        initRecyclerView();
        LoadData();
    }

    private void initRecyclerView() {
        recyclerView = RecyclerViewInstance.getRecyclerViewVertical(recyclerView, ChatBotActivity.this);
    }

    private void OpenDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatBotActivity.this);

        alertDialog.setTitle("Chat Bot");
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_chatbot, null);
        edName = view.findViewById(R.id.edName);
        edResponse = view.findViewById(R.id.edResponse);
        spinnerDivision = view.findViewById(R.id.spinnerDivision);

        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
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
                            }
                            ArrayAdapter<ListSpinner> adapter = new ArrayAdapter<ListSpinner>(ChatBotActivity.this,  android.R.layout.simple_spinner_dropdown_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDivision.setAdapter(adapter);
                            break;

                        default:
                            List<String> listSpinner2 = new ArrayList<>();
                            listSpinner2.add("There Is No Division Data");
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ChatBotActivity.this,  android.R.layout.simple_spinner_dropdown_item, listSpinner2);
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
                Toast.makeText(ChatBotActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                String division_id = listSpinner.get((int) spinnerDivision.getSelectedItemId()).getId();
                Call<RequestInsertChatBot> call = RetrofitInstance.getInstance().insertChatBot(Token, edName.getText().toString(), edResponse.getText().toString(),
                        parent, division_id, "1");
                call.enqueue(new Callback<RequestInsertChatBot>() {
                    @Override
                    public void onResponse(Call<RequestInsertChatBot> call, Response<RequestInsertChatBot> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {
                                default:
                                    Toast.makeText(ChatBotActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    LoadData();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void LoadData() {
        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<List<RequestChatBot>> call = RetrofitInstance.getInstance().getChatBot(Token, parent);
        call.enqueue(new Callback<List<RequestChatBot>>() {
            @Override
            public void onResponse(Call<List<RequestChatBot>> call, Response<List<RequestChatBot>> response) {
                if (response.isSuccessful()){
                    results = response.body();
                    chatBotAdapter = new ChatBotAdapter(ChatBotActivity.this, results);
                    recyclerView.setAdapter(chatBotAdapter);
                    chatBotAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ChatBotActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RequestChatBot>> call, Throwable t) {
                Toast.makeText(ChatBotActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}