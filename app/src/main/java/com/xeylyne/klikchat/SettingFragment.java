package com.xeylyne.klikchat;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Main.MainActivity;
import com.xeylyne.klikchat.Request.RequestSettings;
import com.xeylyne.klikchat.Response.ResponseSuccess;
import com.xeylyne.klikchat.Testing.adapter.PreferenceAdapter;
import com.xeylyne.klikchat.Testing.model.preference.CategoryPreference;
import com.xeylyne.klikchat.Testing.model.preference.Preference;
import com.xeylyne.klikchat.Testing.model.preference.SettingsPreference;
import com.xeylyne.klikchat.Utilities.BitMapInstance;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
public class SettingFragment extends Fragment implements Preference.OnClickListener {

    private static final String KEY_PROFILE = "1";
    private static final String KEY_PHONENUMBER = "2";
    private static final String KEY_EMAIL = "3";
    private static final String KEY_ADDRESS = "4";
    private static final String KEY_LOGOUT = "5";
    private static final String KEY_CHANGEPASS = "6";
    private static final String KEY_PRIVACY = "7";
    private static final String KEY_VERSION = "8";
    private static final int GALLERY_REQUEST = 10;
    TextView txtProfile, txtPhoneNumber, txtEmail, txtAddress;
    ImageView imgPhotos;
    FloatingActionButton fabSettings;
    PreferenceAdapter preferenceAdapter;
    ArrayList<CategoryPreference> results = new ArrayList<>();
    RecyclerView recyclerView;
    SettingsPreference settingsPreference;
    CategoryPreference categoryPreference;
    String Profile, PhoneNumber, Email, Address;
    private EditText edSettings;
    private Bitmap selectedImage;


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        recyclerView = view.findViewById(R.id.recyclerSetting);
        fabSettings = view.findViewById(R.id.fabImg);
        imgPhotos = view.findViewById(R.id.imgPhotos);

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }
        });

        imgPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }
        });

        ProgressDialogInstance.createProgressDialoge(getContext(), "Loading Data");
        ProgressDialogInstance.showProgress();

        initData();
        LoadData();

        return view;
    }

    private void initData() {
        settingsPreference = new SettingsPreference(KEY_PROFILE, "Profile", null, R.drawable.ic_businessman, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference = new CategoryPreference("GENERAL", "My Profile");
        categoryPreference.addPreference(settingsPreference);
        settingsPreference = new SettingsPreference(KEY_PHONENUMBER, "Phone Number", null, R.drawable.ic_contact, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference.addPreference(settingsPreference);
        settingsPreference = new SettingsPreference(KEY_EMAIL, "Email", null, R.drawable.ic_mail_black_24dp, "Can't Change");
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference.addPreference(settingsPreference);
        settingsPreference = new SettingsPreference(KEY_ADDRESS, "Address", null, R.drawable.ic_location_on_black_24dp, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference.addPreference(settingsPreference);
        settingsPreference = new SettingsPreference(KEY_LOGOUT, "Log out", null, R.drawable.ic_logout, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference.addPreference(settingsPreference);
        results.add(categoryPreference);

        settingsPreference = new SettingsPreference(KEY_CHANGEPASS, "Change Password", null, R.drawable.ic_password, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference = new CategoryPreference("GENERAL", "Privacy & Security");
        categoryPreference.addPreference(settingsPreference);
        results.add(categoryPreference);

        settingsPreference = new SettingsPreference(KEY_PRIVACY, "Privacy Policy", null, R.drawable.ic_contact_us, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference = new CategoryPreference("GENERAL", "Support");
        categoryPreference.addPreference(settingsPreference);
        settingsPreference = new SettingsPreference(KEY_VERSION, "Version", "V1.0", R.drawable.ic_contact_us, null);
        settingsPreference.setClickListener(SettingFragment.this);
        categoryPreference.addPreference(settingsPreference);
        results.add(categoryPreference);

    }

    private void LoadData() {
        SharedPreferences retrieveToken = getActivity().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        recyclerView = RecyclerViewInstance.getRecyclerViewVertical(recyclerView, getContext());
        preferenceAdapter = new PreferenceAdapter(results);


        recyclerView.setAdapter(preferenceAdapter);

        Call<RequestSettings> call = RetrofitInstance.getInstance().getSettings(Token);
        call.enqueue(new Callback<RequestSettings>() {
            @Override
            public void onResponse(Call<RequestSettings> call, Response<RequestSettings> response) {
                if (response.isSuccessful()) {
                    results.clear();

                    Toast.makeText(getContext(), response.body().getData().getImage(), Toast.LENGTH_SHORT).show();

                    Glide.with(getContext())
                            .load(response.body().getData().getImage())
                            .fitCenter()
                            .into(imgPhotos);

                    settingsPreference = new SettingsPreference(KEY_PROFILE, "Profile", response.body().getData().getName(), R.drawable.ic_businessman, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference = new CategoryPreference("GENERAL", "My Profile");
                    categoryPreference.addPreference(settingsPreference);
                    settingsPreference = new SettingsPreference(KEY_PHONENUMBER, "Phone Number", response.body().getData().getNumber(), R.drawable.ic_contact, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference.addPreference(settingsPreference);
                    settingsPreference = new SettingsPreference(KEY_EMAIL, "Email", response.body().getData().getEmail(), R.drawable.ic_mail_black_24dp, "Can't Change");
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference.addPreference(settingsPreference);
                    settingsPreference = new SettingsPreference(KEY_ADDRESS, "Address", response.body().getData().getAddress(), R.drawable.ic_location_on_black_24dp, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference.addPreference(settingsPreference);
                    settingsPreference = new SettingsPreference(KEY_LOGOUT, "Log out", null, R.drawable.ic_logout, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference.addPreference(settingsPreference);
                    results.add(categoryPreference);

                    settingsPreference = new SettingsPreference(KEY_CHANGEPASS, "Change Password", null, R.drawable.ic_password, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference = new CategoryPreference("GENERAL", "Privacy & Security");
                    categoryPreference.addPreference(settingsPreference);
                    results.add(categoryPreference);

                    settingsPreference = new SettingsPreference(KEY_PRIVACY, "Privacy Policy", null, R.drawable.ic_contact_us, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference = new CategoryPreference("GENERAL", "Support");
                    categoryPreference.addPreference(settingsPreference);
                    settingsPreference = new SettingsPreference(KEY_VERSION, "Version", "V1.0", R.drawable.ic_contact_us, null);
                    settingsPreference.setClickListener(SettingFragment.this);
                    categoryPreference.addPreference(settingsPreference);
                    results.add(categoryPreference);

                    Profile = response.body().getData().getName();
                    Email = response.body().getData().getEmail();
                    PhoneNumber = response.body().getData().getNumber();
                    Address = response.body().getData().getAddress();

                    preferenceAdapter.notifyDataSetChanged();

                    ProgressDialogInstance.dissmisProgress();

                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestSettings> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(Preference preference) {
        switch (preference.getKey()) {
            case KEY_PROFILE:
                OpenDialog("profile");
                break;

            case KEY_ADDRESS:
                OpenDialog("address");
                break;

            case KEY_PHONENUMBER:
                OpenDialog("phone");
                break;

            case KEY_LOGOUT:
                Alert();
        }
    }

    private void Alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Log out");
        alertDialog.setMessage("Are you sure ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences settings = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
                        settings.edit().clear().apply();

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        alertDialog.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                Glide.with(getContext())
                        .load(selectedImage)
                        .fitCenter()
                        .into(imgPhotos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void PickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    private void OpenDialog(String key) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext());

        alertDialog.setTitle("Form Update Profile");
        LayoutInflater layoutInflater = ((MainActivity) getContext()).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_settings, null);
        edSettings = view.findViewById(R.id.edSettings);

        alertDialog.setView(view);
        final androidx.appcompat.app.AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialogInstance.createProgressDialoge(getContext(), "Saving Data");
                ProgressDialogInstance.showProgress();
                switch (key) {
                    case "profile":
                        Profile = edSettings.getText().toString();
                        break;

                    case "address":
                        Address = edSettings.getText().toString();
                        break;

                    case "phone":
                        PhoneNumber = edSettings.getText().toString();
                        break;
                }
                Bitmap bitmap = BitMapInstance.getBitmap(imgPhotos);
                uploadImage(bitmap);
                dialog.dismiss();
            }
        });
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    public void uploadImage(Bitmap gambarbitmap) {
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", createPartFromString(Profile));
        map.put("number", createPartFromString(PhoneNumber));
        map.put("address", createPartFromString(Address));

//convert gambar jadi File terlebih dahulu dengan memanggil createTempFile yang di atas tadi.
        File file = BitMapInstance.createTempFile(gambarbitmap, getContext());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        Log.d("SettingUpload", "uploadImage: " + file.getName());
// finally, kirim map dan body pada param interface retrofit
        SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<ResponseSuccess> call = RetrofitInstance.getInstance().updateSettings(Token, body, map);
        call.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.isSuccessful()) {

                    LoadData();
                    Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
