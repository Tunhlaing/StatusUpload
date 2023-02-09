package com.example.statusupload.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.statusupload.MyDatabase;
import com.example.statusupload.R;
import com.example.statusupload.adapter.statusAdapter;
import com.example.statusupload.model.StatusModel;
import com.example.statusupload.util.SharePrefs;
import com.example.statusupload.util.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    MyDatabase myDatabase;
    TextInputEditText etStatus;
    Button btUpload;
    RecyclerView rvStatus;
    boolean isFilter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myDatabase = new MyDatabase(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        Utils.showToast(this, "Welcome " + (SharePrefs.getStringPref(this, "userName")));
        initView();
        handleClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter(myDatabase.getAllStatus(0, SharePrefs.getIntPref(this, "userId")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showDialog("Are You Sure, You Want To Exit?", false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_filter: {
                if (!isFilter) {
                    isFilter = true;
                    item.setIcon(R.drawable.baseline_filter_list_off_24);
                    setAdapter(myDatabase.getAllStatus(1, SharePrefs.getIntPref(this, "userId")));

                } else {
                    isFilter = false;
                    item.setIcon(R.drawable.baseline_filter_alt_24);
                    setAdapter(myDatabase.getAllStatus(0, SharePrefs.getIntPref(this, "userId")));
                }

                break;
            }
            case R.id.nav_logout: {
                showDialog("Are You Sure To Logout", true);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        btUpload = findViewById(R.id.btUpload);

        etStatus = findViewById(R.id.etStatus);

        rvStatus = findViewById(R.id.rvStatus);
    }

    private void handleClick() {
        btUpload.setOnClickListener(v -> {
            if (myDatabase.insertStatus(SharePrefs.getIntPref(this, "userId"), etStatus.getText().toString())) {
                Utils.showToast(this, "Status uploaded");
                setAdapter(myDatabase.getAllStatus(0, SharePrefs.getIntPref(this, "userId")));
                etStatus.setText("");

            } else {
                Utils.showToast(this, "Something went wrong");
            }
        });
    }


    private void showDialog(String message, boolean isLogout) {
        new AlertDialog.Builder(this).setMessage(message).setCancelable(false).setPositiveButton("yes", (dialog, which) -> {
            if (isLogout) {
                SharePrefs.clearPref(HomeActivity.this);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.clear();
//                    editor.apply();

                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            } else {
                HomeActivity.super.onBackPressed();
            }
        }).setNegativeButton("No", null).show();
    }

    private void setAdapter(List<StatusModel> statusModelList) {
        statusAdapter adapter = new statusAdapter(this, myDatabase, statusModelList);
        //rvStatus.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rvStatus.setAdapter(adapter);
    }
}
