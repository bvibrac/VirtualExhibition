package com.example.vibrac_b.virtualexhibition;

import android.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import eu.kudan.kudan.ARAPIKey;

import static com.example.vibrac_b.virtualexhibition.R.id.toolbar;

public class Profil_Gestion extends AppCompatActivity {

    private Handler mHandler;
    private boolean loadHomeFragmentOnBackPress = true;
    private DrawerLayout drawer = null;
    public Toolbar toolbar = null;

    private NavigationView navigationView = null;
    private View headerView = null;
    private ImageView img_profil = null;
    private TextView name_profil = null;

    private String[] activity_title = null;
    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_PROFIL = "profil";
    private static final String TAG_AR = "augmneted reality";
    private static final String TAG_LIB = "library";
    private static final String TAG_SET = "settings";
    public static String CURRENT_TAG = TAG_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil__gestion);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        img_profil = (ImageView) navigationView.findViewById(R.id.imageView);

        activity_title = getResources().getStringArray(R.array.nav_item_title);

        loadNavHeader();

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        ARAPIKey key = ARAPIKey.getInstance();
//        key.setAPIKey("sVmoznmKZ+4nFEHD6HoslwpC26PNuBZGHrikUwyon2BKSvza1yu2CqbSrae+pHPr1NHjhsf5pHQOZn8IEqXlqXFodGsrOJhxJANbMOdvnRLUi9/QWGqyRL9FViDmyohw6e5R7U4Ex8H7d7spLLvhfp5HFv56DgLr8c8sC2ipDtv9g1IjOTaY7UGxata3eulG2A/UkIdRv2NcotZXqan01xQUWFAislEwlGguParEYiwu11T4mqtU3dQBbfxpvxbczjdYz493YG3rAO2RHgT+5M5TJShJsz2irkNo71JD2Fzqf4AR2b4+7t1c55zKjegXzGS6Xa/rpNn9yiXUn7rUYIHNvN3cEQa9HsZiVxAV4vJgxFS+T/AxfWqKrEg1uj6xF5MsodZ2EkZ8mqliYIsxZqnFz+Re2HeWG8wvrEob0ZwRIO0TxppAemZc3HChTAPLcNt5gzeBk0oRP4wnrFAFFBDi8XjDocwTSVw++hWZb1qNHzt6bKLsMDRT057UVuuZB6M8f7EOQD79Oah0Vrx/3DUK6e9BEV8oGFNHtk1wyYEkg0i6RLhVSokGx//Qj36A4gCz3h1OjtfB0OuukbNq7xI1L/FcNQLmGYNGZwszARjGr9ESw1gVAkbQMxaV27uo/KoIq4+nR7RL8iT7t7NAaXCFIi24RR+7WGjTvKqWYjA=");
        key.setAPIKey("JB3F4vuTpICoutTTKyskKfTBRoNXtDKJO4qrNABHyPKYu8V4Wl4GtTHRB+Hwgq27H1QBm8fYmsXagH4O0KBjkF6WonZ+dTQrOQKVKkSOJ2nBkynOThCuETg0Mnjrs6vv9eRq/n6VaqUKRH+moetJDjDIObwE/LqZ+ykwus05CBXY2feBnx6gKeimKSBPTA8TfnerhJDrMb1IttqheqodC7o7+cqyDJSeYEzNaL5UfsgOGaQCoq8QXpTwomIqdRQdWQu6phgtAR7T4dLnpptAY6iSj1z3jsbhS447p2GypAYGbVPPb8g63/JpPCZzY0w1TmZyUL0AHjQZQedjoigHbI7+VKiLclL8wZxrXTMiEDJeWB0lQnTADpIVwpmHvde0UJmX67pS6+WNzURrJvjUBNX7zwR1ci7GhiAO9RNxwn8LHQfW+506LxVxW59MlH/Gt92nde/+y66fsOVbGn38w6xY6RcsB45aezdwlYVXZq1HCBeVwS1cXozZivtWsUOskP5KlKtCfU14SvexN9KmZMjU2XCgYZfHrDBzB7WZX3M1WqZH3NMu1KOpe1dxxgz1C/i4wdLPNCn8DgSOImsOxiYxk7bjy1Smvr//3m1sQaqp4WpkKjQsnPy6i1Vm9TpBLshlBe1c0JJ7lD6OW+FlpwYADLe2PXxu7a+KbmIM84I=\n");
        permissionsRequest();

    }

    // Requests app permissions
    public void permissionsRequest() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.INTERNET, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA
                    }, 111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ) {

                } else {
                    permissionsNotSelected();
                }
            }
        }
    }

    private void permissionsNotSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setTitle("Permissions Requred");
        builder.setMessage("Please enable the requested permissions in the app settings in order to use this demo app");
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                System.exit(1);
            }
        });
        AlertDialog noInternet = builder.create();
        noInternet.show();
    }

//    public void startARActivity(View view) {
    public void startARActivity() {
        Intent intent = new Intent(Profil_Gestion.this, ARCameraViewActivity.class);
        startActivity(intent);
    }


    private void loadNavHeader(){
        // TODO : Load profil information get by call api
    }

    private void loadHomeFragment(){
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                ft.replace(R.id.frame, fragment, CURRENT_TAG);
                ft.commitAllowingStateLoss();
            }
        };

        if (runnable != null){
            mHandler.post(runnable);
        }

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment(){
        switch (navItemIndex){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                ARFragment arFragment = new ARFragment();
                return arFragment;
            case 2:
                ProfilFragment profilFragment= new ProfilFragment();
                return profilFragment;
            case 3:
                LibFragment libFragment = new LibFragment();
                return libFragment;
            case 4:
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle(){
        getSupportActionBar().setTitle(activity_title[navItemIndex]);
    }

    private void selectNavMenu(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_ar:
                        startARActivity();
//                        navItemIndex = 1;
//                        CURRENT_TAG = TAG_AR;
                        break;
                    case R.id.nav_profil:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_PROFIL;
                        break;
                    case R.id.nav_lib:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_LIB;
                        break;
                    case R.id.nav_set:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SET;
                        break;
                    default:
                        navItemIndex = 0;
                }
                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (loadHomeFragmentOnBackPress){
            if (navItemIndex != 0){
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profil__gestion, menu);
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
}
