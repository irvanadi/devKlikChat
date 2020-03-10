package com.xeylyne.klikchat.Main.Chart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xeylyne.klikchat.R;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    ArrayList<String> results = new ArrayList<>();
    TextView txtGone;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        txtGone = findViewById(R.id.txtGone);
        toolbar = findViewById(R.id.toolbarChart);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (results.size() == 0){
            txtGone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
