package com.xeylyne.klikchat.Main.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.xeylyne.klikchat.Adapter.DivisionAdapter;
import com.xeylyne.klikchat.Main.Division.DivisionActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestInsertUser;
import com.xeylyne.klikchat.Request.RequestSuccess;
import com.xeylyne.klikchat.Request.RequestUser;
import com.xeylyne.klikchat.Response.ListSpinner;
import com.xeylyne.klikchat.Response.Message;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    EditText edName, edPhoneNumber, edEmail, edAddress;
    Spinner spinnerDivision, spinnerUserType;
    Toolbar toolbar;
    String name;
    int id_user;
    private ArrayList<ResponseDivision> results = new ArrayList<>();
    private List<ListSpinner> listSpinner = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        toolbar = findViewById(R.id.toolbarEditUser);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edName = findViewById(R.id.edName);
        edPhoneNumber = findViewById(R.id.edPhoneNumber);
        edEmail = findViewById(R.id.edEmail);
        edAddress = findViewById(R.id.edAddress);
        spinnerDivision = findViewById(R.id.spinnerDivision);
        spinnerUserType = findViewById(R.id.spinnerUserType);

        Intent intent = getIntent();
        if (intent.getStringExtra("name") != null){
            id_user = intent.getIntExtra("id_user", 0);
            name = intent.getStringExtra("name");
            edName.setText(intent.getStringExtra("name"));
            edPhoneNumber.setText(intent.getStringExtra("phonenumber"));
            edEmail.setText(intent.getStringExtra("email"));
            edAddress.setText(intent.getStringExtra("address"));
        }

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialogInstance.createProgressDialoge(EditUserActivity.this, "Saving Data");
                ProgressDialogInstance.showProgress();
                if (name == null){
                    InsertUser();
                    Toast.makeText(EditUserActivity.this, String.valueOf(spinnerUserType.getSelectedItemId()), Toast.LENGTH_SHORT).show();
                } else {
                    UpdateUser();
                }
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });

        findViewById(R.id.btnSendEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(id_user);
            }
        });

        ProgressDialogInstance.createProgressDialoge(this, "Loading Data");
        ProgressDialogInstance.showProgress();
        LoadData();
    }

    private void sendEmail(int id) {
        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<Message> call = RetrofitInstance.getInstance().emailSend(Token, String.valueOf(id));
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditUserActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditUserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OpenDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditUserActivity.this);

        alertDialog.setTitle("Delete User");
        alertDialog.setMessage("Are you sure ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProgressDialogInstance.createProgressDialoge(EditUserActivity.this, "Deleting Data");
                        ProgressDialogInstance.showProgress();
                        DeleteUser();
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
                            for (int i = 0; i < results.size(); i ++){
                                listSpinner.add(new ListSpinner(results.get(i).getId(), results.get(i).getName()));
                            }
                            ArrayAdapter<ListSpinner> adapter = new ArrayAdapter<ListSpinner>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDivision.setAdapter(adapter);
                            break;

                        default:
                            List<String> listSpinner2 = new ArrayList<>();
                            listSpinner2.add("There Is No Division Data");
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, listSpinner2);
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
                ProgressDialogInstance.dissmisProgress();
                Log.d("DivisionKLIKCHAT", "onFailure: " + t.getMessage());
            }
        });
    }

    private void InsertUser() {

        String usertype_id = String.valueOf(spinnerUserType.getSelectedItemId()+1);
        String division_id = listSpinner.get((int) spinnerDivision.getSelectedItemId()).getId();

        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Log.d("Insert User", "InsertUser: " + spinnerUserType.getSelectedItem());
        Call<RequestInsertUser> call = RetrofitInstance.getInstance().insertUser(Token, division_id,
                usertype_id,
                edName.getText().toString(),
                edEmail.getText().toString(), edPhoneNumber.getText().toString(), edAddress.getText().toString());
        call.enqueue(new Callback<RequestInsertUser>() {
            @Override
            public void onResponse(Call<RequestInsertUser> call, Response<RequestInsertUser> response) {
                if (response.isSuccessful()){
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(EditUserActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditUserActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Log.d("ERROR_123", "onResponse: " + String.valueOf(response.errorBody()));
                    Toast.makeText(EditUserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestInsertUser> call, Throwable t) {
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                ProgressDialogInstance.dissmisProgress();
            }
        });
    }

    private void UpdateUser() {
        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");
        String usertype_id = String.valueOf(spinnerUserType.getSelectedItemId()+1);
        String division_id = listSpinner.get((int) spinnerDivision.getSelectedItemId()).getId();
        Log.d("id_user", "UpdateUser: " + id_user);
        Call<RequestInsertUser> call = RetrofitInstance.getInstance().updateUser(Token, edName.getText().toString(),
                edEmail.getText().toString(), edPhoneNumber.getText().toString(), edAddress.getText().toString(),
                division_id, usertype_id, "method/user/update/" + id_user);
        call.enqueue(new Callback<RequestInsertUser>() {
            @Override
            public void onResponse(Call<RequestInsertUser> call, Response<RequestInsertUser> response) {
                if (response.isSuccessful()){
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(EditUserActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditUserActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(EditUserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestInsertUser> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteUser() {
        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestSuccess> call = RetrofitInstance.getInstance().deleteUser(Token, "method/user/destroy/" + id_user);
        call.enqueue(new Callback<RequestSuccess>() {
            @Override
            public void onResponse(Call<RequestSuccess> call, Response<RequestSuccess> response) {
                if (response.isSuccessful()){
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(EditUserActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditUserActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(EditUserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestSuccess> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
