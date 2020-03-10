package com.xeylyne.klikchat.Main.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Adapter.UserAdapter;
import com.xeylyne.klikchat.Main.MainActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestUser;
import com.xeylyne.klikchat.Request.RequestUserData;
import com.xeylyne.klikchat.Response.ResponseUser;
import com.xeylyne.klikchat.Response.ResponseUserData;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ResponseUserData> results = new ArrayList<>();
    UserAdapter userAdapter;
    Toolbar toolbar;
    FloatingActionButton fabUser;
    private static String TAG = "User";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.recyclerUser);
        toolbar = findViewById(R.id.toolbarUser);
        fabUser = findViewById(R.id.fabAddUser);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, EditUserActivity.class);
                startActivity(intent);
            }
        });

        ProgressDialogInstance.createProgressDialoge(this, "Loading Data");
        ProgressDialogInstance.showProgress();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerViewInstance.getRecyclerViewVertical(recyclerView, UserActivity.this);
        LoadDataUser();
    }

    private void LoadDataUser() {

        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");
        Log.d("Token", "LoadDataUser: " + Token);
        Call<RequestUser> call = RetrofitInstance.getInstance().getUser(Token, "10", "0", "ASC");
        call.enqueue(new Callback<RequestUser>() {
            @Override
            public void onResponse(Call<RequestUser> call, Response<RequestUser> response) {
                if (response.isSuccessful()) {
                    ProgressDialogInstance.dissmisProgress();
                    switch (response.body().getSuccess().getCode()) {

                        case 200:
                            results = response.body().getUser().getData();
                            userAdapter = new UserAdapter(UserActivity.this, results);
                            recyclerView.setAdapter(userAdapter);
                            break;

                        default:
                            Toast.makeText(UserActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response.body().getSuccess().getMessage());
                            break;
                    }

                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                    ProgressDialogInstance.dissmisProgress();
                }
            }

            @Override
            public void onFailure(Call<RequestUser> call, Throwable t) {
                Log.d("Err", "onFailure: " + t.getMessage());
                ProgressDialogInstance.dissmisProgress();
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
