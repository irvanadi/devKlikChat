package com.xeylyne.klikchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavMain);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new DashboardFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        // load fragment

        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                fragment = new DashboardFragment();
                break;

            case R.id.menu_chat:
                loadFragment(new DashboardFragment());
                break;
//
//            case R.id.menu_settings:
//                loadFragment(new DashboardFragment());
//                return true;
        }

        return loadFragment(fragment);
    }
}
