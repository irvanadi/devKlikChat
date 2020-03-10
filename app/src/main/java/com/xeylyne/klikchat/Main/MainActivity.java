package com.xeylyne.klikchat.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.xeylyne.klikchat.R;
import com.xeylyne.klikchat.SettingFragment;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavMain);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

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

            case R.id.menu_settings:
                loadFragment(new SettingFragment());
                return true;
        }
        return loadFragment(fragment);
    }
}
