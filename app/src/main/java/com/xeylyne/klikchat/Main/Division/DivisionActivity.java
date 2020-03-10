package com.xeylyne.klikchat.Main.Division;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Adapter.DivisionAdapter;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisionActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabDivision;
    EditText edDivision;
    RecyclerView recyclerDivision;
    ArrayList<ResponseDivision> results = new ArrayList<>();
    DivisionAdapter divisionAdapter;
    private static String TAG = "DivisionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        toolbar = findViewById(R.id.toolbarDivision);
        fabDivision = findViewById(R.id.fabDivision);
        recyclerDivision = findViewById(R.id.recyclerDivison);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });

        ProgressDialogInstance.createProgressDialoge(DivisionActivity.this, "Loading Data");
        ProgressDialogInstance.showProgress();
        initRecyclerView();
        LoadData();
    }

    private void initRecyclerView() {
        RecyclerViewInstance.getRecyclerViewVertical(recyclerDivision, DivisionActivity.this);
    }

    private void LoadData() {

        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestDivision> call = RetrofitInstance.getInstance().getDivision(Token, "10", "ASC");
        call.enqueue(new Callback<RequestDivision>() {
            @Override
            public void onResponse(Call<RequestDivision> call, Response<RequestDivision> response) {
                if (response.isSuccessful()) {
                    ProgressDialogInstance.dissmisProgress();

                    switch (response.body().getSuccess().getCode()) {
                        case 200:
                            results = response.body().getDivision().getData();
                            divisionAdapter = new DivisionAdapter(DivisionActivity.this, results);
                            recyclerDivision.setAdapter(divisionAdapter);
                            divisionAdapter.notifyDataSetChanged();

                        default:
                            Log.d("DivisionKLIKCHAT4", "onResponse: " + response.body().getSuccess().getMessage());
                            divisionAdapter.notifyDataSetChanged();

                    }
                } else {
                    Log.d("DivisionKLIKCHAT", "onResponse: " + response.message());
                    ProgressDialogInstance.dissmisProgress();
                }
            }

            @Override
            public void onFailure(Call<RequestDivision> call, Throwable t) {
                Log.d("DivisionKLIKCHAT", "onFailure: " + t.getMessage());
                ProgressDialogInstance.dissmisProgress();
            }
        });
    }

    private void OpenDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Form Division");

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_division, null);
        edDivision = view.findViewById(R.id.edDivision);

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                Call<RequestDivision> call = RetrofitInstance.getInstance().insertDivision(Token, edDivision.getText().toString());
                call.enqueue(new Callback<RequestDivision>() {
                    @Override
                    public void onResponse(Call<RequestDivision> call, Response<RequestDivision> response) {
                        if (response.isSuccessful()) {
                            switch (response.body().getSuccess().getCode()) {

                                case 200:
                                    Toast.makeText(DivisionActivity.this, "Division has been Inserted", Toast.LENGTH_SHORT).show();
                                    LoadData();
                                    dialog.dismiss();
                                    break;

                                default:
                                    Toast.makeText(DivisionActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: " + response.body().getSuccess().getMessage());
                                    dialog.dismiss();
                                    break;
                            }
                        } else {
                            Log.d(TAG, "onResponse: " + response.message());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestDivision> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        dialog.dismiss();
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
}
