package com.xeylyne.klikchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xeylyne.klikchat.Main.MainActivity;
import com.xeylyne.klikchat.Request.RequestRegister;
import com.xeylyne.klikchat.Response.Message;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static String TAG = "Register";
    EditText edCompanyName, edEmail, edPassword, edConfirmPassword, edAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edCompanyName = findViewById(R.id.edCompanyName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        edAddress = findViewById(R.id.edCompanyAddress);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckInput();
            }
        });
    }

    private void CheckInput() {

        if (edCompanyName.getText().toString().isEmpty()){
            edCompanyName.setError("Please Input Company Name");
        } else if(edEmail.getText().toString().isEmpty()){
            edEmail.setError("Please Input Email");
        } else if (edPassword.getText().toString().isEmpty()){
            edPassword.setError("Please Input Password");
        } else if (edConfirmPassword.getText().toString().isEmpty()){
            edConfirmPassword.setError("Please Input Confirm Password");
        } else if (edAddress.getText().toString().isEmpty()){
            edAddress.setError("Please Input Address");
        } else {
            if (edPassword.getText().toString().equals(edConfirmPassword.getText().toString())){
                ProgressDialogInstance.createProgressDialoge(RegisterActivity.this, "Progress Register");
                ProgressDialogInstance.showProgress();
                Register();
            }
            if (edPassword.getText().toString().length() <8){
                edPassword.setError("Password Must Be 8 Or Higher");
            }
        }
    }

    private void Register() {
        Call<RequestRegister> call = RetrofitInstance.getInstance().Register(edCompanyName.getText().toString(),
                edEmail.getText().toString(), edPassword.getText().toString(), edAddress.getText().toString());
        call.enqueue(new Callback<RequestRegister>() {
            @Override
            public void onResponse(Call<RequestRegister> call, Response<RequestRegister> response) {
                if (response.isSuccessful()){
                    ProgressDialogInstance.dissmisProgress();
                    switch (response.body().getSuccess().getCode()){

                        case 200:
                            Log.d(TAG, "onResponse: " + response.body().getUser().getUuid());
                            Log.d(TAG, "onResponse: " + response.body().getToken());

                            //Save Token To Local
                            SharedPreferences tokenData = getApplicationContext().getSharedPreferences("token", MODE_PRIVATE);
                            SharedPreferences.Editor editor = tokenData.edit();
                            editor.putString("token", "Bearer " + response.body().getToken());
                            editor.apply();

                            Toast.makeText(RegisterActivity.this, "Register Has Been Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        case 404:
                            Log.d(TAG, "onResponse: " + response.body().getSuccess().getMessage());
                            Toast.makeText(RegisterActivity.this, response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestRegister> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
