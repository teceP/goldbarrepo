package com.example.goldbarlift.model.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldbarlift.R;
import com.example.goldbarlift.model.fragments.FragmentCreatePubcrawl;
import com.example.goldbarlift.model.fragments.FragmentFavorites;
import com.example.goldbarlift.model.fragments.FragmentHome;
import com.example.goldbarlift.model.fragments.FragmentMaps;
import com.example.goldbarlift.model.fragments.SettingsDialogFragment;

public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SETTINGS = "SETTINGS";
    public static final String DISTANCE_SETTING = "DISTANCE_SETTING";
    public static final String NOTIFICATION_SETTING = "NOTIFICATION_SETTING";

    private DrawerLayout drawer;

    //Hier Moeglichkeit, Daten zu sichern bevor App ganz beendet wird
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //App wurde gestartet
    @Override
    protected void onStart() {
        super.onStart();
    }

    //Wird aufgerufen, wenn App das erste mal geoeffnet wird
    //Layout wird gesetzt
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //BOTTOM NAVIGATION
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();

        //MENU BAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Username
        TextView navHeader = findViewById(R.id.navHeaderName);
        //navHeader.setText("Mario");

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setFocusableInTouchMode(true);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.menu_createPubcrawl:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreatePubcrawl()).commit();
                break;
            case R.id.menu_settings:
                FragmentManager fm = getSupportFragmentManager();
                SettingsDialogFragment sdf = SettingsDialogFragment.newInstance("title");
                sdf.show(fm, "settings_title");
                break;
            case R.id.donate:
                Toast.makeText(this, "Paypal Email: mario.teklic77@googlemail.com :)", Toast.LENGTH_LONG).show();
                break;
            case R.id.info:
                Toast.makeText(this, "v0.1 / not released yet", Toast.LENGTH_LONG).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch(menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new FragmentHome();
                    break;
                case R.id.nav_favorites:
                    selectedFragment = new FragmentFavorites();
                    break;
                case R.id.nav_map:
                    selectedFragment = new FragmentMaps();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

}
