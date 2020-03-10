package com.xeylyne.klikchat.Main.Company;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xeylyne.klikchat.Adapter.PagerAdapter;
import com.xeylyne.klikchat.R;

public class CompanyActivity extends AppCompatActivity {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    private static String TAG = "CompanyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        viewPager = findViewById(R.id.fragmentContainer);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbarCompany);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
        setupTabLayout();
    }

    private void setupTabLayout() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(3);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(ProfileFragment.newInstance());
        pagerAdapter.addFragment(CustomerServiceFragment.newInstance());
        pagerAdapter.addFragment(OfficialAccountFragment.newInstance());
        viewPager.setAdapter(pagerAdapter);
    }

}
