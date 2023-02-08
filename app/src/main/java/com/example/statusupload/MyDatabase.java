package com.example.statusupload;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.statusupload.model.StatusModel;
import com.example.statusupload.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_Name = "myDB.db";
    public static final int DB_VERSION = 1;

    public final String TBL_USER = "tbl_user";
    public final String TBL_STATUS = "tbl_status";

    public MyDatabase(@Nullable Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE table " + TBL_USER + "(user_id INTEGER PRIMARY KEY AUtOINCREMENT, user_name TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE table " + TBL_STATUS + "(status_id INTEGER PRIMARY KEY AUtOINCREMENT, user_id TEXT, status TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUser(String userName, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_name", userName);
        cv.put("password", password);

        try {
            db.insert(TBL_USER, null, cv);
            db.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            db.close();
            return false;
        }

    }

    public boolean isUserExist(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cv = db.rawQuery(" SELECT * FROM " + TBL_USER + " WHERE user_name = ? ", new String[]{userName});

        return cv.getCount() > 0;
    }

    @SuppressLint("Range")
    public UserModel loginUser(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TBL_USER + " WHERE user_name = ? AND password = ? ", new String[]{userName, password});
        if (c.moveToFirst()) {
            int userId = c.getInt(c.getColumnIndex("user_id"));
            String uName = c.getString(c.getColumnIndex("user_name"));
            String pwd = c.getString(c.getColumnIndex("password"));
            c.close();
            return new UserModel(userId, uName, pwd);
        } else {
            return null;

        }
    }

    public boolean insertStatus(int userid, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userid);
        cv.put("status", status);

        try {
            db.insert(TBL_STATUS, null, cv);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
            return false;
        }

    }

    @SuppressLint("Range")
    public List<StatusModel> getAllStatus(int filterStatus, int userId) {
        List<StatusModel> statusModelList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "";
        if (filterStatus == 1) {
            query = "SELECT u.user_name, s.status_id, s.status From tbl_user u INNER JOIN tbl_status s ON u.user_id = s.user_id WHERE u.user_id="+userId;
        } else {
            query = "SELECT u.user_name, s.status_id, s.status From tbl_user u INNER JOIN tbl_status s ON u.user_id = s.user_id";

        }
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int statusId = cursor.getInt(cursor.getColumnIndex("status_id"));
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                StatusModel statusModel = new StatusModel(statusId, userName, status);
                statusModelList.add(statusModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return statusModelList;
    }

    public boolean deleteStatus(int statusId) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete(TBL_STATUS, "status_id=" + statusId, null);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }

    public boolean updateStatus(int statusId, String newStatus) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", newStatus);
        try {
            db.update(TBL_STATUS, cv, "status_id=" + statusId, null);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
            return false;
        }
    }
}
