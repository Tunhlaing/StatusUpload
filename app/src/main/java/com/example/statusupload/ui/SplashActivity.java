package com.example.statusupload.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.statusupload.R;
import com.example.statusupload.util.SharePrefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (SharePrefs.getBooleanPref(this, "isLoggedIn")) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            startActivity((new Intent(this, RegisterActivity.class)));
            finish();
        }
    }
}