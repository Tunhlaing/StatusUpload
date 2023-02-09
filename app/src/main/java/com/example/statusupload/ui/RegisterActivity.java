package com.example.statusupload.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.example.statusupload.MyDatabase;
import com.example.statusupload.R;
import com.example.statusupload.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    MyDatabase myDatabase;

    TextInputLayout tiUserName, tiPassword, tiCPassword;
    TextInputEditText etUserName, etPassword, etCPassword;
    Button btRegister,btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDatabase = new MyDatabase(this);
        initView();
        handleClick();

    }

    private void initView() {
        tiUserName = findViewById(R.id.tiUserName);
        tiPassword = findViewById(R.id.tiPassword);
        tiCPassword = findViewById(R.id.tiCPassword);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etCPassword);

        btRegister = findViewById(R.id.btRegister);
        btLogin = findViewById(R.id.btLogin);
    }

    private void handleClick() {
        btRegister.setOnClickListener(view -> {
            if (checkValidations()) {
               // if (!(myDatabase.isUserExist(etUserName.getText().toString()))) {
                    myDatabase.insertUser(etUserName.getText().toString(), etPassword.getText().toString());
                    Utils.showToast(this, "Register successfully !!!");
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();

                } else {
                    Utils.showToast(this, "This username already exist !!!");
                }
    //        }
        });
        btLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private boolean checkValidations() {
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            tiUserName.setError("Enter Username");
            return false;

        } else if (myDatabase.isUserExist(etUserName.getText().toString())) {
            tiUserName.setError(null);
            tiUserName.setError("Username is already exits");
            return false;

        }   else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            tiUserName.setError(null);
            tiPassword.setError("Enter Password");
            return false;

        }else if (etPassword.getText().length()<6) {
            tiUserName.setError(null);
            tiPassword.setError("Password Must be Greater Than 6");
            return false;
        }else if (TextUtils.isEmpty(etCPassword.getText().toString())) {
            tiUserName.setError(null);
            tiPassword.setError(null);
            tiCPassword.setError("Enter Confirm Password");
            return false;

        } else if (!etPassword.getText().toString().equals(etCPassword.getText().toString())) {
            tiUserName.setError(null);
            tiPassword.setError(null);
            tiCPassword.setError("Password Don't Match");
            return false;

        }else {
            return true;
        }
    }







}