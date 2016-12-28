package com.wisnukrn_.activityex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import static com.wisnukrn_.activityex.Constans.DEFAULT_STRING;
import static com.wisnukrn_.activityex.Constans.PREF_FILE;

/**
 * Created by wisnu on 26/12/2016.
 */

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;

    private EditText komentarField;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        logData("onCreate Called");

        komentarField = (EditText) findViewById(R.id.komentar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(toolbar);

        loadString();
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
        String komentar = komentarField.getText().toString();
        saveString(komentar);
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
    
    void logData(String data){
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
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
