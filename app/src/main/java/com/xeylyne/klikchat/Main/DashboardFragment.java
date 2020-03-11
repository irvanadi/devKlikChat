package com.xeylyne.klikchat.Main;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeylyne.klikchat.Main.Chart.ChartActivity;
import com.xeylyne.klikchat.Main.Company.CompanyActivity;
import com.xeylyne.klikchat.Main.DetailTicket.DetailTicketActivity;
import com.xeylyne.klikchat.Main.Division.DivisionActivity;
import com.xeylyne.klikchat.Main.FAQ.FAQActivity;
import com.xeylyne.klikchat.Main.User.UserActivity;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.Main.Ticket.TicketActivity;

public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        view.findViewById(R.id.imgNewTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.txtCountNewTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.txtNewTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.imgOnProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.txtCountOnProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.txtOnProgress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.imgTicketDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.txtCountTicketDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.txtTicketDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTicketActivity.class);
                intent.putExtra("new","new");
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

        view.findViewById(R.id.btnDivision).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DivisionActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btnUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btnTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TicketActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btnFAQ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FAQActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btnShowChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
