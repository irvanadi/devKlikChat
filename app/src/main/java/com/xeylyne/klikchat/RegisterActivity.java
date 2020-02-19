package com.xeylyne.klikchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        }

        if (edPassword.getText().toString().equals(edConfirmPassword.getText().toString())){
            Register();
        }
        if (edPassword.getText().toString().length() <8){
            edPassword.setError("Password Must Be 8 Or Higher");
        }
    }

    private void Register() {
        Call<Message> call = RetrofitInstance.getInstance().Register(edCompanyName.getText().toString(),
                edEmail.getText().toString(), edPassword.getText().toString(), edAddress.getText().toString());
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d(TAG, "onResponse: " + response.body().getMessage());
                Log.d(TAG, "onResponse: " + response.body().getCode());
                if (response.body().getCode() == 200){
                    Log.d(TAG, "onResponse: " + "Success");
                    Toast.makeText(RegisterActivity.this, "Register Has Been Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Log.d(TAG, "onResponse: " + response.message());
                    Toast.makeText(RegisterActivity.this, "Check Your Input", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
