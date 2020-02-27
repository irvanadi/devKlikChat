package com.xeylyne.klikchat.Main.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestUser;
import com.xeylyne.klikchat.Request.RequestUserData;
import com.xeylyne.klikchat.Response.ResponseUser;
import com.xeylyne.klikchat.Response.ResponseUserData;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ResponseUser> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.recyclerUser);

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
        Call<RequestUserData> call = RetrofitInstance.getInstance().getUser(Token, "10", "0", "ASC");
        call.enqueue(new Callback<RequestUserData>() {
            @Override
            public void onResponse(Call<RequestUserData> call, Response<RequestUserData> response) {
                Log.d("Succ1", "onResponse: " + response.message());
            }

            @Override
            public void onFailure(Call<RequestUserData> call, Throwable t) {
                Log.d("Err", "onFailure: " + t.getMessage());
            }
        });
    }
}
