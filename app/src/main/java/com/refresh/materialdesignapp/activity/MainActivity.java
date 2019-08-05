package com.refresh.materialdesignapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.refresh.materialdesignapp.R;

public class MainActivity extends AppCompatActivity {
    MenuItem checkedMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View to_navigationviewactivitiy = findViewById(R.id.to_navigationviewactivitiy);
        final View to_recycleviewdraghelpactivity = findViewById(R.id.to_recycleviewdraghelpactivity);
        to_navigationviewactivitiy.setOnClickListener(view -> {
            Intent start = new Intent();
            start.setClass(MainActivity.this, NavigationViewActivity.class);
            startActivity(start);
        });
        to_recycleviewdraghelpactivity.setOnClickListener(view -> {
            Intent start = new Intent();
            start.setClass(MainActivity.this, RecycleViewDragHelpActivity.class);
            startActivity(start);
        });

    }
}
