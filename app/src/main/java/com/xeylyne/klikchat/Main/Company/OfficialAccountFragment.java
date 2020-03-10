package com.xeylyne.klikchat.Main.Company;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestCompany;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialAccountFragment extends Fragment {

    private static String TAG = "CustomerService";
    private String Token;
    private EditText edDisplayName;

    public static OfficialAccountFragment newInstance() {
        return new OfficialAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_official_account, container, false);

        edDisplayName = view.findViewById(R.id.edDisplayName);
        LoadData();

        return view;
    }

    private void LoadData() {
        SharedPreferences retrieveToken = getActivity().getSharedPreferences("token", MODE_PRIVATE);
        Token = retrieveToken.getString("token", "isNull");

        Call<RequestCompany> call = RetrofitInstance.getInstance().getCompany(Token);
        call.enqueue(new Callback<RequestCompany>() {
            @Override
            public void onResponse(Call<RequestCompany> call, Response<RequestCompany> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 200:
                            edDisplayName.setText(response.body().getCompany().getInfoName());
                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestCompany> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
