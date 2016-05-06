package edu.drury.mcs.Dnav.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.LocationServices;

import edu.drury.mcs.Dnav.FragmentControl.AboutPage;
import edu.drury.mcs.Dnav.FragmentControl.DruryMap;
import edu.drury.mcs.Dnav.FragmentControl.FAQ_Fragment;
import edu.drury.mcs.Dnav.FragmentControl.Orientation;
import edu.drury.mcs.Dnav.FragmentControl.Schedule_Frag;
import edu.drury.mcs.Dnav.FragmentControl.contact_list_final;
import edu.drury.mcs.Dnav.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.placeholder, new DruryMap()).commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //slow down the navigation drawer close speed to optimize user experience
        assert drawer != null;
        drawer.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (id == R.id.nav_schedule) {
                    // Handle the Schedule_Frag action
                    toolbar.setTitle("SCHEDULE");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder, new Schedule_Frag()).commit();

                } else if (id == R.id.nav_map) {
                    // Handle the Map action
                    toolbar.setTitle("DRURY MAP");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder, new DruryMap()).commit();

                } else if (id == R.id.nav_faq) {
                    // Handle the FAQ action
                    toolbar.setTitle("FAQ");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder, new FAQ_Fragment()).commit();

                } else if (id == R.id.nav_contact) {
                    toolbar.setTitle("CONTACT");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder, new contact_list_final()).commit();

                } else if (id == R.id.nav_orientation) {
                    toolbar.setTitle("ORIENTATION");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder, new Orientation()).commit();
                } else if (id == R.id.nav_about) {
                    toolbar.setTitle("ABOUT");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder, new AboutPage()).commit();
                }

            }
        }, 400);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
