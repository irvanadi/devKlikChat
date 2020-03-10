package com.xeylyne.klikchat.Main.DetailTicket;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xeylyne.klikchat.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewTicketFragment extends Fragment {

    public static NewTicketFragment newInstance() {
        return new NewTicketFragment();
    }

    ArrayList<String> results = new ArrayList<>();
    TextView txtGone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);

        txtGone = view.findViewById(R.id.txtGone);

        if (results.size() == 0){
            txtGone.setVisibility(View.VISIBLE);
        }


        return view;
    }

}
