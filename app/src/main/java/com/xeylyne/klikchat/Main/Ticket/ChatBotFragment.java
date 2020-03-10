package com.xeylyne.klikchat.Main.Ticket;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xeylyne.klikchat.Adapter.ChatBotAdapter;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Request.RequestChatBot;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestInsertChatBot;
import com.xeylyne.klikchat.Response.ListSpinner;
import com.xeylyne.klikchat.Response.ResponseChatBot;
import com.xeylyne.klikchat.Response.ResponseDivision;
import com.xeylyne.klikchat.Utilities.ProgressDialogInstance;
import com.xeylyne.klikchat.Utilities.RecyclerViewInstance;
import com.xeylyne.klikchat.Utilities.RetrofitInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatBotFragment extends Fragment {

    public static ChatBotFragment newInstance() {
        return new ChatBotFragment();
    }
    String parent, name;
    RecyclerView recyclerView;
    List<RequestChatBot> results = new ArrayList<>();
    ChatBotAdapter chatBotAdapter;
    FloatingActionButton fabChatBot;
    private EditText edName, edResponse;
    private Spinner spinnerDivision;
    private ArrayList<ResponseDivision> resultsDivision = new ArrayList<>();
    private List<ListSpinner> listSpinner = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_bot, container, false);


        recyclerView = view.findViewById(R.id.recyclerChatBot);
        fabChatBot = view.findViewById(R.id.fabChatBot);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            parent = bundle.getString("parent");
            name = bundle.getString("name");
            Toast.makeText(getContext(), parent, Toast.LENGTH_SHORT).show();
        }
        fabChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });

        ProgressDialogInstance.createProgressDialoge(getContext(), "Loading Data");
        ProgressDialogInstance.showProgress();

        initRecyclerView();

        LoadData();
        return view;
    }

    private void OpenDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Chat Bot");

        LayoutInflater layoutInflater = ((TicketActivity)getContext()).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.form_chatbot, null);
        ProgressDialogInstance.createProgressDialoge(getContext(),"Loading Data").show();
        edName = view.findViewById(R.id.edName);
        edResponse = view.findViewById(R.id.edResponse);
        spinnerDivision = view.findViewById(R.id.spinnerDivision);

        SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<RequestDivision> call = RetrofitInstance.getInstance().getDivision(Token,"100","ASC");
        call.enqueue(new Callback<RequestDivision>() {
            @Override
            public void onResponse(Call<RequestDivision> call, Response<RequestDivision> response) {
                if (response.isSuccessful()) {
                    ProgressDialogInstance.dissmisProgress();
                    switch (response.body().getSuccess().getCode()) {
                        case 200:
                            resultsDivision = response.body().getDivision().getData();
                            listSpinner = new ArrayList<>();
                            for (int i = 0; i < resultsDivision.size(); i ++){
                                listSpinner.add(new ListSpinner(resultsDivision.get(i).getId(), resultsDivision.get(i).getName()));
                            }
                            ArrayAdapter<ListSpinner> adapter = new ArrayAdapter<ListSpinner>(getContext(),  android.R.layout.simple_spinner_dropdown_item, listSpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDivision.setAdapter(adapter);
                            break;

                        default:
                            List<String> listSpinner2 = new ArrayList<>();
                            listSpinner2.add("There Is No Division Data");
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, listSpinner2);
                            adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                            spinnerDivision.setAdapter(adapter2);
                            break;

                    }
                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Log.d("DivisionKLIKCHAT", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RequestDivision> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialogInstance.createProgressDialoge(getContext(), "Saving Data").show();
                SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
                String Token = retrieveToken.getString("token", "isNull");

                if (listSpinner.isEmpty()){
                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }
                String division_id = listSpinner.get((int) spinnerDivision.getSelectedItemId()).getId();
                Call<RequestInsertChatBot> call = RetrofitInstance.getInstance().insertChatBot(Token, edName.getText().toString(), edResponse.getText().toString(),
                        parent, division_id, "1");
                call.enqueue(new Callback<RequestInsertChatBot>() {
                    @Override
                    public void onResponse(Call<RequestInsertChatBot> call, Response<RequestInsertChatBot> response) {
                        if (response.isSuccessful()) {
                            ProgressDialogInstance.dissmisProgress();
                            switch (response.body().getSuccess().getCode()) {
                                default:
                                    Toast.makeText(getContext(), response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    LoadData();
                                    break;
                            }
                        } else {
                            ProgressDialogInstance.dissmisProgress();
                            Log.d("Division", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestInsertChatBot> call, Throwable t) {
                        ProgressDialogInstance.dissmisProgress();
                        Log.d("Division", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }


    private void initRecyclerView() {
        RecyclerViewInstance.getRecyclerViewVertical(recyclerView, getContext());
    }

    private void LoadData() {
        SharedPreferences retrieveToken = getContext().getSharedPreferences("token", MODE_PRIVATE);
        String Token = retrieveToken.getString("token", "isNull");

        Call<List<RequestChatBot>> call = RetrofitInstance.getInstance().getChatBot(Token, parent);
        call.enqueue(new Callback<List<RequestChatBot>>() {
            @Override
            public void onResponse(Call<List<RequestChatBot>> call, Response<List<RequestChatBot>> response) {
                if (response.isSuccessful()){
                    ProgressDialogInstance.dissmisProgress();
                    results = response.body();
                    chatBotAdapter = new ChatBotAdapter(getContext(), results);
                    recyclerView.setAdapter(chatBotAdapter);
                    chatBotAdapter.notifyDataSetChanged();

                } else {
                    ProgressDialogInstance.dissmisProgress();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RequestChatBot>> call, Throwable t) {
                ProgressDialogInstance.dissmisProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
