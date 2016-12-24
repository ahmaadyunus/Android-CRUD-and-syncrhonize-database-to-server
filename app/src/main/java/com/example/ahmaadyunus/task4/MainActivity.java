package com.example.ahmaadyunus.task4;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    private static boolean isLaunch=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if(isLaunch){
            openActivity(R.id.nav_dashboard);
            isLaunch=false;
        }
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        openActivity(id);
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void openActivity(int id) {
        switch (id) {
            case R.id.nav_dashboard:
                finish();
                startActivity(new Intent(this, DashboardActivity.class));
                break;
            case R.id.nav_transaction:
                finish();
                startActivity(new Intent(this, TransactionActivity.class));
                break;
            case R.id.nav_synchronize:

                break;
            default:
                break;
        }
    }


}
