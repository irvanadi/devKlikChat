package com.xeylyne.klikchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xeylyne.klikchat.Main.MainActivity;
import com.xeylyne.klikchat.Request.RequestLogin;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

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

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if (data != null){
            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
        }

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
        SharedPreferences retrieveToken = getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");
        if (!Token.equalsIgnoreCase("isNull")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Log.d("onToken", Token);
    }


    private void CheckInput() {
        if (edEmail.getText().toString().isEmpty()) {
            edEmail.setError("Please Input Email");
        } else if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Please Input Password");
        } else {

            ProgressDialogInstance.createProgressDialoge(LoginActivity.this, "Logging in");
            ProgressDialogInstance.showProgress();

            Login(edEmail.getText().toString(), edPassword.getText().toString());
        }
    }

    private void Login(String email, String password) {
        Call<RequestLogin> call = RetrofitInstance.getInstance().Login(email, password);
        call.enqueue(new Callback<RequestLogin>() {
            @Override
            public void onResponse(Call<RequestLogin> call, Response<RequestLogin> response) {
                if (response.isSuccessful()){
                    switch (response.body().getSuccess().getCode()) {
                        case 200:
                            ProgressDialogInstance.dissmisProgress();
                            Log.d(TAG, "onResponse: " + response.body().getToken());

                            //Save Token To Local
                            SharedPreferences tokenData = getApplicationContext().getSharedPreferences("token", MODE_PRIVATE);
                            SharedPreferences.Editor editor = tokenData.edit();
                            editor.putString("token", "Bearer " + response.body().getToken());
//                            editor.putString("company_id", String.valueOf(response.body().getUser().getCompanyId()));
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;

                        case 404:
                            Toast.makeText(LoginActivity.this, "Email / Password Incorrect", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response.body().getSuccess().getMessage());

                        default:
                            Toast.makeText(LoginActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response.message());
                    }
                    if (response.body().getCode() != null){
                        switch (response.body().getCode()){
                            default:
                                ProgressDialogInstance.dissmisProgress();
                                Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestLogin> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
