package com.example.ahmaadyunus.task4.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ahmaadyunus.task4.R;

import com.example.ahmaadyunus.task4.realm.RealmHelper;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    RealmHelper realmHelper;
    private static boolean isLaunch=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                //versi database
                .schemaVersion(0)
                .migration(new DataMigration())
                .build();
        Realm.setDefaultConfiguration(config);

        if(isLaunch){
            openActivity(R.id.nav_dashboard);
            isLaunch=false;
        }
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       realmHelper = new RealmHelper(MainActivity.this);
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
    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            //Mengambil schema
            RealmSchema schema = realm.getSchema();

            //membuat schema baru jika versi 0
            if (oldVersion == 0) {
                schema.create("Bill")
                        .addField("id", int.class)
                        .addField("date", String.class)
                        .addField("type", String.class)
                        .addField("description", String.class)
                        .addField("amount", int.class);
                oldVersion++;
            }
        }
    }



}
