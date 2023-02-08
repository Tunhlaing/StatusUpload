package com.example.statusupload.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.statusupload.MyDatabase;
import com.example.statusupload.R;
import com.example.statusupload.util.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class EditActivity extends AppCompatActivity {
    TextInputEditText etStatusUpdate;
    Button btStatusUpdate;
    MyDatabase myDatabase;
    int statusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        myDatabase = new MyDatabase(this);
        initView();
        handleClick();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Status");
        actionBar.setDisplayHomeAsUpEnabled(true);

        statusId = getIntent().getIntExtra("statusId", 0);
        String status = getIntent().getStringExtra("status");
        etStatusUpdate.setText(status);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    private void initView() {
        etStatusUpdate = findViewById(R.id.etStatusUpdate);

        btStatusUpdate = findViewById(R.id.btStatusUpdate);

    }

    private void handleClick() {
        btStatusUpdate.setOnClickListener(v -> {
            if (myDatabase.updateStatus(statusId, etStatusUpdate.getText().toString())) {
                Utils.showToast(this, "Status Updated");
                finish();
            } else {
                Utils.showToast(this, "Somethings Is Wrong");
            }

        });
    }
}