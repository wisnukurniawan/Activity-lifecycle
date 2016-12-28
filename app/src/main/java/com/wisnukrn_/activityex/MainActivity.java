package com.wisnukrn_.activityex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static com.wisnukrn_.activityex.Constans.DEFAULT_STRING;
import static com.wisnukrn_.activityex.Constans.PREF_FILE;

/**
 * Created by wisnu on 26/12/2016.
 */

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;

    private EditText komentarField;

    private static final String TAG = MainActivity.class.getSimpleName();

    MenuItem mMenuItem;
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        logData("onCreate Called");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main);

        setNavListener(navigationView);
        addNavListener(toolbar, navigationView);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
//        loadString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        } else {
            finish();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
        showHomeFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();

        logData("onStart Called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        logData("onResume Called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        logData("onPause Called");
//        saveString(komentar);
    }

    @Override
    protected void onStop() {
        super.onStop();

        logData("onStop Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        logData("onRestart Called");
    }

    public int getStatusBarHeight() {
        final int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private void addNavListener(final Toolbar toolbar, final NavigationView navigationView) {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (null != mMenuItem){
                    switchNavigationMenu(mMenuItem.getItemId());
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (null != drawerView && drawerView == navigationView){
                    super.onDrawerSlide(drawerView, 0);
                } else {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }
        };
    }

    private void setNavListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mMenuItem = item;
                item.setCheckable(true);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    private void switchNavigationMenu(int itemId) {
        switch (itemId){
            case R.id.menu_home: {
                showHomeFragment();
                break;
            }
            case R.id.menu_detail:{
                showDetailFragment();
                break;
            }
        }
    }

    private void showHomeFragment() {
        final String tags = HomeFragment.class.getSimpleName();

        if (getSupportFragmentManager().findFragmentByTag(tags) == null){
            final Fragment fragment = new HomeFragment();
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_main, fragment, tags);
            fragmentTransaction.commit();
        }
    }

    private void showDetailFragment() {
        final String tags = DetailFragment.class.getSimpleName();

        if (getSupportFragmentManager().findFragmentByTag(tags) == null) {
            final Fragment fragment = new DetailFragment();
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_main, fragment, tags);
            fragmentTransaction.commit();
        }
    }

    void logData(String data){
//        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
        Log.d(TAG, data);
    }

    public void saveString(String value) {
        mSharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("Data", value);
        editor.apply();
    }

    public void loadString() {
        mSharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String savedValue = mSharedPreferences.getString("Data", DEFAULT_STRING);
        if (!savedValue.equals(DEFAULT_STRING)){
            komentarField.setText(savedValue);
            komentarField.setSelection(komentarField.getText().length());
        }else {
            //Toast.makeText(getApplicationContext(), "Nilai KOSONG", Toast.LENGTH_LONG).show();
        }
    }
}
