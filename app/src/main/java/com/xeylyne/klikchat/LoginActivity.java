package com.xeylyne.klikchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    private static String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CheckToken();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckInput();
            }
        });

        findViewById(R.id.txtRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CheckToken() {

        SharedPreferences tokenData = getApplicationContext().getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenData.edit();
        editor.putString("token", "Bearer ");
        editor.apply();

        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");
        if (!Token.equalsIgnoreCase("isNull")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Log.d("onToken",Token);
    }


    private void CheckInput() {
        if (edEmail.getText().toString().isEmpty()){
            edEmail.setError("Please Input Email");
        } else if (edPassword.getText().toString().isEmpty()){
            edPassword.setError("Please Input Password");
        } else {
            Login(edEmail.getText().toString(), edPassword.getText().toString());
        }
    }

    private void Login(String email, String password) {
        Call<Message> call = RetrofitInstance.getInstance().Login(email, password);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body().getMessage());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                    Toast.makeText(LoginActivity.this, "Check Input", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
