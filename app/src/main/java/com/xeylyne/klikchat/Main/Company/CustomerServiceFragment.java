package com.xeylyne.klikchat.Main.Company;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestCompany;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerServiceFragment extends Fragment {

    private static String TAG = "CustomerService";
    private String Token;
    private EditText edDisplayName, edMaxTicket;
    private Bitmap selectedImage;
    private ImageView imgPick;

    private static int GALLERY_REQUEST = 10;


    public static CustomerServiceFragment newInstance() {
        return new CustomerServiceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        edDisplayName = view.findViewById(R.id.edDisplayName);
        edMaxTicket = view.findViewById(R.id.edMaxTicket);
        imgPick = view.findViewById(R.id.imgProfile);

        view.findViewById(R.id.imgProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }
        });

        LoadData();

        return view;
    }

    private void PickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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
                            edDisplayName.setText(response.body().getCompany().getCsName());
                            edMaxTicket.setText(response.body().getCompany().getCsMaxOpen());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                imgPick.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.webp");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }


    public void uploadImage(Bitmap gambarbitmap) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("cycle_time_begin", createPartFromString("2017-07-19"));
        map.put("fish_species", createPartFromString("LELE SUPER"));
        map.put("crop_species", createPartFromString("BAYAM MERAH"));
        map.put("id", createPartFromString("323278djsadkhjye2"));

        File file = createTempFile(gambarbitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("cycle", file.getName(), reqFile);
    }

}
