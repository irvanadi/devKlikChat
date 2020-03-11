package com.xeylyne.klikchat.Main.Company;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestCompany;
import com.xeylyne.klikchat.Response.ResponseSuccess;
import com.xeylyne.klikchat.Utilities.BitMapInstance;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private String Token;
    private EditText edCompanyName, edCompanyAddress;
    private Button btnSave;
    String companyName, companyAddress, csName, csMaxOpen, infoName, csImage, infoImage;
    Bitmap cs_image, info_image;
    File file_info, file_cs;
    ImageView imgCs, imgInfo;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edCompanyName = view.findViewById(R.id.edCompanyName);
        edCompanyAddress = view.findViewById(R.id.edCompanyAddress);
        imgCs = view.findViewById(R.id.imgCs);
        imgInfo = view.findViewById(R.id.imgInfo);
        btnSave = view.findViewById(R.id.btnSave);
        ProgressDialogInstance.createProgressDialoge(getContext(),"Loading Data");
        ProgressDialogInstance.showProgress();
        LoadData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Savee", "onClick: " + csImage);
                Log.d("Savee", "onClick: " + infoImage);

                SaveData();
            }
        });

        return view;
    }

    private void LoadData() {
        SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestCompany> call = RetrofitInstance.getInstance().getCompany(Token);
        call.enqueue(new Callback<RequestCompany>() {
            @Override
            public void onResponse(Call<RequestCompany> call, Response<RequestCompany> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 200:
                            edCompanyName.setText(response.body().getCompany().getName());
                            edCompanyAddress.setText(response.body().getCompany().getAddress());
                            csName = response.body().getCompany().getCsName();
                            csMaxOpen = response.body().getCompany().getCsMaxOpen();
                            infoName = response.body().getCompany().getInfoName();
                            csImage = response.body().getCompany().getCsImage();
                            infoImage = response.body().getCompany().getInfoImage();

                            ProgressDialogInstance.dissmisProgress();

                            Glide
                                    .with(getContext())
                                    .asBitmap()
                                    .load(response.body().getCompany().getCsImage())
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            cs_image = resource;
                                        }
                                    });

                            Glide
                                    .with(getContext())
                                    .asBitmap()
                                    .load(response.body().getCompany().getInfoImage())
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            info_image = resource;
                                        }
                                    });
                    }
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestCompany> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    public void SaveData() {

        Log.d(TAG, "SaveData: ");

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", createPartFromString(edCompanyName.getText().toString()));
        map.put("address", createPartFromString(edCompanyAddress.getText().toString()));
        map.put("cs_name", createPartFromString(csName));
        map.put("cs_max_open", createPartFromString(csMaxOpen));

//convert gambar jadi File terlebih dahulu dengan memanggil createTempFile yang di atas tadi.
        File file_cs = BitMapInstance.createTempFile(cs_image, getContext());
        File file_info = BitMapInstance.createTempFile(info_image, getContext());
        RequestBody reqFile_cs = RequestBody.create(MediaType.parse("image/*"), file_cs);
        RequestBody reqFile_info = RequestBody.create(MediaType.parse("image/*"), file_info);
        MultipartBody.Part body_cs = MultipartBody.Part.createFormData("cs_image", file_cs.getName(), reqFile_cs);
        MultipartBody.Part body_info = MultipartBody.Part.createFormData("info_image", file_info.getName(), reqFile_info);

// finally, kirim map dan body pada param interface retrofit
        SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestCompany> call = RetrofitInstance.getInstance().updateCompany(Token, body_cs, body_info, map);
        call.enqueue(new Callback<RequestCompany>() {
            @Override
            public void onResponse(Call<RequestCompany> call, Response<RequestCompany> response) {
                if (response.isSuccessful()) {
                    LoadData();
                    Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestCompany> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
