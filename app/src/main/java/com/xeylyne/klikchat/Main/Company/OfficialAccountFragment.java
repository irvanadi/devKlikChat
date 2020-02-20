package com.xeylyne.klikchat.Main.Company;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeylyne.klikchat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialAccountFragment extends Fragment {


    public static OfficialAccountFragment newInstance() {
        return new OfficialAccountFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_official_account, container, false);
    }

}
