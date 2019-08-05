package com.refresh.materialdesignapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.refresh.materialdesignapp.R;

/**
 * 使用DrawerLayout和NavigationView实现侧边拽拖
 * @author Administrator
 */
public class NavigationViewActivity extends AppCompatActivity {
    MenuItem checkedMenu =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_view);

        final NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.mDrawerLayout);
        final Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(1);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //MenuItem checkedItem = navigationView.getCheckedItem();
                if(checkedMenu!=null) {
                    checkedMenu.setChecked(false);
                }
                checkedMenu = menuItem;
                menuItem.setCheckable(true);
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Snackbar.make(navigationView, "item click", Snackbar.LENGTH_LONG).show();
                return false;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(view ->{
            Snackbar.make(navigationView, "head click", Snackbar.LENGTH_LONG).show();
        });


    }
}
