package com.xeylyne.klikchat.Main.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestUser;
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
        Call<RequestUser> call = RetrofitInstance.getInstance().getUser(Token, "10", "0", "ASC");
        call.enqueue(new Callback<RequestUser>() {
            @Override
            public void onResponse(Call<RequestUser> call, Response<RequestUser> response) {
                if (response.body().getCode() == 200){

                }
            }

            @Override
            public void onFailure(Call<RequestUser> call, Throwable t) {

            }
        });
    }
}
