package com.example.statusupload.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.example.statusupload.MyDatabase;
import com.example.statusupload.R;
import com.example.statusupload.model.UserModel;
import com.example.statusupload.util.SharePrefs;
import com.example.statusupload.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    MyDatabase myDatabase;

    TextInputLayout tiUserName, tiPassword;
    TextInputEditText etUserName, etPassword;

    Button btRegister, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDatabase = new MyDatabase(this);
        initView();
        handleClick();
    }

    private void initView() {
        tiUserName = findViewById(R.id.tiUserName);
        tiPassword = findViewById(R.id.tiPassword);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        btRegister = findViewById(R.id.btRegister);
        btLogin = findViewById(R.id.btLogin);
    }

    private void handleClick() {
        btRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
        btLogin.setOnClickListener(view -> {
            if (checkValidations()) {
                UserModel userModel = myDatabase.loginUser(etUserName.getText().toString(), etPassword.getText().toString());
                if (userModel != null) {

                    SharePrefs.setBooleanPref(this, "isLoggedIn", true);
                    SharePrefs.setStringPref(this, "userName", userModel.getUserName());
                    SharePrefs.setIntPref(this, "userId", userModel.getUserId());

                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                } else if (!(myDatabase.isUserExist(etUserName.getText().toString()))) {
                    Utils.showToast(this,"Please Register First");
                } else {
                    Utils.showToast(this, "Username Or Password Incorrect");
                }
            }
        });
    }

    private boolean checkValidations() {
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            tiUserName.setError("Enter Username");
            return false;

        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            tiUserName.setError(null);
            tiPassword.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

}