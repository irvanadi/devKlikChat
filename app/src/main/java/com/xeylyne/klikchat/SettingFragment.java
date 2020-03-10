package com.xeylyne.klikchat;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.xeylyne.klikchat.Request.RequestSettings;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    TextView txtProfile, txtPhoneNumber, txtEmail, txtAddress;
    ImageView imgPhotos;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        txtProfile = view.findViewById(R.id.txtDetailProfile);
        txtPhoneNumber = view.findViewById(R.id.txtDetailPhoneNumber);
        txtEmail = view.findViewById(R.id.txtDetailEmail);
        txtAddress = view.findViewById(R.id.txtDetailAddress);
        imgPhotos = view.findViewById(R.id.imgPhotos);

        view.findViewById(R.id.txtContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
                emailSelectorIntent.setData(Uri.parse("mailto:business@klikchat.com"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"business@klikchat.com"});
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                emailIntent.setSelector(emailSelectorIntent);

//                Uri attachment = FileProvider.getUriForFile(this, "my_fileprovider", myFile);
//                emailIntent.putExtra(Intent.EXTRA_STREAM, attachment);

                if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(emailIntent);
            }
        });

        view.findViewById(R.id.imgContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
                emailSelectorIntent.setData(Uri.parse("mailto:business@klikchat.com"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"business@klikchat.com"});
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                emailIntent.setSelector(emailSelectorIntent);

//                Uri attachment = FileProvider.getUriForFile(this, "my_fileprovider", myFile);
//                emailIntent.putExtra(Intent.EXTRA_STREAM, attachment);

                if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(emailIntent);
            }
        });

        view.findViewById(R.id.imgLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
                settings.edit().clear().commit();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ProgressDialogInstance.createProgressDialoge(getContext(), "Loading Data");
        LoadData();

        return view;
    }

    private void LoadData() {
        SharedPreferences retrieveToken = getActivity().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestSettings> call = RetrofitInstance.getInstance().getSettings(Token);
        call.enqueue(new Callback<RequestSettings>() {
            @Override
            public void onResponse(Call<RequestSettings> call, Response<RequestSettings> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                    txtProfile.setText(response.body().getData().getName());
                    txtEmail.setText(response.body().getData().getEmail());
                    txtAddress.setText(response.body().getData().getAddress());
                    txtPhoneNumber.setText(response.body().getData().getNumber());

                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestSettings> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
