package com.xeylyne.klikchat.Main.DetailTicket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.xeylyne.klikchat.Adapter.PagerAdapter;
import com.xeylyne.klikchat.Main.Company.CustomerServiceFragment;
import com.xeylyne.klikchat.Main.Company.OfficialAccountFragment;
import com.xeylyne.klikchat.Main.Company.ProfileFragment;
import com.xeylyne.klikchat.R;

public class DetailTicketActivity extends AppCompatActivity {

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    private static String TAG = "CompanyActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket);

        viewPager = findViewById(R.id.fragmentContainer);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbarDetailTicket);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(3);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(NewTicketFragment.newInstance());
        pagerAdapter.addFragment(TicketOnProgressFragment.newInstance());
        pagerAdapter.addFragment(TicketDoneFragment.newInstance());
        viewPager.setAdapter(pagerAdapter);
    }

}
