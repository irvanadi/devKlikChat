package com.xeylyne.klikchat.Main;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeylyne.klikchat.Main.Company.CompanyActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Main.Ticket.TicketActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        view.findViewById(R.id.btnShowDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TicketActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btnCompany).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CompanyActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
