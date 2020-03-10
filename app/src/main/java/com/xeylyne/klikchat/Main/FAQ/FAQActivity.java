package com.xeylyne.klikchat.Main.FAQ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Adapter.DivisionAdapter;
import com.xeylyne.klikchat.Adapter.FaqAdapter;
import com.xeylyne.klikchat.Main.Division.DivisionActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestFAQ;
import com.xeylyne.klikchat.Request.RequestInsertFaq;
import com.xeylyne.klikchat.Response.ResponseParentFAQ;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<RequestFAQ> results = new ArrayList<>();
    String parent, header;
    FaqAdapter faqAdapter;
    FloatingActionButton fabFAQ;
    Toolbar toolbar;
    private EditText edCommand, edFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerView = findViewById(R.id.recyclerFaq);
        fabFAQ = findViewById(R.id.fabFaq);
        toolbar = findViewById(R.id.toolbarFAQ);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.getStringExtra("parent") != null){
            parent = intent.getStringExtra("parent");
            header = intent.getStringExtra("header");
            toolbar.setTitle(header);
        }

        ProgressDialogInstance.createProgressDialoge(FAQActivity.this, "Loading Data");
        ProgressDialogInstance.showProgress();

        fabFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });

        initRecyclerView();
        LoadData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void OpenDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Form FAQ");

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_faq, null);
        edCommand = view.findViewById(R.id.edCommand);
        edFeedback = view.findViewById(R.id.edFeedback);

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                Call<RequestInsertFaq> call = RetrofitInstance.getInstance().insertFAQ(Token, edCommand.getText().toString(),
                        edFeedback.getText().toString(), parent);
                call.enqueue(new Callback<RequestInsertFaq>() {
                    @Override
                    public void onResponse(Call<RequestInsertFaq> call, Response<RequestInsertFaq> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {

                                case 200:
                                    Toast.makeText(FAQActivity.this, "FAQ has been Inserted", Toast.LENGTH_SHORT).show();
                                    LoadData();
                                    dialog.dismiss();
                                    break;

                                default:
                                    Toast.makeText(FAQActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("FAQ", "onResponse: " + response.body().getSuccess().getMessage());
                                    dialog.dismiss();
                                    break;
                            }
                        } else {
                            Log.d("FAQ", "onResponse: " + response.message());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestInsertFaq> call, Throwable t) {
                        Log.d("FAQActivity", "onFailure: " + t.getMessage());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void initRecyclerView() {
        RecyclerViewInstance.getRecyclerViewVertical(recyclerView, FAQActivity.this);
    }

    private void LoadData() {

        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<List<RequestFAQ>> call = RetrofitInstance.getInstance().getFAQ(Token, parent);
        call.enqueue(new Callback<List<RequestFAQ>>() {
            @Override
            public void onResponse(Call<List<RequestFAQ>> call, Response<List<RequestFAQ>> response) {
                if (response.isSuccessful()){
                    ProgressDialogInstance.dissmisProgress();
                    results = response.body();
                    faqAdapter = new FaqAdapter(FAQActivity.this, results);
                    recyclerView.setAdapter(faqAdapter);
                    faqAdapter.notifyDataSetChanged();
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(FAQActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RequestFAQ>> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(FAQActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
